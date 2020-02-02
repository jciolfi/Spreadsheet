package edu.cs3500.spreadsheets.model;

/**
 * Represents a visitor for a spreadsheet function that handles each type of SpreadsheetFunc.
 *
 * @param <R> The return type of the specific visitor
 */
public interface SpreadsheetFuncVisitor<R> {
  /**
   * Handle a Spreadsheet concatenate function.
   *
   * @param s the SpreadsheetConcat function
   * @return an R object converted from a SpreadsheetFunc
   */
  R visitConcat(SpreadsheetConcat s);

  /**
   * Handle a Spreadsheet less than function.
   *
   * @param s the SpreadsheetLessThan function
   * @return an R object converted from a SpreadsheetFunc
   */
  R visitLessThan(SpreadsheetLessThan s);

  /**
   * Handle a Spreadsheet product function.
   *
   * @param s the SpreadsheetProd function
   * @return an R object converted from a SpreadsheetFunc
   */
  R visitProd(SpreadsheetProd s);

  /**
   * Handle a Spreadsheet sum function.
   *
   * @param s the SpreadsheetSum function
   * @return an R object converted from a SpreadsheetFunc
   */
  R visitSum(SpreadsheetSum s);
}
