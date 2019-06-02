package model;

import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;

public interface Tile {

  // Newly defined objects that represent initial & current coordinates
  // These can be changed easily by assigning new values
  // in PictureTileFactory or SolidTileFactory
//  TODO: check comment


  default void assembleTileBase(TileFactory tileFactory) {
    System.out.print("Assembling tile: ");
    tileFactory.generateIDFor(this);
    tileFactory.assignCoordinatesTo(this);
    tileFactory.setType(this);
    System.out.println(getID() + "\tType: " + getType());
    tileFactory.updateStock();
  }

  void assembleTile();

  TileClassification getType();

  void setType(TileClassification type);

  String getID();

  void setID(String id);

  void alterPosition();

  Coordinates getCoordinates();


  void setCoordinates(Coordinates newCoordinates);


  void setInitialCoordinates(Coordinates newCoordinates);


  boolean isInRightPosition();

  void swapWith(Tile tile);

}
