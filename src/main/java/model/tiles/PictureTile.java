package model.tiles;

import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory;

public class PictureTile extends FXTile {

//  private Image

  public PictureTile(TileFactory tileFactory) {
    super(tileFactory);
    setShapeType(TileShape.SQUARE);
  }

  @Override
  public void assembleTile() {

  }


}
