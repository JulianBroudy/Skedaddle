package controller;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
  private Task<ArrayList<FXTile>> tilesOrderTask;
  private Task<Void> boardInitializingTask;
  private Runnable orderTiles, initializeGame;

  private GameModel gameModel;
  private FXTilesBuilding tilesBuilder;
  private ArrayList<FXTile> tiles;
  private MainUIController mainUIController = null;

  private GamePlayManager() {
    tilesBuilder = new FXTilesBuilding();
    initializeListeners();
    initializeTasks();
  }

  public static GamePlayManager getInstance() {
    return onlyInstance;
  }

  private void initializeTasks() {

    // Service<ArrayList<FXTile>>

    /*tilesOrderTask = new Task<>() {
      @Override
      protected ArrayList<FXTile> call() {
        return (ArrayList<FXTile>) tilesBuilder
            .orderTiles(GameState.getGridSize(), TileClassification.SOLID);
      }
    };
    tilesOrderTask.setOnSucceeded(event -> {
      tiles = (ArrayList<FXTile>) event.getSource().getValue();
    });

    boardInitializingTask = new Task<Void>() {
      @Override
      protected Void call() {
        gameModel = new GameModel(GameState.getGridSize(), tiles);
        return null;
      }
    };*/

  }

  public void startNewGame() {
    tilesBuilder.setTilesShape(TileShape.SQUARE);

    tiles = (ArrayList<FXTile>) tilesBuilder
        .orderTiles(GameState.getGridSize(), TileClassification.SOLID);

    gameModel = new GameModel(GameState.getGridSize(), tiles);

    // new Thread(tilesOrderTask).start();
    // new Thread(boardInitializingTask).start();

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
      mainUIController.loadSolution((ArrayList<FXTile>) tilesBuilder
          .orderTiles(GameState.getGridSize(), TileClassification.SOLID));
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
    this.mainUIController = mainUIController;
  }

}

