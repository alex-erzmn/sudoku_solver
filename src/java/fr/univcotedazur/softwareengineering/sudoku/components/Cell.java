package fr.univcotedazur.softwareengineering.sudoku.components;

import java.util.HashSet;
import java.util.Set;

public class Cell {
    private int value;
    private final Set<Integer> possibleValues = HashSet.newHashSet(9);

    public Cell(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Set<Integer> getPossibleValues() {
        return possibleValues;
    }

    public void addPossibleValue(int value) {
        possibleValues.add(value);
    }

    public void removePossibleValue(int value) {
        possibleValues.remove(value);
    }
}