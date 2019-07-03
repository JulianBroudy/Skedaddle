package model.tiles;

import java.util.ArrayList;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import model.service.TransitionsGenerator;
import model.service.TransitionsGenerator.XorY;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FXTileSwapper implements Runnable {

  private final static Logger logger = LogManager.getLogger(FXTileSwapper.class);

  private static Object aLock = new Object();
  private static ArrayList<Integer> order = new ArrayList<>();
  private static Integer counter = 0;
  private FXTile thisTile, thatTile;
  private Integer myNumber;


  public FXTileSwapper(FXTile thisTile, FXTile thatTile) {
    logger.traceEntry(thisTile.getID());
    this.thisTile = thisTile;
    this.thatTile = thatTile;
    myNumber = counter++;
  }

  @Override
  public void run() {
    logger.traceEntry(myNumber.toString());
    order.add(myNumber);
    order.sort(Integer::compareTo);
    synchronized (aLock) {
      logger.traceEntry(myNumber + "in synchronized.");
      while (!order.get(0).equals(myNumber)) {
        try {
          logger.traceEntry(myNumber + "waiting...");
          aLock.wait();
        } catch (InterruptedException e) {
          logger.error(e);
          e.printStackTrace();
        }
      }

      TranslateTransition swapThisTileWithThatTile = TransitionsGenerator
          .generateTranslateTransition(0.4, XorY.BOTH, thatTile.getTranslateX(),
              thatTile.getTranslateY());
      swapThisTileWithThatTile.setNode(thisTile);

      TranslateTransition swapThatTileWithThisTile = TransitionsGenerator
          .generateTranslateTransition(0.4, XorY.BOTH, thisTile.getTranslateX(),
              thisTile.getTranslateY());
      swapThatTileWithThisTile.setNode(thatTile);

      ParallelTransition parallelTransition = TransitionsGenerator
          .generateParallelTransition(swapThisTileWithThatTile, swapThatTileWithThisTile);
      parallelTransition.setOnFinished(
          handler -> {
            logger.traceEntry();
            synchronized (aLock) {
              logger.traceEntry("myID:" + Thread.currentThread().getId() + myNumber
                  + " in synchronized of OnFinished handler.");
              order.remove(myNumber);
              order.sort(Integer::compareTo);
              thisTile.isInRightPosition();
              logger.traceEntry(myNumber + " handled OnFinished, notifying others.");
              aLock.notifyAll();
            }
          });
      parallelTransition.play();
    }
  }

}
