package org.adrianwalker.antlr.dynamicrules;

import static java.lang.String.format;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public enum ParserLookup {

  INSTANCE;

  private static final Logger LOGGER = Logger.getLogger(ParserLookup.class.getName());
  private final Map<Integer, Boolean> ruleIdEnabledMap;

  private ParserLookup() {
    ruleIdEnabledMap = new HashMap<>();
  }

  public void put(final int ruleId, final boolean enabled) {

    LOGGER.info(format("ruleId = %s, enabled = %s\n", ruleId, enabled));

    this.ruleIdEnabledMap.put(ruleId, enabled);
  }

  public boolean enabled(final int ruleId) {

    return ruleIdEnabledMap.getOrDefault(ruleId, true);
  }
}
