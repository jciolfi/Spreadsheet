package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a spreadsheet function that applies a function to a list of Formulas, like sum or less
 * than.
 */
public interface SpreadsheetFunc {

  /**
   * Apply the function to the given input, and throw an error if the input is invalid (e.g. too
   * many arguments for a function that only takes in two arguments).
   * @param args the list of Formula arguments
   * @return the result of the function to the list of arguments
   */
  Value apply(ArrayList<Value> args);

  @Override
  String toString();

  <R> R accept(SpreadsheetFuncVisitor<R> visitor);
}
