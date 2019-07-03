package model.service;

import animatefx.animation.AnimationFX;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class AnimationUnifier {

  AnimationFX animationFX;
  Transition transition;
  ParallelTransition parallelTransition;
  SequentialTransition sequentialTransition;
  private AnimationType type;

  public AnimationUnifier(AnimationFX animationFX) {
    this.animationFX = animationFX;
    type = AnimationType.ANIMATION;
  }


  public AnimationUnifier(ParallelTransition parallelTransition) {
    this.parallelTransition = parallelTransition;
    type = AnimationType.PARALLEL;
  }

  public AnimationUnifier(SequentialTransition sequentialTransition) {
    this.sequentialTransition = sequentialTransition;
    type = AnimationType.SEQUENTIAL;
  }

  public AnimationUnifier(Transition transition) {
    this.transition = transition;
    this.type = AnimationType.ONE_TRANSITION;
  }

  public void play() {
    switch (type) {
      case ANIMATION: {
        animationFX.play();
        break;
      }
      case PARALLEL: {
        parallelTransition.play();
        break;
      }
      case SEQUENTIAL: {
        sequentialTransition.play();
        break;
      }
      default: {
        transition.play();
      }
    }
  }

  public void stop() {
    switch (type) {
      case ANIMATION: {
        animationFX.stop();
        break;
      }
      case PARALLEL: {
        parallelTransition.stop();
        break;
      }
      case SEQUENTIAL: {
        sequentialTransition.stop();
        break;
      }
      default: {
        transition.stop();
      }
    }
  }

  public Animation.Status getStatus() {
    Animation.Status animationsStatus;
    switch (type) {
      case ANIMATION: {
        throw new UnsupportedOperationException("AnimationFX does not support a getStatus method!");
      }
      case PARALLEL: {
        animationsStatus = parallelTransition.getStatus();
        break;
      }
      case SEQUENTIAL: {
        animationsStatus = sequentialTransition.getStatus();
        break;
      }
      default: {
        animationsStatus = transition.getStatus();
      }
    }
    return animationsStatus;
  }

  public void setNode(Node node) {
    switch (type) {
      case ANIMATION: {
        animationFX.setNode(node);
        break;
      }
      case PARALLEL: {
        parallelTransition.setNode(node);
        break;
      }
      case SEQUENTIAL: {
        sequentialTransition.setNode(node);
        break;
      }
    }
  }

  protected void setOnFinished(EventHandler eventHandler) {
    switch (type) {
      case ANIMATION: {
        animationFX.setOnFinished(eventHandler);
        break;
      }
      case PARALLEL: {
        parallelTransition.setOnFinished(eventHandler);
        break;
      }
      case SEQUENTIAL: {
        sequentialTransition.setOnFinished(eventHandler);
        break;
      }
    }
  }

}
