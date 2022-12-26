package com.openrsc.openrunescript.datamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents an OpenRunescript file / Translaton Unit.
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class TranslationUnit {
    private static final Logger log = LogManager.getLogger();

    /**
     * A list of {@link Block} objects held by this {@link TranslationUnit}
     */
    private final LinkedHashMap<String, Block> blocks;

    /**
     * Create a new {@link TranslationUnit}.
     */
    public TranslationUnit() {
        blocks = new LinkedHashMap<>();
    }

    /**
     * Get a script block by the hash of its block header.
     *
     * @param hash The hash of the block header.
     * @return Returns a {@link Block} that matches the hash, or null if there is no block.
     */
    public Block getBlock(final String hash) {
        return blocks.get(hash);
    }

    /**
     * Add a {@link Block} to the {@link TranslationUnit}
     * @param block The {@link Block} to add
     * @return The {@link Block} that was added.
     */
    public Block addBlock(final Block block) {
        return blocks.put(block.getHash(), block);
    }

    /**
     * Links another {@link TranslationUnit} into this one.
     * @param other The other {@link TranslationUnit} to link.
     */
    public void linkTo(final TranslationUnit other) {
        blocks.putAll(other.blocks);
    }

    public String toString() {
        final List<String> stringParams = new ArrayList<>();

        for (final Map.Entry<String, Block> entry : blocks.entrySet()) {
            stringParams.add(entry.getValue().toString());
        }

        return stringParams.stream().collect(Collectors.joining("\n\n"));
    }
}
