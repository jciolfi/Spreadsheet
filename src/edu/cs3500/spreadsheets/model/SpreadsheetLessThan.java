package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a function object that determines if one cell is greater than another cell, and
 * supports more than type double inputs. Will throw an error if more than two inputs are being
 * checked.
 */
public class SpreadsheetLessThan implements SpreadsheetFunc {

  @Override
  public Value apply(ArrayList<Value> args) {
    if (args.size() != 2 || args.get(0) == null || args.get(1) == null) {
      throw new IllegalArgumentException("Can't make less than comparison");
    }
    try {
      boolean result = new ValueLessThanVisitor().apply(args.get(0))
              < new ValueLessThanVisitor().apply(args.get(1));
      return new ValueBoolean(result);
    } catch (UnsupportedOperationException e) {
      return new ValueError("#VALUE!");
    }
  }

  @Override
  public String toString() {
    return "<";
  }

  @Override
  public <R> R accept(SpreadsheetFuncVisitor<R> visitor) {
    return visitor.visitLessThan(this);
  }
}
