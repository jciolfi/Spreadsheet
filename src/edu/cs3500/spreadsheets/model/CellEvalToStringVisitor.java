package edu.cs3500.spreadsheets.model;

/**
 * Get the string representation of this cell's evaluated value.
 */
public class CellEvalToStringVisitor implements CellVisitor<String> {

  @Override
  public String visitCell(Cell c) {
    if (c.isInCycle()) {
      return "#REF!";
    } else {
      if (c.getFormula() instanceof ValueError) {
        return "#ERROR!";
      }
      Value v = c.getValue();
      if (v == null) {
        return "";
      }
      return v.toString();
    }
  }
}
