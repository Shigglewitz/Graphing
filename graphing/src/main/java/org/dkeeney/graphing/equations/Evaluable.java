package org.dkeeney.graphing.equations;

import java.math.BigDecimal;
import java.util.Map;

public interface Evaluable {
    public double evaluate(Map<String, BigDecimal> varValues);
}
