package com.zmg.panda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.zmg.panda.bean.AA;
import org.junit.jupiter.api.Test;

import java.util.*;

public class JacksonTest {

    @Test
    public void test1() {
        AA aa = new AA(1,2);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString("哈哈哈"));
            String json = objectMapper.writeValueAsString(aa);
            System.out.println(objectMapper.writeValueAsString(aa));
            System.out.println(objectMapper.writeValueAsBytes(aa));
            AA a1 = objectMapper.readValue(json, AA.class);
            AA a2 = objectMapper.readValue(json, new TypeReference<AA>() {
            });
            Set<AA> as = new HashSet<>();
            as.add(a1);
            a1.setA(2);
            as.add(a1);
            String jsons = objectMapper.writeValueAsString(as);
            List<AA> aas = objectMapper.readValue(jsons, new TypeReference<List<AA>>() {
            });
            System.out.println(aas);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        AA aa = new AA(1,2);
        JsonMapper objectMapper = new JsonMapper();
        try {
            System.out.println(objectMapper.writeValueAsString("哈哈哈"));
            String json = objectMapper.writeValueAsString(aa);
            System.out.println(objectMapper.writeValueAsString(aa));
            System.out.println(objectMapper.writeValueAsBytes(aa));
            AA a1 = objectMapper.readValue(json, AA.class);
            AA a2 = objectMapper.readValue(json, new TypeReference<AA>() {
            });
            AA aa1 = toObject(json, AA.class);
            Set<AA> as = new HashSet<>();
            as.add(a1);
            a1.setA(2);
            as.add(a1);
            String jsons = objectMapper.writeValueAsString(as);
            List<AA> aas = objectMapper.readValue(jsons, List.class);
            Map<String, List<AA>> am = new HashMap<>();
            am.put("zmg", aas);
            String jsonm = objectMapper.writeValueAsString(am);
            Map<String, List<AA>> map = toObject(jsonm, Map.class);
            HashMap<String, List<AA>> stringListHashMap = toObject(jsonm, new HashMap<String, List<AA>>());
            HashMap<String, List<AA>> stringListHashMap1 = toObject(jsonm, new TypeReference<HashMap<String, List<AA>>>() {
            });
            System.out.println(aas);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public <T> T toObject(String json, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            T t = mapper.readValue(json, tClass);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T toObject(String json, T entity) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T t = mapper.readValue(json, new TypeReference<T>() {
            });
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T toObject(String json, TypeReference<T> valueTypeRef) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T t = mapper.readValue(json, valueTypeRef);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Test
    public void test3() {
        List<Object> objects = Collections.singletonList(null);
        System.out.println(objects);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String s = mapper.writeValueAsString("[]");
            System.out.println("结果集为" + s);
            List<String> strings = mapper.readValue("[]", new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
