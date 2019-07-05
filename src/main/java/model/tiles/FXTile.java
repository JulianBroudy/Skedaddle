package model.tiles;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import model.Coordinates;
import model.Tile;
import model.service.factory.FXTileFactory;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class FXTile extends StackPane implements Tile {

  private static final Logger logger = LogManager.getLogger(Tile.class);

  private final TileFactory tileFactory;
  private final Coordinates initialCoordinates, currentCoordinates;
  protected Text text;
  protected Shape shape;
  private String id = "John Doe";
  private TileClassification tilesClassification;
  private TileShape tilesShape;

  public FXTile(TileFactory tileFactory) {
    this.tileFactory = tileFactory;
    initialCoordinates = new Coordinates(0, 0);
    currentCoordinates = new Coordinates(0, 0);
  }

  public void assembleFXTileBase() {
    logger.traceEntry("assembling FXTileBase");
    assembleTileBase(tileFactory);
    FXTileFactory fxFactory = (FXTileFactory) tileFactory;
    text = fxFactory.createText(getID());
    shape = fxFactory.createShape(getShapeType(), initialCoordinates);
    getChildren().add(shape);
    getChildren().add(text);
    setTranslateX(getCoordinates().getCol() * FXTileFactory.getRequestedTileSize());
    setTranslateY(getCoordinates().getRow() * FXTileFactory.getRequestedTileSize());
    logger.traceExit("done assembling FXTileBase");
  }

  @Override
  abstract public void assembleTile();

  public void updateStyle(String newStyle) {
    getStyleClass().add(newStyle);
  }

  // Because I defined the toString method in shape
  // when it is printed the String defined in toString goes
  // on the screen

  public String toString() {
    // If any Tile object is printed to screen this shows up

    String infoTile = "The " + tilesClassification + " has a top speed of " +
        " and an attack power of ";

    return infoTile;

  }

  @Override
  public void alterPosition() {

  }

  @Override
  public TileClassification getType() {
    return this.tilesClassification;
  }

  @Override
  public void setType(TileClassification tilesClassification) {
    this.tilesClassification = tilesClassification;
  }

  @Override
  public String getID() {
    return this.id;
  }

  @Override
  public void setID(String id) {
    this.id = id;
  }

  @Override
  public Coordinates getCoordinates() {
    return currentCoordinates;
  }

  @Override
  public void setCoordinates(Coordinates newCoordinates) {
    currentCoordinates.setRow(newCoordinates.getRow());
    currentCoordinates.setCol(newCoordinates.getCol());
  }

  @Override
  public void setInitialCoordinates(Coordinates newCoordinates) {
    initialCoordinates.setRow(newCoordinates.getRow());
    initialCoordinates.setCol(newCoordinates.getCol());
  }

  @Override
  public boolean isInRightPosition() {
    return currentCoordinates.equals(initialCoordinates);
  }

  @Override
  public void swapWith(Tile tile) {
    // FXTileSwapper swapper = new FXTileSwapper(this, (FXTile) tile);
    // Thread swapperThread = new Thread(swapper);
    // swapperThread.start();

    getCoordinates().swapWith(tile.getCoordinates());

    FXTileSwapper swapper = new FXTileSwapper(this, (FXTile) tile);
    Thread swapperThread = new Thread(swapper);
    swapperThread.start();

    // FXTile thatTile = (FXTile) tile;
    // double thisTileTranslateX = getTranslateX();
    // double thisTileTranslateY = getTranslateY();
    // double thatTileTranslateX = thatTile.getTranslateX();
    // double thatTileTranslateY = thatTile.getTranslateY();
    //
    // System.out.println(
    //     "\n\nSaved This --- " + "thisTileTranslateX: " + thisTileTranslateX
    //         + "\tthisTileTranslateY: "
    //         + thisTileTranslateY);
    // System.out.println(
    //     "Saved That --- " + "thatTileTranslateX: " + thatTileTranslateX + "\tthatTileTranslateY: "
    //         + thatTileTranslateY);
    //
    // Path pathFromThisTileToThatTile = new Path();
    // pathFromThisTileToThatTile.getElements()
    //     .add(new MoveTo(getTranslateX() + getBoundsInParent().getWidth() / 2.0,
    //         getTranslateY() + getBoundsInParent().getHeight() / 2.0));
    // pathFromThisTileToThatTile.getElements()
    //     .add(new LineTo(thatTile.getTranslateX() + thatTile.getBoundsInParent().getWidth() / 2.0,
    //         thatTile.getTranslateY() + thatTile.getBoundsInParent().getHeight() / 2.0));
    // thatTile.setTranslateX(thisTileTranslateX);
    // thatTile.setTranslateY(thisTileTranslateY);
    // getCoordinates().swapWith(tile.getCoordinates());
    //
    // setTranslateX(thatTileTranslateX);
    // setTranslateY(thatTileTranslateY);
    //
    // System.out.println(
    //     "MoveTo:\n" + "getTranslateX(): " + getTranslateX() + "\tgetBoundsInParent().getWidth(): "
    //         + getBoundsInParent().getWidth() + "\t/2: " + getBoundsInParent().getWidth() / 2
    //         + "\ngetTranslateY(): " + getTranslateY() + "\tgetBoundsInParent().getHeight(): "
    //         + getBoundsInParent().getHeight() + "\t/2: " + getBoundsInParent().getHeight() / 2);
    // System.out.println(
    //     "LineTo:\n" + "thatTile.getTranslateX(): " + thatTile.getTranslateX()
    //         + "\tthatTile.getBoundsInParent().getWidth(): "
    //         + thatTile.getBoundsInParent().getWidth() + "\t/2: "
    //         + thatTile.getBoundsInParent().getWidth() / 2 + "\nthatTile.getTranslateY(): "
    //         + thatTile.getTranslateY() + "\tthatTile.getBoundsInParent().getHeight(): "
    //         + thatTile.getBoundsInParent().getHeight() + "\t/2: "
    //         + thatTile.getBoundsInParent().getHeight() / 2);
    //
    // pathTransition = new PathTransition();
    // pathTransition.setDuration(Duration.seconds(1));
    // pathTransition.setNode(this);
    // pathTransition.setPath(pathFromThisTileToThatTile);
    // pathTransition.setOnFinished(e -> {
    //       alterPosition();
    //     }
    // );
    // pathTransition.play();
    //
    // System.out.println(
    //     "After Transition This --- " + "getTranslateX(): " + getTranslateX() + "\tgetTranslateY(): "
    //         + getTranslateY());
    // System.out.println(
    //     "After Transition that --- " + "thatTile.getTranslateX(): " + thatTile.getTranslateX()
    //         + "\tthatTile.getTranslateY(): "
    //         + thatTile.getTranslateY());
    //
    //
    //
    //
    // System.out.println(
    //     "After Setting This --- " + "getTranslateX(): " + getTranslateX() + "\tgetTranslateY(): "
    //         + getTranslateY());
    // System.out.println(
    //     "After Setting that --- " + "thatTile.getTranslateX(): " + thatTile.getTranslateX()
    //         + "\tthatTile.getTranslateY(): "
    //         + thatTile.getTranslateY());

    // thatTile.setTranslateX(thisTileTranslateX);
    // thatTile.setTranslateY(thisTileTranslateY);
    // setTranslateX(thatTileTranslateX);
    // setTranslateY(thatTileTranslateY);
    //
    // TranslateTransition swapThisTileWithThatTile = TransitionsGenerator
    //     .generateTranslateTransition(0.5, thisTileTranslateX, thisTileTranslateY,
    //         thatTileTranslateX, thatTileTranslateY);
    // swapThisTileWithThatTile.setNode(this);
    //
    // TranslateTransition swapThatTileWithThisTile = TransitionsGenerator
    //     .generateTranslateTransition(0.5, thatTileTranslateX,
    //         thatTileTranslateY, thisTileTranslateX, thisTileTranslateY);
    // swapThatTileWithThisTile.setNode(thatTile);
    //
    //
    // ParallelTransition parallelTransition =
    //     TransitionsGenerator
    //         .generateParallelTransition(/*handler -> isSwapping = false, */swapThisTileWithThatTile,
    //             swapThatTileWithThisTile);
    // getCoordinates().swapWith(tile.getCoordinates());
    //
    // parallelTransition.setOnFinished(e -> {
    //       alterPosition();
    //     }
    // );
    // parallelTransition.play();









    /*

    Path pathFromThisToThatTile = new Path();
    pathFromThisToThatTile.getElements()
        .add(new MoveTo(thatTile.getTranslateX() + thatTile.getBoundsInParent().getWidth() / 2.0,
            thatTile.getTranslateY() + thatTile.getBoundsInParent().getHeight() / 2.0));
    pathFromThisToThatTile.getElements()
        .add(new LineTo(getTranslateX() + getBoundsInParent().getWidth() / 2.0,
            getTranslateY() + getBoundsInParent().getHeight() / 2.0));

    Path pathFromThatTileToThisTile = new Path();
    pathFromThatTileToThisTile.getElements()
        .add(new MoveTo(getTranslateX() + getBoundsInParent().getWidth() / 2.0,
            getTranslateY() + getBoundsInParent().getHeight() / 2.0));
    pathFromThatTileToThisTile.getElements()
        .add(new LineTo(thatTile.getTranslateX() + thatTile.getBoundsInParent().getWidth() / 2.0,
            thatTile.getTranslateY() + thatTile.getBoundsInParent().getHeight() / 2.0));
    */

    
    
    
   /* FXTile thatTile = (FXTile) tile;
    TranslateTransition swapCurrentWithNew = TransitionsGenerator
        .generateTranslateTransition(1, XorY.BOTH,thatTile.getCoordinates().getCol()*FXTileFactory.requestedTileSize, thatTile.getCoordinates().getRow()*FXTileFactory.requestedTileSize);
    swapCurrentWithNew.setNode(this);
    TranslateTransition swapNewWithCurrent = TransitionsGenerator
        .generateTranslateTransition(1, XorY.BOTH,getCoordinates().getCol()*FXTileFactory.requestedTileSize, getCoordinates().getRow()*FXTileFactory.requestedTileSize);
    swapNewWithCurrent.setNode(thatTile);
    tile.getCoordinates().swapWith(this.currentCoordinates);
*/

    // TranslateTransition swapThisTileWithThatTile = TransitionsGenerator
    //     .generateTranslateTransition(1, XorY.BOTH, thatTile.getTranslateX(),
    //         thatTile.getTranslateY());
    // swapThisTileWithThatTile.setNode(this);
    //
    // TranslateTransition swapThatTileWithThisTile = TransitionsGenerator
    //     .generateTranslateTransition(1, XorY.BOTH, getTranslateX(), getTranslateY());
    // swapThatTileWithThisTile.setNode(thatTile);

    //
    //
    // TranslateTransition swapThisTileWithThatTile = new TranslateTransition();
    // swapThisTileWithThatTile.setByX(
    //     FXTileFactory.requestedTileSize * (thatTile.getCoordinates().getCol() - getCoordinates()
    //         .getCol()));
    // swapThisTileWithThatTile.setByY(
    //     FXTileFactory.requestedTileSize * (thatTile.getCoordinates().getRow() - getCoordinates()
    //         .getRow()));
    // swapThisTileWithThatTile.setNode(this);
    // TranslateTransition swipeThatTileWithThisTile = new TranslateTransition();
    // swipeThatTileWithThisTile.setByX(
    //     FXTileFactory.requestedTileSize * (getCoordinates().getCol() - thatTile.getCoordinates()
    //         .getCol()));
    // swipeThatTileWithThisTile.setByY(
    //     FXTileFactory.requestedTileSize * (getCoordinates().getRow() - thatTile.getCoordinates()
    //         .getRow()));
    // swipeThatTileWithThisTile.setNode(thatTile);

    // tile.getCoordinates().swapWith(this.currentCoordinates);

    // isSwapping = true;

  }


  public TileShape getShapeType() {
    return tilesShape;
  }

  public void setShapeType(TileShape tilesShape) {
    this.tilesShape = tilesShape;
  }
}