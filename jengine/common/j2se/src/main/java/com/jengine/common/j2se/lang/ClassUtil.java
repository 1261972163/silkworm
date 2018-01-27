package com.jengine.common.j2se.lang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Created by nouuid on 2015/5/7.
 */
public class ClassUtil {

    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 获取泛型参数列表中的类型
     * @param type
     * @param i
     * @return
     */
    public static Class<?> getGenericParaClass(Type type, int i) {
        if (type instanceof ParameterizedType) { // 处理泛型类型
            return getParameterizedGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return (Class<?>) getGenericParaClass(((TypeVariable<?>) type).getBounds()[0], 0); // 处理泛型擦拭对象
        } else {// class本身也是type，强制转型
            return (Class<?>) type;
        }
    }

    /**
     * 获取泛型参数列表中的类型
     * @param parameterizedType 泛型类型
     * @param i 位置
     * @return
     */
    private static Class<?> getParameterizedGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) { // 处理多级泛型
            return (Class<?>) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
            return (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
            return (Class<?>) getGenericParaClass(((TypeVariable<?>) genericClass).getBounds()[0], 0);
        } else {
            return (Class<?>) genericClass;
        }
    }


}
