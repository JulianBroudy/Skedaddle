package model.service.builders;

// This is the only class that needs to change, if you
// want to determine which enemy ships you want to
// provide as an option to build


import model.Tile;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.PictureTileFactory;
import model.service.factory.SolidTileFactory;
import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;
import model.tiles.FXTile;
import model.tiles.PictureTile;
import model.tiles.SolidFXTile;

public class FXTilesBuilding extends TilesBuilding {

  private TileShape tilesShape;

  public void setTilesShape(TileShape tilesShape) {
    this.tilesShape = tilesShape;
  }

  protected Tile buildTile(TileClassification tilesClassification) {
    Tile theTile = null;

    switch (tilesClassification) {
      case PICTURE: {
        // If Image Tile was sent grab use the factory that knows
        // what types of power-ups (in future development) a regular Image Tile
        // needs. The Tile object is returned.

        TileFactory tilePartsFactory = new PictureTileFactory();
        theTile = new PictureTile(tilePartsFactory);
        break;
      }
      case SOLID: {
        // If Solid Tile was sent grab use the factory that knows
        // what types of  power-ups (in future development) a Solid Tile
        // needs. The Tile object is returned.
        TileFactory tilePartsFactory = new SolidTileFactory();
        theTile = new SolidFXTile(tilePartsFactory);
        break;
      }
    }
    theTile.setType(tilesClassification);
    return theTile;
  }

  protected void updateStyle(String newStyle) {
    for (Tile tile : tilesList) {
      ((FXTile) tile).updateStyle(newStyle);
    }
  }
}