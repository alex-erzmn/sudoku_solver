package fr.univcotedazur.softwareengineering.sudokufactory.sudoku;

import fr.univcotedazur.softwareengineering.client.SudokuObserver;
import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    private Row[] rows;
    private Column[] columns;
    private Box[] boxes;
    private List<SudokuObserver> observers;  // Liste der Observer

    public Sudoku() {
        rows = new Row[9];
        columns = new Column[9];
        boxes = new Box[9];
        observers = new ArrayList<>(); // Initialisierung der Observer-Liste

        for (int i = 0; i < 9; i++) {
            rows[i] = new Row();
            columns[i] = new Column();
            boxes[i] = new Box();
        }
    }

    // Methode, um einen Observer hinzuzufügen
    public void addObserver(SudokuObserver observer) {
        observers.add(observer);
    }

    // Methode, um alle Observer zu benachrichtigen
    private void notifyObservers() {
        for (SudokuObserver observer : observers) {
            observer.updateSudoku(); // Übergebe das aktuelle Board
        }
    }

    public Row getRow(int index) {
        return rows[index];
    }

    public Column getColumn(int index) {
        return columns[index];
    }

    public Box getBox(int index) {
        return boxes[index];
    }

    public void setCell(int rowIndex, int colIndex, int value) {
        getRow(rowIndex).setCell(colIndex, value);
        getColumn(colIndex).setCell(rowIndex, value);
        getBox(getBoxIndex(rowIndex, colIndex)).setCell(getCellIndexInBox(rowIndex, colIndex), value);
        notifyObservers(); // Benachrichtige Observer nach Änderung einer Zelle
    }

    public int getCell(int rowIndex, int colIndex) {
        return getRow(rowIndex).getValue(colIndex);
    }

    private int getBoxIndex(int rowIndex, int colIndex) {
        return (rowIndex / 3) * 3 + (colIndex / 3);
    }

    private int getCellIndexInBox(int rowIndex, int colIndex) {
        return (rowIndex % 3) * 3 + (colIndex % 3);
    }

    public List<Integer> calculatePossibleValues(int rowIndex, int colIndex) {
        List<Integer> possibleValues = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            possibleValues.add(i);
        }
        for (int i = 0; i < 9; i++) {
            possibleValues.remove(Integer.valueOf(getRow(rowIndex).getValue(i)));
            possibleValues.remove(Integer.valueOf(getColumn(colIndex).getValue(i)));
            possibleValues.remove(Integer.valueOf(getBox(getBoxIndex(rowIndex, colIndex)).getValue(i)));
        }
        return possibleValues;
    }

    public List<Integer> getPossibleValues(int rowIndex, int colIndex) {
        return getRow(rowIndex).getCell(colIndex).getPossibleValues();
    }

    public void addPossibleValue(int rowIndex, int colIndex, int value) {
        getRow(rowIndex).getCell(colIndex).addPossibleValue(value);
        getColumn(colIndex).getCell(rowIndex).addPossibleValue(value);
        getBox(getBoxIndex(rowIndex, colIndex)).getCell(getCellIndexInBox(rowIndex, colIndex)).addPossibleValue(value);
    }

    public void removePossibleValue(int rowIndex, int colIndex, int value) {
        getRow(rowIndex).getCell(colIndex).removePossibleValue(value);
        getColumn(colIndex).getCell(rowIndex).removePossibleValue(value);
        getBox(getBoxIndex(rowIndex, colIndex)).getCell(getCellIndexInBox(rowIndex, colIndex)).removePossibleValue(value);
    }

    public int[][] getSudokuGrid() {
        int[][] sudoku = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = getCell(i, j);
            }
        }
        return sudoku;
    }
}
