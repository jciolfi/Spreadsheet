package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Value;
import edu.cs3500.spreadsheets.model.ValueAddVisitor;
import edu.cs3500.spreadsheets.model.WorksheetReadOnlyModel;

/**
 * Represents the panel that the pie graph where the work to draw everything is handled by drawing
 * each entry in the pie chart piece by piece.
 */
public class PieGraphPanel extends JPanel {
  private ArrayList<Piece> pie;
  private WorksheetReadOnlyModel model;
  private ArrayList<Coord> refs;
  private ArrayList<Color> allColors = new ArrayList<>(List.of(new Color(255, 55, 55),
          Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.ORANGE, Color.PINK));

  /**
   * Constructor that takes in a model and a wrapper object that gets the cells to be used in the
   * spreadsheet.
   *
   * @param model the model this is drawing frmo
   * @param refs  the list of coordinates this graph refers to
   */
  public PieGraphPanel(WorksheetReadOnlyModel model, ArrayList<Coord> refs) {
    if (model == null || refs == null) {
      throw new IllegalArgumentException("null inputs");
    }
    this.model = model;
    this.refs = refs;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.setup();
    this.setPreferredSize(new Dimension(400, 400));
    if (this.pie.size() == 0) {
      g.drawString("No data", 175, 175);
    } else {
      double totalWeight = 0.0;
      for (Piece p : pie) {
        totalWeight += p.value;
      }
      double cumulativeValue = 0;
      Rectangle bounds = this.getBounds();
      int width = bounds.width;
      int height = bounds.height;

      for (Piece toDraw : pie) {
        g.setColor(toDraw.color);
        int angle = (int) (toDraw.value * 360 / totalWeight + .5);
        g.fillArc(0, 0, width, height, (int) (cumulativeValue * 360 / totalWeight + .5), angle);
        cumulativeValue += toDraw.value;
      }

      int angleSoFar = 0;
      for (Piece toDraw : pie) {
        int angle = (int) (toDraw.value * 360 / totalWeight + .5);
        g.setColor(Color.BLACK);
        double curAngle = angleSoFar + angle / 2.0;
        int xPos = width / 2 + (int) (150 * Math.cos(Math.toRadians(curAngle)) + .5);
        int yPos = height / 2 - (int) (150 * Math.sin(Math.toRadians(curAngle)) + .5);
        g.drawString(String.valueOf(toDraw.value), xPos, yPos);
        angleSoFar += angle;
      }
    }
  }

  /**
   * Gets the necessary information to build the pie chart by creating a list of all the pieces
   * necessary to display the pie chart.
   */
  private void setup() {
    ArrayList<Double> entries = new ArrayList<>();
    for (Coord c : refs) {
      Value v = model.getCellAt(c).getValue();
      if (v != null) {
        Double check;
        try {
          check = v.accept(new ValueAddVisitor());
        } catch (UnsupportedOperationException e) {
          check = 0.0;
        }
        if (check > .00000001) {
          entries.add(check);
        }
      }
    }
    pie = new ArrayList<>();
    int loopLength = entries.size();
    if (entries.size() > 1 && entries.size() % allColors.size() == 1) {
      loopLength--;
    }
    for (int i = 0; i < loopLength; i++) {
      this.pie.add(new Piece(entries.get(i), allColors.get(i % allColors.size())));
    }
    if (loopLength < entries.size()) {
      this.pie.add(new Piece(entries.get(entries.size() - 1), allColors.get(1)));
    }
  }

  /**
   * Represents a piece of the pie with a specified value and color.
   */
  private static class Piece {
    private double value;
    private Color color;

    /**
     * Constructor for a piece of the pie that takes in a value and a color.
     *
     * @param value the numerical value this has
     * @param c     the color this will be painted
     */
    private Piece(double value, Color c) {
      if (c == null) {
        throw new IllegalArgumentException("null color");
      }
      this.value = value;
      this.color = c;
    }
  }
}
