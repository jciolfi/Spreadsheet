package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;

public class PieGraphWorksheet implements Graph {
  private String title;
  private Coord start;
  private Coord end;

  public PieGraphWorksheet(String title, Coord start, Coord end) {
    if (start == null || end == null || start.col > end.col || start.row > end.row) {
      throw new IllegalArgumentException("invalid coordinates");
    }
    this.title = title;
    this.start = start;
    this.end = end;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void updateTitle(String newTitle) {
    this.title = newTitle;
  }

  @Override
  public ArrayList<Coord> graphBounds() {
    ArrayList<Coord> result = new ArrayList<>();
    for (int row = start.row; row <= end.row; row++) {
      result.add(new Coord(start.col, row));
    }
    return result;
  }
}
