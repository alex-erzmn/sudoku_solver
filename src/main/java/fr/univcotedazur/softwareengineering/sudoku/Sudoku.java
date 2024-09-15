package fr.univcotedazur.softwareengineering.sudoku;

import fr.univcotedazur.softwareengineering.client.SudokuObserver;
import fr.univcotedazur.softwareengineering.sudoku.components.Box;
import fr.univcotedazur.softwareengineering.sudoku.components.Cell;
import fr.univcotedazur.softwareengineering.sudoku.components.Column;
import fr.univcotedazur.softwareengineering.sudoku.components.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a Sudoku grid. The grid is composed of 9 rows, 9 columns and 9 boxes.
 * Each row, column and box is composed of 9 cells. Each cell can have a value between 1 and 9.
 * The grid is initialized with all possible values for each cell.
 * @since 12/09/2024
 */
public class Sudoku {
    public static final int SIZE = 9;
    private final Row[] rows;
    private final Column[] columns;
    private final Box[] boxes;
    private final List<SudokuObserver> observers;

    public Sudoku() {
        rows = new Row[SIZE];
        columns = new Column[SIZE];
        boxes = new Box[SIZE];
        observers = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            rows[i] = new Row();
            columns[i] = new Column();
            boxes[i] = new Box();
        }
    }

    public void initializePossibleValues() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                List<Integer> possibleValues = calculatePossibleValues(row, col);
                for (Integer value : possibleValues) {
                    addPossibleValue(row, col, value);
                }
            }
        }
    }

    private List<Integer> calculatePossibleValues(int rowIndex, int colIndex) {
        List<Integer> possibleValues = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) {
            possibleValues.add(i);
        }
        for (int i = 0; i < SIZE; i++) {
            possibleValues.remove(Integer.valueOf(getRow(rowIndex).getValue(i)));
            possibleValues.remove(Integer.valueOf(getColumn(colIndex).getValue(i)));
            possibleValues.remove(Integer.valueOf(getBox(getBoxIndex(rowIndex, colIndex)).getValue(i)));
        }
        return possibleValues;
    }

    public void addObserver(SudokuObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (SudokuObserver observer : observers) {
            observer.updateSudoku(this);
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
        getRow(rowIndex).setValue(colIndex, value);
        getColumn(colIndex).setValue(rowIndex, value);
        getBox(getBoxIndex(rowIndex, colIndex)).setValue(getCellIndexInBox(rowIndex, colIndex), value);
        removePossibleValueFromPeers(rowIndex, colIndex, value);
        notifyObservers();
    }

    public Cell getCell(int rowIndex, int colIndex) {
        return getRow(rowIndex).getCell(colIndex);
    }

    public int getValue(int rowIndex, int colIndex) {
        return getRow(rowIndex).getValue(colIndex);
    }

    private int getBoxIndex(int rowIndex, int colIndex) {
        return (rowIndex / 3) * 3 + (colIndex / 3);
    }

    private int getCellIndexInBox(int rowIndex, int colIndex) {
        return (rowIndex % 3) * 3 + (colIndex % 3);
    }

    public Set<Integer> getPossibleValues(int rowIndex, int colIndex) {
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

    public void removePossibleValues(int rowIndex, int colIndex, Set<Integer> values) {
        for (Integer value : values) {
            removePossibleValue(rowIndex, colIndex, value);
        }
    }

    private void removePossibleValueFromPeers(int row, int col, int value) {
        for (int c = 0; c < SIZE; c++) {
            if (c != col) {
                removePossibleValue(row, c, value);
            }
        }

        for (int r = 0; r < SIZE; r++) {
            if (r != row) {
                removePossibleValue(r, col, value);
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (r != row || c != col) {
                    removePossibleValue(r, c, value);
                }
            }
        }
    }

    public int[][] getSudokuGrid() {
        int[][] sudoku = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sudoku[i][j] = getValue(i, j);
            }
        }
        return sudoku;
    }

    public static boolean isSolved(Sudoku sudoku) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (sudoku.getValue(row, col) == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}