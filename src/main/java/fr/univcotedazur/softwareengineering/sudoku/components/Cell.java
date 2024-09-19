package fr.univcotedazur.softwareengineering.sudoku.components;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Cell {
    @Setter
    private int value;
    private final Set<Integer> possibleValues = new HashSet<>();

    public Cell(int value) {
        this.value = value;
    }

    public void addPossibleValue(int value) {
        possibleValues.add(value);
    }

    public void removePossibleValue(int value) {
        possibleValues.remove(value);
    }
}