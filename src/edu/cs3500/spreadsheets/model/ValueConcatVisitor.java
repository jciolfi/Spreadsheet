package edu.cs3500.spreadsheets.model;

/**
 * Represents a visitor for Values that gets the String value for each type of Value.
 */
public class ValueConcatVisitor implements ValueVisitor<String> {
  @Override
  public String apply(Value v) {
    return v.accept(this);
  }

  @Override
  public String visitBoolean(ValueBoolean vb) {
    return String.valueOf(vb.getBoolean());
  }

  @Override
  public String visitDouble(ValueDouble vd) {
    if (vd.getDouble() == (long)vd.getDouble()) {
      return String.format("%d", (long)vd.getDouble());
    }
    return String.format("%s", vd.getDouble());
  }

  @Override
  public String visitString(ValueString vs) {
    return "\"" + vs.getString() + "\"";
  }

  @Override
  public String visitValueError(ValueError ve) {
    throw new UnsupportedOperationException("can't handle a cell with an invalid formula");
  }
}
