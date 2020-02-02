package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.ArrayList;

import edu.cs3500.spreadsheets.controller.Feature;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetReadOnlyModel;

/**
 * Represents a view for a specific Worksheet model that renders the contents of the model to a file
 * with each line containing a Cell's coordinate and contents separated by a space.
 */
public class WorksheetTextualView implements WorksheetView {
  private WorksheetReadOnlyModel model;
  private Appendable out;

  /**
   * Constructor for a WorksheetTextualView that takes in a worksheet model and the Appendable to
   * print to.
   *
   * @param model the Worksheet
   * @param out   the appendable in the form of a PrintWriter
   */
  public WorksheetTextualView(WorksheetReadOnlyModel model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("null model or appendable");
    }
    this.model = model;
    this.out = out;
  }

  @Override
  public void render() {
    try {
      StringBuilder toAdd = new StringBuilder();
      ArrayList<Coord> coords = model.getNonEmptyCells();
      for (int i = 0; i < coords.size(); i++) {
        String curCoord = coords.get(i).toString();
        String curFormula = model.getCellAt(coords.get(i)).getRawContents();
        toAdd.append(curCoord).append(" ").append(curFormula);
        if (i < coords.size() - 1) {
          toAdd.append("\n");
        }
      }
      out.append(toAdd.toString());
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  @Override
  public void addFeature(Feature feature) {
    //do nothing
  }
}
