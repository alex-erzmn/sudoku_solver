package fr.univcotedazur.softwareengineering.sudoku.components;

public interface RowColumnBox {
    Cell getCell(int index);
    void setValue(int index, int value);
    int getValue(int index);
}
