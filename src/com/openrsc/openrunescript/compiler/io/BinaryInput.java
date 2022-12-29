package com.openrsc.openrunescript.compiler.io;

import com.openrsc.openrunescript.datamodel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.nio.ByteBuffer;

/**
 * Converts binary data into a {@link TranslationUnit}
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class BinaryInput {
    private static final Logger log = LogManager.getLogger();

    private String fileName;
    private FileInputStream fileInputStream;
    private DataInputStream fileDataInputStream;
    private ByteArrayInputStream textInputStream;
    private DataInputStream textDataInputStream;
    private ByteBuffer rodata;
    private DataInputStream rodataDataInputStream;
    private int textOffset;
    private int textSize;
    private int rodataOffset;
    private int rodataSize;
    private TranslationUnit translationUnit;

    public BinaryInput() {

    }

    public TranslationUnit run(final String inputFileName) {
        translationUnit = new TranslationUnit();

        // Load the file
        loadFile(inputFileName);

        // Load the binary header files.
        try {
            textOffset = fileDataInputStream.readInt();
        } catch (final IOException e) {
            log.error("Failed to read textOffset from file \"" + fileName + "\": " + e.getMessage());
        }
        try {
            textSize = fileDataInputStream.readInt();
        } catch (final IOException e) {
            log.error("Failed to read textSize from file \"" + fileName + "\": " + e.getMessage());
        }
        try {
            rodataOffset = fileDataInputStream.readInt();
        } catch (final IOException e) {
            log.error("Failed to read rodataOffset from file \"" + fileName + "\": " + e.getMessage());
        }
        try {
            rodataSize = fileDataInputStream.readInt();
        } catch (final IOException e) {
            log.error("Failed to read rodataSize from file \"" + fileName + "\": " + e.getMessage());
        }

        // Load the text and data section input streams from the file.
        try {
            textInputStream = new ByteArrayInputStream(fileDataInputStream.readNBytes(textSize));
        } catch(final IOException e) {
            log.error("Failed to read text section from file \"" + fileName + "\": " + e.getMessage());
        }
        try {
            rodata = ByteBuffer.wrap(fileDataInputStream.readNBytes(rodataSize));
        } catch(final IOException e) {
            log.error("Failed to read text section from file \"" + fileName + "\": " + e.getMessage());
        }
        textDataInputStream = new DataInputStream(textInputStream);

        try {
            // Read Blocks until there is no data left.
            while (textDataInputStream.available() > 0) {
                loadBlock();
            }
        } catch (final IOException e) {
            log.error("Failure to get available bytes from text input stream, file: \"" + fileName +
                    "\": " + e.getMessage());
        }

        return translationUnit;
    }

    protected Block loadBlock() {
        // Get the name of the Block.
        int blockNameOffset = 0;
        try {
            blockNameOffset = textDataInputStream.readInt();
        } catch (final IOException e) {
            log.error("Failed to read block size from file \"" + fileName + "\": " + e.getMessage());
        }
        final String blockName = readStringFromData(blockNameOffset);

        int actionStatementCount = 0;
        try {
            actionStatementCount = textDataInputStream.readInt();
        } catch (final IOException e) {
            log.error("Failed to read action statement count from file \"" + fileName + "\": " + e.getMessage());
        }

        // Create the Block object.
        final Block block = new Block(translationUnit, blockName);

        // Get the Header Statement of the Block.
        loadStatement(block);

        // Get the Action Statements of the Block.
        for (int i = 0; i < actionStatementCount; i++) {
            loadStatement(block);
        }

        // Add the block to the TU.
        translationUnit.addBlock(block);

        return block;
    }

    protected Statement loadStatement(final Block block) {
        // Read the statement specifier
        byte value = -1;
        Statement.StatementSpecifier statementSpecifier = Statement.StatementSpecifier.Always;
        try {
            value = textDataInputStream.readByte();
            statementSpecifier = Statement.StatementSpecifier.values()[value];
        } catch (final IOException e) {
            log.error("Failed to load statement specifier in file \"" + fileName + "\": " + e.getMessage());
        } catch (final IndexOutOfBoundsException e) {
            log.error("Failed to convert byte to statement specifier, byte value: " + value + " in file \"" + fileName
                    + "\": " + e.getMessage());
        }

        // Create the statement based on the statement specifier.
        Statement statement = null;
        if (statementSpecifier == Statement.StatementSpecifier.Label) {
            if (block.getHeaderStatement() != null) {
                log.error("Data tried to add a header statement, but already has one in file \"" + fileName + "\"");
            }

            final HeaderStatement headerStatement = new HeaderStatement(block);
            block.setHeaderStatement(headerStatement);
            statement = headerStatement;
        } else {
            final ActionStatement actionStatement = new ActionStatement(block);
            block.addActionStatement(actionStatement);
            statement = actionStatement;
        }

        statement.setStatementSpecifier(statementSpecifier);

        // Read the action name.
        final Literal actionName = loadLiteral(statement);
        statement.setActionName(actionName);

        // Read the number of literals in the statement.
        int literalCount = 0;
        try {
            literalCount = textDataInputStream.readByte();
        } catch (final IOException e) {
            log.error("Failed to load statement specifier in file \"" + fileName + "\": " + e.getMessage());
        }

        // Read each literal.
        for (int i = 0; i < literalCount; i++) {
            final Literal literal = loadLiteral(statement);
            statement.addParameter(literal);
        }

        return statement;
    }

    protected Literal loadLiteral(final Statement statement) {
        // Read the literal type.
        int read = Literal.LiteralType.Null.ordinal();
        Literal.LiteralType literalType = Literal.LiteralType.Null;
        try {
            read = textDataInputStream.readByte();
            literalType = Literal.LiteralType.values()[read];
        } catch (final IOException e) {
            log.error("Failed to read literal type from file \"" + fileName + "\": " + e.getMessage());
        } catch (final IndexOutOfBoundsException e) {
            log.error("Failed to convert read literal type " + read + " from file \"" + fileName +
                    "\": " + e.getMessage());
        }

        // Get the literal data offset.
        try {
            read = textDataInputStream.readInt();
        } catch (final IOException e) {
            log.error("Failed to read literal data offset from file \"" + fileName + "\": " + e.getMessage());
        }

        // Create the literal with the correct data.
        switch (literalType) {
            default:
            case Null:
                return new Literal(statement, literalType, "");
            case Number:
                final Integer number = readIntFromData(read);
                return new Literal(statement, literalType, number != null ? number.toString() : null);
            case Identifier:
            case String:
                final String string = readStringFromData(read);
                return new Literal(statement, literalType, string);
        }
    }

    protected Byte readByteFromData(final int offset) {
        try {
            return rodata.get(offset);
        } catch (final IndexOutOfBoundsException e) {
            log.error("Failed to read byte from file \"" + fileName + "\": " + e.getMessage());
        }

        return null;
    }

    protected Integer readIntFromData(final int offset) {
        try {
            return rodata.getInt(offset);
        } catch (final IndexOutOfBoundsException e) {
            log.error("Failed to read int from file \"" + fileName + "\": " + e.getMessage());
        }

        return null;
    }

    protected String readStringFromData(final int offset) {
        int stringLength = 0;
        try {
            stringLength = rodata.getShort(offset);
        } catch (final IndexOutOfBoundsException e) {
            log.error("Failed to read string length from file \"" + fileName + "\": " + e.getMessage());
        }
        String blockName = "";
        try {
            byte[] nameArray = new byte[stringLength];
            // Add two because the first two bytes of a serialized string is the length.
            rodata.get(offset + 2, nameArray, 0, stringLength);
            return new String(nameArray, "UTF-8");
        } catch (final IndexOutOfBoundsException e) {
            log.error("Failed to read string from file \"" + fileName + "\": " + e.getMessage());
        } catch (final UnsupportedEncodingException e) {
            log.error("Failed to encode string as UTF-8" + e.getMessage());
        }

        return null;
    }

    public void loadFile(final String inputFileName) {
        try {
            fileName = inputFileName;
            fileInputStream = new FileInputStream(inputFileName);
            fileDataInputStream = new DataInputStream(fileInputStream);
        } catch (final FileNotFoundException e) {
            log.error("Binary file not found: " + inputFileName);
        }
    }
}
