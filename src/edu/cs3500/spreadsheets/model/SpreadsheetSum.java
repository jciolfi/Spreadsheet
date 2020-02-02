package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a summation for any arbitrary number of cells, which can take in any cell value
 * including non-double cells.
 */
public class SpreadsheetSum implements SpreadsheetFunc {

  @Override
  public Value apply(ArrayList<Value> args) {
    double result = 0;
    try {
      for (Value a : args) {
        if (a != null) {
          result += new ValueAddVisitor().apply(a);
        }
      }
      return new ValueDouble(result);
    } catch(UnsupportedOperationException e) {
      return new ValueError("#VALUE!");
    }
  }

  @Override
  public String toString() {
    return "SUM";
  }

  @Override
  public <R> R accept(SpreadsheetFuncVisitor<R> visitor) {
    return visitor.visitSum(this);
  }
}
