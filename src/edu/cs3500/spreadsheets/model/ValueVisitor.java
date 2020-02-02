package edu.cs3500.spreadsheets.model;

/**
 * Represents a visitor that handles different values and returns different results for each value
 * based on the desired behavior of each value.
 *
 * @param <R> the return type for each value based on a specific visitor
 */
public interface ValueVisitor<R> {
  /**
   * Delegates to the specific Value and accepts this visitor to apply the visitor's desired
   * functionality.
   *
   * @param v the Value to delegate to
   * @return the correct return type for a specific visitor
   */
  R apply(Value v);

  /**
   * Handles a boolean value and returns the correct R related to that value.
   * @param vb the boolean value
   * @return the R related to the value based on the visitor's functionality
   */
  R visitBoolean(ValueBoolean vb);

  /**
   * Handles a double value and returns the correct R related to that value.
   * @param vd the double value
   * @return the R related to the value based on the visitor's functionality
   */
  R visitDouble(ValueDouble vd);

  /**
   * Handles a String value and returns the correct R related to that value.
   * @param vs the String value
   * @return the R related to the value based on the visitor's functionality
   */
  R visitString(ValueString vs);

  /**
   * Handles an Error value and returns the correct R related to that value.
   * @param ve the String error
   * @return the R related to the value based on the visitor's functionality
   */
  R visitValueError(ValueError ve);
}
