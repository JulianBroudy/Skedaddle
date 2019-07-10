package model.tiles;

import animatefx.animation.RubberBand;
import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.service.factory.FXTileFactory;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.PictureTileFactory;
import model.service.factory.TileFactory;

public class PictureTile extends FXTile {

  private ImageView picView;

  public PictureTile(TileFactory tileFactory) {
    super(tileFactory);
    setShapeType(TileShape.SQUARE);
  }

  @Override
  public void assembleTile() {
    assembleFXTileBase();
    PictureTileFactory pictureTileFactory = (PictureTileFactory) tileFactory;
    shape = pictureTileFactory.createShape(getShapeType(), initialCoordinates);
    picView = pictureTileFactory.addPicture()
    getChildren().add(shape);

    shape.getStyleClass().add("right-pos-tilePic");
    inInitialPositionProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        RubberBand rubberBand = new RubberBand(this);
        PauseTransition pause = new PauseTransition(Duration.millis(350));
        pause.setOnFinished(event -> {
          shape.getStyleClass().clear();
          shape.getStyleClass().add("right-pos-tilePic");
        });
        rubberBand.play();
        pause.play();
      } else {
        shape.getStyleClass().clear();
        shape.getStyleClass().add("wrong-pos-tilePic");
      }
    });

    shape.setStrokeWidth(-1);
    ((Rectangle) shape).arcHeightProperty()
        .bind(FXTileFactory.requestedTileSizeProperty().divide(10));
    ((Rectangle) shape).arcWidthProperty()
        .bind(FXTileFactory.requestedTileSizeProperty().divide(10));
  }


}
