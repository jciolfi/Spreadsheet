package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a formula that refers to a block of cells based on a starting and ending coordinate.
 */
public class MultiReference implements Formula {
  private ArrayList<Coord> refs;
  private WorksheetModel model;

  /**
   * Constructor for a reference to a block of cells.
   *
   * @param start the coordinate of the upper left cell
   * @param end   the coordinate of the lower right cell
   * @param model the worksheet the cells are located in
   */
  public MultiReference(Coord start, Coord end, WorksheetModel model) {
    if (start.col > end.col || start.row > end.row || model == null) {
      throw new IllegalArgumentException("malformed coordinates");
    }
    ArrayList<Coord> arr = new ArrayList<>();
    for (int c = start.col; c <= end.col; c++) {
      for (int r = start.row; r <= end.row; r++) {
        arr.add(new Coord(c, r));
      }
    }
    this.refs = arr;
    this.model = model;
  }

  /**
   * Convenience constructor for a reference to a block of cells.
   *
   * @param refs  the list of coordinates this formula refers to
   * @param model the worksheet the list of coordinates applies to
   */
  private MultiReference(ArrayList<Coord> refs, WorksheetModel model) {
    this.refs = refs;
    this.model = model;
  }

  /**
   * Get the list of coordinates that this refers to.
   * @return the list of coordinates this depends on
   */
  ArrayList<Coord> getRefs() {
    return new ArrayList<>(refs);
  }

  @Override
  public Value evaluate() {
    throw new UnsupportedOperationException("can't evaluate multiple cells on their own");
  }

  @Override
  public ArrayList<Formula> getParts() {
    ArrayList<Formula> result = new ArrayList<>();
    for (Coord c : refs) {
      //Cell cell = (Cell)model.getCellAt(c);
      //if (!cell.isInCycle()) { }
      result.add(model.getCellAt(c).getFormula());
    }
    return result;
  }

  @Override
  public Formula getCopy() {
    return new MultiReference(this.refs, this.model);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitMultiRef(this);
  }

  @Override
  public String toString() {
    return Coord.colIndexToName(refs.get(0).col) + refs.get(0).row + ":"
            + Coord.colIndexToName(refs.get(refs.size() - 1).col) + refs.get(refs.size() - 1).row;
  }
}