package model.tiles;

import animatefx.animation.RubberBand;
import javafx.animation.PauseTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.service.factory.FXTileFactory;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.SolidTileFactory;
import model.service.factory.TileFactory;

public class SolidFXTile extends FXTile {

  public SolidFXTile(TileFactory tileFactory) {
    super(tileFactory);
    setShapeType(TileShape.SQUARE);
  }

  @Override
  public void assembleTile() {
    assembleFXTileBase();
    SolidTileFactory solidTileFactory = (SolidTileFactory) tileFactory;
    text = solidTileFactory.createText(getID());
    shape = solidTileFactory.createShape(getShapeType(), initialCoordinates);
    getChildren().add(shape);
    getChildren().add(text);

    shape.getStyleClass().add("right-pos-tile");
    inInitialPositionProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        RubberBand rubberBand = new RubberBand(this);
        PauseTransition pause = new PauseTransition(Duration.millis(350));
        pause.setOnFinished(event -> {
          shape.getStyleClass().clear();
          shape.getStyleClass().add("right-pos-tile");
        });
        rubberBand.play();
        pause.play();
      } else {
        shape.getStyleClass().clear();
        shape.getStyleClass().add("wrong-pos-tile");
      }
    });

    shape.setStrokeWidth(-1);
    ((Rectangle) shape).arcHeightProperty()
        .bind(FXTileFactory.requestedTileSizeProperty().divide(10));
    ((Rectangle) shape).arcWidthProperty()
        .bind(FXTileFactory.requestedTileSizeProperty().divide(10));
  }


}
