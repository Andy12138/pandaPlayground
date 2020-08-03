package com.zmg.panda;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.zmg.panda.common.AESUtils;
import com.zmg.panda.common.AESUtils2;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

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

    @Test
    public void test4() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        map.put("c", "d");
        String json = JSONObject.toJSONString(map);
        System.out.println(json);
        // 方法一
        Map newMap = JSONObject.parseObject(json, Map.class);
        System.out.println(newMap.toString());
        // 方法二
        JsonMapper mapper = new JsonMapper();
        try {
            Map map1 = mapper.readValue(json, new TypeReference<Map>() {});
            System.out.println(map1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test5() {
        // 注意：一定要使用创建对象的格式创建数组
        Integer[] a = new Integer[] { 6, 3, 9, 3, 2, 4, 5, 7 };
        Integer[] b = new Integer[] { 5, 8, 6, 2, 1, 9 };
        List _a = Arrays.asList(a);
        List _b = Arrays.asList(b);
        // 创建集合
        Collection realA = new ArrayList<Integer>(_a);
        Collection realB = new ArrayList<Integer>(_b);
        // 求交集
        realA.retainAll(realB);
        System.out.println("交集结果：" + realA);
        Set result = new HashSet();
        // 求全集
        result.addAll(_a);
        result.addAll(_b);
        System.out.println("全集结果：" + result);
        // 求差集：结果
        Collection aa = new ArrayList(realA);
        Collection bb = new ArrayList(result);
        bb.removeAll(aa);
        System.out.println("最终结果：" + bb);
    }

    @Test
    public void test6() {
        String url = "loc:8080/ots/api/v1/sys/sysClientDict";
        String urlmate = "api/v1/sys/sys*";
        url = url.substring(url.indexOf("api/v1"));
        System.out.println(url);
        String[] split = urlmate.split("\\*");
        System.out.println(this.validate1(split, url));
    }

    private boolean validate1(String[] array, String url) {
        if (url.indexOf(array[0]) != 0) {
            return false;
        }
        for (int i = array.length - 1; i >= 0; i--) {
            if (url.lastIndexOf(array[i]) >= 0) {
                url = url.substring(0, url.lastIndexOf(array[i]));
            } else {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test7() throws Exception {
        String zmg = AESUtils.encryptByAES("钟名桂");
//        System.out.println(zmg);
//        String s = AESUtils.decryptByAES(zmg);
//        System.out.println(s);
        String zmg2 = "钟名sdfsdfsf桂";
        String encrypt = AESUtils2.encrypt(zmg2, "1234567812345678");
        System.out.println(encrypt);
        byte[] decrypt = AESUtils2.decrypt(encrypt, "1234567812345678");
        System.out.println(new String(decrypt));

    }

    private static boolean validateIP(String startIP, String endIP) {
        // 分离出ip中的四个数字位
        String[] startIPArray = startIP.split("\\.");
        String[] endIPArray = endIP.split("\\.");
        // 取得各个数字
        long[] startIPNum = new long[4];
        long[] endIPNum = new long[4];
        for (int i = 0; i < 4; i++) {
            startIPNum[i] = Long.parseLong(startIPArray[i].trim());
            endIPNum[i] = Long.parseLong(endIPArray[i].trim());
        }
        // 各个数字乘以相应的数量级
        long startIPNumTotal = startIPNum[0] * 256 * 256 * 256 + startIPNum[1] * 256 * 256 + startIPNum[2] * 256
            + startIPNum[3];
        long endIPNumTotal = endIPNum[0] * 256 * 256 * 256 + endIPNum[1] * 256 * 256 + endIPNum[2] * 256 + endIPNum[3];
        return startIPNumTotal <= endIPNumTotal;
    }

    @Test
    public void test8() {
        String ipConf = "192.168.1.1-192.168.1.40,192.168.2.1-192.168.2.40";
        String ip = "192.168.1.3";
        System.out.println("handleIp:"+ip);
        String[] ipArray = ipConf.split(",");
        for (String ipRange : ipArray) {
            System.out.println("****===>****");
            String[] ipPart = ipRange.split("-");
            System.out.println("start:"+ipPart[0]);
            System.out.println("end:"+ipPart[1]);
            System.out.println(validateIP(ipPart[0], ip));
            System.out.println(validateIP(ip, ipPart[1]));
        }

    }

    @Test
    public void test9() {
        String a = "ODER BY a,b,c";
        String b = "d";
        int index = a.toLowerCase().indexOf("desc") > 0 ? a.toLowerCase().indexOf("desc") : a.toLowerCase().indexOf("asc");
        if (0 > index) {
            index = a.length();
        }
        System.out.println(a.substring(0, index) + ", " + b + " " + a.substring(index));
    }

    private void handleArray(int[] array) {
        array[0] = 4;
    }

    @Test
    public void test10() {
        String a = null;
        System.out.println(String.valueOf(a));
        int[] arr = new int[]{1,2,3,4,0};
        int tmp=0;
        for(int i= 0;i<arr.length-1;i++) {//冒泡次数
            for(int j=0;j<arr.length-1-i;j++) {//比较的次数
                if(arr[j]>arr[j+1]) {//加入前面的元素小于后面的元素 调换位置
                    tmp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=tmp;
                }
            }
        }
        for (int i : arr) {
            System.out.println(i);
        }
        Integer[] arr2 = new Integer[]{1,2,3,4,0};
        List<Integer> integers1 = Arrays.asList(arr2);
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 2);
        integers.sort(Comparator.comparingInt(i -> i));
        System.out.println(integers);
    }

    @Test
    public void test11() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        System.out.println(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String json = "{\"d\":\"b\"}";
        System.out.println(json.charAt(0));
        System.out.println(json.substring(1));
        String date = "2020-07-27";
        String[] split = date.split("-");
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(Integer.parseInt(split[0]) == calendar.get(Calendar.YEAR));
        System.out.println(Integer.parseInt(split[1]) == calendar.get(Calendar.MONTH) + 1);
        System.out.println(Integer.parseInt(split[2]) == calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void test12() throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        /*天数差*/
        Date fromDate1 = simpleFormat.parse("2008-02-01 12:23");
        Date toDate1 = simpleFormat.parse("2008-3-1 12:23");
        long from1 = fromDate1.getTime();
        long to1 = toDate1.getTime();
        int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
        System.out.println("两个时间之间的天数差为：" + days);
        System.out.println(Calendar.getInstance().get(Calendar.MONTH));
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.DAY_OF_MONTH, 3);
        System.out.println(simpleFormat.format(instance.getTime()));
        instance.set(Calendar.HOUR, 5);
        System.out.println(simpleFormat.format(instance.getTime()));
    }

    @Test
    public void test13() throws JsonProcessingException {
        Abc abc = new Abc();
        abc.setDBc("哈哈");

        JsonMapper mapper = new JsonMapper();
        String json = mapper.writeValueAsString(abc);
        System.out.println(json);

        String json2 = "{\"d_bc\":\"哈哈\"}";
        System.out.println(json2);
        Abc abc1 = mapper.readValue(json2, Abc.class);
        System.out.println(abc1.toString());
    }

    @Test
    public void test14() {
        int a = 2;
        Integer c = a == 2 ? 1 : null;
        System.out.println(c);

        System.out.println(Math.round(2.839027*100.001)/100.0);
    }

    @Data
    public static class Abc{
        @JsonProperty("d_bc")
        private String dBc;
    }

    @Data
    public static class HHFF{
        private String d;
    }



    private void handleList(List<String> strings) {
        strings.removeIf("1"::equals);
        Integer[] arr2 = new Integer[]{1,2,3,4,0};
        ddd(arr2);

//        strings = new ArrayList<>(Arrays.asList("a", "b"));
    }

    private void ddd(Integer... d) {

    }

    public static Map<String, Object> parseJSON2Map(JSONObject json) {
        Map<String, Object> map = new HashMap<>();
        // 最外层解析
        for (Object k : json.keySet()) {
            Object v = json.get(k);
        }
        return map;
    }


}
