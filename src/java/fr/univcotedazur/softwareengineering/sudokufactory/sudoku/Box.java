package fr.univcotedazur.softwareengineering.sudokufactory.sudoku;

public class Box {
    private Cell[] cells;

    public Box() {
        cells = new Cell[9];
        for (int i = 0; i < 9; i++) {
            cells[i] = new Cell(0, false); // Standardmäßig alle Zellen leer und nicht initialisiert
        }
    }

    public Cell getCell(int index) {
        return cells[index];
    }

    public void setCell(int index, int value) {
        cells[index].setValue(value);
    }

    public int getValue(int index) {
        return cells[index].getValue();
    }
}
