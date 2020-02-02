package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a double for a cell.
 */
public class ValueDouble implements Value {
  private double d;

  public ValueDouble(double d) {
    this.d = d;
  }

  /**
   * Returns the primitive double represented by this ValueDouble.
   * @return the double used in this ValueDouble
   */
  double getDouble() {
    return this.d;
  }

  @Override
  public Formula getCopy() {
    return new ValueDouble(d);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitValueDouble(this);
  }

  @Override
  public <R> R accept(ValueVisitor<R> v) {
    return v.visitDouble(this);
  }

  @Override
  public Value evaluate() {
    return (Value)this.getCopy();
  }

  @Override
  public ArrayList<Formula> getParts() {
    return new ArrayList<>(List.of(this));
  }

  @Override
  public String toString() {
    return this.accept(new ValueConcatVisitor());
    //return String.format("%f", d);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    ValueDouble that = (ValueDouble)other;
    return Math.abs(this.d - that.d) < .0000001;
  }

  @Override
  public int hashCode() {
    return Objects.hash(d);
  }
}
