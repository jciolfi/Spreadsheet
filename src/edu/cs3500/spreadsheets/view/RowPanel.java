package edu.cs3500.spreadsheets.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Class to handle rendering the row labels on the left side of the worksheet with standard numbers
 * for each row, where each value is enclosed in a rectangle with two horizontal lines with
 * two large vertical bars spanning the height of the frame.
 */
public class RowPanel extends JPanel {
  private Dimension frameSize;

  /**
   * Constructor for a row panel that sets the background color to beige.
   */
  RowPanel(Dimension frameSize) {
    super();
    this.frameSize = frameSize;
    this.setOpaque(true);
    this.setBackground(new Color(230, 208, 171));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawLine(0, 0, 0, this.frameSize.height);
    int rowInd = 1;
    for (int rowHeight = 0; rowHeight < this.frameSize.height;
         rowHeight += WorksheetGraphicalView.cellHeight) {
      g.drawLine(0, rowHeight, WorksheetGraphicalView.cellWidth, rowHeight);
      g.drawString(String.valueOf(rowInd), WorksheetGraphicalView.cellWidth / 2,
              (WorksheetGraphicalView.cellHeight / 2) + rowHeight);
      rowInd++;
    }
    g.drawLine(0, (rowInd - 1) * WorksheetGraphicalView.cellHeight,
            WorksheetGraphicalView.cellWidth, (rowInd - 1) * WorksheetGraphicalView.cellHeight);
  }


  @Override
  public Dimension getPreferredSize() {
    return new Dimension(WorksheetGraphicalView.cellWidth, this.frameSize.height);
  }
}
