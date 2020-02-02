package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Concatenates the evaluated result of an arbitrary number of cells together. Supports all cell
 * types including ValueDouble and ValueBoolean.
 */
public class SpreadsheetConcat implements SpreadsheetFunc {

  @Override
  public Value apply(ArrayList<Value> args) {
    try {
      StringBuilder result = new StringBuilder();
      for (Value a : args) {
        if (a != null) {
          result.append(a.accept(new ValueConcatVisitor()));
        }
      }
      return new ValueString(result.toString());
    } catch (UnsupportedOperationException e) {
      return new ValueError("#VALUE!");
    }
  }

  @Override
  public String toString() {
    return "CONCATENATE";
  }

  @Override
  public <R> R accept(SpreadsheetFuncVisitor<R> visitor) {
    return visitor.visitConcat(this);
  }
}
