package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

import edu.cs3500.spreadsheets.StringToCoordinate;
import edu.cs3500.spreadsheets.controller.Feature;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetReadOnlyModel;

/**
 * Represents a view with a toolbar below the spreadsheet view, that shows a cell's raw contents
 * when a user clicks on a cell, and has check and x buttons to confirm editing contents of a cell.
 * Also can handle saving the current state of the worksheet and loading the first 50 triangular
 * numbers.
 */
public class WorksheetToolbarView implements WorksheetView {
  private WorksheetReadOnlyModel model;
  private WorksheetGraphicalView view;
  private JTextField textField;
  private JButton checkButton;
  private JButton loadButton;
  private JButton deleteCell;
  protected static int buttonSpace = 0;

  /**
   * Constructor to construct the view with scrolling and setting the bounds for how much will be
   * drawn based on the given read-only worksheet.
   *
   * @param model the read-only Worksheet model
   */
  public WorksheetToolbarView(WorksheetReadOnlyModel model) {
    if (model == null) {
      throw new IllegalArgumentException("null model");
    }
    this.model = model;
    this.view = new WorksheetGraphicalView(model);
    this.checkButton = new JButton("✔");
    JButton xButton = new JButton("✖");
    JButton saveButton = new JButton("save");
    this.loadButton = new JButton("load");
    this.deleteCell = new JButton();
    JButton graphButton = new JButton("graph");

    this.textField = new JTextField();
    //5 buttons, (5 + 1 = 6) six 10px spacing between buttons and border in toolbar
    buttonSpace = checkButton.getPreferredSize().width + xButton.getPreferredSize().width
            + saveButton.getPreferredSize().width + loadButton.getPreferredSize().width +
            + graphButton.getPreferredSize().width + (6 * 10);
    textField.setPreferredSize(new Dimension(view.getTextFieldSize().width - buttonSpace,
            view.getTextFieldSize().height));

    JPanel toolbar = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        textField.setPreferredSize(view.getTextFieldSize());
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(view.getTextFieldSize().width + buttonSpace, 40);
      }
    };

    toolbar.add(checkButton);
    toolbar.add(xButton);
    toolbar.add(saveButton);
    toolbar.add(loadButton);
    toolbar.add(graphButton);
    toolbar.add(textField);
    view.add(toolbar, BorderLayout.SOUTH);
    view.getWorksheetPanel().addMouseListener(new MouseHandler(view.getWorksheetPanel()));
    view.getWorksheetPanel().addKeyListener(new KeyHandler(view.getWorksheetPanel()));

    xButton.addActionListener(ActionEvent -> {
      Coord selected = view.getWorksheetPanel().getSelected();
      if (selected != null) {
        textField.setText(model.getCellAt(selected).getRawContents());
      }
    });
    saveButton.addActionListener(ActionEvent -> {
      try {
        PrintWriter pw = new PrintWriter("resources/worksheet.txt");
        WorksheetTextualView view = new WorksheetTextualView(model, pw);
        view.render();
        pw.close();
      } catch (FileNotFoundException e) {
        System.out.println("Could not find worksheet.txt:\n" + e.getMessage());
      }
    });
    graphButton.addActionListener(ActionEvent -> {
      try {
        String title = JOptionPane.showInputDialog("input title for pie chart");
        String coord1 = JOptionPane.showInputDialog("input starting coordinate for pie chart");
        Coord start = new StringToCoordinate().apply(coord1);
        String coord2 = JOptionPane.showInputDialog("input ending coordinate for pie chart");
        Coord end = new StringToCoordinate().apply(coord2);
        if (start.col > end.col) {
          throw new IllegalArgumentException("coordinates invalid");
        }
        ArrayList<Coord> refs = new ArrayList<>();
        //pie charts only read from one column
        for (int row = start.row; row <= end.row; row++) {
          refs.add(new Coord(start.col, row));
        }
        PieGraph pieGraph = new PieGraph(model, title, refs);
        pieGraph.display();
      } catch (IllegalArgumentException e) {
        System.out.println("could not render graph: " + e.getMessage());
      }
    });
  }

  @Override
  public void render() {
    view.render();
  }

  @Override
  public void addFeature(Feature f) {
    WorksheetPanel wp = view.getWorksheetPanel();

    checkButton.addActionListener(ActionEvent -> {
      wp.setFocusable(true);
      wp.requestFocus();
      Coord selected = view.getWorksheetPanel().getSelected();
      if (selected != null) {
        String contents = textField.getText();
        f.updateCell(selected, contents);
        view.render();
      }
    });

    loadButton.addActionListener(ActionEvent -> {
      wp.setFocusable(true);
      wp.requestFocus();
      try {
        Scanner s = new Scanner(new FileReader("resources/triangle.txt"));
        for (Coord c : this.model.getNonEmptyCells()) {
          f.removeCell(c);
        }
        while (s.hasNext()) {
          String line = s.nextLine();
          int split = line.indexOf(' ');
          if (split < 2 || split == line.length() - 1) {
            System.out.println("invalid formatting");
            break;
          } else {
            Coord where = new StringToCoordinate().apply(line.substring(0, split));
            String contents = line.substring(split + 1);
            f.updateCell(where, contents);
          }
        }
        view.getWorksheetPanel().updateSelected(null);
        view.render();
        this.textField.setText("");
      } catch (FileNotFoundException e) {
        System.out.println("could not find triangle.txt:\n" + e.getMessage());
      }
    });

    deleteCell.addActionListener(ActionEvent -> {
      wp.setFocusable(true);
      wp.requestFocus();
      Coord selected = view.getWorksheetPanel().getSelected();
      if (selected != null) {
        f.removeCell(selected);
        view.render();
        this.textField.setText("");
      }
    });
  }

  /**
   * Represents a class for a mouse click that only handles when the mouse button was pressed to
   * determine which cell to highlight.
   */
  class MouseHandler extends MouseAdapter {
    private WorksheetPanel wp;

    /**
     * Constructor for a mouse handler that takes in the worksheet panel to be updated when clicked
     * on.
     *
     * @param wp the WorksheetPanel to be clicked on
     */
    MouseHandler(WorksheetPanel wp) {
      this.wp = wp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      int col = e.getX() / WorksheetGraphicalView.cellWidth + 1;
      int row = e.getY() / WorksheetGraphicalView.cellHeight + 1;
      Coord pos = new Coord(col, row);
      wp.updateSelected(pos);
      textField.setText(model.getCellAt(pos).getRawContents());
    }
  }

  /**
   * Represents a class to handle input from the keyboard, such as the arrow keys, to navigate
   * between a selected cell.
   */
  class KeyHandler implements KeyListener {
    private WorksheetPanel wp;

    /**
     * Constructor for a key handler that takes in the worksheet panel to be updated when specific
     * keys are pressed.
     *
     * @param wp the WorksheetPanel to be pressed on
     */
    KeyHandler(WorksheetPanel wp) {
      this.wp = wp;
      wp.setFocusable(true);
      wp.requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
      //do nothing
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
      if (wp.getSelected() != null) {
        Coord selected = wp.getSelected();
        Coord newSelected = null;
        switch (keyEvent.getKeyCode()) {
          case KeyEvent.VK_LEFT:
            if (selected.col > 1) {
              newSelected = new Coord(selected.col - 1, selected.row);
            }
            break;
          case KeyEvent.VK_RIGHT:
            newSelected = new Coord(selected.col + 1, selected.row);
            break;
          case KeyEvent.VK_UP:
            if (selected.row > 1) {
              newSelected = new Coord(selected.col, selected.row - 1);
            }
            break;
          case KeyEvent.VK_DOWN:
            newSelected = new Coord(selected.col, selected.row + 1);
            break;
          case KeyEvent.VK_BACK_SPACE:
            deleteCell.doClick();
            break;
          default:
            newSelected = null;
        }
        if (newSelected != null) {
          wp.updateSelected(newSelected);
          textField.setText(model.getCellAt(newSelected).getRawContents());
        }
      }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
      //do nothing
    }
  }
}
