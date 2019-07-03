package model.service;

import animatefx.animation.AnimationFX;
import animatefx.animation.Jello;
import animatefx.animation.Shake;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import model.service.TransitionsGenerator.XorY;

public enum Animator {

  SHOW_OPTIONS(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionsGenerator.generateFadeTransition(1, 1))
  ), HIDE_OPTIONS(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 75),
          TransitionsGenerator.generateFadeTransition(0.5, 0))
  ), SHOW_UPLOAD(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionsGenerator.generateFadeTransition(1, 1))
  ), HIDE_UPLOAD(
      TransitionsGenerator.generaPauseTransition(0.1),
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.8, XorY.Y, 0, 75),
          TransitionsGenerator.generateFadeTransition(0.8, 0))
  ), SHOW_BROWSE(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionsGenerator.generateFadeTransition(1, 1))
  ), HIDE_BROWSE(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 75),
          TransitionsGenerator.generateFadeTransition(0.9, 0))
  ), SHOW_GO(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.X, 0, 0),
          TransitionsGenerator.generateFadeTransition(1, 1))
  ), HIDE_GO(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.X, 500, 0),
          TransitionsGenerator.generateFadeTransition(0.5, 0))
  ), SHOW_MOVES(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionsGenerator.generateFadeTransition(1, 1))
  ), HIDE_MOVES(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 800, 0),
          TransitionsGenerator.generateFadeTransition(0.5, 0))
  ), SHOW_PEEK(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionsGenerator.generateFadeTransition(1, 1)
      )
  ), HIDE_PEEK(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 800, 0),
          TransitionsGenerator.generateFadeTransition(0.5, 0))
  ), SHOW_PEEKBORDER(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionsGenerator.generateFadeTransition(1, 1)
      )
  ), HIDE_PEEKBORDER(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 800, 0),
          TransitionsGenerator.generateFadeTransition(0.5, 0)
      )
  ), SHOW_BOARD(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 0),
          TransitionsGenerator.generateFadeTransition(1, 1))
  ), HIDE_BOARD(
      TransitionsGenerator.generateFadeTransition(0.2, 0)
  ), SHOW_SHUFFLEBUTTONPANE(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.5, XorY.X, 0, 0),
          TransitionsGenerator.generateFadeTransition(1, 1))
  ), HIDE_SHUFFLEBUTTONPANE(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.2, XorY.X, -210, 0),
          TransitionsGenerator.generateFadeTransition(0.2, 0))
  ), EXIT_INITIAL_TO_STANDBY(
      TransitionsGenerator.generateTranslateTransition(0.5, XorY.X, 200, 0)
  ), EXIT_STANDBY_TO_INITIAL(
      TransitionsGenerator.generateTranslateTransition(0.5, XorY.X, 0, 0)
  ), EXIT_STANDBY_TO_INGAME(
      TransitionsGenerator.generateTranslateTransition(0.5, XorY.Y, 0, 470)
  ), EXIT_INGAME_TO_STANDBY(
      TransitionsGenerator.generateTranslateTransition(0.1, XorY.X, -210, 0),
      TransitionsGenerator.generateTranslateTransition(0.1, XorY.Y, 0, 220),
      TransitionsGenerator.generateTranslateTransition(0.1, XorY.X, 0, 0),
      TransitionsGenerator.generateTranslateTransition(0.1, XorY.Y, 0, 70),
      TransitionsGenerator.generateTranslateTransition(0.1, XorY.X, 200, 0),
      TransitionsGenerator.generateTranslateTransition(0.1, XorY.Y, 0, 0)
  ), NUDGE(
      new Shake()
  ), SHOW_SHUFFLEPANE(TransitionsGenerator.generateParallelTransition(
      TransitionsGenerator.generateTranslateTransition(0.5, XorY.X, 0, 0),
      TransitionsGenerator.generateFadeTransition(1, 1))), HIDE_SHUFFLEPANE(
      TransitionsGenerator.generateParallelTransition(
          TransitionsGenerator.generateTranslateTransition(0.2, XorY.X, -210, 0),
          TransitionsGenerator.generateFadeTransition(0.2, 0))), JELLO(new Jello());

  AnimationUnifier animation;

//  private Animator(Transition transition) {
//    this.animation = new AnimationUnifier(transition);
//  }

  Animator(AnimationFX animation) {
    this.animation = new AnimationUnifier(animation);
  }

  Animator(ParallelTransition parallelTransition) {
    this.animation = new AnimationUnifier(parallelTransition);
  }

  Animator(Transition... transitions) {
    this.animation = new AnimationUnifier(
        TransitionsGenerator.generateSequentialTransition(transitions));
  }

  public AnimationUnifier getAnimation() {
    return animation;
  }

}
