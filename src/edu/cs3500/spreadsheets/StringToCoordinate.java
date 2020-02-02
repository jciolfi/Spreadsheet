package edu.cs3500.spreadsheets;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a function class that takes in a String representing a single reference, and splits
 * the string when the String switches from letters to numbers.
 */
public class StringToCoordinate implements Function<String, Coord> {

  /**
   * Converts the given String to a coordinate if it's well formed.
   * @param s the inputted coordinate in string form
   * @return the Coord corresponding to the inputted string
   * @throws IllegalArgumentException if the coordinate in string form is malformed
   */
  public Coord apply(String s) throws IllegalArgumentException {
    ArrayList<String> result = new ArrayList<>(List.of("", ""));
    if (s == null || s.length() < 2 || !Character.isLetter(s.charAt(0))) {
      throw new IllegalArgumentException("Invalid coordinate");
    }
    boolean addLetter = true;
    result.set(0, Character.toString(s.charAt(0)));
    for (int i = 1; i < s.length(); i++) {
      char curr = s.charAt(i);
      if (Character.isLetter(curr)) {
        if (addLetter) {
          result.set(0, result.get(0) + curr);
        } else {
          throw new IllegalArgumentException("Invalid coordinate");
        }
      } else if (Character.isDigit(curr)) {
        if (addLetter) {
          addLetter = false;
        }
        result.set(1, result.get(1) + curr);
      } else {
        throw new IllegalArgumentException("Invalid coordinate");
      }
    }
    return new Coord(Coord.colNameToIndex(result.get(0)),
            Integer.parseInt(result.get(1)));
  }
}
