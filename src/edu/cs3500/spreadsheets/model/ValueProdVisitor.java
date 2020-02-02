package edu.cs3500.spreadsheets.model;

/**
 * Represents a multiplictaion between values, which supports booleans and strings which should not
 * effect the product.
 */
public class ValueProdVisitor implements ValueVisitor<Double> {

  @Override
  public Double apply(Value v) {
    return v.accept(this);
  }

  @Override
  public Double visitBoolean(ValueBoolean vb) {
    return 1.0;
  }

  @Override
  public Double visitDouble(ValueDouble vd) {
    return vd.getDouble();
  }

  @Override
  public Double visitString(ValueString vs) {
    return 1.0;
  }

  @Override
  public Double visitValueError(ValueError ve) {
    throw new UnsupportedOperationException("can't handle a cell with an invalid formula");
  }
}
