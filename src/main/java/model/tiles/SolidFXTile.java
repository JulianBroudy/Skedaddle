package model.tiles;

import javafx.scene.shape.Rectangle;
import model.service.factory.FXTileFactory;
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
    shape.getStyleClass().clear();
    shape.getStyleClass().add("right-pos-tile");
    hoverProperty().addListener(observable -> {
      if (isHover()) {
        shape.getStyleClass().clear();
        shape.getStyleClass().add("tile-hover");
      } else {
        alterPosition();
      }
    });
    shape.setStrokeWidth(-1);
    ((Rectangle) shape).arcHeightProperty()
        .bind(FXTileFactory.requestedTileSizeProperty().divide(10));
    ((Rectangle) shape).arcWidthProperty()
        .bind(FXTileFactory.requestedTileSizeProperty().divide(10));
    // ((Rectangle) shape).setArcHeight(FXTileFactory.requestedTileSize.get() / 10);
    // ((Rectangle) shape).setArcWidth(FXTileFactory.requestedTileSize.get() / 10);
  }



}
