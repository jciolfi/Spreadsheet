package edu.cs3500.spreadsheets.model;

/**
 * Represents a cell and the basic functionality of what a cell should support.
 */
public interface ICell {
  /**
   * Get the evaluated result of this cell's formula.
   *
   * @return the value corresponding to this cell's formula, null if cell is blank or in a cycle
   */
  Value getValue();

  /**
   * Get the formula of this cell.
   *
   * @return a Formula representing the contents of the cell; if this is null, the cell is blank
   */
  Formula getFormula();

  /**
   * Accepts a CellVisitor.
   *
   * @param visitor the CellVisitor
   * @param <R>     the object returned based on what the cellvisitor returns
   * @return an object handled by the Cell visitor
   */
  <R> R accept(CellVisitor<R> visitor);

  /**
   * Gets the raw contents that were entered for this cell.
   *
   * @return the string contents that the user entered for this cell.
   */
  String getRawContents();
}
