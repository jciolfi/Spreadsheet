package edu.cs3500.spreadsheets;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.controller.WorksheetController;
import edu.cs3500.spreadsheets.model.WorksheetBasic;
import edu.cs3500.spreadsheets.model.WorksheetReadOnlyModel;
import edu.cs3500.spreadsheets.model.WorksheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.WorksheetToolbarView;
import edu.cs3500.spreadsheets.view.WorksheetGraphicalView;
import edu.cs3500.spreadsheets.view.WorksheetTextualView;
import edu.cs3500.spreadsheets.view.WorksheetView;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    /*
      TODO: For now, look in the args array to obtain a filename and a cell name,
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */
    try {
      if (args.length == 1) {
        if (args[0].equals("-gui")) {
          WorksheetReadOnlyModel model = new WorksheetBasic.Builder().createWorksheet();
          WorksheetView guiView = new WorksheetGraphicalView(model);
          guiView.render();
        } else if (args[0].equals("-edit")) {
          WorksheetModel model = new WorksheetBasic.Builder().createWorksheet();
          WorksheetToolbarView view =
                  new WorksheetToolbarView((WorksheetReadOnlyModel)model);
          WorksheetController controller = new WorksheetController(model, view);
          view.render();
        }
      } else if (args.length == 3 && args[0].equals("-in")) {
        if (args[2].equals("-gui")) {
          String filename = args[1];
          WorksheetReadOnlyModel model = WorksheetReader.read(new WorksheetBasic.Builder(),
                  new FileReader(filename));
          WorksheetView guiView = new WorksheetGraphicalView(model);
          guiView.render();
        } else if (args[2].equals("-edit")) {
          String filename = args[1];
          WorksheetBasic model = WorksheetReader.read(new WorksheetBasic.Builder(),
                  new FileReader(filename));
          WorksheetToolbarView view = new WorksheetToolbarView(model);
          WorksheetController controller = new WorksheetController(model, view);
          view.render();
        }
      } else if (args.length == 4 && args[0].equals("-in") && args[2].equals("-save")) {
        String filename = args[1];
        String newFilename = args[3];
        WorksheetBasic model = WorksheetReader.read(new WorksheetBasic.Builder(),
                new FileReader(filename));
        PrintWriter pw = new PrintWriter(newFilename);
        WorksheetTextualView view = new WorksheetTextualView(model, pw);
        view.render();
        pw.close();
      } else if (args.length == 4 && args[0].equals("-in") && args[2].equals("-eval")) {
        String filename = args[1];
        String cell = args[3];
        WorksheetBasic model = WorksheetReader.read(new WorksheetBasic.Builder(),
                new FileReader(filename));
        System.out.print(model.getCellAt(new StringToCoordinate().apply(cell)).getRawContents());
      } else {
        System.out.println("inputted command line arguments \"" + args + "\" is invalid.");
      }
    } catch (IOException e) {
      System.out.println("IOException occurred: " + e.getMessage() + "\nTry again.");
    }
  }
}
