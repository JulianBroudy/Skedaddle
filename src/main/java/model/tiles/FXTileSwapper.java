// package model.tiles;
//
// import animatefx.animation.RubberBand;
// import java.util.ArrayList;
// import javafx.animation.ParallelTransition;
// import javafx.animation.TranslateTransition;
// import model.service.TransitionsGenerator;
// import model.service.TransitionsGenerator.XorY;
// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
//
// public class FXTileSwapper implements Runnable {
//
//   private final static Logger logger = LogManager.getLogger(FXTileSwapper.class);
//
//   private static Object aLock = new Object();
//   private static ArrayList<Integer> order = new ArrayList<>();
//   private static Integer counter = 0;
//   private FXTile thisTile, thatTile;
//   private Integer myNumber;
//
//
//   public FXTileSwapper(FXTile thisTile, FXTile thatTile) {
//     myNumber = counter++;
//     logger.traceEntry(
//         "ID: " + myNumber + " - swapping tile number " + thisTile.getID() + " with tile number "
//             + thatTile.getID());
//     this.thisTile = thisTile;
//     this.thatTile = thatTile;
//   }
//
//   @Override
//   public void run() {
//     synchronized (aLock) {
//       order.add(myNumber);
//       order.sort(Integer::compareTo);
//       while (!order.get(0).equals(myNumber)) {
//         logger.traceEntry("ID: " + myNumber + " - I'm gonna wait.");
//         try {
//           aLock.wait();
//         } catch (InterruptedException e) {
//           logger.error(e);
//           e.printStackTrace();
//         }
//       }
//       logger.traceEntry("ID: " + myNumber + " - I'm in! creating transitions...");
//       TranslateTransition swapThisTileWithThatTile = TransitionsGenerator
//           .generateTranslateTransition(0.4, XorY.BOTH, thatTile.getTranslateX(),
//               thatTile.getTranslateY());
//       swapThisTileWithThatTile.setNode(thisTile);
//
//       TranslateTransition swapThatTileWithThisTile = TransitionsGenerator
//           .generateTranslateTransition(0.4, XorY.BOTH, thisTile.getTranslateX(),
//               thisTile.getTranslateY());
//       swapThatTileWithThisTile.setNode(thatTile);
//
//       ParallelTransition parallelTransition = TransitionsGenerator
//           .generateParallelTransition(swapThisTileWithThatTile, swapThatTileWithThisTile);
//       parallelTransition.setOnFinished(
//           handler -> {
//             synchronized (aLock) {
//               logger.traceEntry("ID: " + myNumber + " - the animation just finished.");
//               thisTile.getCoordinates().swapWith(thatTile.getCoordinates());
//               if (thisTile.isInRightPosition()) {
//                 RubberBand rightPositionEffect = new RubberBand(thisTile);
//                 rightPositionEffect.setOnFinished(handle -> thisTile.refreshStyle());
//                 rightPositionEffect.play();
//               } else {
//                 thisTile.refreshStyle();
//               }
//               order.remove(myNumber);
//               order.sort(Integer::compareTo);
//               logger.traceEntry(
//                   "ID: " + myNumber + " - removed myself from queue, notifying others...");
//               aLock.notifyAll();
//             }
//           });
//       parallelTransition.play();
//     }
//   }
//
// }
