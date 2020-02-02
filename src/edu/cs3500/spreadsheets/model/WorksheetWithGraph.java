package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashMap;

public class WorksheetWithGraph extends WorksheetBasic {
  private ArrayList<Graph> graphs;

  private WorksheetWithGraph(HashMap<Coord, ICell> grid) {
    super(grid);
  }

  private void addGraph(String type, String title, Coord start, Coord end) {
    if (type.toLowerCase().equals("pie")) {
      this.graphs.add(new PieGraphWorksheet(title, start, end));
    } else {
      System.out.println("currently only supporting pie graphs");
    }
  }

  @Override
  public ArrayList<Graph> getGraphs() {
    return new ArrayList<Graph>(graphs);
  }

  /**
   * Represents a class that builds instances of Worksheets. Allows users to build cell by cell by
   * inputting each cell's column, row, and contents as a String.
   */
  public static class GraphBuilder implements WorksheetReader.WorksheetWithGraphBuilder<WorksheetBasic> {
    private WorksheetWithGraph modelSoFar;

    public GraphBuilder() {
      this.modelSoFar = new WorksheetWithGraph(new HashMap<>());
    }

    @Override
    public WorksheetReader.WorksheetWithGraphBuilder<WorksheetBasic> createCell(int col, int row,
                                                                       String contents) {
      modelSoFar.setCell(new Coord(col, row), contents);
      return this;
    }

    @Override
    public WorksheetWithGraph createWorksheet() {
      return modelSoFar;
    }

    @Override
    public WorksheetReader.WorksheetWithGraphBuilder<WorksheetBasic> insertGraph(String type,
                                                                                 String title,
                                                                                 Coord start,
                                                                                 Coord end) {
      modelSoFar.addGraph(type, title, start, end);
      return this;
    }
  }
}
