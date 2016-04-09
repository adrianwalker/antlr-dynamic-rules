package org.adrianwalker.antlr.dynamicrules;

import static java.lang.String.format;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import static org.adrianwalker.antlr.dynamicrules.DynamicRulesParser.ruleNames;
import org.antlr.v4.runtime.CharStream;

public enum LexerLookup {

  INSTANCE;

  private static final Logger LOGGER = Logger.getLogger(LexerLookup.class.getName());
  private static final Comparator<String> LONGEST_FIRST = (s1, s2) -> s2.length() - s1.length();

  private final Map<Integer, Set<String>> tokenIdTermsMap;

  private LexerLookup() {
    tokenIdTermsMap = new HashMap<>();
  }

  public void put(final int tokenId, final List<String> tokens) {

    if (null == tokens) {
      throw new IllegalArgumentException("Illegal argument, tokens must be not null");
    }

    tokens.removeIf(Objects::isNull);

    Collections.sort(tokens, LONGEST_FIRST);

    LinkedHashSet tokenSet = new LinkedHashSet(tokens);

    LOGGER.info(format("tokens '%s' %s\n", ruleNames[tokenId - 1], tokenSet));

    this.tokenIdTermsMap.put(tokenId, tokenSet);
  }

  public boolean contains(final int tokenId, final CharStream input) {

    boolean contains = false;

    if (!tokenIdTermsMap.containsKey(tokenId)) {
      return contains;
    }

    Set<String> terms = tokenIdTermsMap.get(tokenId);

    for (String term : terms) {

      contains = ahead(term, input);

      if (contains) {
        LOGGER.info(format("contains '%s' ('%s')\n", term, ruleNames[tokenId - 1]));
        break;
      }
    }

    return contains;
  }

  private boolean ahead(final String word, final CharStream input) {

    for (int i = 0; i < word.length(); i++) {

      char wordChar = word.charAt(i);
      int inputChar = input.LA(i + 1);

      if (inputChar != wordChar) {
        return false;
      }
    }

    input.seek(input.index() + word.length() - 1);

    return true;
  }
}
