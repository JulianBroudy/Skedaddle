package model.tiles;

import java.util.ArrayList;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import model.service.TransitionsGenerator;
import model.service.TransitionsGenerator.XorY;

public class FXTileSwapper implements Runnable {

  private static Object aLock = new Object();
  private static ArrayList<Integer> order = new ArrayList<>();
  private static Integer counter = 0;
  private FXTile thisTile, thatTile;
  private Integer myNumber;


  public FXTileSwapper(FXTile thisTile, FXTile thatTile) {
    this.thisTile = thisTile;
    this.thatTile = thatTile;
    myNumber = counter++;
  }

  @Override
  public void run() {
    System.out.println(myNumber + " - I'm in run bro!");
    order.add(myNumber);
    order.sort(Integer::compareTo);
    synchronized (aLock) {

      System.out.println(myNumber + " - I'm in synchronized bro!");
      while (!order.get(0).equals(myNumber)) {
        try {
          System.out.println(myNumber + " - I'm gonna wait bro!");
          aLock.wait();
        } catch (InterruptedException e) {
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
            synchronized (aLock) {
              order.remove(myNumber);
              order.sort(Integer::compareTo);
              System.out.println(myNumber + " - I'm done bro!");
              thisTile.isInRightPosition();
              aLock.notifyAll();
            }
          });
      parallelTransition.play();
    }
  }

}
