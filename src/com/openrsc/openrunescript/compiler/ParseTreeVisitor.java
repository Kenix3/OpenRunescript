package com.openrsc.openrunescript.compiler;

import com.openrsc.openrunescript.datamodel.*;
import com.openrsc.openrunescript.compiler.parser.OpenRunescriptBaseVisitor;
import com.openrsc.openrunescript.compiler.parser.OpenRunescriptParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Visitor for the OpenRunescript Parser.
 **
 * @author kenix3 kenixwhisperwind@gmail.com
 */
public class ParseTreeVisitor extends OpenRunescriptBaseVisitor<Object> {
    private static final Logger log = LogManager.getLogger();

    /**
     * The {@link TranslationUnit} that this {@link ParseTreeVisitor} is operating on.
     */
    TranslationUnit contextTranslationUnit;
    /**
     * The {@link Block} that this {@link ParseTreeVisitor} is operating on.
     */
    Block contextBlock;
    /**
     * The {@link Statement} that this {@link ParseTreeVisitor} is operating on.
     */
    Statement contextStatement;
    /**
     * The name of the file that this {@link ParseTreeVisitor} is operating on.
     */
    final String fileName;

    /**
     * Create a {@link ParseTreeVisitor}.
     * @param fileName The fileName that this ParseTreeVisitor is operating on.
     */
    public ParseTreeVisitor(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get the context {@link TranslationUnit}
     * @return The context {@link TranslationUnit}
     */
    public TranslationUnit getTranslationUnit() {
        return contextTranslationUnit;
    }

    /**
     * Get the context {@link Block}.
     * @return The context {@link Block}.
     */
    public Block getBlock() {
        return contextBlock;
    }

    /**
     * Get the context {@link Statement}.
     * @return The context {@link Statement}.
     */
    public Statement getStatement() {
        return contextStatement;
    }

    /**
     * Get the File Name
     * @return The File Name
     */
    public String getFileName() {
        return fileName;
    }

    @Override
    public Literal visitActionName(OpenRunescriptParser.ActionNameContext ctx) {
        return new Literal(getStatement(), Literal.LiteralType.Identifier, ctx.Identifier().getText().toLowerCase());
    }

    @Override
    public Statement.StatementSpecifier visitActionSpecifier(OpenRunescriptParser.ActionSpecifierContext ctx) {
        return Statement.getStatementSpecifierEnum(ctx.getText());
    }

    @Override
    public Statement.StatementSpecifier visitLabelSpecifier(OpenRunescriptParser.LabelSpecifierContext ctx) {
        return Statement.getStatementSpecifierEnum(ctx.getText());
    }

    @Override
    public Statement.StatementSpecifier visitStatementSpecifier(OpenRunescriptParser.StatementSpecifierContext ctx) {
        return Statement.getStatementSpecifierEnum(ctx.getText());
    }

    @Override
    public Literal visitLiteral(OpenRunescriptParser.LiteralContext ctx) {
        if (ctx.Identifier() != null) {
            return new Literal(getStatement(), Literal.LiteralType.Identifier, ctx.Identifier().getText().toLowerCase());
        } else if (ctx.String() != null) {
            // Chop off the first and last character of the string, which is just the quotes.
            String stringLiteral = ctx.String().getText();
            stringLiteral = stringLiteral.substring(1, stringLiteral.length() - 1);
            return new Literal(getStatement(), Literal.LiteralType.String, stringLiteral);
        } else if (ctx.Number() != null) {
            return new Literal(getStatement(), Literal.LiteralType.Number, ctx.Number().getText());
        }

        log.error("Encountered a Literal with no valid token.");
        // This should never happen...
        return new Literal(getStatement());
    }

    @Override
    public ArrayList<Literal> visitLiteralList(OpenRunescriptParser.LiteralListContext ctx) {
        final ArrayList<Literal> literalList = new ArrayList<>();
        for (final OpenRunescriptParser.LiteralContext literalContext : ctx.literal()) {
            literalList.add(visitLiteral(literalContext));
        }

        return literalList;
    }

    @Override
    public ActionStatement visitActionStatement(OpenRunescriptParser.ActionStatementContext ctx) {
        contextStatement = new ActionStatement(getBlock());
        getStatement().setStatementSpecifier(visitActionSpecifier(ctx.actionSpecifier()));
        getStatement().setActionName(visitActionName(ctx.actionName()));
        getStatement().addParameters(visitLiteralList(ctx.literalList()));

        return (ActionStatement) getStatement();
    }

    @Override
    public HeaderStatement visitHeaderStatement(OpenRunescriptParser.HeaderStatementContext ctx) {
        contextStatement = new HeaderStatement(getBlock());
        getStatement().setStatementSpecifier(visitLabelSpecifier(ctx.labelSpecifier()));
        getStatement().setActionName(visitActionName(ctx.actionName()));
        getStatement().addParameters(visitLiteralList(ctx.literalList()));

        return (HeaderStatement) getStatement();
    }

    @Override
    public Block visitBlock(OpenRunescriptParser.BlockContext ctx) {
        contextBlock = new Block(getTranslationUnit(), getFileName());
        final HeaderStatement headerStatement = visitHeaderStatement(ctx.headerStatement());
        final ArrayList<ActionStatement> actionStatements = new ArrayList<>(ctx.actionStatement().size());

        for (OpenRunescriptParser.ActionStatementContext actionStatementContext : ctx.actionStatement()) {
            actionStatements.add(visitActionStatement(actionStatementContext));
        }

        getBlock().setHeaderStatement(headerStatement);
        getBlock().addAdtionStatements(actionStatements);

        log.trace("In TU, \"" + getFileName() + "\" encountered block: " + getBlock().getHash());

        return getBlock();
    }

    @Override
    public TranslationUnit visitTranslationUnit(OpenRunescriptParser.TranslationUnitContext ctx) {
        log.trace("In TU, \"" + getFileName() + "\" encountered " + ctx.block().size() + " blocks.");

        contextTranslationUnit = new TranslationUnit();

        for (final OpenRunescriptParser.BlockContext blockCtx : ctx.block()) {
            getTranslationUnit().addBlock(visitBlock(blockCtx));
        }

        return getTranslationUnit();
    }
}
