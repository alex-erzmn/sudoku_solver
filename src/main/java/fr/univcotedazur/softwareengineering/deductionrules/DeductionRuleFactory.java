package fr.univcotedazur.softwareengineering.deductionrules;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class to create deduction rules. This class is a singleton.
 * It is responsible for creating deduction rules. It uses the Factory pattern to create deduction rules.
 * @since 12/09/2024
 * @implNote Factory pattern & Singleton pattern
 */
public class DeductionRuleFactory {

    private DeductionRuleFactory() {
    }

    private static final class InstanceHolder {
        private static final DeductionRuleFactory instance = new DeductionRuleFactory();
    }

    public static DeductionRuleFactory getInstance() {
        return InstanceHolder.instance;
    }

    public DeductionRule createDeductionRule(DeductionRuleType ruleName) {
        return switch (ruleName) {
            case NAKED_SINGLE -> new DR1();
            case HIDDEN_SINGLE -> new DR2();
            case BOX_LINE_REDUCTION -> new DR3();
            default -> null;
        };
    }

    public List<DeductionRule> createAllDeductionRules() {
        List<DeductionRule> rules = new ArrayList<>();
        rules.add(new DR1());
        rules.add(new DR2());
        rules.add(new DR3());
        return rules;
    }
}
