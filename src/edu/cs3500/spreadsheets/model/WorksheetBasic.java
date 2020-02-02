package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpToDependenciesVisitor;
import edu.cs3500.spreadsheets.sexp.SexpToFormulaVisitor;

/**
 * Represents a worksheet that contains a collection of cells in specific coordinates. One
 * coordinate can only contain one cell.
 */
public class WorksheetBasic implements WorksheetModel, WorksheetReadOnlyModel {
  private HashMap<Coord, ICell> grid;
  private ArrayList<ArrayList<Coord>> allCycles;

  /**
   * Represents a constructor for a spreadsheet that takes in a hashmap of non-empty cells.
   *
   * @param grid the hashmap of cells
   */
  protected WorksheetBasic(HashMap<Coord, ICell> grid) {
    this.grid = grid;
    this.allCycles = new ArrayList<ArrayList<Coord>>();
  }

  @Override
  public void setCell(Coord coord, String rawContents) {
    if (coord == null) {
      throw new IllegalArgumentException("null coordinate");
    }
    if (rawContents == null) {
      grid.put(coord, new Cell(null, null));
      this.updateInCycle(coord, false);
    } else {
      try {
        String contents = rawContents;
        if (rawContents.startsWith("=")) {
          contents = contents.substring(1);
        }
        Sexp s = Parser.parse(contents);
        ArrayList<Coord> dependsOn = s.accept(new SexpToDependenciesVisitor(this, coord));
        Formula f = s.accept(new SexpToFormulaVisitor(this));
        Cell toAdd = new Cell(rawContents, f);
        toAdd.addDependsOn(dependsOn);
        grid.put(coord, toAdd);

        if (this.createsCycle(dependsOn, new ArrayList<>(List.of(coord)))) {
          //handle a cycle by setting inCycle field to true for each cell in the cycle
          this.updateInCycle(coord, true);
        } else {
          //if creating this cell broke it out of a cycle, re-update inCycle field to false
          this.updateInCycle(coord, false);
        }
      } catch (IllegalArgumentException e) {
        grid.put(coord, new Cell(rawContents, new ValueError(rawContents)));
      }
    }
  }

  /**
   * If the cell at the given position is involved in a cyclical reference, update its inCycle field
   * to the given boolean. If the given boolean is false and the given coord used to be involved in
   * a cycle, remove that cycle from the list of cycles allCycle.
   *
   * @param pos     the Coord of the cell
   * @param inCycle boolean representing if cells are involved in the cyclical reference
   */
  private void updateInCycle(Coord pos, boolean inCycle) {
    ArrayList<ArrayList<Coord>> toRemoveAllCycle = new ArrayList<>();
    for (ArrayList<Coord> loc : allCycles) {
      if (loc.contains(pos)) {
        for (Coord c : loc) {
          ((Cell) grid.get(c)).updateCycle(inCycle);
        }
        if (!inCycle) {
          toRemoveAllCycle.add(loc);
        }
      }
    }
    for (ArrayList<Coord> remove : toRemoveAllCycle) {
      allCycles.remove(remove);
    }
  }

  @Override
  public void removeCell(Coord coord) {
    this.setCell(coord, null);
  }

  /**
   * Determines if a cycle is found based on searching through the first given list of Coords by
   * keeping track if any of the items in seenSoFar are in refs.
   *
   * @param refs        the list of coords being searched through
   * @param initialSeen the list of coords already visited
   * @return true if cyclical data occurs, false otherwise
   */
  private boolean createsCycle(ArrayList<Coord> refs, ArrayList<Coord> initialSeen) {
    for (Coord c : refs) {
      ArrayList<Coord> seenSoFar = new ArrayList<>(initialSeen);
      if (seenSoFar.contains(c)) {
        allCycles.add(seenSoFar);
        return true;
      } else {
        seenSoFar.add(c);
        Cell toCheck = (Cell) grid.get(c);
        if (toCheck != null && this.createsCycle(toCheck.getDependsOn(), seenSoFar)) {
          allCycles.add(seenSoFar);
          return true;
        }
      }
    }
    return false;
  }


  @Override
  public ICell getCellAt(Coord pos) throws IllegalArgumentException {
    if (grid.containsKey(pos)) {
      Cell result = new Cell(grid.get(pos).getRawContents(), grid.get(pos).getFormula());
      if (((Cell) grid.get(pos)).isInCycle()) {
        result.updateCycle(true);
      }
      return result;
    } else {
      return new Cell(null, null);
    }
  }

  @Override
  public ArrayList<Coord> getNonEmptyCells() {
    if (grid.size() == 0) {
      return new ArrayList<>();
    }
    return new ArrayList<>(grid.keySet());
  }

  @Override
  public Coord getMaxBound() {
    Coord result = new Coord(1, 1);
    for (Coord curr : grid.keySet()) {
      if (curr.col > result.col) {
        result = new Coord(curr.col, result.row);
      }
      if (curr.row > result.row) {
        result = new Coord(result.col, curr.row);
      }
    }
    return result;
  }

  @Override
  public ArrayList<Graph> getGraphs() {
    return new ArrayList<>();
  }

  /**
   * Represents a class that builds instances of Worksheets. Allows users to build cell by cell by
   * inputting each cell's column, row, and contents as a String.
   */
  public static class Builder implements WorksheetReader.WorksheetBuilder<WorksheetBasic> {
    private WorksheetBasic modelSoFar;

    public Builder() {
      this.modelSoFar = new WorksheetBasic(new HashMap<>());
    }

    @Override
    public WorksheetReader.WorksheetBuilder<WorksheetBasic> createCell(int col, int row,
                                                                       String contents) {
      modelSoFar.setCell(new Coord(col, row), contents);
      return this;
    }

    @Override
    public WorksheetBasic createWorksheet() {
      return modelSoFar;
    }
  }
}
