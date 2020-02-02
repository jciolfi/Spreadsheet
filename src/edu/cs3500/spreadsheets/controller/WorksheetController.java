package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.view.WorksheetView;

/**
 * Represents a controller for a worksheet that allows users to interact with the displayed view.
 * For example, an implementation of a view has a toolbar that allows users to edit cells, changing
 * the state of the model.
 */
public class WorksheetController implements Feature {
  private WorksheetModel model;

  /**
   * Constructor for a controller that takes in a mutable model and a view to update back to once
   * the model gets updated.
   *
   * @param model the model that the controller updates.
   * @param view  the view that the controller writes to again when the
   */
  public WorksheetController(WorksheetModel model, WorksheetView view) {
    if (model == null) {
      throw new IllegalArgumentException("null inputs");
    }
    this.model = model;
    view.addFeature(this);
  }

  @Override
  public void updateCell(Coord where, String contents) {
    model.setCell(where, contents);
  }

  @Override
  public void removeCell(Coord where) {
    model.removeCell(where);
  }
}
