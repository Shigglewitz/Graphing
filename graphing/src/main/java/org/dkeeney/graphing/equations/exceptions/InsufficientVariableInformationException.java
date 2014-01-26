package org.dkeeney.graphing.equations.exceptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dkeeney.graphing.equations.Equation;

public class InsufficientVariableInformationException extends Exception {

    private static final long serialVersionUID = 2624232956195618460L;
    private static final Pattern REMAINING_VARIABLES_PATTERN = Pattern
            .compile("(" + Equation.VARIABLE_REGEX + ")");

    public InsufficientVariableInformationException() {
        super();
    }

    public static InsufficientVariableInformationException findMissingVars(
            String equation) {
        Matcher matcher = REMAINING_VARIABLES_PATTERN.matcher(equation);
        String missingVars = null;

        while (matcher.find()) {
            if (missingVars == null) {
                missingVars = matcher.group(0);
            } else {
                missingVars += ", " + matcher.group(0);
            }
        }

        return new InsufficientVariableInformationException(
                "Missing variable values for " + missingVars);
    }

    private InsufficientVariableInformationException(String message) {
        super(message);
    }
}
