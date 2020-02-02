package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Read only version of the Model Interface that only contains methods that do not mutate the
 * contents of the implementing classes.
 */
public interface WorksheetReadOnlyModel {
  /**
   * Gets the cell at the given coordinate.
   *
   * @param coord the position fo the cell in the worksheet
   * @return the cell at the position of the worksheet
   * @throws IllegalArgumentException if the cell coord is invalid
   */
  ICell getCellAt(Coord coord);

  /**
   * Gets all the coordinates of all the non-empty cells contained in this worksheet.
   *
   * @return a list of the non-empty cells' coordinates
   */
  ArrayList<Coord> getNonEmptyCells();

  /**
   * Convenience method that returns the maximum column and row that contains a non-empty cell
   * entry.
   *
   * @return a Coord representing the max row and column
   */
  Coord getMaxBound();

  /**
   * Gets all the graphs that are used with this worksheet.
   *
   * @return a list of graphs represented used with this worksheet
   */
  ArrayList<Graph> getGraphs();
}
