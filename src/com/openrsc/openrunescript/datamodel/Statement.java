package com.openrsc.openrunescript.datamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Represents an OpenRunescript Statement.
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public abstract class Statement {
    private static final Logger log = LogManager.getLogger();

    /**
     * The {@link StatementSpecifier} for this {@link Statement}.
     */
    private StatementSpecifier statementSpecifier;
    /**
     * A {@link Literal} representing the action name.
     */
    private Literal actionName;
    /**
     * A list of {@link Literal} parameters to the block header.
     */
    private final ArrayList<Literal> parameterList;

    /**
     * The parent {@link Block} of this {@link Statement}.
     */
    private final Block parentBlock;

    /**
     * Create a {@link Statement}.
     * @param parentBlock The parent {@link Block} of this {@link Statement}.
     * This should not be used directly, instead create a {@link HeaderStatement} or {@link ActionStatement}.
     */
    protected Statement(final Block parentBlock) {
        this.parentBlock = parentBlock;
        statementSpecifier = StatementSpecifier.Always;
        actionName = new Literal(this);
        parameterList = new ArrayList<>();
    }

    /**
     * Get the {@link StatementSpecifier} for this {@link Statement}.
     * @return The {@link StatementSpecifier} for this {@link Statement}.
     */
    public StatementSpecifier getStatementSpecifier() {
        return statementSpecifier;
    }

    /**
     * Set the {@link StatementSpecifier} for this {@link Statement}.
     * @param statementSpecifier The {@link StatementSpecifier} for this {@link Statement}.
     */
    public void setStatementSpecifier(StatementSpecifier statementSpecifier) {
        this.statementSpecifier = statementSpecifier;
    }

    /**
     * Get the action name {@link Literal} for this {@link Statement}.
     * @return The action name {@link Literal} for this {@link Statement}.
     */
    public Literal getActionName() {
        return actionName;
    }

    /**
     * Set the action name {@link Literal} for this {@link Statement}.
     * @param actionName The action name {@link Literal} for this {@link Statement}.
     */
    public void setActionName(Literal actionName) {
        this.actionName = actionName;
    }

    /**
     * Add a {@link Literal} to the parameter list for this {@link Statement}.
     * @param literal The {@link Literal} to add to the parameter list for this {@link Statement}.
     * @return True if the operation was successful, or false if the operation failed.
     */
    public boolean addParameter(final Literal literal) {
        return parameterList.add(literal);
    }

    /**
     * Add multiple {@link Literal} to the parameter list for this {@link Statement}.
     * @param literals The {@link Literal} to add to the parameter list for this {@link Statement}.
     * @return True if the operation was successful, or false if the operation failed.
     */
    public boolean addParameters(final ArrayList<Literal> literals) {
        return parameterList.addAll(literals);
    }

    /**
     * Get the list of {@link Literal} for this {@link Statement}.
     * @return The list of {@link Literal} for this {@link Statement}.
     */
    public ArrayList<Literal> getParameterList() {
        return parameterList;
    }

    /**
     * Get the parent {@link Block} of this {@link Statement}.
     * @return The parent {@link Block} of this {@link Statement}.
     */
    public Block getParentBlock() {
        return parentBlock;
    }

    /**
     * Get the string representing the {@link StatementSpecifier}.
     * @param statementSpecifier The {@link StatementSpecifier} to convert to string.
     * @return The string representing the passed in {@link StatementSpecifier}.
     */
    public static String getStatementSpecifierString(final StatementSpecifier statementSpecifier) {
        switch (statementSpecifier) {
            case Label:
                return "=";
            case Always:
                return "*";
            case True:
                return "+";
            case False:
                return "-";
            default:
                return "<error>";
        }
    }

    /**
     * Get the enum {@link StatementSpecifier} value for a string.
     * @param statementSpecifier The string {@link StatementSpecifier} to convert to enum.
     * @return The enum {@link StatementSpecifier} representing the string passed in.
     */
    public static StatementSpecifier getStatementSpecifierEnum(final String statementSpecifier) {
        switch (statementSpecifier) {
            case "=":
                return StatementSpecifier.Label;
            case "*":
                return StatementSpecifier.Always;
            case "+":
                return StatementSpecifier.True;
            case "-":
                return StatementSpecifier.False;
            default:
                return null;
        }
    }

    /**
     * A type describing when the {@link: Statement}  is executed.
     */
    public enum StatementSpecifier {
        /**
         * The {@link Statement} is part of the header.
         */
        Label,
        /**
         * The {@link Statement} is always executed.
         */
        Always,
        /**
         * The {@link Statement} is only executed when the internal execution flag is true.
         */
        True,
        /**
         * The {@link Statement} is only executed when the internal execution flag is false.
         */
        False
    }
}
