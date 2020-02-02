package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;

public final class ValueError implements Value {
  private String error;

  public ValueError(String error) {
    if (error == null) {
      throw new IllegalArgumentException("null string");
    }
    this.error = error;
  }

  /**
   * Returns the error represented by this ValueError.
   *
   * @return a String representing this error
   */
  String getError() {
    return error;
  }

  @Override
  public <R> R accept(ValueVisitor<R> v) {
    return v.visitValueError(this);
  }

  @Override
  public Value evaluate() {
    return new ValueError(error);
  }

  @Override
  public String toString() {
    return error;
  }

  @Override
  public ArrayList<Formula> getParts() {
    return new ArrayList<>(List.of(this));
  }

  @Override
  public Formula getCopy() {
    return new ValueError(this.error);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitValueError(this);
  }
}
