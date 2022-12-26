package com.openrsc.openrunescript.datamodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an OpenRunescript Action Statement
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class ActionStatement extends Statement {
    private static final Logger log = LogManager.getLogger();

    /**
     * Create a {@link ActionStatement} object.
     * @param parentBlock The parent {@link Block} of this {@link Statement}.
     */
    public ActionStatement(final Block parentBlock) {
        super(parentBlock);
    }

    public String toString() {
        if (getActionName().getType() == Literal.LiteralType.Null) {
            return "<error> Null ActionStatement actionName";
        }

        final StringBuilder sb = new StringBuilder();
        final List<String> stringParams = new ArrayList<>();

        for (final Literal literal : getParameterList()) {
            stringParams.add(literal.toString());
        }

        sb.append("     ");
        sb.append(getStatementSpecifierString(getStatementSpecifier()));
        sb.append(getActionName());
        sb.append("(");
        sb.append(stringParams.stream().collect(Collectors.joining(",")));
        sb.append(")");
        sb.append(";");

        return sb.toString();
    }
}
