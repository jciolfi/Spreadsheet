package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetReadOnlyModel;

/**
 * Renders a pie chart based on the cells passed to this class from a worksheet. The greater the
 * quantity of a evaluated value, the larger the region is in the rendered pie chart.
 */
public class PieGraph extends JFrame implements GraphView {

  /**
   * Constructor for a pie graph that takes in the model of the spreadsheet it is displaying from,
   * the title of the graph, and an object from the model that has access to
   * @param model the model this pie graph draws from
   * @param title the title of this pie graph
   * @param refs the coordinates this depends on to draw
   */
  public PieGraph(WorksheetReadOnlyModel model, String title, ArrayList<Coord> refs) {
    if (model == null || title == null || refs == null) {
      throw new IllegalArgumentException("null inputs");
    }
    this.setLayout(new BorderLayout());
    this.setTitle(title);
    PieGraphPanel pgp = new PieGraphPanel(model, refs);
    this.add(pgp, BorderLayout.CENTER);
    this.setSize(new Dimension(400, 400));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
  }

  @Override
  public void display() {
    this.setVisible(true);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    this.repaint();
  }
}
