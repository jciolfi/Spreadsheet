package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a set of features that can be used with a view to possibly allow the user to interact
 * with the model, and will update the user's view if anything changes from the model for the user's
 * view.
 */
public interface Feature {
  /**
   * Updates the cell to the given String contents at the given coord position.
   * @param where the Coord for where to update the cell
   * @param contents the contents for the new cell
   */
  void updateCell(Coord where, String contents);

  /**
   * Removes the cell at the given position, i.e. setting it to be blank.
   * @param where the Cooord for where to remove the cell
   */
  void removeCell(Coord where);
}