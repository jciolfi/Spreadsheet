package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a cell in a spreadsheet with a formula that can be evaluated.
 */
public class Cell implements ICell {
  private Formula f;
  private String rawContents;
  private ArrayList<Coord> dependsOn;
  private boolean inCycle;

  public Cell(String rawContents) {
    this(null, null);
  }

  /**
   * Constructor for a cell that takes in a Formula and sets its value field to the evaluated
   * Formula. A Value field was added so the formula didn't need to be evaluated multiple times when
   * trying to access this cell's effective value.
   *
   * @param rawContents the inputted string for this cell
   * @param f           the Formula for this cell
   */
  public Cell(String rawContents, Formula f) {
    if (rawContents == null || f == null) {
      this.rawContents = "";
      this.f = null;
    } else {
      this.rawContents = rawContents;
      this.f = f;
    }
    this.dependsOn = new ArrayList<>();
    this.inCycle = false;
  }

  /**
   * Determines if this cell's formula is involved in a cyclical reference.
   *
   * @return true if the formula is in a cyclical reference, false otherwise.
   */
  boolean isInCycle() {
    return inCycle;
  }

  /**
   * Updates whether or not this cell is in a cyclical reference.
   *
   * @param b the boolean representing if this cell is in a cycle
   */
  void updateCycle(boolean b) {
    this.inCycle = b;
  }

  @Override
  public Formula getFormula() {
    if (f == null) {
      return null;
    }
    return f;
  }

  @Override
  public Value getValue() {
    if (f == null) {
      return null;
    } else if (inCycle) {
      return new ValueError("#REF!");
    }
    return f.evaluate();
  }

  @Override
  public String getRawContents() {
    return rawContents;
  }

  @Override
  public <R> R accept(CellVisitor<R> visitor) {
    return visitor.visitCell(this);
  }

  /**
   * Return a copy of a list of the coords pointing to the cells that this cell depends on, such
   * that if the contents at any of the coords changed, this cell's contents may change too.
   *
   * @return an ArrayList of the coords that this cell depends on
   */
  ArrayList<Coord> getDependsOn() {
    return new ArrayList<>(dependsOn);
  }

  /**
   * Adds every item in the given list of Coords to this cell's list of coords it depends on, if the
   * Coord is not already contained in the field.
   *
   * @param loc the list of Coords
   */
  void addDependsOn(ArrayList<Coord> loc) {
    for (Coord c : loc) {
      if (!dependsOn.contains(c)) {
        dependsOn.add(c);
      }
    }
  }

  @Override
  public String toString() {
    if (f == null) {
      return "";
    } else if (inCycle) {
      return "#REF!";
    }
    return this.accept(new CellEvalToStringVisitor());
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Cell that = (Cell) other;
    return (this.f == null && that.f == null) || ((this.f != null && that.f != null) &&
            this.accept(new CellEvalToStringVisitor()).equals(
                    that.accept(new CellEvalToStringVisitor())));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this);
  }
}
