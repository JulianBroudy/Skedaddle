package model.service;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class TransitionsGenerator {

  public static TranslateTransition generateTranslateTransition(double seconds, XorY xORy,
      double toX,
      double toY) {
    TranslateTransition transition = new TranslateTransition(Duration.seconds(seconds));
    switch (xORy) {
      case X: {
        transition.setToX(toX);
        break;
      }
      case Y: {
        transition.setToY(toY);
        break;
      }
      case BOTH: {
        transition.setToX(toX);
        transition.setToY(toY);
        break;
      }
    }
    return transition;
  }

  public static TranslateTransition generateTranslateTransition(double seconds, XorY xORy,
      double toX,
      double toY, EventHandler eventHandler) {
    TranslateTransition transition = TransitionsGenerator
        .generateTranslateTransition(seconds, xORy, toX, toY, eventHandler);
    transition.setOnFinished(eventHandler);
    return transition;
  }

  public static TranslateTransition generateTranslateTransition(double seconds, double fromX,
      double fromY, double toX, double toY) {
    TranslateTransition transition = TransitionsGenerator
        .generateTranslateTransition(seconds, XorY.BOTH, toX, toY);
    transition.setFromX(fromX);
    transition.setFromY(fromY);
    return transition;
  }

  public static TranslateTransition generateTranslateTransition(double seconds, double fromX,
      double fromY, double toX, double toY, EventHandler eventHandler) {
    TranslateTransition transition = TransitionsGenerator
        .generateTranslateTransition(seconds, XorY.BOTH, toX, toY);
    transition.setOnFinished(eventHandler);
    return transition;
  }

  static FadeTransition generateFadeTransition(double seconds, double opacityTo) {
    FadeTransition transition = new FadeTransition(Duration.seconds(seconds));
    transition.setToValue(opacityTo);
    return transition;
  }

  static FadeTransition generateFadeTransition(double seconds, double opacityTo,
      EventHandler eventHandler) {
    FadeTransition transition = TransitionsGenerator.generateFadeTransition(seconds, opacityTo);
    transition.setOnFinished(eventHandler);
    return transition;
  }

  public static ParallelTransition generateParallelTransition(EventHandler handler,
      Transition... transitions) {
    ParallelTransition parallelTransition = new ParallelTransition(transitions);
    parallelTransition.setOnFinished(handler);
    return parallelTransition;
  }

  public static ParallelTransition generateParallelTransition(Transition... transitions) {
    return new ParallelTransition(transitions);
  }

  public static SequentialTransition generateSequentialTransition(Transition... transitions) {
    return new SequentialTransition(transitions);
  }

  public static PauseTransition generaPauseTransition(double seconds) {
    return new PauseTransition(Duration.seconds(seconds));
  }

  public static PauseTransition generaPauseTransition(double seconds, EventHandler eventHandler) {
    PauseTransition pauseTransition = generaPauseTransition(seconds);
    pauseTransition.setOnFinished(eventHandler);
    return pauseTransition;
  }


  public enum XorY {
    X, Y, BOTH
  }


}
