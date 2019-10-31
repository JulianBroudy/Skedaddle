package model;


import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for the game's logic.
 */
public class GameModel {


  private static final Logger logger = LogManager.getLogger(GameModel.class);

  private final int boardSize;
  private final Tile[][] tileBoard;
  private Tile blankTile;
  private final List<? extends Tile> tilesList;

  /**
   * @param boardSize is the size of one dimension of the array.
   * @param tilesList is a list containing all the tiles.
   */
  public GameModel(int boardSize, ArrayList<? extends Tile> tilesList) {
    this.boardSize = boardSize;
    this.tilesList = tilesList;
    this.tileBoard = new Tile[boardSize][boardSize];
    blankTile = tilesList.get(boardSize * boardSize - 1);
    for (Tile t : this.tilesList) {
      tileBoard[t.getCoordinates().getRow()][t.getCoordinates().getCol()] = t;
    }
  }

  /**
   * Checks if move is legal and swaps places.
   *
   * @param tile is the tile to be moved.
   * @return false if move is illegal else true.
   */
  public boolean move(Tile tile) {
    logger.traceEntry("moving tile " + tile.getID() + ".");
    if (!isLegalMove(tile)) {
      logger.traceExit(tile.getID() + " moving is not legal!");
      return false;
    }

    tile.swapWith(blankTile);
    /*TODO: check if the opposite works*/
    // tileBoard[tile.getCoordinates().getRow()][tile.getCoordinates().getCol()] = blankTile;
    // tileBoard[blankTile.getCoordinates().getRow()][blankTile.getCoordinates().getCol()] = tile;
    tileBoard[blankTile.getCoordinates().getRow()][blankTile.getCoordinates().getCol()] = blankTile;
    tileBoard[tile.getCoordinates().getRow()][tile.getCoordinates().getCol()] = tile;

    logger.traceExit(tile.getID() + " was successfully moved.");
    return true;
  }

  /**
   * Checks if the tile can be moved to an empty space (i.e. up,down,left,right). #isLegalMove
   *
   * @param tile is the tile to be checked.
   * @return true if tile can be moved, else returns false.
   */
  private boolean isLegalMove(Tile tile) {
    for (LegalMoves move : LegalMoves.values()) {
      if (blankTile.getCoordinates().getRow() == tile.getCoordinates().getRow() + move.getRow()
          && blankTile.getCoordinates().getCol() == tile.getCoordinates().getCol() + move
          .getCol()) {
        return true;
      }
    }
    return false;
  }

  /**
   * checks if all tiles are back to their original coordinates.
   *
   * @return false if at least one tile is not, else returns true.
   */
  public boolean isSolved() {
    logger.traceEntry("checking if puzzle was solved...");
    for (Tile tile : tilesList) {
      if (!tile.isInRightPosition()) {
        logger.traceExit("puzzle is NOT solved yet.");
        return false;
      }
    }
    logger.traceExit("puzzle was solved!!");
    return true;
  }

  /**
   * @return the blank tile
   */
  public Tile getBlankTile() {
    return blankTile;
  }

  /**
   * @param tile is the new blankTile
   */
  public void setBlankTile(Tile tile) {
    //TODO: delete this?
    this.blankTile = tile;
  }

  public void printBoard() {
    System.out.println();
    for (Tile t : tilesList) {
      System.out.println(
          "Tile " + t.getID() + "\tRow: " + t.getCoordinates().getRow() + "\tCol: " + t
              .getCoordinates().getCol());
    }
    System.out.println();
    Tile tmp;
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        tmp = tileBoard[i][j];
        System.out.print(tmp == blankTile ? "\t" : tmp.getID() + "\t");
      }
      System.out.println();
    }
    System.out.println();
  }

  /**
   * Acts as a list of legal moves.
   *
   * {@see #isLegalMove}
   */
  public enum LegalMoves {

    up(-1, 0), down(1, 0), left(0, -1), right(0, 1);

    private final int row;
    private final int col;

    LegalMoves(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public int getRow() {
      return row;
    }

    public int getCol() {
      return col;
    }

  }

}

