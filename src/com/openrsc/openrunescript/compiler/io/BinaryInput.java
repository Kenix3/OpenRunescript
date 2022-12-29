package com.openrsc.openrunescript.compiler.io;

import com.openrsc.openrunescript.datamodel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Converts binary data into a {@link TranslationUnit}
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class BinaryInput {
    private static final Logger log = LogManager.getLogger();

    /**
     * The file name from which this {@link BinaryInput} is reading data from.
     */
    private String fileName;
    /**
     * The {@link FileInputStream} for the raw data from the file.
     */
    private FileInputStream fileInputStream;
    /**
     * The {@link DataInputStream} to read from the {@link BinaryInput#fileInputStream}.
     */
    private DataInputStream fileDataInputStream;
    /**
     * The {@link ByteArrayInputStream} for the text section of the file.
     */
    private ByteArrayInputStream textInputStream;
    /**
     * The {@link DataInputStream} to read from the {@link BinaryInput#textInputStream}.
     */
    private DataInputStream textDataInputStream;
    /**
     * A {@link ByteBuffer} holding the rodata section for this file.
     */
    private ByteBuffer rodata;
    /**
     * The offset in the file that the text section begins.
     */
    private int textOffset;
    /**
     * The size in bytes of the text section.
     */
    private int textSize;
    /**
     * The offset in the file that the rodata section begins.
     */
    private int rodataOffset;
    /**
     * The size in bytes of the rodata section.
     */
    private int rodataSize;
    /**
     * The {@link TranslationUnit} that we are currently loading from the file.
     */
    private TranslationUnit translationUnit;

    /**
     * Create a {@link BinaryInput} object.
     */
    public BinaryInput() {

    }

    /**
     * Process a file's data and load a {@link TranslationUnit}.
     * @param inputFileName The name of the file to process.
     * @return The newly loaded {@link TranslationUnit}.
     */
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

    /**
     * Load a {@link Block} and add it to the {@link TranslationUnit} that we are currently loading.
     * @return The newly loaded {@link Block}.
     */
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

    /**
     * Load {@link Statement} and add it to the passed in {@link Block}.
     * @param block The {@link Block} that will be the parent of the new {@link Statement}.
     * @return The newly loaded {@link Statement}.
     */
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
        Statement statement;
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

    /**
     * Load a {@link Literal}. It will not automatically be added to the passed in {@link Statement}.
     * @param statement The parent {@link Statement} of the {@link Literal} we are loading.
     * @return The newly loaded {@link Literal}.
     */
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

    /**
     * Read a {@link Byte} from the rodata section at the specified offset.
     * @param offset The offset within the rodata section to read the {@link Byte}.
     * @return The {@link Byte} that was read, or null if read failed.
     */
    protected Byte readByteFromData(final int offset) {
        try {
            return rodata.get(offset);
        } catch (final IndexOutOfBoundsException e) {
            log.error("Failed to read byte from file \"" + fileName + "\": " + e.getMessage());
        }

        return null;
    }

    /**
     * Read a {@link Integer} from the rodata section at the specified offset.
     * @param offset The offset within the rodata section to read the {@link Integer}.
     * @return The {@link Integer} that was read, or null if read failed.
     */
    protected Integer readIntFromData(final int offset) {
        try {
            return rodata.getInt(offset);
        } catch (final IndexOutOfBoundsException e) {
            log.error("Failed to read int from file \"" + fileName + "\": " + e.getMessage());
        }

        return null;
    }

    /**
     * Read a {@link String} from the rodata section at the specified offset.
     * @param offset The offset within the rodata section to read the {@link String}.
     * @return The {@link String} that was read, or null if read failed.
     */
    protected String readStringFromData(final int offset) {
        int stringLength = 0;
        try {
            stringLength = rodata.getShort(offset);
        } catch (final IndexOutOfBoundsException e) {
            log.error("Failed to read string length from file \"" + fileName + "\": " + e.getMessage());
        }
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

    /**
     * Load a file from disk and populate the internal file streams.
     * @param inputFileName The name of the file to load.
     */
    protected void loadFile(final String inputFileName) {
        try {
            fileName = inputFileName;
            fileInputStream = new FileInputStream(inputFileName);
            fileDataInputStream = new DataInputStream(fileInputStream);
        } catch (final FileNotFoundException e) {
            log.error("Binary file not found: " + inputFileName);
        }
    }
}
