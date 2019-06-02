package model;

public class Coordinates {

  private int row, col;

  public Coordinates(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }

  public boolean areTheSameAs(Coordinates thoseCoord) {
    if (getRow() == thoseCoord.getRow() && getCol() == thoseCoord.getCol()) {
      return true;
    }
    return false;
  }

  public void swapWith(Coordinates these){
    Coordinates tmp=new Coordinates(these.getRow(),these.getCol());
    these.setRow(getRow());
    these.setCol(getCol());
    this.setRow(tmp.getRow());
    this.setCol(tmp.getCol());
  }

  @Override
  public String toString() {
    return "Coordinates - \tRow: " + row + "\tCol: " + col;
  }
}
