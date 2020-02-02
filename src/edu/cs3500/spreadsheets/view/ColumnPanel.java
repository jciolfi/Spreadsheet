package edu.cs3500.spreadsheets.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Column panel to handle the column labels of the worksheet, in letter formatting, such
 * that A = 1, B = 2, etc. Each column is enclosed in its own rectangle, which is represented by
 * two vertical and horizontal lines that go across the screen's width.
 */
public class ColumnPanel extends JPanel {
  private Dimension frameSize;

  /**
   * Constructor for a column panel that sets its background color to beige.
   */
  ColumnPanel(Dimension frameSize) {
    super();
    this.frameSize = frameSize;
    this.setOpaque(true);
    this.setBackground(new Color(230, 208, 171));
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawLine(0, 0, this.frameSize.width, 0);
    int colInd = 1;
    for (int colWidth = 0; colWidth < this.frameSize.width;
         colWidth += WorksheetGraphicalView.cellWidth) {
      g.drawLine(colWidth, 0, colWidth, WorksheetGraphicalView.cellHeight);
      g.drawString(Coord.colIndexToName(colInd),
              WorksheetGraphicalView.cellWidth / 2 + colWidth,
              WorksheetGraphicalView.cellHeight / 2);
      colInd++;
    }
    g.drawLine((colInd - 1) * WorksheetGraphicalView.cellWidth, 0,
            (colInd - 1) * WorksheetGraphicalView.cellWidth, WorksheetGraphicalView.cellHeight);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(this.frameSize.width, WorksheetGraphicalView.cellHeight);
  }
}
