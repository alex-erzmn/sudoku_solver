package fr.univcotedazur.softwareengineering.sudokufactory.sudoku.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReplaceDotsInFile {
    public static void replaceDotsWithZeros(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Ersetze alle Punkte durch Nullen
                String modifiedLine = line.replace('.', '0');
                writer.write(modifiedLine);
                writer.newLine();
            }

            System.out.println("Ersetzung abgeschlossen. Ausgabe gespeichert in: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen oder Schreiben der Datei: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String inputFilePath = "src/java/fr/univcotedazur/softwareengineering/sudokufactory/sudoku/data/input_hard.txt"; // Pfad zur Eingabedatei
        String outputFilePath = "output.txt"; // Pfad zur Ausgabedatei
        replaceDotsWithZeros(inputFilePath, outputFilePath);
    }
}
