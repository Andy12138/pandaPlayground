package com.zmg.panda.common.constants;

import org.junit.jupiter.api.Test;

class RoleEnumTest {

    @Test
    public void test1() {
        System.out.println(RoleEnum.valueOf("ADMIN").getRoleName());
    }

}