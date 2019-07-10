package model.service;

import java.util.concurrent.BlockingQueue;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnimationPlayer implements Runnable {

  private static Logger logger = LogManager.getLogger(AnimationPlayer.class);

  private volatile boolean stop = false;

  private BlockingQueue<TranslateTransition> queue;

  public AnimationPlayer(BlockingQueue<? extends Transition> queue) {
    this.queue = (BlockingQueue<TranslateTransition>) queue;
  }


  public void run() {
    while (!stop) {
      logger.traceEntry(Thread.currentThread().getName() + " entered while loop...");

      try {
        queue.take().play();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      // logger.trace(transition);
      // transition.play();

      logger.traceExit(Thread.currentThread().getName() + " exiting while loop...");
    }
  }

  public void stop() {
    stop = true;
  }

}
