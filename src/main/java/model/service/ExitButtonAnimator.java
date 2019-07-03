package model.service;

import javafx.scene.Node;

public class ExitButtonAnimator extends NodeAnimator {

  private Animator initialToStandBy, standByToInitial, standByToInGame, inGameToStandby;

  private ButtonLocation position;

  public ExitButtonAnimator(Node node, Animator initialToStandBy, Animator standByToInitial,
      Animator standByToInGame, Animator inGameToStandby) {
    super(node);
    this.position = ButtonLocation.INITIAL;
    this.initialToStandBy = initialToStandBy;
    this.standByToInitial = standByToInitial;
    this.standByToInGame = standByToInGame;
    this.inGameToStandby = inGameToStandby;
    initializeAnimations(initialToStandBy, standByToInitial, standByToInGame, inGameToStandby);
  }

  @Override
  public void show() {
    switch (position) {
      case INITIAL: {
        initialToStandBy.getAnimation().play();
//        inGameToStandby.getAnimation().play();

        position = ButtonLocation.STANDBY;
        break;
      }
      case STANDBY: {
        standByToInGame.getAnimation().play();
        position = ButtonLocation.IN_GAME;
      }
    }
  }

  @Override
  public void hide() {
    switch (position) {
      case STANDBY: {
        standByToInitial.getAnimation().play();
        position = ButtonLocation.INITIAL;
        break;
      }
      case IN_GAME: {
        inGameToStandby.getAnimation().play();
        position = ButtonLocation.STANDBY;
        break;
      }
    }
  }

  enum ButtonLocation {
    INITIAL, STANDBY, IN_GAME
  }
}
