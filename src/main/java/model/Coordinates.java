package model;

import java.util.Objects;

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

  public void swapWith(Coordinates these) {
    Coordinates tmp = new Coordinates(these.getRow(), these.getCol());
    these.setRow(getRow());
    these.setCol(getCol());
    this.setRow(tmp.getRow());
    this.setCol(tmp.getCol());
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Coordinates)) {
      return false;
    }
    Coordinates that = (Coordinates) o;
    return getRow() == that.getRow() &&
        getCol() == that.getCol();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getRow(), getCol());
  }

  @Override
  public String toString() {
    return "Coordinates - \tRow: " + row + "\tCol: " + col;
  }


}
