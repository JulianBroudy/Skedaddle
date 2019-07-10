package controller;

import animatefx.animation.Jello;
import controller.GameState.GameMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import model.Coordinates;
import model.GameModel;
import model.GameModel.LegalMoves;
import model.service.AnimationPlayer;
import model.service.builders.FXTilesBuilding;
import model.service.factory.FXTileFactory;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory.TileClassification;
import model.service.tasks.TilesMovingService;
import model.tiles.FXTile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.MainUIController;

public class GamePlayManager {

  private final static Logger logger = LogManager.getLogger(GamePlayManager.class);

  private final static GamePlayManager onlyInstance = new GamePlayManager();
  private AnimationPlayer animationPlayer;
  private BlockingQueue<TranslateTransition> transitionsQueue = new LinkedBlockingQueue<>();
  private TilesMovingService movingService;
  private GameModel gameModel;
  private FXTilesBuilding tilesBuilder;
  private ArrayList<FXTile> tiles;
  private MainUIController mainUIController = null;


  private GamePlayManager() {
    tilesBuilder = new FXTilesBuilding();
    movingService = new TilesMovingService();
    initializeListeners();
    initializeServices();
  }

  public static GamePlayManager getInstance() {
    return onlyInstance;
  }

  private void initializeServices() {

  }

  public void startNewGame() {

    tilesBuilder.setTilesShape(TileShape.SQUARE);

    tiles = (ArrayList<FXTile>) tilesBuilder
        .orderTiles(GameState.getGridSize(),
            GameState.getMode() == GameMode.PICTURE ? TileClassification.PICTURE
                : TileClassification.SOLID);

    gameModel = new GameModel(GameState.getGridSize(), tiles);

    transitionsQueue = new LinkedBlockingQueue<>();

    // Stop already running thread if it was activated in a previous run.
    if (animationPlayer != null) {
      animationPlayer.stop();
    }

    // Create a new AnimationPlayer with new transitions queue
    animationPlayer = new AnimationPlayer(transitionsQueue);

    Thread animationsPlayerThread = new Thread(animationPlayer);
    animationsPlayerThread.start();

    FXTile emptyTile = (FXTile) gameModel.getBlankTile();
    emptyTile.setVisible(false);

    for (FXTile tile : tiles) {
      tile.setOnMouseClicked(event -> new SwappingService(tile, false).start());
    }

    Platform.runLater(() -> {
      mainUIController.loadBoard(tiles);
      ArrayList<FXTile> solvedBoardTiles = (ArrayList<FXTile>) tilesBuilder
          .orderTiles(GameState.getGridSize(), TileClassification.SOLID);
      solvedBoardTiles.get(solvedBoardTiles.size() - 1).setVisible(false);
      mainUIController.loadSolution(solvedBoardTiles);
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
    new ShufflingService(numberOfShuffles).start();
  }

  private List<FXTile> getMovableFXTiles() {
    logger.traceEntry("getMovableFXTiles");
    FXTile emptyTile = (FXTile) gameModel.getBlankTile();
    List<Coordinates> currentPossibleMoves = new ArrayList<>();
    for (LegalMoves move : LegalMoves.values()) {
      int row = emptyTile.getCoordinates().getRow() + move.getRow();
      int col = emptyTile.getCoordinates().getCol() + move.getCol();
      if (row < GameState.getGridSize() && row >= 0 && col < GameState.getGridSize() && col >= 0) {
        currentPossibleMoves.add(new Coordinates(row, col));
      }
    }
    logger.traceExit("getMovableFXTiles");
    return tiles.stream().filter(tile -> currentPossibleMoves.contains(tile.getCoordinates()))
        .collect(Collectors.toList());

  }


  public class SwappingService extends Service<Boolean> {

    FXTile theTile;
    boolean tileWasMoved;
    TranslateTransition translateTransition;
    boolean shuffleRequest;

    private SwappingService(FXTile theTile, boolean shuffleRequest) {
      this.theTile = theTile;
      Thread tileMover = new Thread(() -> tileWasMoved = gameModel.move(theTile));
      tileMover.start();
      this.shuffleRequest = shuffleRequest;
      translateTransition = new TranslateTransition();

      try {
        tileMover.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (!shuffleRequest) {
        GameState.setCurrentMoves(GameState.getCurrentMoves() + 1);
      }

    }

    @Override
    protected Task<Boolean> createTask() {
      return new Task<>() {

        @Override
        protected Boolean call() {

          if (tileWasMoved) {

            double toX = FXTileFactory.getRequestedTileSize()
                * theTile.getCoordinates().getCol();
            double toY = FXTileFactory.getRequestedTileSize()
                * theTile.getCoordinates().getRow();

            translateTransition.setOnFinished(this::endOfAnimationHandler);
            translateTransition.setNode(theTile);

            translateTransition.setToX(toX);
            translateTransition.setToY(toY);

            try {
              transitionsQueue.put(translateTransition);
            } catch (InterruptedException e) {
              logger.error(e);
              e.printStackTrace();
            }
          } else {
            new Jello(theTile).play();
          }
          return tileWasMoved;
        }

        private void endOfAnimationHandler(ActionEvent actionEvent) {
          logger.traceEntry("actionEvent");
          if (theTile.refreshInInitialPosition()) {
            if (!shuffleRequest && gameModel.isSolved()) {
              Platform.runLater(() -> mainUIController.showWinnerScreen());
            }
          }
          logger.traceExit(actionEvent);
        }
      };
    }
  }

  private class ShufflingService extends Service {

    private int numberOfShuffles;

    public ShufflingService(int numberOfShuffles) {
      this.numberOfShuffles = numberOfShuffles;
      setOnSucceeded(event -> {
        if (gameModel.isSolved()) {
          new ShufflingService(numberOfShuffles).start();
        }
      });
    }

    @Override
    protected Task<Void> createTask() {
      return new Task<>() {
        @Override
        protected Void call() {
          Random randomizer = new Random(System.currentTimeMillis());
          List<FXTile> possibleMoves;
          for (int shuffle = 0; shuffle < numberOfShuffles; shuffle++) {
            possibleMoves = getMovableFXTiles();
            new SwappingService(possibleMoves.get(randomizer.nextInt(possibleMoves.size())), true)
                .start();
          }
          return null;
        }
      };
    }
  }
}

