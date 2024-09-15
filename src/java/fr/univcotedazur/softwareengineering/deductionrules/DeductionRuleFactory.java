package fr.univcotedazur.softwareengineering.deductionrules;

import java.util.ArrayList;
import java.util.List;

public class DeductionRuleFactory {

    public DeductionRule createDeductionRule(String ruleName) {
        switch (ruleName) {
            case "Naked Single":
                return new DR1();
            case "Hidden Single":
                return new DR2();
            default:
                return null;
        }
    }

    public List<DeductionRule> createAllDeductionRules() {
        List<DeductionRule> rules = new ArrayList<>();
        rules.add(new DR1());
        rules.add(new DR2());

        //TODO: Add the other deduction rules here

        return rules;
    }
}
