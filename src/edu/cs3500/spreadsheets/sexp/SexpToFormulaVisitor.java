package edu.cs3500.spreadsheets.sexp;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.StringToCoordinate;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.FunctionType;
import edu.cs3500.spreadsheets.model.MultiReference;
import edu.cs3500.spreadsheets.model.ReferenceColumn;
import edu.cs3500.spreadsheets.model.SingleReference;
import edu.cs3500.spreadsheets.model.SpreadsheetConcat;
import edu.cs3500.spreadsheets.model.SpreadsheetFunc;
import edu.cs3500.spreadsheets.model.SpreadsheetLessThan;
import edu.cs3500.spreadsheets.model.SpreadsheetProd;
import edu.cs3500.spreadsheets.model.SpreadsheetSum;
import edu.cs3500.spreadsheets.model.ValueBoolean;
import edu.cs3500.spreadsheets.model.ValueDouble;
import edu.cs3500.spreadsheets.model.ValueString;
import edu.cs3500.spreadsheets.model.WorksheetBasic;

/**
 * Represents a visitor that transforms a Sexp to its corresponding Formula to be used in the
 * model.
 */
public class SexpToFormulaVisitor implements SexpVisitor<Formula> {
  private WorksheetBasic model;

  /**
   * Constructor for this visitor that transforms an Sexp to a Formula for a given worksheet.
   *
   * @param model the Worksheet the Sexp pertains to
   */
  public SexpToFormulaVisitor(WorksheetBasic model) {
    if (model == null) {
      throw new IllegalArgumentException("null model");
    }
    this.model = model;
  }

  @Override
  public Formula visitBoolean(boolean b) {
    return new ValueBoolean(b);
  }

  @Override
  public Formula visitNumber(double d) {
    return new ValueDouble(d);
  }

  @Override
  public Formula visitSList(List<Sexp> l) {
    SpreadsheetFunc f;
    if (l.get(0).equals(new SSymbol("SUM"))) {
      f = new SpreadsheetSum();
    } else if (l.get(0).equals(new SSymbol("PRODUCT"))) {
      f = new SpreadsheetProd();
    } else if (l.get(0).equals(new SSymbol("<"))) {
      f = new SpreadsheetLessThan();
    } else if (l.get(0).equals(new SSymbol("CONCATENATE"))) {
      f = new SpreadsheetConcat();
    } else {
      throw new IllegalArgumentException(l.get(0).toString() + " is not a function type");
    }
    ArrayList<Formula> args = new ArrayList<>();
    for (int i = 1; i < l.size(); i++) {
      args.add(l.get(i).accept(new SexpToFormulaVisitor(model)));
    }
    return new FunctionType(f, args);
  }

  @Override
  public Formula visitSymbol(String s) {
    if (!s.contains(":")) {
      return new SingleReference(new StringToCoordinate().apply(s), model);
    } else {
      int split = s.indexOf(':');
      String left = s.substring(0, split);
      String right = s.substring(split + 1);
      if (s.matches(".*\\d.*")) {
        return new MultiReference(new StringToCoordinate().apply(s.substring(0, split)),
                new StringToCoordinate().apply(s.substring(split + 1)), model);
      } else {
        return new ReferenceColumn(Coord.colNameToIndex(left), Coord.colNameToIndex(right), model);
      }
    }
  }

  @Override
  public Formula visitString(String s) {
    return new ValueString(s);
  }
}
