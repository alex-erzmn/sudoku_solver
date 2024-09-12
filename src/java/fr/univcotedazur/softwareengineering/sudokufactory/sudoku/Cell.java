package fr.univcotedazur.softwareengineering.sudokufactory.sudoku;

import java.util.List;

public class Cell {
    private int value; // Der Wert in der Zelle
    private List<Integer> possibleValues; // Die möglichen Werte, die die Zelle annehmen kann
    private boolean isInitial; // Kennzeichnet, ob der Wert vorgegeben ist

    public Cell(int value, boolean isInitial) {
        this.value = value;
        this.isInitial = isInitial;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (!isInitial) {
            this.value = value;
        }
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public void addPossibleValue(int value) {
        possibleValues.add(value);
    }

    public void removePossibleValue(int value) {
        possibleValues.remove(value);
    }

    public boolean isInitial() {
        return isInitial;
    }
}