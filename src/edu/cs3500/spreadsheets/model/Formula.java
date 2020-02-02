package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a non-empty cell, which can be one of a Value, Function, or Reference.
 */
public interface Formula {

  /**
   * Evaluates this Formula to give a Value, which represents a boolean, String, or double.
   *
   * @return this Formula's effective value.
   */
  Value evaluate();

  /**
   * Returns a list of the parts in this Formula, which is just a list of itself if it's not a
   * reference to multiple cells.
   *
   * @return a list of Formulas that need to be evaluated
   */
  ArrayList<Formula> getParts();

  /**
   * Returns a copy of this formula which is semantically the same, but doesn't have an alias to
   * this.
   *
   * @return a new Formula equal to this formula.
   */
  Formula getCopy();

  /**
   * Handles transforming a formula to another object.
   *
   * @param visitor the visitor that handles each subclass of Formula
   * @param <R> the resulting return type class
   * @return a class of type R that is transformed from this Formula
   */
  <R> R accept(FormulaVisitor<R> visitor);
}
