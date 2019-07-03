package controller;

import java.util.ArrayList;
import javafx.application.Platform;
import model.GameModel;
import model.service.AnimationUnifier;
import model.service.Animator;
import model.service.builders.FXTilesBuilding;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory.TileClassification;
import model.tiles.FXTile;
import view.MainUIController;

public class GamePlayManager {

  private static GamePlayManager onlyInstance = new GamePlayManager();
  private static GameModel gameModel;
  private static FXTilesBuilding tilesBuilder;
  private static ArrayList<FXTile> tiles;
  private static MainUIController mainUIController = null;

  private GamePlayManager() {
    tilesBuilder = new FXTilesBuilding();
    initializeListeners();

  }

  public static GamePlayManager getInstance() {
    return onlyInstance;
  }

  public static void startNewGame() {
    tilesBuilder.setTilesShape(TileShape.SQUARE);
    // int gridSize = GameState.getGridSize() == 0 ? 8 : GameState.getGridSize();
    tiles = (ArrayList<FXTile>) tilesBuilder
        .orderTiles(GameState.getGridSize(), TileClassification.SOLID);
    gameModel = new GameModel(GameState.getGridSize(), tiles);

    FXTile emptyTile = (FXTile) gameModel.getBlankTile();
    emptyTile.setVisible(false);
    AnimationUnifier nudge = Animator.JELLO.getAnimation();
    for (FXTile tile : tiles) {
      tile.setOnMouseClicked(click -> {
        if (gameModel.move(tile)) {
          if (gameModel.isDone()) {
            mainUIController.showWinnerScreen();
          }
        } else {
          nudge.setNode(tile);
          nudge.play();
        }
      });
    }
    Platform.runLater(() -> {
      // mainUIController.loadSolution(); TODO: generate solution tiles?!
      mainUIController.loadBoard(tiles);
    });
  }

  private void initializeListeners() {
    GameState.isActiveProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        startNewGame();
      }
    });
  }

  public void setMainUIController(MainUIController mainUIController) {
    GamePlayManager.mainUIController = mainUIController;
  }

}

