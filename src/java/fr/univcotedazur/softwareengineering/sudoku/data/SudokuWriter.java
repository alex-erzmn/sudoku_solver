package fr.univcotedazur.softwareengineering.sudoku.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SudokuWriter {

    // Methode zum Schreiben des formatierten Sudokus (Puzzle oder Lösung) in eine Datei
    public static void writeSudokuToFile(List<String> sudokuStrings, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (String sudokuString : sudokuStrings) {
                int length = sudokuString.length();

                // Prüfe, ob der Sudoku-String 81 Zeichen enthält
                if (length != 81) {
                    System.out.println("Ungültiger Sudoku-String: " + sudokuString);
                    continue;
                }

                for (int i = 0; i < length; i++) {
                    writer.write(sudokuString.charAt(i)); // Schreibe jede Zahl
                    if ((i + 1) % 9 != 0) {
                        writer.write(","); // Komma nach jeder Zahl, außer bei der letzten in der Zeile
                    }

                    // Zeilenumbruch nach jedem 9. Zeichen
                    if ((i + 1) % 9 == 0) {
                        writer.newLine();
                    }
                }
                writer.newLine(); // Leerzeile zwischen Sudokus
            }

            System.out.println("Sudoku wurde erfolgreich in die Datei geschrieben: " + fileName);
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Datei: " + e.getMessage());
        }
    }

    // Methode zum Lesen der Puzzle- und Lösungspaare aus einer Datei
    public static List<String[]> readSudokuPairsFromFile(String inputFileName) {
        List<String[]> sudokuPairs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;

            // Lies jede Zeile der Datei (jede Zeile sollte ein Puzzle,Solution-Paar sein)
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    sudokuPairs.add(parts); // Puzzle und Lösung als Array speichern
                } else {
                    System.out.println("Ungültiges Format: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }

        return sudokuPairs;
    }

    // Hauptprogramm
    public static void main(String[] args) {
        // Name der Eingabedatei, die die Puzzle- und Lösungspaare enthält
        String inputFileName = "src/java/fr/univcotedazur/softwareengineering/sudokufactory/sudoku/data/input_hard.txt";

        // Name der Ausgabedateien für Puzzle und Lösung
        String puzzleFileName = "easySudokus.txt";
        String solutionFileName = "sudoku_solution_output.txt";

        // Puzzle- und Lösungspaare aus der Datei einlesen
        List<String[]> sudokuPairs = readSudokuPairsFromFile(inputFileName);

        // Erstelle separate Listen für Puzzle und Lösungen
        List<String> puzzles = new ArrayList<>();
        List<String> solutions = new ArrayList<>();

        for (String[] pair : sudokuPairs) {
            puzzles.add(pair[0].trim()); // Puzzle hinzufügen
            solutions.add(pair[1].trim()); // Lösung hinzufügen
        }

        // Schreibe Puzzle und Lösungen in separate Dateien
        writeSudokuToFile(puzzles, puzzleFileName);
        writeSudokuToFile(solutions, solutionFileName);
    }
}
