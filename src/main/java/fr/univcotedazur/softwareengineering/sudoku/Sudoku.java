package fr.univcotedazur.softwareengineering.sudoku;

import fr.univcotedazur.softwareengineering.client.DisplayObserver;
import fr.univcotedazur.softwareengineering.client.SudokuObserver;
import fr.univcotedazur.softwareengineering.sudoku.components.Box;
import fr.univcotedazur.softwareengineering.sudoku.components.Column;
import fr.univcotedazur.softwareengineering.sudoku.components.Row;
import lombok.Getter;

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
    @Getter
    private final Row[] rows;
    @Getter
    private final Column[] columns;
    @Getter
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

    public void addObserver(SudokuObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (SudokuObserver observer : observers) {
            observer.updateSudoku(this);
        }
    }

    private void notifyDisplayObservers() {
        for (SudokuObserver observer : observers) {
            if (observer instanceof DisplayObserver) {
                observer.updateSudoku(this);
            }
        }
    }

    public int getValue(int rowIndex, int colIndex) {
        return rows[rowIndex].getValue(colIndex);
    }

    public void setValue(int rowIndex, int colIndex, int value) {
        if(getValue(rowIndex, colIndex) != 0) {
            return;
        }
        rows[rowIndex].setValue(colIndex, value);
        columns[colIndex].setValue(rowIndex, value);
        boxes[getBoxIndex(rowIndex, colIndex)].setValue(getCellIndexInBox(rowIndex, colIndex), value);
        removePossibleValueFromPeers(rowIndex, colIndex, value);
        notifyObservers();
    }


    private int getBoxIndex(int rowIndex, int colIndex) {
        return (rowIndex / 3) * 3 + (colIndex / 3);
    }

    private int getCellIndexInBox(int rowIndex, int colIndex) {
        return (rowIndex % 3) * 3 + (colIndex % 3);
    }

    public Set<Integer> getPossibleValues(int rowIndex, int colIndex) {
        return rows[rowIndex].getCell(colIndex).getPossibleValues();
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
        if (getValue(rowIndex, colIndex) != 0) {
            return possibleValues;
        }
        for (int i = 1; i <= SIZE; i++) {
            possibleValues.add(i);
        }
        for (int i = 0; i < SIZE; i++) {
            possibleValues.remove(Integer.valueOf(rows[rowIndex].getValue(i)));
            possibleValues.remove(Integer.valueOf(columns[colIndex].getValue(i)));
            possibleValues.remove(Integer.valueOf(boxes[getBoxIndex(rowIndex, colIndex)].getValue(i)));
        }
        return possibleValues;
    }

    public void addPossibleValue(int rowIndex, int colIndex, int value) {
        rows[rowIndex].getCell(colIndex).addPossibleValue(value);
        columns[colIndex].getCell(rowIndex).addPossibleValue(value);
        boxes[getBoxIndex(rowIndex, colIndex)].getCell(getCellIndexInBox(rowIndex, colIndex)).addPossibleValue(value);
    }

    public void removePossibleValue(int rowIndex, int colIndex, int value) {
        rows[rowIndex].getCell(colIndex).removePossibleValue(value);
        columns[colIndex].getCell(rowIndex).removePossibleValue(value);
        boxes[getBoxIndex(rowIndex, colIndex)].getCell(getCellIndexInBox(rowIndex, colIndex)).removePossibleValue(value);
        notifyDisplayObservers();
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

    public boolean isSolved() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (getValue(row, col) == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
