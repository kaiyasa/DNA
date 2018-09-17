grammar Dna;

xUnit
  :
  | statement+
  ;

statement
  : definition
  | assignment
  ;

definition
  : types identifier (',' identifier)*
  | 'struct' identifier '{' definition+ '}'
  ;

types
  : 'int'
  | 'string'
  | identifier
  ;

assignment
  : identifier '=' expression
  ;

expression
  : expr1=expression op=('+'|'-') expr2=expression
  | expr1=expression op=('*'|'/') expr2=expression
  | '(' expression ')'
  | atom
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
