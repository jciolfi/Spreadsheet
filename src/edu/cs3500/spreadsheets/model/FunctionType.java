package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a function type, such as sum or less than, that takes in either two cells or any
 * arbitrary number of cells.
 */
public class FunctionType implements Formula {
  private SpreadsheetFunc func;
  private ArrayList<Formula> args;

  /**
   * Constructor for a FunctionType that takes in the function and the list of arguments it will be
   * applied to.
   *
   * @param func the SpreadsheetFunc
   * @param args the list of Formulas that the spreadsheet func will be applied to
   */
  public FunctionType(SpreadsheetFunc func, ArrayList<Formula> args) {
    this.func = func;
    this.args = args;
  }

  /**
   * Gets the spreadsheet function of this formula.
   *
   * @return a SpreadsheetFunc contained in this formula
   */
  SpreadsheetFunc getSpreadsheetFunc() {
    return func;
  }

  /**
   * Gets the list of formulas that is a copy of the list of arguments that this function
   * operates over.
   *
   * @return a list of the arguments this uses
   */
  ArrayList<Formula> getArgs() {
    return new ArrayList<>(args);
  }

  @Override
  public Value evaluate() {
    ArrayList<Value> argVals = new ArrayList<>();
    for (Formula raw : args) {
      for (Formula f : raw.getParts()) {
        if (f != null) {
          //only evaluate if no errors
          argVals.add(f.evaluate());
        }
      }
    }
    if (argVals.size() == 0) {
      return new ValueError("#EMPTY!");
    }
    return func.apply(argVals);
  }

  @Override
  public ArrayList<Formula> getParts() {
    return new ArrayList<>(List.of(this));
  }

  @Override
  public Formula getCopy() {
    return new FunctionType(this.func, this.args);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFunctionType(this);
  }

  @Override
  public String toString() {
    StringBuilder funcContents = new StringBuilder();
    for (int i = 0; i < args.size(); i++) {
      String toAdd = args.get(i).toString();
      if (toAdd.charAt(0) == '=') {
        toAdd = toAdd.substring(1);
      }
      funcContents.append(toAdd);
      if (i < args.size() - 1) {
        funcContents.append(" ");
      }
    }
    return "=(" + func.toString() + " " + funcContents + ")";
  }
}
