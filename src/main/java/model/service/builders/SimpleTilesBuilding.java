package model.service.builders;

// This is the only class that needs to change, if you
// want to determine which enemy ships you want to
// provide as an option to build


import model.Tile;
import model.service.factory.SimpleTileFactory;
import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;
import model.tiles.SimpleTile;

public class SimpleTilesBuilding extends TilesBuilding {

  protected Tile buildTile(TileClassification tilesClassification) {

    Tile theTile;

    // If Tile was sent grab use the factory that knows
    // what types of weapons and engines a regular SimpleTile
    // needs. The Tile object is returned.

        TileFactory tilePartsFactory = new SimpleTileFactory();
        theTile = new SimpleTile(tilePartsFactory);

    return theTile;
  }
}

