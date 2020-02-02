package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

public interface Graph {
  /**
   * Gets the title of this graph
   *
   * @return the String text that of the title of this graph.
   */
  String getTitle();

  /**
   * Allows updating the title of the graph to the desired String.
   *
   * @param newTitle the new title of this graph
   */
  void updateTitle(String newTitle);

  /**
   * Gets the coordinates pointing to the cells in the worksheet that are used in this graph.
   *
   * @return a list of coordinates for the cells to be represented in this graph.
   */
  ArrayList<Coord> graphBounds();
}
