package yal.analyse ;

import java_cup.runtime.*;
import yal.exceptions.AnalyseLexicaleException;
      
%%
   
%class AnalyseurLexical
%public

%line
%column
    
%type Symbol
%eofval{
        return symbol(CodesLexicaux.EOF) ;
%eofval}

%cup

%{

  private StringBuilder chaine ;

  private Symbol symbol(int type) {
	return new Symbol(type, yyline, yycolumn) ;
  }

  private Symbol symbol(int type, Object value) {
	return new Symbol(type, yyline, yycolumn, value) ;
  }
%}

idf = [A-Za-z_][A-Za-z_0-9]*

csteE = [0-9]+

commentaire = "//".*

finDeLigne = \r|\n
espace = {finDeLigne}  | [ \t\f]

%%

"programme"            { return symbol(CodesLexicaux.PROGRAMME); }
"debut"                { return symbol(CodesLexicaux.DEBUT); }
"fin"              	   { return symbol(CodesLexicaux.FIN); }

"entier"               { return symbol(CodesLexicaux.ENTIER); }

"ecrire"               { return symbol(CodesLexicaux.ECRIRE); }

"lire"                 { return symbol(CodesLexicaux.LIRE); }

";"                    { return symbol(CodesLexicaux.POINTVIRGULE); }

"="                    { return symbol(CodesLexicaux.EGAL); }

"("                    { return symbol(CodesLexicaux.PARENTHESEOUVRANTE); }

")"                    { return symbol(CodesLexicaux.PARENTHESEFERMANTE); }

"-"                    { return symbol(CodesLexicaux.MOINS); }

"+"                    { return symbol(CodesLexicaux.PLUS); }

"*"                    { return symbol(CodesLexicaux.FOIS); }

"/"                    { return symbol(CodesLexicaux.DIVISE); }

">"                    { return symbol(CodesLexicaux.SUPERIEUR, yytext()); }

"<"                    { return symbol(CodesLexicaux.INFERIEUR, yytext()); }

"si"                    { return symbol(CodesLexicaux.SI); }

"alors"                 { return symbol(CodesLexicaux.ALORS); }

"sinon"                 { return symbol(CodesLexicaux.SINON); }

"finsi"                 { return symbol(CodesLexicaux.FINSI); }

{csteE}      	       { return symbol(CodesLexicaux.CSTENTIERE, yytext()); }

{idf}      	           { return symbol(CodesLexicaux.IDF, yytext()); }

{espace}               { }

{commentaire}          { }

.                      { throw new AnalyseLexicaleException(yyline, yycolumn, yytext()) ; }

