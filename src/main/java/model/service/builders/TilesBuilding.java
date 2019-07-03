package model.service.builders;

import java.util.ArrayList;
import model.Tile;
import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TilesBuilding {

  private final static Logger logger = LogManager.getLogger(TilesBuilding.class);

  protected ArrayList<Tile> tilesList;


  // This acts as an ordering mechanism for creating
  // EnemyShips that have a weapon, engine & name
  // & nothing else

  // The specific parts used for engine & weapon depend
  // upon the String that is passed to this method

  protected abstract Tile buildTile(TileClassification tilesClassification);

  // When called a new Tile is made. The specific parts
  // are based on the String entered. After the ship is made
  // we execute multiple methods in the Tile Object

  public ArrayList<? extends Tile> orderTiles(int requestedGridSize,
      TileClassification tilesClassification) {

    logger.traceEntry(String.valueOf(requestedGridSize));
    TileFactory.resetFactory(requestedGridSize);
    tilesList = new ArrayList<>();
    Tile theTile;

    for (int stockCount = 0; stockCount < requestedGridSize * requestedGridSize; stockCount++) {
      theTile = buildTile(tilesClassification);

      theTile.assembleTile();
      tilesList.add(theTile);
    }
    return tilesList;

  }
}