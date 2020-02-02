package edu.cs3500.spreadsheets.model;

/**
 * Represents addition for Values, which supports adding booleans and Strings (which do not effect
 * the sum).
 */
public class ValueAddVisitor implements ValueVisitor<Double> {

  @Override
  public Double apply(Value v) {
    return v.accept(this);
  }

  @Override
  public Double visitBoolean(ValueBoolean vb) {
    return 0.0;
  }

  @Override
  public Double visitDouble(ValueDouble vd) {
    return vd.getDouble();
  }

  @Override
  public Double visitString(ValueString vs) {
    return 0.0;
  }

  @Override
  public Double visitValueError(ValueError ve) {
    throw new UnsupportedOperationException("can't handle a cell with an invalid formula");
  }
}
