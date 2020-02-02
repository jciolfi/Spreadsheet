package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a reference to all the cells between the two specified columns.
 */
public final class ReferenceColumn implements Formula {
  private int startCol;
  private int endCol;
  private WorksheetModel model;

  /**
   * Constructor for a reference to a column of cells that takes in two integers representing the
   * two columns and the model this refers to.
   *
   * @param col1  the first column
   * @param col2  the second column
   * @param model the worksheet this refers to
   */
  public ReferenceColumn(int col1, int col2, WorksheetModel model) {
    if (model == null) {
      throw new IllegalArgumentException("null inputs");
    }
    this.startCol = Math.min(col1, col2);
    this.endCol = Math.max(col1, col2);
    this.model = model;
  }

  int getStartCol() {
    return this.startCol;
  }

  int getEndCol() {
    return this.endCol;
  }

  @Override
  public Value evaluate() {
    //throw new UnsupportedOperationException("Can't evaluate a reference to columns");
    return null;
  }

  @Override
  public ArrayList<Formula> getParts() {
    ArrayList<Formula> result = new ArrayList<>();
    for (Coord c : model.getNonEmptyCells()) {
      if (startCol <= c.col && c.col <= endCol) {
        result.add(model.getCellAt(c).getFormula());
      }
    }
    return result;
  }

  @Override
  public Formula getCopy() {
    return new ReferenceColumn(startCol, endCol, model);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitColumnRef(this);
  }

  @Override
  public String toString() {
    for (Formula f : this.getParts()) {
      if (f instanceof ValueError) {
        return "#VALUE!";
      }
    }
    return this.accept(new FormulaToStringVisitor());
  }
}
