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
    private TranslationUnit contextTranslationUnit;
    /**
     * The {@link Block} that this {@link ParseTreeVisitor} is operating on.
     */
    private Block contextBlock;
    /**
     * The {@link Statement} that this {@link ParseTreeVisitor} is operating on.
     */
    private Statement contextStatement;
    /**
     * The name of the file that this {@link ParseTreeVisitor} is operating on.
     */
    private final String fileName;

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

    /**
     * Create a {@link Literal} from the data in the parse tree.
     * Additionally, transform it to lower case in order to allow the action name to be case-insensitive.
     * This does require the class which has runtime functions for Runescript calls to be defined in lower case.
     * @param ctx the parse tree
     * @return a {@link Literal} representing the parse tree.
     */
    @Override
    public Literal visitActionName(OpenRunescriptParser.ActionNameContext ctx) {
        return new Literal(getStatement(), Literal.LiteralType.Identifier, ctx.Identifier().getText().toLowerCase());
    }

    /**
     * Create an action {@link Statement.StatementSpecifier} from the parse tree.
     * @param ctx the parse tree
     * @return A {@link Statement.StatementSpecifier} representing the parse tree.
     */
    @Override
    public Statement.StatementSpecifier visitActionSpecifier(OpenRunescriptParser.ActionSpecifierContext ctx) {
        return Statement.getStatementSpecifierEnum(ctx.getText());
    }

    /**
     * Create a label {@link Statement.StatementSpecifier} from the parse tree.
     * @param ctx the parse tree
     * @return A {@link Statement.StatementSpecifier} representing the parse tree.
     */
    @Override
    public Statement.StatementSpecifier visitLabelSpecifier(OpenRunescriptParser.LabelSpecifierContext ctx) {
        return Statement.getStatementSpecifierEnum(ctx.getText());
    }

    /**
     * Create a statement specified from the parse tree.
     * The grammar doesn't currently use this rule in any higher order rules, so this should be considered unused.
     * @param ctx the parse tree
     * @return A {@link Statement.StatementSpecifier} representing the parse tree.
     */
    @Override
    public Statement.StatementSpecifier visitStatementSpecifier(OpenRunescriptParser.StatementSpecifierContext ctx) {
        // Create a general statement specifier from the StatementSpecifierContext.
        return Statement.getStatementSpecifierEnum(ctx.getText());
    }

    /**
     * Create a general {@link Literal} from the parse tree.
     * @param ctx the parse tree
     * @return a {@link Literal} representing the parse tree.
     */
    @Override
    public Literal visitLiteral(OpenRunescriptParser.LiteralContext ctx) {
        if (ctx.Identifier() != null) {
            // If the Literal has a child Identifier token, then we create an Identifier type Literal.
            return new Literal(getStatement(), Literal.LiteralType.Identifier, ctx.Identifier().getText().toLowerCase());
        } else if (ctx.String() != null) {
            // If the Literal has a child String token, then we create a String type Literal.

            // Chop off the first and last character of the string, which is just the quotes.
            String stringLiteral = ctx.String().getText();
            stringLiteral = stringLiteral.substring(1, stringLiteral.length() - 1);
            return new Literal(getStatement(), Literal.LiteralType.String, stringLiteral);
        } else if (ctx.Number() != null) {
            // If the Literal has a child Number token, then we create a Number type Literal.
            return new Literal(getStatement(), Literal.LiteralType.Number, ctx.Number().getText());
        }

        // If the Literal does not have an Identifier, String, or Number token, then the Literal is null.
        // This should never happen as per the language grammar.
        log.error("Encountered a Literal with no valid token.");
        return new Literal(getStatement());
    }

    /**
     * Create a list of {@link Literal} from the parse tree.
     * @param ctx the parse tree
     * @return a list of {@link Literal} representing the parse tree.
     */
    @Override
    public ArrayList<Literal> visitLiteralList(OpenRunescriptParser.LiteralListContext ctx) {
        // Populate a list of Literals and return it.
        final ArrayList<Literal> literalList = new ArrayList<>();
        for (final OpenRunescriptParser.LiteralContext literalContext : ctx.literal()) {
            literalList.add(visitLiteral(literalContext));
        }

        return literalList;
    }

    /**
     * Extract an {@link ActionStatement} object from the parse tree.
     * First we get the {@link Statement.StatementSpecifier}, then the action name, and then a {@link Literal} list.
     * All the extracted data is added to the returned {@link ActionStatement}.
     * @param ctx the parse tree
     * @return an {@link ActionStatement} representing the parse tree.
     */
    @Override
    public ActionStatement visitActionStatement(OpenRunescriptParser.ActionStatementContext ctx) {
        contextStatement = new ActionStatement(getBlock());
        getStatement().setStatementSpecifier(visitActionSpecifier(ctx.actionSpecifier()));
        getStatement().setActionName(visitActionName(ctx.actionName()));
        getStatement().addParameters(visitLiteralList(ctx.literalList()));

        return (ActionStatement) getStatement();
    }

    /**
     * Extract an {@link HeaderStatement} object from the parse tree.
     * First we get the {@link Statement.StatementSpecifier}, then the action name, and then a {@link Literal} list.
     * All the extracted data is added to the returned {@link HeaderStatement}.
     * @param ctx the parse tree
     * @return an {@link HeaderStatement} representing the parse tree.
     */
    @Override
    public HeaderStatement visitHeaderStatement(OpenRunescriptParser.HeaderStatementContext ctx) {
        contextStatement = new HeaderStatement(getBlock());
        getStatement().setStatementSpecifier(visitLabelSpecifier(ctx.labelSpecifier()));
        getStatement().setActionName(visitActionName(ctx.actionName()));
        getStatement().addParameters(visitLiteralList(ctx.literalList()));

        return (HeaderStatement) getStatement();
    }

    /**
     * Extract a {@link Block} object from the parse tree.
     * We first get the {@link HeaderStatement}, then get a list of {@link ActionStatement}.
     * All the extracted data is added to the returned {@link Block}.
     * @param ctx the parse tree
     * @return a {@link Block} representing the parse tree.
     */
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

    /**
     * Extract a TranslationUnit object from the parse tree.
     * We loop through the available child {@link Block} in the parse tree and create {@link Block} objects.
     * All the extracted data is added to the returned {@link TranslationUnit}.
     * @param ctx the parse tree
     * @return a {@link TranslationUnit} representing the parse tree.
     */
    @Override
    public TranslationUnit visitTranslationUnit(OpenRunescriptParser.TranslationUnitContext ctx) {
        //
        //
        // All the extracted data mentioned in this comment is added to the TranslationUnit.
        log.trace("In TU, \"" + getFileName() + "\" encountered " + ctx.block().size() + " blocks.");

        contextTranslationUnit = new TranslationUnit();

        for (final OpenRunescriptParser.BlockContext blockCtx : ctx.block()) {
            getTranslationUnit().addBlock(visitBlock(blockCtx));
        }

        return getTranslationUnit();
    }
}
