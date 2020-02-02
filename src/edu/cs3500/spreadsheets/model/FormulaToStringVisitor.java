package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a visitor that transforms a Formula to its String contents.
 */
public class FormulaToStringVisitor implements FormulaVisitor<String> {
  @Override
  public String visitFunctionType(FunctionType ft) {
    SpreadsheetFunc func = ft.getSpreadsheetFunc();
    ArrayList<Formula> args = ft.getArgs();

    StringBuilder funcContents = new StringBuilder();
    for (int i = 0; i < args.size(); i++) {
      String toAdd = args.get(i).accept(this);
      if (toAdd.charAt(0) == '=') {
        toAdd = toAdd.substring(1);
      }
      funcContents.append(toAdd);
      if (i < args.size() - 1) {
        funcContents.append(" ");
      }
    }
    return "=(" + func.accept(new SpreadsheetFuncToStringVisitor()) + " " + funcContents + ")";
  }

  @Override
  public String visitSingleRef(SingleReference sr) {
    Coord ref = sr.getPos();
    return "=" + Coord.colIndexToName(ref.col) + ref.row;
  }

  @Override
  public String visitMultiRef(MultiReference mr) {
    ArrayList<Coord> refs = mr.getRefs();
    return Coord.colIndexToName(refs.get(0).col) + refs.get(0).row + ":"
            + Coord.colIndexToName(refs.get(refs.size() - 1).col) + refs.get(refs.size() - 1).row;
  }

  @Override
  public String visitColumnRef(ReferenceColumn cr) {
    return Coord.colIndexToName(cr.getStartCol()) + ":"
            + Coord.colIndexToName(cr.getEndCol());
  }

  @Override
  public String visitValueBoolean(ValueBoolean vb) {
    return new ValueConcatVisitor().apply(vb);
  }

  @Override
  public String visitValueDouble(ValueDouble vd) {
    return new ValueConcatVisitor().apply(vd);
  }

  @Override
  public String visitValueString(ValueString vs) {
    return new ValueConcatVisitor().apply(vs);
  }

  @Override
  public String visitValueError(ValueError ve) {
    return ve.getError();
  }
}
