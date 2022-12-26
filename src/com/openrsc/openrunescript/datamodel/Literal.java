package com.openrsc.openrunescript.datamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents an OpenRunescript Literal
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class Literal {
    private static final Logger log = LogManager.getLogger();



    /**
     * The parent {@link Statement} of this {@link Literal}.
     */
    private final Statement parentStatement;

    /**
     * An enumeration for the possible Literal types.
     */
    public enum LiteralType {
        /**
         * The literal has no value
         */
        Null,
        /**
         * The Literal is a number.
         */
        Number,
        /**
         * The Literal is a string.
         */
        String,
        /**
         * The literal is an identifier.
         * It can be an action name for a header statement, a function to call in an action statement, or one of the following data types:
         * NPC type, object type, shop type, label name, variable name, character stat
         */
        Identifier
    }

    /**
     * Create a literal with a known type and value.
     * @param parentStatement The parent {@link Statement} for this {@link Literal}.
     * @param type The {@link LiteralType} of the Literal
     * @param value The string value of the Literal
     */
    public Literal(final Statement parentStatement, final LiteralType type, final String value) {
        this.parentStatement = parentStatement;
        this.type = type;
        this.value = value;
    }

    /**
     * Create a literal with default type and value
     * @param parentStatement The parent {@link Statement} for this {@link Literal}.
     */
    public Literal(final Statement parentStatement) {
        this(parentStatement, LiteralType.Null, "");
    }

    /**
     * The {@link LiteralType} of the {@link Literal}
     */
    private LiteralType type;

    /**
     * The value of the {@link Literal}
     */
    private String value;

    /**
     * Gets the string value of the {@link Literal}
     * @return The string value of the {@link Literal}
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the string value of the {@link Literal}
     * @param value The string value to set.
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Get the integer representation of the {@link Literal}
     * @return The integer representation of the {@link Literal}
     * @throws NumberFormatException When the value of this {@link Literal} can't be converted to an integer.
     */
    public int getIntegerValue() throws NumberFormatException {
        return Integer.parseInt(value);
    }

    /**
     * Get the {@link LiteralType} of the {@link Literal}.
     * @return The {@link LiteralType} of the {@link Literal}.
     */
    public LiteralType getType() {
        return type;
    }

    /**
     * Set the {@link LiteralType} of the {@link Literal}.
     * @param type The new {@link LiteralType} of the {@link Literal}.
     */
    public void setType(LiteralType type) {
        this.type = type;
    }

    /**
     * Get the parent {@link Statement}.
     * @return The parent {@link Statement}.
     */
    public Statement getParentStatement() {
        return parentStatement;
    }

    public String toString() {
        switch (type) {
            default:
                return "<error>";
            case Null:
                return "";
            case Identifier:
            case Number:
                return getValue();
            case String:
                return '"' + getValue() + '"';
        }
    }
}
