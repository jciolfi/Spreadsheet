package edu.cs3500.spreadsheets.model;

/**
 * Represents a visitor for a Formula that transforms a Formula into something else.
 */
public interface FormulaVisitor<R> {

  /**
   * Processes a FunctionType.
   *
   * @param ft the FunctionType
   * @return an object R returned by the implemented visitor
   */
  R visitFunctionType(FunctionType ft);

  /**
   * Processes a Single Reference to another cell.
   *
   * @param sr the SingleReference.
   * @return an object R returned by the implemented visitor
   */
  R visitSingleRef(SingleReference sr);

  /**
   * Processes a multi reference to multiple cells.
   *
   * @param mr the MultiReference
   * @return object R returned by the implemented visitor
   */
  R visitMultiRef(MultiReference mr);

  /**
   * Processes a column reference to multiple cells.
   *
   * @param cr the ReferenceColumn
   * @return object R returned by the implemented visitor
   */
  R visitColumnRef(ReferenceColumn cr);

  /**
   * Processes a boolean value.
   *
   * @param vb the ValueBoolean
   * @return object R returned by the implemented visitor
   */
  R visitValueBoolean(ValueBoolean vb);

  /**
   * Processes a double value.
   *
   * @param vd the ValueDouble
   * @return object R returned by the implemented visitor
   */
  R visitValueDouble(ValueDouble vd);

  /**
   * Processes a String value.
   *
   * @param vs the ValueString
   * @return object R returned by the implemented visitor
   */
  R visitValueString(ValueString vs);

  /**
   * Processes and Error value
   *
   * @param ve the ValueError
   * @return object R returned by the implemented visitor
   */
  R visitValueError(ValueError ve);
}
