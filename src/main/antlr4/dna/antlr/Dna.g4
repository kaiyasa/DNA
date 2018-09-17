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
  : expression op=('+'|'-') expression
  | expression op=('*'|'/') expression
  | '(' expression ')'
  | literal
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