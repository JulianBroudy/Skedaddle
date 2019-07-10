package model.service.factory;

// This factory uses the TileFactory interface
// to create very specific UFO Enemy Ship

// This is where we define all of the parts the ship
// will use by defining the methods implemented
// being ESWeapon and ESEngine

// The returned object specifies a specific weapon & engine

import controller.GameState;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import model.Coordinates;
import model.Tile;

public class PictureTileFactory extends FXTileFactory {


  public Tile addShape() {
    System.out.println("return new TileShape(this)");
//    return new TileShape(this); // Specific to regular UFO
    return null;
  }

  // Defines the engine object to associate with the ship

  public Tile addStyle() {
    System.out.println("return new TileStyle(this); ");
    return null;
//    return new TileStyle(this); // Specific to regular UFO
  }

  public Tile addPicture(Coordinates tilesInitialCoordinates) {

    ImageView picView = new ImageView(GameState.);

    Rectangle2D rect = new Rectangle2D(getRequestedTileSize() * tilesInitialCoordinates.getCol(),
        getRequestedTileSize() * tilesInitialCoordinates.getRow(), getRequestedTileSize() - 3,
        getRequestedTileSize() - 3);
    picView.setViewport(rect);

    System.out.println("return new TilePictureView(this);");
    return null;
//    return new TilePictureView(this); // Specific to regular UFO
  }


}