package com.ggxueche.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类转换初始化
 */
public class TUtil {
    public static <T> T getT(Object o, int i) {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) o.getClass().getGenericSuperclass();
            Type type = parameterizedType.getActualTypeArguments()[i];
            return (T) type;
        } catch (ClassCastException e) {
        }
        return null;
    }

    public static <T> T getType(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (ClassCastException e) {
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
