package edu.cs3500.spreadsheets.model;

/**
 * Represents a less than operation between two Values, which supports booleans and strings which
 * every double is less than.
 */
public class ValueLessThanVisitor implements ValueVisitor<Double> {

  @Override
  public Double apply(Value v) {
    return v.accept(this);
  }

  @Override
  public Double visitBoolean(ValueBoolean vb) {
    return Double.MAX_VALUE;
  }

  @Override
  public Double visitDouble(ValueDouble vd) {
    return vd.getDouble();
  }

  @Override
  public Double visitString(ValueString vs) {
    return Double.MAX_VALUE;
  }

  @Override
  public Double visitValueError(ValueError ve) {
    throw new UnsupportedOperationException("can't handle a cell with an invalid formula");
  }
}
