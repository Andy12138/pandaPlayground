package com.zmg20200111.panda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OtsTest {

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
//        list.set(0, "0");
        List<String> list2 = new ArrayList<>(list.size() + 1);
        list2.add("0");
        list2.addAll(list);

        System.out.println(list2);
    }

    @Test
    public void test2() throws InvocationTargetException, IllegalAccessException {
        @AllArgsConstructor
        @ToString
        @Data
        class AA{
            private Date a;
            private Date b;
        }

        @AllArgsConstructor
        @ToString
        @Data
        class BB{
            private Date a;
            private String b;
        }

        try {
            AA a = new AA(new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-2 12:12:12"), new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-2 12:12:12"));
            BB b = new BB(new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-2 12:12:14"), "2020-4-2");
            Class<? extends AA> aClass = a.getClass();
            Class<? extends BB> bClass = b.getClass();
//            Field aField = aClass.getDeclaredField("b");
//            Field bField = bClass.getDeclaredField("b");
//            System.out.println(aField.getType());
//            System.out.println(Date.class.equals(aField.getType()));
//            System.out.println(bField.getType());
//            System.out.println(String.class.equals(bField.getType()));
//            Method aMethod = aClass.getMethod("getB");
//            System.out.println(aMethod.invoke(a).toString());
//            Method bMethod = bClass.getMethod("getB");
//            System.out.println(bMethod.invoke(b).toString());
//            System.out.println(aMethod.invoke(a).equals(new SimpleDateFormat("yyyy-MM-dd").parse(bMethod.invoke(b).toString())));

            Field aField = aClass.getDeclaredField("a");
            Field bField = bClass.getDeclaredField("a");
            System.out.println(aField.getType());
            System.out.println(Date.class.equals(aField.getType()));
            System.out.println(bField.getType());
            System.out.println(String.class.equals(bField.getType()));
            Method aMethod = aClass.getMethod("getA");
            System.out.println(aMethod.invoke(a).toString());
            Method bMethod = bClass.getMethod("getA");
            System.out.println(bMethod.invoke(b));
            System.out.println(aMethod.invoke(a).toString().equals(bMethod.invoke(b).toString()));
            System.out.println("b".substring(1));
        } catch (ParseException | NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
