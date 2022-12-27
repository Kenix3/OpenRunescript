// Generated from java-escape by ANTLR 4.11.1
package com.openrsc.openrunescript.compiler.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class OpenRunescriptParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LabelSpecifier=1, AlwaysSpecifier=2, TrueSpecifier=3, FalseSpecifier=4, 
		EndStatement=5, Comma=6, OpenParens=7, CloseParens=8, LineComment=9, BlockComment=10, 
		WhiteSpace=11, Number=12, String=13, Identifier=14;
	public static final int
		RULE_actionName = 0, RULE_actionSpecifier = 1, RULE_labelSpecifier = 2, 
		RULE_statementSpecifier = 3, RULE_literal = 4, RULE_literalList = 5, RULE_actionStatement = 6, 
		RULE_headerStatement = 7, RULE_block = 8, RULE_translationUnit = 9;
	private static String[] makeRuleNames() {
		return new String[] {
			"actionName", "actionSpecifier", "labelSpecifier", "statementSpecifier", 
			"literal", "literalList", "actionStatement", "headerStatement", "block", 
			"translationUnit"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "'*'", "'+'", "'-'", "';'", "','", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LabelSpecifier", "AlwaysSpecifier", "TrueSpecifier", "FalseSpecifier", 
			"EndStatement", "Comma", "OpenParens", "CloseParens", "LineComment", 
			"BlockComment", "WhiteSpace", "Number", "String", "Identifier"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public OpenRunescriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ActionNameContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(OpenRunescriptParser.Identifier, 0); }
		public ActionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterActionName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitActionName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitActionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionNameContext actionName() throws RecognitionException {
		ActionNameContext _localctx = new ActionNameContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_actionName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ActionSpecifierContext extends ParserRuleContext {
		public TerminalNode AlwaysSpecifier() { return getToken(OpenRunescriptParser.AlwaysSpecifier, 0); }
		public TerminalNode TrueSpecifier() { return getToken(OpenRunescriptParser.TrueSpecifier, 0); }
		public TerminalNode FalseSpecifier() { return getToken(OpenRunescriptParser.FalseSpecifier, 0); }
		public ActionSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterActionSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitActionSpecifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitActionSpecifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionSpecifierContext actionSpecifier() throws RecognitionException {
		ActionSpecifierContext _localctx = new ActionSpecifierContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_actionSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			_la = _input.LA(1);
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 28L) != 0) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LabelSpecifierContext extends ParserRuleContext {
		public TerminalNode LabelSpecifier() { return getToken(OpenRunescriptParser.LabelSpecifier, 0); }
		public LabelSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterLabelSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitLabelSpecifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitLabelSpecifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelSpecifierContext labelSpecifier() throws RecognitionException {
		LabelSpecifierContext _localctx = new LabelSpecifierContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_labelSpecifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(LabelSpecifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementSpecifierContext extends ParserRuleContext {
		public LabelSpecifierContext labelSpecifier() {
			return getRuleContext(LabelSpecifierContext.class,0);
		}
		public ActionSpecifierContext actionSpecifier() {
			return getRuleContext(ActionSpecifierContext.class,0);
		}
		public StatementSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterStatementSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitStatementSpecifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitStatementSpecifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementSpecifierContext statementSpecifier() throws RecognitionException {
		StatementSpecifierContext _localctx = new StatementSpecifierContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statementSpecifier);
		try {
			setState(28);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LabelSpecifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(26);
				labelSpecifier();
				}
				break;
			case AlwaysSpecifier:
			case TrueSpecifier:
			case FalseSpecifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(27);
				actionSpecifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode Number() { return getToken(OpenRunescriptParser.Number, 0); }
		public TerminalNode String() { return getToken(OpenRunescriptParser.String, 0); }
		public TerminalNode Identifier() { return getToken(OpenRunescriptParser.Identifier, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			_la = _input.LA(1);
			if ( !(((_la) & ~0x3f) == 0 && ((1L << _la) & 28672L) != 0) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralListContext extends ParserRuleContext {
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(OpenRunescriptParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(OpenRunescriptParser.Comma, i);
		}
		public LiteralListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterLiteralList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitLiteralList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitLiteralList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralListContext literalList() throws RecognitionException {
		LiteralListContext _localctx = new LiteralListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_literalList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			literal();
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(33);
				match(Comma);
				setState(34);
				literal();
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ActionStatementContext extends ParserRuleContext {
		public ActionSpecifierContext actionSpecifier() {
			return getRuleContext(ActionSpecifierContext.class,0);
		}
		public ActionNameContext actionName() {
			return getRuleContext(ActionNameContext.class,0);
		}
		public TerminalNode OpenParens() { return getToken(OpenRunescriptParser.OpenParens, 0); }
		public TerminalNode CloseParens() { return getToken(OpenRunescriptParser.CloseParens, 0); }
		public TerminalNode EndStatement() { return getToken(OpenRunescriptParser.EndStatement, 0); }
		public LiteralListContext literalList() {
			return getRuleContext(LiteralListContext.class,0);
		}
		public ActionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterActionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitActionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitActionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionStatementContext actionStatement() throws RecognitionException {
		ActionStatementContext _localctx = new ActionStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_actionStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			actionSpecifier();
			setState(41);
			actionName();
			setState(42);
			match(OpenParens);
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 28672L) != 0) {
				{
				setState(43);
				literalList();
				}
			}

			setState(46);
			match(CloseParens);
			setState(47);
			match(EndStatement);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HeaderStatementContext extends ParserRuleContext {
		public LabelSpecifierContext labelSpecifier() {
			return getRuleContext(LabelSpecifierContext.class,0);
		}
		public ActionNameContext actionName() {
			return getRuleContext(ActionNameContext.class,0);
		}
		public TerminalNode EndStatement() { return getToken(OpenRunescriptParser.EndStatement, 0); }
		public TerminalNode Comma() { return getToken(OpenRunescriptParser.Comma, 0); }
		public LiteralListContext literalList() {
			return getRuleContext(LiteralListContext.class,0);
		}
		public HeaderStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_headerStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterHeaderStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitHeaderStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitHeaderStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderStatementContext headerStatement() throws RecognitionException {
		HeaderStatementContext _localctx = new HeaderStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_headerStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			labelSpecifier();
			setState(50);
			actionName();
			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Comma) {
				{
				setState(51);
				match(Comma);
				setState(52);
				literalList();
				}
			}

			setState(55);
			match(EndStatement);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public HeaderStatementContext headerStatement() {
			return getRuleContext(HeaderStatementContext.class,0);
		}
		public List<ActionStatementContext> actionStatement() {
			return getRuleContexts(ActionStatementContext.class);
		}
		public ActionStatementContext actionStatement(int i) {
			return getRuleContext(ActionStatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			headerStatement();
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((_la) & ~0x3f) == 0 && ((1L << _la) & 28L) != 0) {
				{
				{
				setState(58);
				actionStatement();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TranslationUnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(OpenRunescriptParser.EOF, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TranslationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_translationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).enterTranslationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof OpenRunescriptListener ) ((OpenRunescriptListener)listener).exitTranslationUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof OpenRunescriptVisitor ) return ((OpenRunescriptVisitor<? extends T>)visitor).visitTranslationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TranslationUnitContext translationUnit() throws RecognitionException {
		TranslationUnitContext _localctx = new TranslationUnitContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_translationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(64);
				block();
				}
				}
				setState(67); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==LabelSpecifier );
			setState(69);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u000eH\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0003\u0003\u001d\b\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005"+
		"$\b\u0005\n\u0005\f\u0005\'\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0003\u0006-\b\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u00076\b\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0005\b<\b\b\n\b\f\b?\t\b\u0001"+
		"\t\u0004\tB\b\t\u000b\t\f\tC\u0001\t\u0001\t\u0001\t\u0000\u0000\n\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0000\u0002\u0001\u0000\u0002"+
		"\u0004\u0001\u0000\f\u000eC\u0000\u0014\u0001\u0000\u0000\u0000\u0002"+
		"\u0016\u0001\u0000\u0000\u0000\u0004\u0018\u0001\u0000\u0000\u0000\u0006"+
		"\u001c\u0001\u0000\u0000\u0000\b\u001e\u0001\u0000\u0000\u0000\n \u0001"+
		"\u0000\u0000\u0000\f(\u0001\u0000\u0000\u0000\u000e1\u0001\u0000\u0000"+
		"\u0000\u00109\u0001\u0000\u0000\u0000\u0012A\u0001\u0000\u0000\u0000\u0014"+
		"\u0015\u0005\u000e\u0000\u0000\u0015\u0001\u0001\u0000\u0000\u0000\u0016"+
		"\u0017\u0007\u0000\u0000\u0000\u0017\u0003\u0001\u0000\u0000\u0000\u0018"+
		"\u0019\u0005\u0001\u0000\u0000\u0019\u0005\u0001\u0000\u0000\u0000\u001a"+
		"\u001d\u0003\u0004\u0002\u0000\u001b\u001d\u0003\u0002\u0001\u0000\u001c"+
		"\u001a\u0001\u0000\u0000\u0000\u001c\u001b\u0001\u0000\u0000\u0000\u001d"+
		"\u0007\u0001\u0000\u0000\u0000\u001e\u001f\u0007\u0001\u0000\u0000\u001f"+
		"\t\u0001\u0000\u0000\u0000 %\u0003\b\u0004\u0000!\"\u0005\u0006\u0000"+
		"\u0000\"$\u0003\b\u0004\u0000#!\u0001\u0000\u0000\u0000$\'\u0001\u0000"+
		"\u0000\u0000%#\u0001\u0000\u0000\u0000%&\u0001\u0000\u0000\u0000&\u000b"+
		"\u0001\u0000\u0000\u0000\'%\u0001\u0000\u0000\u0000()\u0003\u0002\u0001"+
		"\u0000)*\u0003\u0000\u0000\u0000*,\u0005\u0007\u0000\u0000+-\u0003\n\u0005"+
		"\u0000,+\u0001\u0000\u0000\u0000,-\u0001\u0000\u0000\u0000-.\u0001\u0000"+
		"\u0000\u0000./\u0005\b\u0000\u0000/0\u0005\u0005\u0000\u00000\r\u0001"+
		"\u0000\u0000\u000012\u0003\u0004\u0002\u000025\u0003\u0000\u0000\u0000"+
		"34\u0005\u0006\u0000\u000046\u0003\n\u0005\u000053\u0001\u0000\u0000\u0000"+
		"56\u0001\u0000\u0000\u000067\u0001\u0000\u0000\u000078\u0005\u0005\u0000"+
		"\u00008\u000f\u0001\u0000\u0000\u00009=\u0003\u000e\u0007\u0000:<\u0003"+
		"\f\u0006\u0000;:\u0001\u0000\u0000\u0000<?\u0001\u0000\u0000\u0000=;\u0001"+
		"\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000>\u0011\u0001\u0000\u0000"+
		"\u0000?=\u0001\u0000\u0000\u0000@B\u0003\u0010\b\u0000A@\u0001\u0000\u0000"+
		"\u0000BC\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000CD\u0001\u0000"+
		"\u0000\u0000DE\u0001\u0000\u0000\u0000EF\u0005\u0000\u0000\u0001F\u0013"+
		"\u0001\u0000\u0000\u0000\u0006\u001c%,5=C";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}