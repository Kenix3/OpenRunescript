package com.openrsc.openrunescript.compiler;

import com.openrsc.openrunescript.datamodel.TranslationUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Links compiled OpenRunescript {@link com.openrsc.openrunescript.datamodel.TranslationUnit}
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class Linker {
    private static final Logger log = LogManager.getLogger();

    /**
     * The parent {@link Compiler} of this {@link Linker}.
     */
    private final Compiler parentCompiler;

    /**
     * The {@link TranslationUnit} that the {@link Linker} will return.
     */
    private final TranslationUnit translationUnit;

    /**
     * Create a {@link Linker} object.
     * @param parentCompiler The parent {@link Compiler} for this {@link Linker}.
     */
    public Linker(final Compiler parentCompiler) {
        this.parentCompiler = parentCompiler;
        this.translationUnit = new TranslationUnit();
    }

    /**
     * Link all of the {@link TranslationUnit} on the parent {@link Compiler}.
     * @return A new {@link TranslationUnit} with all of the {@link com.openrsc.openrunescript.datamodel.Block}
     *
     */
    public TranslationUnit link() {
        // Loop through all the translation units on the parent compiler, and link them into the newly created one.
        for (final TranslationUnit compiledTranslationUnit : getCompiler().getTranslationUnits()) {
            getTranslationUnit().linkTo(compiledTranslationUnit);
        }

        return getTranslationUnit();
    }

    /**
     * Get the parent {@link Compiler} for this {@link Linker}.
     * @return The parent {@link Compiler} for this {@link Linker}.
     */
    public Compiler getCompiler() {
        return parentCompiler;
    }

    /**
     * Get the linked {@link TranslationUnit}.
     * @return The linked {@link TranslationUnit}.
     */
    public TranslationUnit getTranslationUnit() {
        return translationUnit;
    }
}
