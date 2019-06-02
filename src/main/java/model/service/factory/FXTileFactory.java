package model.service.factory;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import model.Coordinates;
import model.Tile;
import model.tiles.FXTile;

public abstract class FXTileFactory extends TileFactory {

  ShapeFactory shapeFactory = new ShapeFactory(requestedGridSize);

  public Shape createShape(TileShape tileShape, Coordinates coordinates) {
    switch (tileShape) {
      case SQUARE:
        return shapeFactory.newRectangle(coordinates.getRow(), coordinates.getCol());
      case CIRCLE:
        return null;
      default:
        return null;
    }
  }

  public Text createText(String id) {
    return null;
  }

  public void bla(ObservableList<Node> children, Tile tile) {
    switch (((FXTile) tile).getShapeType()) {
      case SQUARE: {
        children.add(shapeFactory
            .newRectangle(tile.getCoordinates().getRow(), tile.getCoordinates().getCol()));
        break;
      }
      case CIRCLE: {
        children.add(shapeFactory
            .newCircle(tile.getCoordinates().getRow(), tile.getCoordinates().getCol()));
        break;
      }
    }
  }

  public enum TileShape {
    SQUARE, CIRCLE
  }

  class ShapeFactory {

    int size;

    public ShapeFactory(int requestedGridSize) {
      this.size = 400 / requestedGridSize;
    }

    Rectangle newRectangle(int row, int col) {
      Rectangle tmp = new Rectangle(size - 4, size - 4);
      tmp.setArcHeight(size / 10);
      tmp.setArcWidth(size / 10);
      return tmp;
    }


    public Node newCircle(int row, int col) {
//      TODO: implement
      return null;
    }
  }

}


