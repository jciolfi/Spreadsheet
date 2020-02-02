package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.controller.WorksheetController;
import edu.cs3500.spreadsheets.model.WorksheetBasic;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.view.WorksheetToolbarView;

/**
 * Represents a main to run the graphical representation of worksheet models.
 */
public class GraphicalViewMain {
  /**
   * Produce a graphical interactive representation of the worksheet model that allows scrolling and
   * resizing within the window.
   *
   * @param args the String arguments given to this program.
   */
  public static void main(String[] args) {
    /*
    WorksheetModel model = new WorksheetBasic.Builder()
            //doubles
            .createCell(1, 1, "123456789123456789")
            .createCell(1, 2, "=(PRODUCT A1 -1)")
            .createCell(1, 3, "-5")
            .createCell(1, 4, "10")
            .createCell(1, 5, "=(SUM A1:A4)")
            //strings
            .createCell(2, 1, "\"\"")
            .createCell(2, 2, "\"string\"")
            .createCell(2, 3, "\"10\"")
            .createCell(2, 4, "\"false\"")
            .createCell(2, 5, "=(CONCATENATE B1:B4)")
            //booleans
            .createCell(3, 1, "true")
            .createCell(3, 2, "false")
            //.createCell(12, 12, "false")

            //cyclical reference checking
            .createCell(4, 1, "5")
            .createCell(4, 2, "=D1")
            .createCell(4, 1, "=D2")
            //.createCell(4,1,"5")
            .createCell(5, 1, "=E1")
            .createCell(5,1,"5")
            .createCell(5, 1, "=E1")

            //column checking
            .createCell(6,1, "=(SUM A:B)")

            .createWorksheet();
    WorksheetToolbarView view = new WorksheetToolbarView((WorksheetReadOnlyModel)model);
    WorksheetController controller = new WorksheetController(model, view);
    view.render();
    */

    WorksheetModel model = new WorksheetBasic.Builder()
            .createCell(1,1, "1")
            .createCell(1,2, "4.5")
            .createCell(1,3, "1.34324")
            .createCell(1,4, "3.231")
            .createCell(1,5, "2.22132")
            .createCell(1,6, "5")
            .createCell(1,7, "6")
            .createCell(1,8, "7")
            .createCell(1,9, "\"string\"")
            .createCell(1,10, "false")
            .createCell(1,11, "=(badformula")
            .createWorksheet();
    //WorksheetModel empty = new WorksheetBasic.Builder().createWorksheet();
    WorksheetToolbarView view = new WorksheetToolbarView((WorksheetReadOnlyModel)model);
    WorksheetController controller = new WorksheetController(model, view);
    view.render();
  }
}