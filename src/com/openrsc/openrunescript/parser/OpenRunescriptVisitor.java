// Generated from java-escape by ANTLR 4.11.1
package com.openrsc.openrunescript.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link OpenRunescriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface OpenRunescriptVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#actionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitActionName(OpenRunescriptParser.ActionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#actionSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitActionSpecifier(OpenRunescriptParser.ActionSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#labelSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelSpecifier(OpenRunescriptParser.LabelSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#statementSpecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementSpecifier(OpenRunescriptParser.StatementSpecifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(OpenRunescriptParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#literalList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralList(OpenRunescriptParser.LiteralListContext ctx);
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#actionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitActionStatement(OpenRunescriptParser.ActionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#headerStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeaderStatement(OpenRunescriptParser.HeaderStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(OpenRunescriptParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link OpenRunescriptParser#translationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslationUnit(OpenRunescriptParser.TranslationUnitContext ctx);
}