package model.tiles;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import model.Coordinates;
import model.Tile;
import model.service.factory.FXTileFactory;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;

public abstract class FXTile extends StackPane implements Tile {

  protected Text text;
  private final TileFactory tileFactory;
  private final Coordinates initialCoordinates, currentCoordinates;
  private String id = "John Doe";
  private TileClassification tilesClassification;
  private TileShape tilesShape;
  private Shape shape;

  public FXTile(TileFactory tileFactory) {
    this.tileFactory = tileFactory;
    initialCoordinates = currentCoordinates = new Coordinates(0, 0);
  }

  public void assembleFXTileBase() {
    assembleTileBase(tileFactory);
    FXTileFactory fxFactory = (FXTileFactory)tileFactory;
    text = fxFactory.createText(getID());
    shape = fxFactory.createShape(getShapeType(), initialCoordinates);
    getChildren().add(shape);
    setTranslateX(getCoordinates().getCol() * tileFactory.requestedGridSize);
    setTranslateY(getCoordinates().getRow() * tileFactory.requestedGridSize);
  }

  @Override
  abstract public void assembleTile();

  public void updateStyle(String newStyle) {
    getStyleClass().add(newStyle);
  }

  // Because I defined the toString method in shape
  // when it is printed the String defined in toString goes
  // on the screen

  public String toString() {
    // If any Tile object is printed to screen this shows up

    String infoTile = "The " + tilesClassification + " has a top speed of " +
        " and an attack power of ";

    return infoTile;

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
  public void alterPosition() {

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

  @Override
  public boolean isInRightPosition() {
    return (currentCoordinates.areTheSameAs(initialCoordinates));
  }

  @Override
  public void swapWith(Tile tile) {
    tile.getCoordinates().swapWith(this.currentCoordinates);
  }

  public TileShape getShapeType() {
    return tilesShape;
  }

  public void setShapeType(TileShape tilesShape) {
    this.tilesShape = tilesShape;
  }
}