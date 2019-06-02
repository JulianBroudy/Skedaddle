package model.service.factory;

// This factory uses the TileFactory interface
// to create very specific UFO Enemy Ship

// This is where we define all of the parts the ship
// will use by defining the methods implemented
// being ESWeapon and ESEngine

// The returned object specifies a specific weapon & engine


import model.Tile;

public class SolidTileFactory extends FXTileFactory {

  // Defines the tile's coordinates to associate with the tile

  @Override
  public void setType(Tile tile) {
    tile.setType(TileClassification.SOLID);
  }

  // Defines the weapon object to associate with the ship



}