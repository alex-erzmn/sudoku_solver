package fr.univcotedazur.softwareengineering;

import fr.univcotedazur.softwareengineering.client.SudokuController;
import fr.univcotedazur.softwareengineering.client.SudokuGUI;

public class Main {
    public static void main(String[] args) {
        SudokuController controller = new SudokuController();
        SudokuGUI gui = new SudokuGUI(controller);
        gui.setVisible(true);
    }
}