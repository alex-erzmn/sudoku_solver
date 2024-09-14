package fr.univcotedazur.softwareengineering.sudokufactory;

import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;
import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.data.SudokuReader;

import java.io.IOException;

import static java.text.DateFormat.MEDIUM;

public class SudokuFactory {

    public Sudoku createSudoku(SudokuType type) throws IOException {
        switch (type) {
            case RANDOM:
                return createRandomSudoku();
            case EASY:
                throw new IllegalArgumentException("Schwierigkeitsgrad nicht implementiert");
            case MEDIUM:
                throw new IllegalArgumentException("Schwierigkeitsgrad nicht implementiert");
            case HARD:
                throw new IllegalArgumentException("Schwierigkeitsgrad nicht implementiert");
            // Weitere Sudoku-Erstellungstypen können hier hinzugefügt werden
            default:
                throw new IllegalArgumentException("Unbekannter Sudoku-Typ");
        }
    }

    private Sudoku createRandomSudoku() throws IOException {
        return SudokuReader.readSudokuFromFile("src/java/fr/univcotedazur/softwareengineering/sudokuFactory/sudoku/data/randomSudokus.txt");
    }

    // Enumeration für Sudoku-Erstellungstypen
    public enum SudokuType {
        EASY,
        MEDIUM,
        HARD,
        RANDOM
    }
}
