package fr.univcotedazur.softwareengineering.sudoku.components;

public class Box implements RowColumnBox {
    private Cell[] cells;

    public Box() {
        cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            cells[i] = new Cell(0);
        }
    }

    public Cell getCell(int index) {
        return cells[index];
    }

    public void setValue(int index, int value) {
        cells[index].setValue(value);
    }

    public int getValue(int index) {
        return cells[index].getValue();
    }
}
