package org.adrianwalker.antlr.dynamicrules;

import static java.util.Arrays.asList;
import org.adrianwalker.antlr.dynamicrules.SentenceParser.Result;
import org.junit.Assert;
import org.junit.Test;

public class SentenceParserTest {

  @Test
  public void testValid() {

    SentenceParser parser = new SentenceParser();
    parser.enableWords(true);
    parser.setWords(asList(new String[]{
      "on", "cat", "mat", "sat", "the"
    }));

    Result result = parser.parse("the cat sat on the mat.");

    Assert.assertEquals("the cat sat on the mat.", result.getText());
    Assert.assertEquals(0, result.getNumberOfSyntaxErrors());
  }

  @Test
  public void testDisabledRule() {

    SentenceParser parser = new SentenceParser();
    parser.enableWords(false);
    parser.setWords(asList(new String[]{
      "on", "cat", "mat", "sat", "the"
    }));

    Result result = parser.parse("the cat sat on the mat.");

    Assert.assertEquals(1, result.getNumberOfSyntaxErrors());
  }

  @Test
  public void testInvalidWords() {

    SentenceParser parser = new SentenceParser();
    parser.enableWords(false);
    parser.setWords(asList(new String[]{
      "on", "cat", "mat", "sat", "the"
    }));

    Result result = parser.parse("INVALID");

    Assert.assertEquals(1, result.getNumberOfSyntaxErrors());
  }

  @Test
  public void testUpdateWordsAndDisableRule() {

    SentenceParser parser = new SentenceParser();
    parser.enableWords(true);
    parser.setWords(asList(new String[]{
      "the"
    }));

    Result result = parser.parse("the cat sat on the mat.");

    Assert.assertEquals(1, result.getNumberOfSyntaxErrors());

    parser.setWords(asList(new String[]{
      "on", "cat", "mat", "sat", "the"
    }));

    result = parser.parse("the cat sat on the mat.");

    Assert.assertEquals(0, result.getNumberOfSyntaxErrors());

    parser.enableWords(false);

    result = parser.parse("the cat sat on the mat.");

    Assert.assertEquals(1, result.getNumberOfSyntaxErrors());
  }
}
