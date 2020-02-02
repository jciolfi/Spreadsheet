package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a boolean value for a cell.
 */
public class ValueBoolean implements Value {
  private boolean b;

  public ValueBoolean(boolean b) {
    this.b = b;
  }

  /**
   * Return the primitive boolean represented by this ValueBoolean.
   * @return the boolean this Value represents
   */
  boolean getBoolean() {
    return b;
  }

  @Override
  public <R> R accept(ValueVisitor<R> v) {
    return v.visitBoolean(this);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitValueBoolean(this);
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
  public Formula getCopy() {
    return new ValueBoolean(b);
  }

  @Override
  public String toString() {
    return String.valueOf(b);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    ValueBoolean that = (ValueBoolean)other;
    return this.b == that.b;
  }

  @Override
  public int hashCode() {
    return Objects.hash(b);
  }
}
