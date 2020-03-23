package com.zmg20200111.panda.utils.validate;

import org.junit.jupiter.api.Test;

public class PatternUtilTest {
    private PatternUtil patternUtil = new PatternUtil();

    @Test
    public void validateCode1() {
        String a = "________________________________________________________________________";
        System.out.println(patternUtil.validateCode1(a));
    }


}
