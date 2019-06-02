package model.service;

import animatefx.animation.AnimationFX;
import animatefx.animation.Shake;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.service.TransitionGenerator.XorY;

public enum Animator {

  SHOW_OPTIONS(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionGenerator.generateFadeTransition(1, 1))
  ), HIDE_OPTIONS(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 75),
          TransitionGenerator.generateFadeTransition(0.5, 0))
  ), SHOW_UPLOAD(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionGenerator.generateFadeTransition(1, 1))
  ), HIDE_UPLOAD(
      TransitionGenerator.generaPauseTransition(0.1),
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 75),
          TransitionGenerator.generateFadeTransition(1, 0))
  ), SHOW_BROWSE(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionGenerator.generateFadeTransition(1, 1))
  ), HIDE_BROWSE(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 75),
          TransitionGenerator.generateFadeTransition(1, 0))
  ), SHOW_GO(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.X, 0, 0),
          TransitionGenerator.generateFadeTransition(1, 1))
  ), HIDE_GO(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.X, 500, 0),
          TransitionGenerator.generateFadeTransition(0.5, 0))
  ), SHOW_MOVES(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionGenerator.generateFadeTransition(1, 1))
  ), HIDE_MOVES(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 800, 0),
          TransitionGenerator.generateFadeTransition(0.5, 0))
  ), SHOW_PEEK(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionGenerator.generateFadeTransition(1, 1)
      )
  ), HIDE_PEEK(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 800, 0),
          TransitionGenerator.generateFadeTransition(0.5, 0))
  ), SHOW_PEEKBORDER(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionGenerator.generateFadeTransition(1, 1)
      )
  ), HIDE_PEEKBORDER(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 800, 0),
          TransitionGenerator.generateFadeTransition(0.5, 0)
      )
  ), SHOW_BOARD(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionGenerator.generateFadeTransition(1, 1))
  ), HIDE_BOARD(
      TransitionGenerator.generateFadeTransition(0.2, 0)
  ), SHOW_SHUFFLEBUTTONPANE(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.5, XorY.X, 0, 0),
          TransitionGenerator.generateFadeTransition(1, 1))
  ), HIDE_SHUFFLEBUTTONPANE(
      TransitionGenerator.generateParallelTransition(
          TransitionGenerator.generateTranslateTransition(0.2, XorY.X, -210, 0),
          TransitionGenerator.generateFadeTransition(0.2, 0))
  ), NUDGE(
      new Shake()
  ), SHOW_SHUFFLEPANE(TransitionGenerator.generateParallelTransition(
      TransitionGenerator.generateTranslateTransition(0.5, XorY.X, 0, 0),
      TransitionGenerator.generateFadeTransition(1, 1))), HIDE_SHUFFLEPANE(TransitionGenerator.generateParallelTransition(
      TransitionGenerator.generateTranslateTransition(0.2, XorY.X, -210, 0),
      TransitionGenerator.generateFadeTransition(0.2, 0)));
  AnimationUnifier animation;


  private Animator(Transition transition) {
    this.animation = new AnimationUnifier(transition);
  }

  private Animator(AnimationFX animation) {
    this.animation = new AnimationUnifier(animation);
  }

  private Animator(ParallelTransition parallelTransition) {
    this.animation = new AnimationUnifier(parallelTransition);
  }

  private Animator(Transition ... transitions) {
    this.animation = new AnimationUnifier(TransitionGenerator.generateSequentialTransition(transitions));
  }

  public AnimationUnifier getAnimation() {
    return animation;
  }

}
