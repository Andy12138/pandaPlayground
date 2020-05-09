package com.zmg.panda;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    @Test
    public void test3() throws IOException {
        int width = 3505;
        int height = 2480;
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        image.setData();
        // 获取Graphics2D
        Graphics2D g2d = image.createGraphics();

        // ----------  增加下面的代码使得背景透明  -----------------
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        // ----------  背景透明代码结束  -----------------


        // 画图
        g2d.setColor(new Color(255,0,0));
        g2d.setStroke(new BasicStroke(1));
        g2d.setFont(new Font("黑体", Font.PLAIN, 65));
        g2d.drawString("黑体黑体黑体黑体黑体黑体黑体黑体", 100, 100);
        //释放对象
        g2d.dispose();
        // 保存文件
        ImageIO.write(image, "png", new File("d:\\tmp\\out\\黑体.png"));
    }

    @Test
    public void passwordTest() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encode = passwordEncoder.encode("123456");
        // $2a$10$oyml2lkPX477EuzA1buXWeH6Pr7OTbOtfb7m7V/MHw5hdLZEDEKnC
        // $2a$10$z5Me/7e4UL6boTE5G4sTiup7SuhsTFSZ6gCawM59MeHMG.Y7d6VY6
//        System.out.println(encode);
        System.out.println(passwordEncoder.matches("123456", "$2a$10$oyml2lkPX477EuzA1buXWeH6Pr7OTbOtfb7m7V/MHw5hdLZEDEKnC"));
    }

    @Test
    public void tokenTest() {
        Date expireTime = new Date(System.currentTimeMillis() + 1000*60*60*24);
        String token = Jwts.builder()
                .setSubject("zmg")
                .setExpiration(expireTime)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, "panda")
                .compact();
        System.out.println("生成的token: Bearer " + token);
        System.out.println("到期时间：" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(expireTime));

    }

    @Test
    public void castClassTest() {
        @AllArgsConstructor
        @ToString
        @Data
        class BB{
            private Date a;
            private String b;
        }

        Object a = null;
        BB b = (BB)a;
        System.out.println(b);
    }

    @Test
    public void dataTest() {
        @Data
        class AA {
            private String a;
        }
        List<Object> list = new ArrayList<>();
        AA aa = new AA();
        aa.setA("a");
        list.add(aa);
        aa.setA("b");
        list.add(aa);
        System.out.println(list);
        Map<String, String> map = new HashMap<>();
        map.put("a", "a");
        list.add(map);
        map.put("a", "b");
        list.add(map);
        System.out.println(list);
    }

    @Test
    public void compareDate() {
        String[] dateArray = {"2020-04-05 00:00:01", "2020-04-06 00:00:01", "2020-03-05 00:00:01", "2020-04-05 01:00:01"};
        for (int i = 0; i < dateArray.length-1; i++) {
            for (int j = 0; j < dateArray.length-1-i; j++) {
                try {
                    if (!compareDate(dateArray[j], dateArray[j+1])) {
                        String tmp = dateArray[j];
                        dateArray[j] = dateArray[j+1];
                        dateArray[j+1] = tmp;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(Arrays.toString(dateArray));
    }

    private boolean compareDate(String s1, String s2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse(s1);
        Date date2 = sdf.parse(s2);
        if (date1.compareTo(date2) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
