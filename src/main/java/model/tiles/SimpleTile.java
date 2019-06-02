package model.tiles;

import model.Coordinates;
import model.Tile;
import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;

public class SimpleTile implements Tile {

  // We define the type of ship we want to create
  // by stating we want to use the factory that
  // makes enemy ships

  private TileFactory tileFactory;
  private String id = "John Doe";
  private TileClassification tilesClassification = TileClassification.SIMPLE;
  private Coordinates initialCoordinates, currentCoordinates;

  // The enemy ship required parts list is sent to
  // this method. They state that the enemy ship
  // must have a weapon and engine assigned. That
  // object also states the specific parts needed
  // to make a Boss UFO versus a Regular UFO

  public SimpleTile(TileFactory tileFactory) {
    this.tileFactory = tileFactory;
    initialCoordinates = currentCoordinates = new Coordinates(0, 0);
  }

  // TilesBuilding calls this method to build a
  // specific SimpleTile

  @Override
  public void assembleTile() {
    assembleTileBase(tileFactory);
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

}
