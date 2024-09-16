package fr.univcotedazur.softwareengineering.sudoku.components;

import java.util.List;

/**
 * Interface for a row, column or box of the sudoku grid.
 */
public interface RowColumnBox {
    Cell getCell(int index);
    List<Cell> getCells();
    void setValue(int index, int value);
    int getValue(int index);
}
