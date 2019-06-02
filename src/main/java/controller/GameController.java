package controller;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.GameModel;
import model.Tile;
import model.service.builders.FXTilesBuilding;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory.TileClassification;

public class GameController {

  private static BooleanProperty isActive;
  private static IntegerProperty gridSize;
  private static IntegerProperty currentMoves;
  private static GameMode mode;
  private static GameModel gameModel;
  private static FXTilesBuilding tilesBuilder;
  private static ArrayList<Tile> tiles;

  static {
    GameController.tilesBuilder = new FXTilesBuilding();
    GameController.isActive = new SimpleBooleanProperty();
    GameController.setIsActive(false);
    GameController.gridSize = new SimpleIntegerProperty();
    GameController.currentMoves = new SimpleIntegerProperty();
  }

  public static boolean isActive() {
    return isActive.get();
  }

  public static void setIsActive(boolean isActive) {
    GameController.isActive.set(isActive);
  }

  public static BooleanProperty isActiveProperty() {
    return isActive;
  }

  public static int getGridSize() {
    return gridSize.get();
  }

  public static void setGridSize(int gridSize) {
    GameController.gridSize.set(gridSize);
  }

  public static IntegerProperty gridSizeProperty() {
    return gridSize;
  }

  public static int getCurrentMoves() {
    return currentMoves.get();
  }

  public static void setCurrentMoves(int currentMoves) {
    GameController.currentMoves.set(currentMoves);
  }

  public static IntegerProperty currentMovesProperty() {
    return currentMoves;
  }

  public static GameMode getMode() {
    return mode;
  }

  public static void setMode(GameMode mode) {
    GameController.mode = mode;
  }

  public static void startNewGame() {
//    check Game Mode
    GameController.tilesBuilder.setTilesShape(TileShape.SQUARE);
    GameController.tiles = tilesBuilder
        .orderTiles(GameController.getGridSize(), TileClassification.SOLID);
    GameController.gameModel = new GameModel(GameController.getGridSize(), tiles);
  }

  public static void shuffleBoard(int i) {
    System.out.println("shuffle pressed");
  }

  public enum GameMode {
    NORMAL, PICTURE;
  }
}
