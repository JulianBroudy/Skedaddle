package model.service.factory;

// With an Abstract Factory Pattern you won't
// just build ships, but also all of the components
// for the ships

// Here is where you define the parts that are required
// if an object wants to be an enemy ship



import model.Coordinates;
import model.Tile;

public abstract class TileFactory {

  public enum TileClassification{
    CONCEPTUAL,SIMPLE, SOLID, PICTURE;
  }

  public static int requestedGridSize;
  public static int currentStockCount;

  public static void resetFactory(int gridSize) {
    requestedGridSize = gridSize;
    currentStockCount = 0;
  }

  public static void updateStock() {
    currentStockCount++;
  }

  public void setType(Tile tile) {
    tile.setType(TileClassification.SIMPLE);
  }

  public void generateIDFor(Tile tile) {
    tile.setID(String.valueOf(currentStockCount + 1));
  }

  public void assignCoordinatesTo(Tile tile) {
    int row = currentStockCount / requestedGridSize;
    int col = currentStockCount % requestedGridSize;
    tile.setInitialCoordinates(new Coordinates(row, col));
    tile.setCoordinates(new Coordinates(row, col));
  }


}