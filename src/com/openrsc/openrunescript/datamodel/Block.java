package com.openrsc.openrunescript.datamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents an OpenRunescript Block within a Translation Unit.
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class Block {
    private static final Logger log = LogManager.getLogger();

    /**
     * The {@link HeaderStatement} for this block.
     */
    private HeaderStatement headerStatement;
    /**
     * The {@link Block} {@link ActionStatement} list.
     */
    private final ArrayList<ActionStatement> actionStatements;
    /**
     * The file name / translation unit that this {@link Block} originally came from.
     */
    private final String fileName;
    /**
     * The parent {@link TranslationUnit} of this {@link Block}.
     */
    private final TranslationUnit parentTranslationUnit;

    /**
     * Create a {@link Block} object.
     */
    public Block(final TranslationUnit parentTranslationUnit, final String fileName) {
        this.fileName = fileName;
        this.parentTranslationUnit = parentTranslationUnit;

        headerStatement = null;
        actionStatements = new ArrayList<>();
    }

    /**
     * Create a {@link Block} header hash from the specified parameters.
     * @param actionName The callback action name.
     * @param parameters The callback {@link Literal} identifiers.
     * @return The string hash of the {@link Block} header.
     */
    public static String hash(final String actionName, final List<String> parameters) {
        final StringBuilder sb = new StringBuilder();

        sb.append(actionName);
        sb.append(":");
        sb.append(parameters.stream().collect(Collectors.joining(":")));

        return sb.toString();
    }

    /**
     * Get the hash of the {@link Block} header.
     * @return The string hash of the {@link Block} header.
     */
    public String getHash() {
        // Copy the Literal parameter list to a string parameter list.
        final ArrayList<String> parameters = new ArrayList<>(getHeaderStatement().getParameterList().size());
        for (final Literal literal : getHeaderStatement().getParameterList()) {
            parameters.add(literal.toString());
        }

        return hash(getHeaderStatement().getActionName().toString(), parameters);
    }

    /**
     * Get the {@link HeaderStatement} for this {@link Block}.
     * @return The {@link HeaderStatement} for this {@link Block}.
     */
    public HeaderStatement getHeaderStatement() {
        return headerStatement;
    }

    /**
     * Set the {@link HeaderStatement} for this {@link Block}.
     * @param headerStatement The {@link HeaderStatement} to set.
     */
    public void setHeaderStatement(HeaderStatement headerStatement) {
        this.headerStatement = headerStatement;
    }

    /**
     * Get the list of {@link ActionStatement} for this {@link Block}.
     * @return the list of {@link ActionStatement} for this {@link Block}.
     */
    public ArrayList<ActionStatement> getActionStatements() {
        return actionStatements;
    }

    /**
     * Add an {@link ActionStatement} to this {@link Block}.
     * @param actionStatement The {@link ActionStatement} to add to this {@link Block}.
     * @return True if the operation was successful, or false if the operation failed.
     */
    public boolean addActionStatement(final ActionStatement actionStatement) {
        return actionStatements.add(actionStatement);
    }

    /**
     * Add multiple {@link ActionStatement} to this {@link Block}.
     * @param actionStatements The {@link ActionStatement} to add to this {@link Block}.
     * @return True if the operation was successful, or false if the operation failed.
     */
    public boolean addAdtionStatements(final ArrayList<ActionStatement> actionStatements) {
        return this.actionStatements.addAll(actionStatements);
    }

    /**
     * Get the file name / translation unit that this {@link Block} originally came from.
     * @return The file name / translation unit that this {@link Block} originally came from.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get the parent {@link TranslationUnit} of this {@link Block}.
     * @return The parent {@link TranslationUnit} of this {@link Block}.
     */
    public TranslationUnit getParentTranslationUnit() {
        return parentTranslationUnit;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final List<String> stringParams = new ArrayList<>();

        for (final ActionStatement actionStatement : actionStatements) {
            stringParams.add(actionStatement.toString());
        }

        sb.append(headerStatement.toString());
        sb.append("\n");
        sb.append(stringParams.stream().collect(Collectors.joining("\n")));

        return sb.toString();
    }
}
