package com.zmg20200111.panda.utils.validate;

import java.util.regex.Pattern;

public class PatternUtil {

    private static final Pattern PATTERN_1 = Pattern.compile("^[0-9a-zA-Z_]{1,}$");

    public boolean validateCode1(String string) {
        if (string.length() > 30) {
            System.out.println("字符串超出30");
            return false;
        }
        return PATTERN_1.matcher(string).matches();
    }
}
