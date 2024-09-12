package fr.univcotedazur.softwareengineering.sudokufactory.sudoku.data;

import fr.univcotedazur.softwareengineering.sudokufactory.sudoku.Sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuReader {

    // Methode zum Lesen eines zufälligen Sudokus aus einer Datei
    public static Sudoku readSudokuFromFile(String fileName) {
        List<Sudoku> sudokus = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Sudoku sudoku = new Sudoku();
            int row = 0;

            while ((line = reader.readLine()) != null) {
                // Leere Zeilen überspringen
                if (line.trim().isEmpty()) continue;

                // Zeile in einzelne Zahlen umwandeln
                String[] numbers = line.split(",");

                for (int col = 0; col < 9; col++) {
                    int value = Integer.parseInt(numbers[col]);
                    sudoku.setCell(row, col, value);
                }

                row++;

                // Wenn 9 Zeilen gelesen wurden, das Puzzle zur Liste hinzufügen
                if (row == 9) {
                    sudokus.add(sudoku);
                    sudoku = new Sudoku();  // Neues Sudoku für das nächste
                    row = 0;
                }
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }

        // Zufälliges Sudoku auswählen
        Random random = new Random();
        int randomIndex = random.nextInt(sudokus.size());

        return sudokus.get(randomIndex);
    }
}
