package fr.univcotedazur.softwareengineering.sudoku;

import fr.univcotedazur.softwareengineering.client.SudokuObserver;
import fr.univcotedazur.softwareengineering.sudoku.components.Box;
import fr.univcotedazur.softwareengineering.sudoku.components.Column;
import fr.univcotedazur.softwareengineering.sudoku.components.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SudokuTest {
/*
    private Sudoku sudoku;
    private Row row;
    private Column column;
    private Box box;

    @BeforeEach
    public void setUp() {
        sudoku = new Sudoku();
        row = mock(Row.class);
        column = mock(Column.class);
        box = mock(Box.class);

        when(sudoku.getRow(anyInt())).thenReturn(row);
        when(sudoku.getColumn(anyInt())).thenReturn(column);
        when(sudoku.getBox(anyInt())).thenReturn(box);
    }

    @Test
    public void testInitializePossibleValues() {
        //arrange
        when(row.getValue(anyInt())).thenReturn(0);  // Alle Werte auf 0 setzen
        when(column.getValue(anyInt())).thenReturn(0);
        when(box.getValue(anyInt())).thenReturn(0);

        //act
        sudoku.initializePossibleValues();

        //assert
        for (int rowIndex = 0; rowIndex < Sudoku.SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < Sudoku.SIZE; colIndex++) {
                Set<Integer> possibleValues = sudoku.getPossibleValues(rowIndex, colIndex);
                assertEquals(Sudoku.SIZE, possibleValues.size(),
                        "Es sollten " + Sudoku.SIZE + " mögliche Werte sein.");
            }
        }
        //cleanup
    }

    @Test
    public void testSetCell() {
        int rowIndex = 0;
        int colIndex = 0;
        int value = 5;

        sudoku.setCell(rowIndex, colIndex, value);

        verify(row).setValue(colIndex, value);
        verify(column).setValue(rowIndex, value);
        verify(box).setValue(anyInt(), value);
    }

    @Test
    public void testObserverNotification() {
        sudoku = new Sudoku();
        SudokuObserver observer = Mockito.mock(SudokuObserver.class);
        sudoku.addObserver(observer);
        sudoku.setCell(0, 0, 1);

        Mockito.verify(observer).updateSudoku(sudoku);
    }

    @Test
    public void testAddObserver() {
        SudokuObserver observer = mock(SudokuObserver.class);
        sudoku.addObserver(observer);

        sudoku.setCell(0, 0, 1);

        verify(observer).updateSudoku(sudoku);
    }

    @Test
    public void testRemovePossibleValueFromPeers() {
        int rowIndex = 0;
        int colIndex = 0;
        int value = 5;

        sudoku.removePossibleValue(rowIndex, colIndex, value);

        verify(sudoku, times(Sudoku.SIZE - 1)).removePossibleValue(anyInt(), anyInt(), eq(value));
    }

    @Test
    public void testGetSudokuGrid() {
        int[][] grid = new int[Sudoku.SIZE][Sudoku.SIZE];
        for (int i = 0; i < Sudoku.SIZE; i++) {
            for (int j = 0; j < Sudoku.SIZE; j++) {
                grid[i][j] = i + j;
                when(sudoku.getValue(i, j)).thenReturn(i + j);
            }
        }

        int[][] result = sudoku.getSudokuGrid();

        assertArrayEquals(grid, result);
    }*/
}
