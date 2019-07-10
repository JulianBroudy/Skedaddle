package model.tiles;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import model.Coordinates;
import model.Tile;
import model.service.factory.FXTileFactory;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class FXTile extends StackPane implements Tile {

  //TODO: refactor styleclasses into constants that are defined in factories
  private static final Logger logger = LogManager.getLogger(Tile.class);
  protected final TileFactory tileFactory;
  protected final Coordinates initialCoordinates;
  private final Coordinates currentCoordinates;
  protected Text text;
  protected Shape shape;
  private String id = "John Doe";
  private TileClassification tilesClassification;
  private TileShape tilesShape;
  private BooleanProperty inInitialPosition;

  // Because I defined the toString method in shape
  // when it is printed the String defined in toString goes
  // on the screen

  public FXTile(TileFactory tileFactory) {
    this.tileFactory = tileFactory;
    initialCoordinates = new Coordinates(0, 0);
    currentCoordinates = new Coordinates(0, 0);
    inInitialPosition = new SimpleBooleanProperty(true);
  }

  public void assembleFXTileBase() {
    logger.traceEntry("assembling FXTileBase");
    assembleTileBase(tileFactory);
    setTranslateX(getCoordinates().getCol() * FXTileFactory.getRequestedTileSize());
    setTranslateY(getCoordinates().getRow() * FXTileFactory.getRequestedTileSize());
    logger.traceExit("done assembling FXTileBase");
  }

  @Override
  abstract public void assembleTile();

  public void updateStyle(String newStyle) {
    getStyleClass().add(newStyle);
  }

  public String toString() {
    // If any Tile object is printed to screen this shows up

    String tilesInfo = "The " + tilesClassification + " has a top speed of " +
        " and an attack power of ";

    return tilesInfo;
  }

  @Override
  public TileClassification getType() {
    return this.tilesClassification;
  }

  @Override
  public void setType(TileClassification tilesClassification) {
    this.tilesClassification = tilesClassification;
  }

  @Override
  public String getID() {
    return this.id;
  }

  @Override
  public void setID(String id) {
    this.id = id;
  }

  @Override
  public Coordinates getCoordinates() {
    return currentCoordinates;
  }

  @Override
  public void setCoordinates(Coordinates newCoordinates) {
    currentCoordinates.setRow(newCoordinates.getRow());
    currentCoordinates.setCol(newCoordinates.getCol());
  }

  @Override
  public void setInitialCoordinates(Coordinates newCoordinates) {
    initialCoordinates.setRow(newCoordinates.getRow());
    initialCoordinates.setCol(newCoordinates.getCol());
  }

  // @Override
  public boolean isInRightPosition() {
    return isInInitialPosition();
    //  isInInitialPositionManualCheck
  }

  @Override
  public void swapWith(Tile emptyTile) {
    this.currentCoordinates.swapWith(emptyTile.getCoordinates());
  }

  public TileShape getShapeType() {
    return tilesShape;
  }

  public void setShapeType(TileShape tilesShape) {
    this.tilesShape = tilesShape;
  }

  public boolean isInInitialPosition() {
    return inInitialPosition.get();
  }

  protected BooleanProperty inInitialPositionProperty() {
    return inInitialPosition;
  }

  public synchronized boolean refreshInInitialPosition() {
    this.inInitialPosition.set(currentCoordinates.equals(initialCoordinates));
    return isInInitialPosition();
  }
}