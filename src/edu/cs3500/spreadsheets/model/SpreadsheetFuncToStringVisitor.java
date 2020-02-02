package edu.cs3500.spreadsheets.model;

/**
 * Represents a visitor that converts a SpreadsheetFunc to its String representation for every
 * SpreadsheetFunc.
 */
public class SpreadsheetFuncToStringVisitor implements SpreadsheetFuncVisitor<String> {
  @Override
  public String visitConcat(SpreadsheetConcat s) {
    return "CONCATENATE";
  }

  @Override
  public String visitLessThan(SpreadsheetLessThan s) {
    return "<";
  }

  @Override
  public String visitProd(SpreadsheetProd s) {
    return "PRODUCT";
  }

  @Override
  public String visitSum(SpreadsheetSum s) {
    return "SUM";
  }
}
