package com.zmg.panda.utils;

import org.junit.jupiter.api.Test;


class DateUtilsTest {

    @Test
    public void test() {
        System.out.println(DateUtils.getNow());
        System.out.println(DateUtils.getLastMonthTime());
        System.out.println(DateUtils.getLastYearTime());
        System.out.println(DateUtils.getLastMonthLastDay());
    }

}