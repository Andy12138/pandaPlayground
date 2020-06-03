package com.zmg.panda.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmg.panda.common.bean.WsMessage;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.*;

/**
 * @author Andy
 */
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public static  <T> String writeValueAsString(T entity) {
        try {
            return OBJECT_MAPPER.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> rangeJson(String json) {
        Map<String, Object> result = new HashMap<>();
        JSONObject jsonObject = new JSONObject(json);
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.get(key);
            // 如果还是封装对象
            if (!JSONObject.NULL.equals(value)) {
                if (JSONObject.class.isAssignableFrom(value.getClass())) {
                    Map<String, Object> map = rangeJson(value.toString());
                    result.putAll(map);
                } else {
                    result.put(key, value);
                }
            }
        }
        return result;
    }

    public Object findVlueByKey(String json, String key) {
        if (this.validateIsJson(json)) {
            if (this.validateIsJSONObject(json)) {
                JSONObject jsonObject = new JSONObject(json);
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String name = keys.next();
                    Object value = jsonObject.get(name);
                    if (name.equals(key)) {
                        return value;
                    } else {
                        Object childValue = findVlueByKey(value.toString(), key);
                        if (childValue != null) {
                            return childValue;
                        }
                    }
                }
            }
            if (this.validateIsJSONArray(json)) {
                JSONArray jsonArray = new JSONArray(json);
                for (Object o : jsonArray) {
                    Object childValue = findVlueByKey(o.toString(), key);
                    if (childValue != null) {
                        return childValue;
                    }
                }
            }
        }
        return null;
    }

    public boolean validateIsJson(String json) {
        if (null != json) {
            try {
                OBJECT_MAPPER.readValue(json, Object.class);
                return true;
            } catch (JsonProcessingException e) {
                return false;
            }
        }
        return false;
    }

    public boolean validateIsJSONObject(String json) {
        if (null != json) {
            try {
                new JSONObject(json);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean validateIsJSONArray(String json) {
        if (null != json) {
            try {
                new JSONArray(json);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");
        jsonMap.put("map", map);
        WsMessage wsMessage = new WsMessage();
        wsMessage.setFromUser("zmg");
        wsMessage.setMessageText("play");
        wsMessage.setToUser("lfq");
//        jsonMap.put("wsMessage", wsMessage);
        jsonMap.put("array", new int[]{1,2,3,4,5});
        jsonMap.put("array2", new ArrayList<>(Arrays.asList(1,2,"3",wsMessage,5L)));
        jsonMap.put("name", 777);
        jsonMap.put("null", null);
        String json = JsonUtils.writeValueAsString(jsonMap);

        JsonUtils ju = new JsonUtils();
        System.out.println(ju.validateIsJson(json));
        System.out.println(ju.validateIsJson(null));
        System.out.println(ju.validateIsJson("{}"));
        System.out.println(ju.validateIsJson("[1,2,3]"));
        System.out.println(ju.validateIsJson("sdfsfds"));
        System.out.println(ju.validateIsJson("ds:sd"));
        Map<String, Object> stringObjectMap = ju.rangeJson(json);
        System.out.println(stringObjectMap);
        System.out.println(ju.findVlueByKey(json, "array3"));
    }


}
