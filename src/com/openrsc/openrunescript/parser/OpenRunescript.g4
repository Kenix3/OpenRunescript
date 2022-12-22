grammar OpenRunescript;

fragment StringCharacter : ~["\r\n];
fragment FirstIdentifierCharacter : [a-zA-Z_];
fragment IdentifierCharacter : [a-zA-Z0-9_];
fragment Digit : [0-9];
fragment NonZeroDigit : [1-9];

fragment OpenBlockComment : '/*';
fragment LongOpenBlockComment : '//*';
fragment CloseBlockComment : '*/';
fragment LongCloseBlockComment : '*//';
fragment StartLineComment : '//';
fragment Quote : '"';
fragment EndOfLine : [\r\n];
fragment NotEndOfLine : ~[\r\n];
fragment Tab : [\t];
fragment Space : ' ';
fragment WhitespaceCharacters : EndOfLine | Tab | Space;

LabelSpecifier : '=';
AlwaysSpecifier : '*';
TrueSpecifier : '+';
FalseSpecifier : '-';

EndStatement : ';';
Comma : ',';
OpenParens : '(';
CloseParens : ')';

LineComment
    : StartLineComment NotEndOfLine*
      -> skip
    ;

BlockComment
    : (OpenBlockComment | LongOpenBlockComment) .*? (CloseBlockComment | LongCloseBlockComment)
      -> skip
    ;

WhiteSpace
    : WhitespaceCharacters+
      -> skip
    ;

Number
    : Digit | NonZeroDigit Digit*
    ;

String
    : Quote StringCharacter+ Quote
    ;

Identifier
    : FirstIdentifierCharacter IdentifierCharacter*
    ;

actionName
    : Identifier
    ;

actionSpecifier
    : AlwaysSpecifier | TrueSpecifier | FalseSpecifier
    ;

labelSpecifier
    : LabelSpecifier
    ;

statementSpecifier
    : labelSpecifier | actionSpecifier
    ;

literal
    : Number | String | Identifier
    ;

literalList
    : literal (Comma literal)*
    ;

actionStatement
    : actionSpecifier actionName OpenParens literalList CloseParens EndStatement
    ;

headerStatement
    : labelSpecifier actionName (',' literalList) EndStatement
    ;

block
    : headerStatement actionStatement*
    ;

translationUnit
    : block+ EOF
    ;
