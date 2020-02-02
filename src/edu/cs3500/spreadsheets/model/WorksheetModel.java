package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a worksheet, containing basic functionality for setting a cell (or removing a cell by
 * setting its contents to null), getting a cell at a specified position, getting the positions of
 * all the non-empty cells, and accepting a visitor that transforms a worksheet.
 */
public interface WorksheetModel extends WorksheetReadOnlyModel {
  /**
   * Set the cell at the given coordinate with the given contents. If the cell already exists, the
   * existing cell will be overwritten, and if there is no cell at the coordinate, add a cell at
   * that coordinate. If contents is null, then it will set a blank cell at the given coord.
   *
   * @param coord    the position of the cell in the worksheet
   * @param contents the contents of the cell
   */
  void setCell(Coord coord, String contents);

  /**
   * Removes the cell at the given coordinate, if it's a valid coordinate.
   *
   * @param coord the position of the cell
   */
  void removeCell(Coord coord);
}
