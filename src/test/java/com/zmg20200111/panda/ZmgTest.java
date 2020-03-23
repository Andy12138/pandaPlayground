package com.zmg20200111.panda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ZmgTest {

    @Test
    public void test() {
        List<String> list = Arrays.asList("sdd");
        String[] strings = {""};
        Set<String> set = new HashSet<>(list);
        System.out.println(Collection.class.isAssignableFrom(list.getClass()));
        System.out.println(Collection.class.isAssignableFrom(strings.getClass()));
        System.out.println(Collection.class.isAssignableFrom(set.getClass()));
        Object[] a = set.toArray();
    }

    @Test
    public void test1() {
        Date date = new Date();
        System.out.println(date.toString());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void test2() {
        List<Integer> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int beginyear = 1995;
        int loop = thisYear - 1995;
        for (int i = 0; i <= loop; i++) {
//            System.out.println(thisYear - i);
            result.add(thisYear - i);
        }
        System.out.println(result);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class AA{
        private int a;
        private String b;
    }
    @Test
    public void test3() {
        System.out.println(new AA().getClass().isAssignableFrom(AA.class));
        AA[] aa = {};
        List<String> collect = Arrays.stream(aa).map(AA::getB).collect(Collectors.toList());
        System.out.println(collect);
        List<AA> a = new ArrayList<>();
        AA[] objects = a.stream().map(map -> new AA(map.getA(), map.getB())).toArray(AA[]::new);
        System.out.println(objects);
    }
}
