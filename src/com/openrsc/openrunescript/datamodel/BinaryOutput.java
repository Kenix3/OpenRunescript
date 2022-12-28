package com.openrsc.openrunescript.datamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

/**
 * Converts the {@link com.openrsc.openrunescript.datamodel.TranslationUnit} list from the Compiler into binary data.
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class BinaryOutput {
    private static final Logger log = LogManager.getLogger();

    /**
     * The {@link TranslationUnit} that we are outputting.
     */
    private final TranslationUnit translationUnit;
    /**
     * The {@link ByteArrayOutputStream} where we are putting statement code.
     */
    private final ByteArrayOutputStream textOutput;
    /**
     * Output stream view for {@link BinaryOutput#textOutput} in order to put more complex types to the stream.
     */
    private final DataOutputStream textOutputStream;
    /**
     * The {@link ByteArrayOutputStream} where we are putting statement data.
     */
    private final ByteArrayOutputStream rodataOutput;
    /**
     * Output stream view for {@link BinaryOutput#rodataOutput} in order to put more complex types to the stream.
     */
    private final DataOutputStream rodataOutputStream;
    /**
     * A map of the binary data in {@link BinaryOutput#rodataOutput}.
     * A {@link HashMap} of the {@link Literal} value to the offset within {@link BinaryOutput#rodataOutput}.
     */
    private final HashMap<String, Integer> rodataOffsetMap;

    /**
     * Create a {@link BinaryOutput} model operating on a specific {@link TranslationUnit}.
     * @param translationUnit The {@link TranslationUnit} to output.
     */
    public BinaryOutput(final TranslationUnit translationUnit) {
        this.translationUnit = translationUnit;

        textOutput = new ByteArrayOutputStream();
        textOutputStream = new DataOutputStream(textOutput);
        rodataOutput = new ByteArrayOutputStream();
        rodataOutputStream = new DataOutputStream(rodataOutput);
        rodataOffsetMap = new HashMap<>();
    }

    /**
     * Convert the {@link TranslationUnit} to binary data and output it to a file.
     * Run is performed in three passes: data is processed, then instructions, then output to file.
     * @param outputFileName The name of the file to output to.
     */
    public void run(final String outputFileName) {
        // Reset to blank slate.
        reset();

        // Build the read only data.
        dataPass();

        // Build the action statements.
        textPass();

        // Flush the contents of text and rodata output streams.
        try {
            textOutputStream.flush();
            rodataOutputStream.flush();
        } catch (final IOException e) {
            log.error("Failed to finalize data stream: " + e.getMessage());
        }

        // Output file to disk.
        outputToFile(outputFileName);

        //System.out.println(HexFormat.of().formatHex(textOutput.toByteArray()));
        //System.out.println(HexFormat.of().formatHex(rodataOutput.toByteArray()));
    }

    /**
     * Output the binary data for the {@link TranslationUnit} to a file.
     * @param outputFileName The file to output the binary data for the {@link TranslationUnit}.
     */
    protected void outputToFile(final String outputFileName) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(outputFileName);
            final DataOutputStream fileDataOutputStream = new DataOutputStream(fileOutputStream);
            final byte[] text = textOutput.toByteArray();
            final byte[] rodata = rodataOutput.toByteArray();
            // Text section starts after the four header ints.
            final int textSectionOffset = Integer.BYTES * 4;
            final int textSectionLength = text.length;
            // Rodata section starts after the text section.
            final int rodataSectionOffset = textSectionOffset + textSectionLength;
            final int rodataSectionLength = rodata.length;
            // Write section information header.
            fileDataOutputStream.writeInt(textSectionOffset);
            fileDataOutputStream.writeInt(textSectionLength);
            fileDataOutputStream.writeInt(rodataSectionOffset);
            fileDataOutputStream.writeInt(rodataSectionLength);
            // Write section data
            fileDataOutputStream.write(text);
            fileDataOutputStream.write(rodata);

            // Finalize the file.
            fileDataOutputStream.flush();
        } catch (final IOException e) {
            log.error("Error writing file \"" + outputFileName + "\", error: " + e.getMessage());
        }
    }

    /**
     * Reset the binary data storage.
     */
    protected void reset() {
        rodataOffsetMap.clear();
        textOutput.reset();
        rodataOutput.reset();
    }

    /**
     * The code instruction pass for conversion to binary.
     */
    protected void textPass() {
        translationUnitTextPass();
    }

    /**
     * Process the instructions in a {@link TranslationUnit}.
     * Converts the instructions to binary via the {@link BinaryOutput#textOutputStream}.
     * Converts the context {@link TranslationUnit} for this {@link BinaryOutput}.
     */
    protected void translationUnitTextPass() {
        // Loop through and process all the blocks in this TU.
        for (final Map.Entry<String, Block> entry : getTranslationUnit().getBlocks().entrySet()) {
            blockTextPass(entry.getValue());
        }
    }

    /**
     * Process the instructions in a {@link Block}.
     * Converts the instructions to binary via the {@link BinaryOutput#textOutputStream}.
     * @param block The {@link Block} to convert child {@link Statement} to binary.
     */
    protected void blockTextPass(final Block block) {
        try {
            // Store a reference to the block file name
            textOutputStream.writeInt(rodataOffsetMap.get(block.getFileName()));
        } catch (final IOException e) {
            log.error("Failed to write block name reference \"" + block.getFileName() + "\": " + e.getMessage());
        }

        try {
            textOutputStream.writeInt(block.getActionStatements().size());
        } catch (final IOException e) {
            log.error("Failed to write block action statement count " + block.getActionStatements().size() + " \"" +
                    block.getFileName() + "\": " + e.getMessage());
        }

        // Process the header block statement.
        statementTextPass(block.getHeaderStatement());

        // Loop through the block and process all the Statements in this block.
        for (final Statement statement : block.getActionStatements()) {
            statementTextPass(statement);
        }
    }

    /**
     * Process the code instruction in a {@link Statement}.
     * Converts the instruction to binary via the {@link BinaryOutput#textOutputStream}.
     * @param statement The {@link Statement} to convert to binary.
     */
    protected void statementTextPass(final Statement statement) {
        // Store the statement specifier.
        try {
            textOutputStream.write(statement.getStatementSpecifier().ordinal());
        } catch (final IOException e) {
            log.error("IO Exception on writing statement specifier \"" + statement.toString() + "\": " + e.getMessage());
        }

        final Literal actionName = statement.getActionName();
        final Integer actionNameOffset = rodataOffsetMap.get(actionName.getValue());

        // Check for errors in the action name.
        if (actionName.getType() != Literal.LiteralType.Identifier) {
            log.error("Statement action name \"" + statement.toString() + "\" has bad literal type: " + actionName.getType().ordinal());
        }
        if (actionNameOffset == null) {
            log.error("Statement action name \"" + statement.toString() + "\" has no data in rodata.");
            return;
        }

        // Store the literal type.
        try {
            textOutputStream.write(actionName.getType().ordinal());
        } catch (final IOException e) {
            log.error("IO Exception on writing statement action name type \"" + statement.toString() + "\": " + e.getMessage());
        }

        // Store the offset into rodata to find the literal.
        try {
            textOutputStream.writeInt(actionNameOffset);
        } catch (final IOException e) {
            log.error("IO Exception on writing statement action name rodata offset \"" + statement.toString() + "\": " + e.getMessage());
        }

        final ArrayList<Literal> parameterList = statement.getParameterList();

        // Store the number of literals that are used as parameters to this statement.
        try {
            if (parameterList.size() > Byte.MAX_VALUE) {
                // TODO: We need to error here.
            }
            textOutputStream.write(parameterList.size());
        } catch (final IOException e) {
            log.error("IO Exception on writing statement literal parameter count \"" + statement.toString() + "\": " + e.getMessage());
        }

        // Loop through the literals in the parameter list and add them to the data map.
        for (final Literal literal : parameterList) {
            final Literal.LiteralType type = literal.getType();
            final String value = literal.getValue();
            final Integer literalOffset = rodataOffsetMap.get(value);

            // Store the literal type.
            try {
                textOutputStream.write(type.ordinal());
            } catch (final IOException e) {
                log.error("IO Exception on writing statement literal parameter type, " + type.ordinal() + ", \"" +
                        value + "\", \"" + statement.toString() + "\": " + e.getMessage());
            }

            if (literalOffset == null) {
                log.error("Statement literal parameter \"" + value + "\" has no data in rodata.");

                // Generate an error if the type is not Null.
                if (type != Literal.LiteralType.Null) {
                    // TODO: We need to error here IF the literal type is not null.
                }

                continue;
            }

            try {
                // Store the offset into rodata to find the literal.
                textOutputStream.writeInt(literalOffset);
            } catch (final IOException e) {
                log.error("IO Exception on writing statement literal parameter offset, " + type.ordinal() + ", \"" +
                        value + "\", \"" + statement.toString() + "\": " + e.getMessage());
            }
        }
    }

    /**
     * The data pass for conversion to binary.
     */
    protected void dataPass() {
        // Process the translation unit to populate the identifierMap, numberMap, and stringMap.
        translationUnitDataPass();
    }

    /**
     * Process the data in a {@link TranslationUnit}.
     * Converts the data to binary via the {@link BinaryOutput#textOutputStream}.
     * Converts the context {@link TranslationUnit} for this {@link BinaryOutput}.
     */
    protected void translationUnitDataPass() {
        // Loop through and process all the blocks in this TU.
        for (final Map.Entry<String, Block> entry : getTranslationUnit().getBlocks().entrySet()) {
            blockDataPass(entry.getValue());
        }
    }

    /**
     * Process the data in a {@link Block}.
     * Converts the data to binary via the {@link BinaryOutput#textOutputStream}.
     * @param block The {@link Block} to convert child {@link Statement} data to binary.
     */
    protected void blockDataPass(final Block block) {
        // Store the Block name into rodata.
        if (!rodataOffsetMap.containsKey(block.getFileName())) {
            // Get the offset that this block name will occupy in the output stream.
            final int rodataOffset = rodataOutput.size();
            try {
                // Write the block name.
                rodataOutputStream.writeUTF(block.getFileName());
            } catch (final IOException e) {
                log.error("Failed to write block filename \"" + block.getFileName() + "\": " + e.getMessage());
            }
            // Add the literal value to the offset map to be used in the .text section.
            rodataOffsetMap.put(block.getFileName(), rodataOffset);
        }

        // Process the header statement for data literals.
        statementDataPass(block.getHeaderStatement());

        // Loop through the block and process all the Statements in this block.
        for (final Statement statement : block.getActionStatements()) {
            statementDataPass(statement);
        }
    }

    /**
     * Process the data in a {@link Statement}.
     * Converts the data to binary via the {@link BinaryOutput#textOutputStream}.
     * @param statement The {@link Statement} to convert child {@link Literal} data to binary.
     */
    protected void statementDataPass(final Statement statement) {
        // Add the action name literal to the data map.
        literalDataPass(statement.getActionName());

        // Loop through the literals in the parameter list and add them to the data map.
        for (final Literal literal : statement.getParameterList()) {
            literalDataPass(literal);
        }
    }

    /**
     * Process the data for a {@link Literal}.
     * Converts the data to binary via the {@link BinaryOutput#textOutputStream}.
     * @param literal The {@link Literal} to convert to binary.
     */
    protected void literalDataPass(final Literal literal) {
        // If the literal value already exists in the rodata offset map, then we skip processing this literal.
        // This avoids duplicating data in the .rodata section.
        if (rodataOffsetMap.containsKey(literal.getValue())) {
            return;
        }

        switch (literal.getType()) {
            default:
                log.error("Attempting to output a literal which has an invalid type (" + literal.getType().ordinal() +
                        ") for block " + literal.getParentStatement().getParentBlock().getHash());
                break;
            case Null:
                // We do nothing for Null literals.
                log.error("Attempting to output a null literal for block " +
                        literal.getParentStatement().getParentBlock().getHash());
                break;
            case Number:
                // Convert the string literal value from string to integer.
                final int integerValue = literal.getIntegerValue();
                // Get the offset that this data will occupy in the output stream.
                final int numberOffset = rodataOutput.size();
                try {
                    // Write the data to the output stream.
                    rodataOutputStream.writeInt(integerValue);
                    // Add the literal value to the offset map to be used in the .text section.
                    rodataOffsetMap.put(literal.getValue(), numberOffset);
                } catch (final NumberFormatException e) {
                    log.error("Unable to parse integer: " + literal.getValue() + ", error: " + e.getMessage() +
                            ", for block " + literal.getParentStatement().getParentBlock().getHash());
                } catch (final IOException e) {
                    log.error("IO Exception on number \"" + integerValue + "\": " + e.getMessage());
                }
                break;
            case Identifier:
                // Get the identifier value.
                final String identifierValue = literal.getValue();
                // Get the offset that this data will occupy in the output stream.
                final int identifierOffset = rodataOutput.size();
                try {
                    // Write the identifier.
                    rodataOutputStream.writeUTF(identifierValue);
                    // Add the literal value to the offset map to be used in the .text section.
                    rodataOffsetMap.put(literal.getValue(), identifierOffset);
                } catch (final UnsupportedEncodingException e) {
                    log.error("Could not write UTF-8 identifier \"" + identifierValue + "\", error: " + e.getMessage());
                } catch (final IOException e) {
                    log.error("IO Exception on identifier \"" + identifierValue + "\": " + e.getMessage());
                }
                break;
            case String:
                // Get the string value.
                final String stringValue = literal.getValue();
                // Get the offset that this data will occupy in the output stream.
                final int stringOffset = rodataOutput.size();
                try {
                    // Write the string.
                    rodataOutputStream.writeUTF(stringValue);
                    // Add the literal value to the offset map to be used in the .text section.
                    rodataOffsetMap.put(literal.getValue(), stringOffset);
                } catch (final UnsupportedEncodingException e) {
                    log.error("Could not write UTF-8 string \"" + stringValue + "\", error: " + e.getMessage());
                } catch (final IOException e) {
                    log.error("IO Exception on string \"" + stringValue + "\": " + e.getMessage());
                }
                break;
        }
    }

    /**
     * Get the context {@link TranslationUnit} for this {@link BinaryOutput}.
     * @return The context {@link TranslationUnit} for this {@link BinaryOutput}.
     */
    public TranslationUnit getTranslationUnit() {
        return translationUnit;
    }
}
