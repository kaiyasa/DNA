grammar Dna;

xUnit
  :
  | statement+
  ;

statement
  : definition
  | codeStatement
  ;

definition
  : simpleDefinition
  | structuredDefinition
  ;

simpleDefinition
  : type identifier (',' identifier)*
  ;

structuredDefinition
  : 'struct' identifier '{' definition+ '}'
  ;

type
  : K_int
  | K_string
  | identifier
  ;

codeStatement
  : assignment
    ;

assignment
  : identifier '=' expression
  ;

expression
  : '(' expr1=expression ')'
  | expr1=expression op=('*'|'/') expr2=expression
  | expr1=expression op=('+'|'-') expr2=expression
  | callSite
  | atom
  ;

callSite
  : identifier '(' expression (',' expression)* ')'
    ;

atom
  : literal
  | identifier
  ;

identifier
  : ID
  ;

literal
  : INT
  | STRING
  ;

// lexer


K_int : 'int' ;
K_string : 'string' ;

ID
  : [A-Za-z_][A-Za-z0-9_]*
  ;

INT
  : '-'? [0-9]+
  ;

STRING
  : '"' .*? '"'
  ;

WS
  : [ \t\r\n]+
  -> skip
  ;

