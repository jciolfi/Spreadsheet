package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a String value for a cell.
 */
public class ValueString implements Value {
  private String s;

  public ValueString(String s) {
    this.s = s;
  }

  /**
   * Return the String represented by this Value.
   * @return the String that this value represents
   */
  String getString() {
    return s;
  }

  @Override
  public Formula getCopy() {
    return new ValueString(s);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitValueString(this);
  }

  @Override
  public <R> R accept(ValueVisitor<R> v) {
    return v.visitString(this);
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
    return "\"" + s + "\"";
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    ValueString that = (ValueString)other;
    return this.s.equals(that.s);
  }

  @Override
  public int hashCode() {
    return Objects.hash(s);
  }
}
