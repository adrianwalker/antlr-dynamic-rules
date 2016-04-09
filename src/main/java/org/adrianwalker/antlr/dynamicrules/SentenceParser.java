package org.adrianwalker.antlr.dynamicrules;

import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

public final class SentenceParser {

  public SentenceParser() {
  }

  public void setWords(final List<String> words) {
    LexerLookup.INSTANCE.put(DynamicRulesLexer.WORD, words);
  }

  public void enableWords(final boolean enabled) {
    ParserLookup.INSTANCE.put(DynamicRulesParser.RULE_words, enabled);
  }

  public Result parse(final String term) throws RecognitionException {

    DynamicRulesLexer lexer = new DynamicRulesLexer(new ANTLRInputStream(term));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    DynamicRulesParser parser = new DynamicRulesParser(tokens);

    return new Result(parser.sentence().getText(), parser.getNumberOfSyntaxErrors());
  }

  public static final class Result {

    private String text;
    private int numberOfSyntaxErrors;

    public Result(final String text, final int numberOfSyntaxErrors) {
      this.text = text;
      this.numberOfSyntaxErrors = numberOfSyntaxErrors;
    }

    public String getText() {
      return text;
    }

    public void setText(final String text) {
      this.text = text;
    }

    public int getNumberOfSyntaxErrors() {
      return numberOfSyntaxErrors;
    }

    public void setNumberOfSyntaxErrors(final int numberOfSyntaxErrors) {
      this.numberOfSyntaxErrors = numberOfSyntaxErrors;
    }
  }
}
