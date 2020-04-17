package com.zmg.panda.utils.jdbc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

class DatabaseMetadataUtilsTest {

    private DatabaseMetadataUtils utils = new DatabaseMetadataUtils();

    @Test
    public void showSechemasTest() {
        utils.showSchemas();
        utils.showTables();
    }

    @Test
    public void test() {
        int count = 5;
        List<String> list = new ArrayList<>(count + 1);
        StringJoiner sj = new StringJoiner(" |");
        for (int i = 0; i < count; i++) {
            sj.add("%-10s");
        }
        list.add(sj.toString() + "\n");
//        list.set(0, "yyyy");
        System.out.println(list.toString());
        System.out.format("%-10s |%-10s %-10s\n","zmg88", "ll", "df");
        System.out.format("%-10s","zmg88");
        System.out.format(" |%-10s", "ll");
        System.out.format(" |%-10s\n","df");
        String[] a = new String[]{};
//        System.out.format(a.);
    }
}