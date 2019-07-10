package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public class GameState {

  private static BooleanProperty isActive;
  private static IntegerProperty gridSize;
  private static IntegerProperty currentMoves;
  private static GameMode mode;
  private static ObjectProperty<Image> currentImage;

  static {
    GameState.isActive = new SimpleBooleanProperty(false);
    GameState.gridSize = new SimpleIntegerProperty(8);
    GameState.currentMoves = new SimpleIntegerProperty();
    GameState.currentImage = new SimpleObjectProperty<>();
  }

  public static boolean isActive() {
    return isActive.get();
  }

  public static void setIsActive(boolean isActive) {
    GameState.isActive.set(isActive);
  }

  public static BooleanProperty isActiveProperty() {
    return isActive;
  }

  public static int getGridSize() {
    return gridSize.get();
  }

  public static void setGridSize(int gridSize) {
    GameState.gridSize.set(gridSize);
  }

  public static IntegerProperty gridSizeProperty() {
    return gridSize;
  }

  public static int getCurrentMoves() {
    return currentMoves.get();
  }

  public static void setCurrentMoves(int currentMoves) {
    GameState.currentMoves.set(currentMoves);
  }

  public static IntegerProperty currentMovesProperty() {
    return currentMoves;
  }

  public static GameMode getMode() {
    return mode;
  }

  public static void setMode(GameMode mode) {
    GameState.mode = mode;
  }

  public static void shuffleBoard(int i) {
    System.out.println("shuffle pressed");
  }

  public static Image getCurrentImage() {
    return currentImage.get();
  }

  public static void setCurrentImage(Image currentImage) {
    GameState.currentImage.set(currentImage);
  }

  public static ObjectProperty<Image> currentImageProperty() {
    return currentImage;
  }

  public enum GameMode {
    NORMAL, PICTURE
  }
}
