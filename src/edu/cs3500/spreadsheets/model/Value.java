package edu.cs3500.spreadsheets.model;

/**
 * Represents a single value that a cell can contain, which is one of a boolean, String, or double.
 */
public interface Value extends Formula {

  /**
   * Apply the given visitor on this, which returns a different type based on the given visitor.
   * @param v the visitor that does something based on which type of value it gets passed
   * @param <R> the return type of that visitor
   * @return a return type of that visitor based on the visitor's purpose
   */
  <R> R accept(ValueVisitor<R> v);
}
