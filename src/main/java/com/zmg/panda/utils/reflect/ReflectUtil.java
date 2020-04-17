package com.zmg.panda.utils.reflect;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class ReflectUtil {

    private static final String GET_METHOD_FLAG = "get";

    private static final String SET_METHOD_FLAG = "set";

    /**
     *  拷贝map到entity
     *  注意： entity中必须有空构造方法； entity的属性都必须为String类型
     * @param map
     * @param entityClass
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public <T> T copyEntity(Map map, Class<T> entityClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        // 校验对象是否有空构造函数
        validateConstructor(entityClass);
        // 校验对象中的属性是否都为String
        // todo
        // 实例化对象
        T entity = entityClass.newInstance();
        // 获取entity中所有的属性
        Field[] entityFields = entityClass.getDeclaredFields();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            for (Field field : entityFields) {
                String fieldName = field.getName();
                if (this.enqalsKeyFieldName(key, fieldName)) {
                    // 相等，则需要拷贝赋值
                    Method setMethod = this.getFieldGetSetMethod(entityClass, fieldName, SET_METHOD_FLAG);
                    setMethod.invoke(entity, map.get(key));
                }
            }
        }
        return entity;
    }

    /**
     * 检查对象的构造函数中是否含有空构造函数，没有则报错
     * @param entityClass
     * @param <T>
     */
    public <T> void validateConstructor(Class<T> entityClass) {
        boolean isNoArgsConstructorFlag = false;
        Constructor<?>[] constructors = entityClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getGenericParameterTypes().length == 0) {
                isNoArgsConstructorFlag = true;
                break;
            }
        }
        if (!isNoArgsConstructorFlag) {
            try {
                throw new Exception("该对象类没有空构造函数，对象拷贝必须含有空构造函数！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断map中的key是否和对象中的字段名一样（例如：user_name == username）
     * @param mapKey
     * @param fieldName
     * @return
     */
    private boolean enqalsKeyFieldName(String mapKey, String fieldName) {
        boolean result = false;
        if (!mapKey.isEmpty() && !fieldName.isEmpty()) {
            String[] strArrays = mapKey.split("_");
            String firstChar = strArrays[0].substring(0, 1);
            String key = Arrays.stream(strArrays).map(str ->
                    str = str.length() > 1 ? str.substring(0, 1).toUpperCase() + str.substring(1) : str
            ).collect(Collectors.joining());
            key = key.length() > 1 ? firstChar + key.substring(1) : firstChar;
            return key.equals(fieldName);
        }
        return result;
    }

    /**
     * 展示对象的所有属性和属性值
     * @param data
     * @param <T>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public <T> void listEntityFields(T data) throws InvocationTargetException, IllegalAccessException {
        Class<?> dataClass = data.getClass();
        Field[] fields = dataClass.getDeclaredFields();
        System.out.println("开始处理对象:" + dataClass.getName());
        System.out.println("------------>该对象所有的属性为");
        for (Field field : fields) {
            String fieldName = field.getName();
            Method getMethod = getFieldGetSetMethod(dataClass, fieldName, GET_METHOD_FLAG);
            String fieldValue = null != getMethod.invoke(data) ? getMethod.invoke(data).toString() : null;
            System.out.println(fieldName + " ---> " + fieldValue);
        }
    }

    /**
     * 暂时map中的所有key-value
     * @param map
     */
    public void listMapKeyValues(Map map) {
        System.out.println("开始处理map:");
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            System.out.println(key + " ---> " + map.get(key));
        }
    }

    /**
     * 获取对象的get/set方法
     * @param dataClass
     * @param fieldName
     * @return
     */
    private Method getFieldGetSetMethod(Class<?> dataClass, String fieldName, String methodFlag) {
        // 拼写get方法名字，属性首字母大写
        String getMethodName = methodFlag + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = null;
        try {
            if (methodFlag.equals(GET_METHOD_FLAG)) {
                method = dataClass.getMethod(getMethodName);
            } else {
                method = dataClass.getMethod(getMethodName, String.class);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

}
