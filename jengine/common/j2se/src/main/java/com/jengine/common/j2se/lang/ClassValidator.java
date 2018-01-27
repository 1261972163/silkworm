package com.jengine.common.j2se.lang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by nouuid on 2015/4/29.
 */
public class ClassValidator {

    public static final Log logger = LogFactory.getLog(ClassValidator.class);

    public static boolean isBoolean(Class<?> clazz) {
        return boolean.class.equals(clazz);
    }

    public static boolean isByte(Class<?> clazz) {
        return byte.class.equals(clazz);
    }

    public static boolean isChar(Class<?> clazz) {
        return char.class.equals(clazz);
    }

    public static boolean isShort(Class<?> clazz) {
        return short.class.equals(clazz);
    }

    public static boolean isInt(Class<?> clazz) {
        return int.class.equals(clazz);
    }

    public static boolean isLong(Class<?> clazz) {
        return long.class.equals(clazz);
    }

    public static boolean isFloat(Class<?> clazz) {
        return float.class.equals(clazz);
    }

    public static boolean isDouble(Class<?> clazz) {
        return double.class.equals(clazz);
    }

    public static boolean isWrapBoolean(Class<?> clazz) {
        return Boolean.class.equals(clazz);
    }

    public static boolean isWrapByte(Class<?> clazz) {
        return Byte.class.equals(clazz);
    }

    public static boolean isWrapChar(Class<?> clazz) {
        return Character.class.equals(clazz);
    }

    public static boolean isWrapShort(Class<?> clazz) {
        return Short.class.equals(clazz);
    }

    public static boolean isWrapInt(Class<?> clazz) {
        return Integer.class.equals(clazz);
    }

    public static boolean isWrapLong(Class<?> clazz) {
        return Long.class.equals(clazz);
    }

    public static boolean isWrapFloat(Class<?> clazz) {
        return Float.class.equals(clazz);
    }

    public static boolean isWrapDouble(Class<?> clazz) {
        return Double.class.equals(clazz);
    }

    public static boolean isEnum(Class<?> clazz) {
        return clazz != null && clazz.isEnum();
    }

    public static boolean isBasicType(Class<?> clazz) {
        return isBoolean(clazz) || isByte(clazz) || isChar(clazz) || isShort(clazz) ||
                isInt(clazz) || isLong(clazz) || isFloat(clazz) || isDouble(clazz);
    }

    public static boolean isWrapClass(Class<?> clazz) {
        return isWrapBoolean(clazz) || isWrapByte(clazz) || isWrapChar(clazz) || isWrapShort(clazz) ||
                isWrapInt(clazz) || isWrapLong(clazz) || isWrapFloat(clazz) || isWrapDouble(clazz);
    }

    public static boolean isBasicOrWrapType(Class<?> clazz) {
        return isBoolean(clazz) || isByte(clazz) || isChar(clazz) || isShort(clazz) ||
                isInt(clazz) || isLong(clazz) || isFloat(clazz) || isDouble(clazz) ||
                isWrapBoolean(clazz) || isWrapByte(clazz) || isWrapChar(clazz) || isWrapShort(clazz) ||
                isWrapInt(clazz) || isWrapLong(clazz) || isWrapFloat(clazz) || isWrapDouble(clazz);
    }

    public static boolean isString(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    public static boolean isDate(Class<?> clazz) {
        return Date.class.equals(clazz);
    }

    public static boolean isBigDecimal(Class<?> clazz) {
        return BigDecimal.class.equals(clazz);
    }

    public static boolean isBigInteger(Class<?> clazz) {
        return BigInteger.class.equals(clazz);
    }

    public static boolean isBigNumber(Class<?> clazz) {
        return (isBigDecimal(clazz) || isBigInteger(clazz));
    }

    public static boolean isArray(Class<?> clazz) {
        return clazz != null && clazz.isArray();
    }

    public static boolean isList(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        try {
            if (!isBasicType(clazz) && !isArray(clazz)) {
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    if (clazz.isAssignableFrom(ArrayList.class)) {
                        return true;
                    }
                } else {
                    if (clazz.newInstance() instanceof List) { //List
                        return true;
                    }
                }
            }

        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("InstantiationException or IllegalAccessException error :", e);
        }
        return false;
    }

    public static boolean isSet(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        try {
            if (!isBasicType(clazz) && !isArray(clazz)) {
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    if (clazz.isAssignableFrom(HashSet.class)) {
                        return true;
                    }
                } else {
                    if (clazz.newInstance() instanceof Set) { //Set
                        return true;
                    }
                }
            }

        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("InstantiationException or IllegalAccessException error :", e);
        }
        return false;
    }

    public static boolean isMap(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        try {
            if (!isBasicType(clazz) && !isArray(clazz)) {
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    if (clazz.isAssignableFrom(HashMap.class)) {
                        return true;
                    }
                } else {
                    if (clazz.newInstance() instanceof Map) { //Map
                        return true;
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("InstantiationException or IllegalAccessException error :", e);
        }
        return false;
    }

    public static boolean isCollection(Class<?> clazz) {
        return isArray(clazz) || isList(clazz) || isSet(clazz) || isMap(clazz);
    }

    public static boolean isCustom(Class<?> clazz) {
        return !(isBasicOrWrapType(clazz) || isString(clazz) || isEnum(clazz) || isDate(clazz) ||
                isBigNumber(clazz) || isCollection(clazz));
    }

}
