// Generated from java-escape by ANTLR 4.11.1
package com.openrsc.openrunescript.compiler.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link OpenRunescriptParser}.
 */
public interface OpenRunescriptListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#actionName}.
	 * @param ctx the parse tree
	 */
	void enterActionName(OpenRunescriptParser.ActionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#actionName}.
	 * @param ctx the parse tree
	 */
	void exitActionName(OpenRunescriptParser.ActionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#actionSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterActionSpecifier(OpenRunescriptParser.ActionSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#actionSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitActionSpecifier(OpenRunescriptParser.ActionSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#labelSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterLabelSpecifier(OpenRunescriptParser.LabelSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#labelSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitLabelSpecifier(OpenRunescriptParser.LabelSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#statementSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterStatementSpecifier(OpenRunescriptParser.StatementSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#statementSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitStatementSpecifier(OpenRunescriptParser.StatementSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(OpenRunescriptParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(OpenRunescriptParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#literalList}.
	 * @param ctx the parse tree
	 */
	void enterLiteralList(OpenRunescriptParser.LiteralListContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#literalList}.
	 * @param ctx the parse tree
	 */
	void exitLiteralList(OpenRunescriptParser.LiteralListContext ctx);
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void enterActionStatement(OpenRunescriptParser.ActionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#actionStatement}.
	 * @param ctx the parse tree
	 */
	void exitActionStatement(OpenRunescriptParser.ActionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#headerStatement}.
	 * @param ctx the parse tree
	 */
	void enterHeaderStatement(OpenRunescriptParser.HeaderStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#headerStatement}.
	 * @param ctx the parse tree
	 */
	void exitHeaderStatement(OpenRunescriptParser.HeaderStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(OpenRunescriptParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(OpenRunescriptParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link OpenRunescriptParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void enterTranslationUnit(OpenRunescriptParser.TranslationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link OpenRunescriptParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void exitTranslationUnit(OpenRunescriptParser.TranslationUnitContext ctx);
}