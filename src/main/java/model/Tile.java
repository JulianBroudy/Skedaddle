package model;

import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Tile {

  // Newly defined objects that represent initial & current coordinates
  // These can be changed easily by assigning new values
  // in PictureTileFactory or SolidTileFactory
//  TODO: check comment
  Logger logger = LogManager.getLogger(Tile.class);

  default void assembleTileBase(TileFactory tileFactory) {
    logger.traceEntry("assembling " + getType() + " tile base...");
    tileFactory.generateIDFor(this);
    tileFactory.assignCoordinatesTo(this);
    tileFactory.setType(this);
    TileFactory.updateStock();
    logger.traceExit("assembled tile base - ID: " + getID());
  }

  void assembleTile();

  TileClassification getType();

  void setType(TileClassification type);

  String getID();

  void setID(String id);

  Coordinates getCoordinates();


  void setCoordinates(Coordinates newCoordinates);


  void setInitialCoordinates(Coordinates newCoordinates);


  boolean isInRightPosition();

  void swapWith(Tile emptyTile);

}
