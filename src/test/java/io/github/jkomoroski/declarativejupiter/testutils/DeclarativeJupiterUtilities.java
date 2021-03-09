package io.github.jkomoroski.declarativejupiter.testutils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DeclarativeJupiterUtilities {

    public boolean isDecimalNumber(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
