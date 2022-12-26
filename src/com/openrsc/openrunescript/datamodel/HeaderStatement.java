package com.openrsc.openrunescript.datamodel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an OpenRunescript Header Statement.
 * The Header Statement is looked up when the virtual machine receives a callback.
 *
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class HeaderStatement extends Statement {
    /**
     * Create a {@link HeaderStatement} object.
     * @param parentBlock The parent {@link Block} of this {@link Statement}.
     */
    public HeaderStatement(final Block parentBlock) {
        super(parentBlock);
    }

    public String toString() {
        if (getActionName().getType() == Literal.LiteralType.Null) {
            return "<error> Null HeaderStatement actionName";
        }

        final StringBuilder sb = new StringBuilder();
        final List<String> stringParams = new ArrayList<>();

        for (final Literal literal : getParameterList()) {
            stringParams.add(literal.toString());
        }

        sb.append(getStatementSpecifierString(getStatementSpecifier()));
        sb.append(getActionName());
        if (stringParams.size() > 0) {
            sb.append(",");
            sb.append(stringParams.stream().collect(Collectors.joining(",")));
        }
        sb.append(";");

        return sb.toString();
    }
}
