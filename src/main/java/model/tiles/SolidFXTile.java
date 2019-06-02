package model.tiles;

import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory;

public class SolidFXTile extends FXTile {

  public SolidFXTile(TileFactory tileFactory) {
    super(tileFactory);
    setShapeType(TileShape.SQUARE);
  }

  @Override
  public void assembleTile() {
    assembleFXTileBase();
  }
}
