package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single reference to another cell, where this formula depends on another cell even
 * when that cell changes.
 */
public class SingleReference implements Formula {
  private Coord pos;
  private WorksheetModel model;

  /**
   * Constructor that takes in the coordinate that this points to and the model that the cells
   * are in.
   *
   * @param pos the coord of the cell that this points to
   * @param model the worksheet the cell is in
   */
  public SingleReference(Coord pos, WorksheetModel model) {
    if (model == null || pos == null) {
      throw new IllegalArgumentException("null inputs");
    }
    this.pos = pos;
    this.model = model;
    if (!model.getNonEmptyCells().contains(pos)) {
      this.model.setCell(pos, null);
    }
  }

  /**
   * Gets the coord that this cell refers to.
   * @return the Coord that this cell depends on
   */
  Coord getPos() {
    return new Coord(pos.col, pos.row);
  }

  @Override
  public Value evaluate() {
    Cell reference = (Cell)model.getCellAt(pos);
    if (reference.isInCycle()) {
      return new ValueError("#REF!");
    } else {
      return reference.getValue();
    }
  }

  @Override
  public ArrayList<Formula> getParts() {
    return new ArrayList<>(List.of(this));
  }

  @Override
  public Formula getCopy() {
    return new SingleReference(this.pos, this.model);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitSingleRef(this);
  }

  @Override
  public String toString() {
    return Coord.colIndexToName(pos.col) + pos.row;
  }
}