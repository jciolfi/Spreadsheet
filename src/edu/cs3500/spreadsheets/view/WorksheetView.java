package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Feature;

/**
 * Represents a general interface for the view of a worksheet, containing a render method to show
 * the actual view for each respective type of WorksheetView.
 */
public interface WorksheetView {
  /**
   * Displays the spreadsheet to the user in the format specified by the underlying view.
   */
  void render();

  /**
   * Adds a new feature to the class that allows the model to be changed when the user inputs
   * information for a specific cell. For example, an implementation of the view has a toolbar and a
   * textfield that allows users to enter a new Sexp for a cell, which will change the state of the
   * model and show the changes to the user.
   *
   * @param feature a Feature, like a controller, to change the state of the model
   */
  void addFeature(Feature feature);
}
