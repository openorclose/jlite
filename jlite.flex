import java_cup.runtime.*;
      
%%
%class Lexer
%line
%column
%cup

%{
  private static final int MAX_ASCII_CHAR = 127; // assumes ascii is from 0 - 127
  private StringBuilder stringBuilder;
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
  private void error(String message) {
    int oneBasedLineNumber = yyline + 1;
    int oneBasedColumnNumber = yycolumn + 1;
    throw new Error(String.format("Line %d Column %d: %s", oneBasedLineNumber, oneBasedColumnNumber, message));
  }
%}

LineTerminator = \r|\n|\r\n

WhiteSpace     = {LineTerminator} | [ \t\f]

int_lit = 0 | [1-9][0-9]*

id = [a-z][A-Za-z_0-9]*

cname = [A-Z][A-Za-z_0-9]*

%state STRING_LITERAL
%state SINGLE_LINE_COMMENT
%state MULTI_LINE_COMMENT

%%

<YYINITIAL> {
// separators
";" { return symbol(sym.SEMI); }
"," { return symbol(sym.COMMA); }
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
"{" { return symbol(sym.LBRACE); }
"}" { return symbol(sym.RBRACE); }

// operators
"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"/" { return symbol(sym.DIVIDE); }
"<" { return symbol(sym.LT); }
">" { return symbol(sym.GT); }
"<=" { return symbol(sym.LTE); }
">=" { return symbol(sym.GTE); }
"==" { return symbol(sym.EQ); }
"!=" { return symbol(sym.NEQ); }
"&&" { return symbol(sym.AND); }
"||" { return symbol(sym.OR); }
"!" { return symbol(sym.NOT); }
"." { return symbol(sym.MEMBER); }

// assignment
"="  { return symbol(sym.ASSIGN); }
   
// keywords
"class" {return symbol(sym.CLASS);}
"if" {return symbol(sym.IF);}
"else" {return symbol(sym.ELSE);}
"while" {return symbol(sym.WHILE);}
"readln" {return symbol(sym.READLN);}
"println" {return symbol(sym.PRINTLN);}
"return" {return symbol(sym.RETURN);}
"main" {return symbol(sym.MAIN);}
"this" {return symbol(sym.THIS);}
"new" {return symbol(sym.NEW);}
"null" {return symbol(sym.NULL);}
"true" { return symbol(sym.TRUE); }
"false" { return symbol(sym.FALSE); }

// primitive types
"Int" {return symbol(sym.INT);}
"Bool" {return symbol(sym.BOOL);}
"String" {return symbol(sym.STRING);}
"Void" {return symbol(sym.VOID);}

// literals

{int_lit} { return symbol(sym.INTEGERLITERAL, new Integer(yytext())); }

\" { stringBuilder = new StringBuilder(); yybegin(STRING_LITERAL); }

{cname} { return symbol(sym.CNAME, (yytext())); }

{id} { return symbol(sym.ID, (yytext()));}

{WhiteSpace} { /* ignore */ }

// comments

\/\/ { yybegin(SINGLE_LINE_COMMENT); }

\/\* { yybegin(MULTI_LINE_COMMENT); }
}

<STRING_LITERAL> {
// we just append any contiguous string of non \ \n \t \r \b " characters to the string
[^\\\n\t\r\b\"]+ { stringBuilder.append(yytext()); }

{LineTerminator} { error("String literals cannot contain a line terminator"); }

// special characters
\\\\ { stringBuilder.append('\\'); }
\\n { stringBuilder.append('\n'); }
\\t { stringBuilder.append('\t'); }
\\r { stringBuilder.append('\r'); }
\\b { stringBuilder.append('\b'); }
\\\" { stringBuilder.append('"'); }

// escaped ascii hex code
\\x[a-fA-F0-9][a-fA-F0-9] {
  int value = Integer.parseInt(yytext().substring(2), 16);
  if (value > MAX_ASCII_CHAR) {
    error(yytext() + " is not a valid ascii character in hex");
  }
  stringBuilder.append((char) value);
}

// escaped decimal character
\\[0-9][0-9][0-9] {
  int value = Integer.parseInt(yytext().substring(1), 10);
  if (value > MAX_ASCII_CHAR) {
    error(yytext() + " is not a valid ascii character in decimal");
  }
  stringBuilder.append((char) value);
}

// allow any other character after \ to return itself (redundant escape)
\\. { stringBuilder.append(yytext().substring(1)); }

// end of string, build the string then return to YYINITIAL state
\" { yybegin(YYINITIAL); return symbol(sym.STRINGLITERAL, stringBuilder.toString());}

<<EOF>> { error("Unterminated string literal at end of file"); }
}

<SINGLE_LINE_COMMENT> {
{LineTerminator} { yybegin(YYINITIAL); } // return to YYINITIAL state on new line

. { /* ignore */ } // ignore any other characters
}

<MULTI_LINE_COMMENT> {
\*\/ { yybegin(YYINITIAL); } // return to YYINITIAL state on new line

. { /* ignore */ } // ignore any other characters

{LineTerminator} { /* ignore */ }

<<EOF>> { error("Unterminated multi-line comment at end of file"); }
}

/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]      { error("Illegal token <"+yytext()+">"); }
