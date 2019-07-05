package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.concurrent.Task;
import model.Coordinates;
import model.GameModel;
import model.GameModel.LegalMoves;
import model.service.AnimationUnifier;
import model.service.Animator;
import model.service.builders.FXTilesBuilding;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory.TileClassification;
import model.service.tasks.TilesMovingService;
import model.tiles.FXTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.MainUIController;

public class GamePlayManager {

  private final static Logger logger = LogManager.getLogger(GamePlayManager.class);

  private static GamePlayManager onlyInstance = new GamePlayManager();
  private Task<ArrayList<FXTile>> tilesOrderTask;
  private Task<Void> boardInitializingTask;
  private Runnable orderTiles, initializeGame;
  private TilesMovingService movingService;

  private GameModel gameModel;
  private FXTilesBuilding tilesBuilder;
  private ArrayList<FXTile> tiles;
  private MainUIController mainUIController = null;

  private GamePlayManager() {
    tilesBuilder = new FXTilesBuilding();
    movingService = new TilesMovingService();
    initializeListeners();
    Services();
  }

  public static GamePlayManager getInstance() {
    return onlyInstance;
  }

  private void Services() {

    movingService.setOnSucceeded(event -> {
      if ((boolean) event.getSource().getValue()) {
        logger.traceEntry("OnSucceeded handler");
        if (gameModel.isSolved()) {
          mainUIController.showWinnerScreen();
        }
      } else {
        AnimationUnifier nudge = Animator.JELLO.getAnimation();
        nudge.setNode(((FXTile) ((TilesMovingService) event.getSource()).getTile()));
        nudge.play();
      }
      logger.traceExit("OnSucceeded");
    });
  }

  public void startNewGame() {
    tilesBuilder.setTilesShape(TileShape.SQUARE);

    tiles = (ArrayList<FXTile>) tilesBuilder
        .orderTiles(GameState.getGridSize(), TileClassification.SOLID);

    gameModel = new GameModel(GameState.getGridSize(), tiles);
    TilesMovingService.setGameModel(gameModel);

    FXTile emptyTile = (FXTile) gameModel.getBlankTile();
    emptyTile.setVisible(false);

    for (FXTile tile : tiles) {
      tile.setOnMouseClicked(click -> {
        movingService.reset();
        movingService.setTile(tile);
        movingService.start();
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

  public void shuffleBoard(int numberOfShuffles) {
    Random randomizer = new Random(System.currentTimeMillis());
    List<FXTile> possibleMoves;
    for (int shuffle = 0; shuffle < numberOfShuffles; shuffle++) {
      possibleMoves = generatePossibleMoves();
      gameModel.move(possibleMoves.get(randomizer.nextInt(possibleMoves.size())));
    }
    if (gameModel.isSolved()) {
      shuffleBoard(numberOfShuffles);
    }
  }

  private List<FXTile> generatePossibleMoves() {
    FXTile emptyTile = (FXTile) gameModel.getBlankTile();
    List<Coordinates> currentPossibleMoves = new ArrayList<>();
    for (LegalMoves move : LegalMoves.values()) {
      int row = emptyTile.getCoordinates().getRow() + move.getRow();
      int col = emptyTile.getCoordinates().getCol() + move.getCol();
      if (row < GameState.getGridSize() && row >= 0 && col < GameState.getGridSize() && col >= 0) {
        currentPossibleMoves.add(new Coordinates(row, col));
      }
    }
    return tiles.stream().filter(tile -> currentPossibleMoves.contains(tile.getCoordinates()))
        .collect(Collectors.toList());
  }
}

