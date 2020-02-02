package edu.cs3500.spreadsheets.view;

import java.util.ArrayList;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Dimension;

import javax.swing.JPanel;

import edu.cs3500.spreadsheets.model.CellEvalToStringVisitor;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetReadOnlyModel;

/**
 * Class to handle the construction of the contents of the worksheet, rendering the evaluated values
 * of all of the non-empty cells.
 */
public class WorksheetPanel extends JPanel {
  private WorksheetReadOnlyModel model;
  private Dimension frameSize;
  private Coord selected;

  /**
   * Constructor with necessary information (model, how wide and tall to draw the worksheet) to
   * render the actual spreadsheet without the column or row labels.
   *
   * @param model the worksheet to be represented.
   */
  WorksheetPanel(WorksheetReadOnlyModel model, Dimension frameSize) {
    if (model == null || frameSize == null) {
      throw new IllegalArgumentException("null inputs");
    }
    this.model = model;
    this.frameSize = frameSize;
    this.setOpaque(true);
    this.setBackground(Color.WHITE);
  }

  /**
   * Update the selected coordinate to the given coordinate.
   *
   * @param c the coordinate of the selected cell
   */
  void updateSelected(Coord c) {
    this.selected = c;
  }

  /**
   * Gets the coordinate of the user's selected cell on the worksheet.
   *
   * @return a copy of the coordinate representing the selected cell.
   */
  Coord getSelected() {
    if (selected == null) {
      return null;
    }
    return new Coord(selected.col, selected.row);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int col = 0; col < frameSize.width; col += WorksheetGraphicalView.cellWidth) {
      g.drawLine(col, 0, col, frameSize.height);
    }
    for (int row = 0; row < frameSize.height;
         row += WorksheetGraphicalView.cellHeight) {
      g.drawLine(0, row, frameSize.width, row);
    }

    if (selected != null) {
      g.setColor(Color.BLUE);
      //x = col/width, y = row/height
      g.drawRect((selected.col - 1) * WorksheetGraphicalView.cellWidth + 1,
              (selected.row - 1) * WorksheetGraphicalView.cellHeight + 1,
              WorksheetGraphicalView.cellWidth - 2,
              WorksheetGraphicalView.cellHeight - 2);
      g.setColor(Color.BLACK);
    }

    ArrayList<Coord> cellsToDraw = model.getNonEmptyCells();
    for (Coord c : cellsToDraw) {
      //5px as padding for content against left border of cell
      int x = (c.col - 1) * WorksheetGraphicalView.cellWidth + 5;
      int y = (c.row - 1) * WorksheetGraphicalView.cellHeight
              + WorksheetGraphicalView.cellHeight / 2;
      String contents = model.getCellAt(c).accept(new CellEvalToStringVisitor());
      StringBuilder displayContents = new StringBuilder();
      FontMetrics metrics = g.getFontMetrics();
      //account for clipping
      if (metrics.stringWidth(contents) > WorksheetGraphicalView.cellWidth) {
        int spaceLeft = WorksheetGraphicalView.cellWidth - metrics.stringWidth("...") - 5;
        for (int i = 0; i < contents.length(); i++) {
          int spaceNeeded = metrics.stringWidth(String.valueOf(contents.charAt(i)));
          spaceLeft -= spaceNeeded;
          if (spaceLeft > 0) {
            displayContents.append(contents.charAt(i));
          } else {
            displayContents.append("...");
            break;
          }
        }
      } else {
        displayContents.append(contents);
      }
      g.drawString(displayContents.toString(), x, y);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(frameSize.width, frameSize.height);
  }
}
