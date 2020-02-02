package edu.cs3500.spreadsheets.sexp;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.StringToCoordinate;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorksheetBasic;

/**
 * Represents a visitor that handles different Sexp's by getting a list of Coords that the Sexp
 * depends on.
 */
public class SexpToDependenciesVisitor implements SexpVisitor<ArrayList<Coord>> {
  private WorksheetBasic model;
  private Coord where;

  /**
   * Constructor for this visitor that takes in the worksheet model that the Sexp exists in.
   *
   * @param model the worksheet
   */
  public SexpToDependenciesVisitor(WorksheetBasic model, Coord where) {
    if (model == null || where == null) {
      throw new IllegalArgumentException("null inputs");
    }
    this.model = model;
    this.where = where;
  }

  @Override
  public ArrayList<Coord> visitBoolean(boolean b) {
    return new ArrayList<>();
  }

  @Override
  public ArrayList<Coord> visitNumber(double d) {
    return new ArrayList<>();
  }

  @Override
  public ArrayList<Coord> visitSList(List<Sexp> l) {
    ArrayList<Coord> result = new ArrayList<>();
    ArrayList<String> possibleFuncs =
            new ArrayList<>(List.of("SUM", "PRODUCT", "<", "CONCATENATE"));
    if (possibleFuncs.contains(l.get(0).toString())) {
      for (int i = 1; i < l.size(); i++) {
        result.addAll(l.get(i).accept(new SexpToDependenciesVisitor(model, where)));
      }
    } else {
      throw new IllegalArgumentException("malformed Sexp");
    }
    return result;
  }

  @Override
  public ArrayList<Coord> visitSymbol(String s) {
    if (!s.contains(":")) {
      Coord pointer = new StringToCoordinate().apply(s);
      if (!model.getNonEmptyCells().contains(where)) {
        this.model.setCell(where, null);
      }
      return new ArrayList<>(List.of(pointer));
    } else {
      int split = s.indexOf(':');
      String left = s.substring(0, split);
      String right = s.substring(split + 1);
      ArrayList<Coord> result = new ArrayList<>();
      if (s.matches(".*\\d.*")) {
        Coord start = new StringToCoordinate().apply(left);
        Coord end = new StringToCoordinate().apply(right);
        for (int c = start.col; c <= end.col; c++) {
          for (int r = start.row; r <= end.row; r++) {
            if (model.getCellAt(new Coord(c, r)).getValue() == null) {
              model.setCell(new Coord(c, r), null);
            }
            result.add(new Coord(c, r));
          }
        }
        return result;
      } else {
        int startCol = Coord.colNameToIndex(left);
        int endCol = Coord.colNameToIndex(right);
        for (Coord c : model.getNonEmptyCells()) {
          if (startCol <= c.col && c.col <= endCol) {
            result.add(new Coord(c.col, c.row));
          }
        }
        if (startCol <= where.col && where.col <= endCol) {
          result.add(where);
        }
        return result;
      }
    }
  }

  @Override
  public ArrayList<Coord> visitString(String s) {
    return new ArrayList<>();
  }
}
