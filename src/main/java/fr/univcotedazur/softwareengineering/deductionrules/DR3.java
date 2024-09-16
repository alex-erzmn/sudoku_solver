package fr.univcotedazur.softwareengineering.deductionrules;

import fr.univcotedazur.softwareengineering.sudoku.Sudoku;

public class DR3 implements DeductionRule {

    private static final String NAME = "Naked Pair";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean run(Sudoku sudoku) {
        return false;
        //TODO: Implement this
    }

}
