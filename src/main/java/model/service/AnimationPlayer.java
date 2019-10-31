package model.service;

import java.util.concurrent.BlockingQueue;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnimationPlayer implements Runnable {

  private static final Logger logger = LogManager.getLogger(AnimationPlayer.class);
  private BlockingQueue<TranslateTransition> queue;
  private volatile boolean stop = false;

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
        System.out.println("Gotya!");
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
