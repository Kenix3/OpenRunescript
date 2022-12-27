package com.openrsc.openrunescript.compiler;

import com.openrsc.openrunescript.datamodel.TranslationUnit;
import com.openrsc.openrunescript.compiler.parser.*;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Compiles OpenRunescript source files.
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class Compiler {
    private static final Logger log = LogManager.getLogger();

    /**
     * The OpenRunescript source files to compile.
     */
    private final HashMap<String, CharStream> files;
    /**
     * A list of {@link TranslationUnit} that the {@link Compiler} is working on.
     */
    private final ArrayList<TranslationUnit> translationUnits;
    /**
     * The {@link Linker} for this {@link Compiler}.
     */
    private final Linker linker;

    /**
     * Create a Compiler.
     * @param files The OpenRunescript source files to compile.
     */
    public Compiler(final HashMap<String, CharStream> files) {
        this.files = files;

        linker = new Linker(this);
        translationUnits = new ArrayList<>(files.size());
    }

    /**
     * Compile an OpenRunescript file.
     * @param fileName The name of the file to be compiled.
     * @param file The specific file to compile.
     * @return The {@link TranslationUnit} OpenRunescript data for the file that was compiled.
     */
    protected TranslationUnit run(final String fileName, final CharStream file) {
        final OpenRunescriptLexer lexer = new OpenRunescriptLexer(file);
        final BufferedTokenStream tokenStream = new BufferedTokenStream(lexer);
        final OpenRunescriptParser parser = new OpenRunescriptParser(tokenStream);
        final ParseTree parseTree = parser.translationUnit();
        final ParseTreeVisitor visitor = new ParseTreeVisitor(fileName);
        final TranslationUnit tu = (TranslationUnit)visitor.visit(parseTree);
        translationUnits.add(tu);
        return tu;
    }

    /**
     * Compile all the OpenRunescript files in {@link Compiler#files} and link them.
     * @return The {@link TranslationUnit} OpenRunescript data for the files that were compiled.
     */
    public TranslationUnit run() {
        translationUnits.clear();

        // Loop through all the files and run the compiler against them.
        for (final Map.Entry<String, CharStream> entry : files.entrySet()) {
            run(entry.getKey(), entry.getValue());
        }

        final TranslationUnit translationUnit = linker.link();
        // TODO: Save binary to disk

        return translationUnit;
    }

    /**
     * Get the list of {@link TranslationUnit} that have been compiled from text source.
     * @return The list of {@link TranslationUnit} that have been compiled from text source.
     */
    protected ArrayList<TranslationUnit> getTranslationUnits() {
        return translationUnits;
    }

    /**
     * Get the {@link Linker} for this {@link Compiler}.
     * @return The {@link Linker} for this {@link Compiler}.
     */
    public Linker getLinker() {
        return linker;
    }

    /**
     * Entry point for the Compiler program.
     * @param args Compiler command line arguments
     */
    public static void main(String[] args) {
        // If there are no source files passed in, we need to quit.
        if (args.length < 1) {
            log.error("No OpenRunescript source files specified.");
            return;
        }

        final HashMap<String, CharStream> files = new HashMap<>(args.length);

        // Loop through all the files, affirm they exist, and create a CharStream from them.
        for (final String arg : args) {
            try {
                files.put(arg, CharStreams.fromFileName(arg));
            } catch (final IOException e) {
                log.error("Could not find file \"" + arg + "\"");
                return;
            }
        }

        try {
            // Create a Compiler.
            final Compiler compiler = new Compiler(files);
            final TranslationUnit translationUnit = compiler.run();
            System.out.print(translationUnit);
        } catch (final Exception e) {
            log.error(e);
        }
    }
}
