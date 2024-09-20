package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the Box-Line Reduction deduction rule.
 * Example for a unit test using Mockito.
 */
class DR3Unittest {

    private DR3 dr3;
    private Sudoku sudoku;

    @BeforeEach
    public void setUp() {
        dr3 = new DR3();
        sudoku = mock(Sudoku.class);
    }

    @Test
    public void testRun_WithBoxLineReductionApplied_ShouldRemovePossibleValuesInOtherColumns() {
        // Arrange
        Set<Integer> possibleValuesInBlock = new HashSet<>();
        possibleValuesInBlock.add(3);

        Set<Integer> possibleValuesOutsideBlock = new HashSet<>();
        possibleValuesOutsideBlock.add(3);

        when(sudoku.getValue(0, 0)).thenReturn(0);
        when(sudoku.getValue(0, 1)).thenReturn(0);
        when(sudoku.getValue(0, 2)).thenReturn(0);
        when(sudoku.getPossibleValues(0, 0)).thenReturn(possibleValuesInBlock);
        when(sudoku.getPossibleValues(0, 1)).thenReturn(possibleValuesInBlock);
        when(sudoku.getPossibleValues(0, 2)).thenReturn(possibleValuesInBlock);

        for (int col = 3; col < 9; col++) {
            when(sudoku.getValue(0, col)).thenReturn(0); // Leere Zellen
            when(sudoku.getPossibleValues(0, col)).thenReturn(possibleValuesOutsideBlock); // Möglicher Wert 3
        }

        for (int col = 3; col < 9; col++) {
            doNothing().when(sudoku).removePossibleValue(0, col, 3);
        }

        // Act
        boolean wasApplied = dr3.run(sudoku);

        // Assert
        assertTrue(wasApplied);

        for (int col = 3; col < 9; col++) {
            verify(sudoku, times(2)).removePossibleValue(0, col, 3);
        }
    }




    @Test
    public void testRun_WithNoBoxLineReduction_ShouldReturnFalse() {
        // Arrange
        Set<Integer> possibleValues = new HashSet<>();
        possibleValues.add(5);

        when(sudoku.getValue(anyInt(), anyInt())).thenReturn(0);
        when(sudoku.getPossibleValues(anyInt(), anyInt())).thenReturn(possibleValues);

        // Act
        boolean wasApplied = dr3.run(sudoku);

        // Assert
        assertFalse(wasApplied);

        verify(sudoku, never()).removePossibleValue(anyInt(), anyInt(), eq(5));
    }
}