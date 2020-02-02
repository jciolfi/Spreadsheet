package edu.cs3500.spreadsheets.model;

/**
 * Represents a visitor for a Cell.
 */
public interface CellVisitor<R> {
  /**
   * Visit a Cell.
   * @param c the Cell
   * @return an object R based on how the visitor handles the given Cell
   */
  R visitCell(Cell c);
}
