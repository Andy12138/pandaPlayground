package com.zmg20200111.panda.utils;

import org.junit.jupiter.api.Test;

public class ProcessUtilTest {

    @Test
    public void runCommandsTest() {
        ProcessUtil processUtil = new ProcessUtil();
        processUtil.runCommands(null);
    }

}
