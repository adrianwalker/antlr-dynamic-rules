grammar DynamicRules;

@lexer::header {
  import org.adrianwalker.antlr.dynamicrules.LexerLookup;
}

@lexer::members {
  public static final LexerLookup LOOKUP = LexerLookup.INSTANCE;
}

@parser::header {
  import org.adrianwalker.antlr.dynamicrules.ParserLookup;
}

@parser::members {
  public static final ParserLookup LOOKUP = ParserLookup.INSTANCE;
}

// Parser Rules

sentence : ({LOOKUP.enabled(RULE_words)}? words) FULL_STOP ;
words : WORD (WS WORD)+ ;

// Lexer Rules

WORD : {LOOKUP.contains(WORD, _input)}? . ;
FULL_STOP : '.' ;
WS : [ \t\r\n]+ ;
OTHER : . ;


