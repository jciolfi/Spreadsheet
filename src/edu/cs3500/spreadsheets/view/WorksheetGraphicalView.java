package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JViewport;

import edu.cs3500.spreadsheets.controller.Feature;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetReadOnlyModel;

/**
 * Renders the whole worksheet with row and column labels, allowing scrolling and resizing of the
 * window. The grid content will be "infinitely" scrollable, such that an end of the spreadsheet
 * will never be reached.
 */
public class WorksheetGraphicalView extends JFrame implements WorksheetView {
  //standard window size is minimum 800 x 500
  private Dimension frameSize = new Dimension(800, 500);
  private Dimension textFieldSize = new Dimension(800, 30);
  private WorksheetPanel wp;

  //package-protected to be accessed in row, column, and worksheet panels where necessary.
  final static int cellHeight = 50;
  final static int cellWidth = 80;

  /**
   * Constructor to construct the view with scrolling and setting the bounds for how much will be
   * drawn based on the given read-only worksheet.
   *
   * @param m the read-only Worksheet model
   */
  public WorksheetGraphicalView(WorksheetReadOnlyModel m) {
    super();
    if (m == null) {
      throw new IllegalArgumentException("null model");
    }
    Coord max = m.getMaxBound();
    if (max.col * WorksheetGraphicalView.cellWidth > frameSize.width) {
      frameSize.setSize(max.col * WorksheetGraphicalView.cellWidth, frameSize.height);
    }
    if (max.row * WorksheetGraphicalView.cellHeight > frameSize.height) {
      frameSize.setSize(frameSize.width, max.row * WorksheetGraphicalView.cellHeight);
    }
    this.setResizable(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.wp = new WorksheetPanel(m, this.frameSize);
    this.add(wp, BorderLayout.CENTER);
    RowPanel rowPanel = new RowPanel(this.frameSize);
    this.add(rowPanel, BorderLayout.WEST);
    ColumnPanel colPanel = new ColumnPanel(this.frameSize);
    this.add(colPanel, BorderLayout.NORTH);
    JScrollPane pane = new JScrollPane(wp);
    JViewport jvp1 = new JViewport();
    jvp1.setView(rowPanel);
    pane.setRowHeader(jvp1);
    JViewport jvp2 = new JViewport();
    jvp2.setView(colPanel);
    pane.setColumnHeader(jvp2);
    pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    pane.getVerticalScrollBar().addAdjustmentListener(adjustmentEvent -> {
      JScrollBar jsb = (JScrollBar) adjustmentEvent.getAdjustable();
      if (jsb.getValue() + jsb.getModel().getExtent() == jsb.getMaximum()) {
        this.frameSize.setSize(frameSize.width,
                frameSize.height + WorksheetGraphicalView.cellHeight);
      }
    });
    pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    pane.getHorizontalScrollBar().addAdjustmentListener(adjustmentEvent -> {
      JScrollBar jsb = (JScrollBar) adjustmentEvent.getAdjustable();
      if (jsb.getValue() + jsb.getModel().getExtent() == jsb.getMaximum()) {
        this.frameSize.setSize(frameSize.width + cellWidth, frameSize.height);
      }
    });
    this.add(pane);
    this.setSize(new Dimension(800, 500));
  }

  /**
   * Gets the worksheet panel used in this graphical view.
   *
   * @return the worksheet panel used
   */
  WorksheetPanel getWorksheetPanel() {
    return wp;
  }

  /**
   * Gets the size of the text field to be used in the toolbar.
   *
   * @return a dimension representing how large the text field should be
   */
  Dimension getTextFieldSize() {
    return textFieldSize;
  }

  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public void addFeature(Feature feature) {
    //do nothing
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    this.textFieldSize.setSize(this.getWidth() - WorksheetToolbarView.buttonSpace,
            textFieldSize.getHeight());
    if (this.getHeight() > frameSize.height) {
      frameSize.setSize(frameSize.width, this.getHeight());
    }
    if (this.getWidth() > this.frameSize.width) {
      frameSize.setSize(this.getWidth(), frameSize.height);
    }
    this.repaint();
  }
}
