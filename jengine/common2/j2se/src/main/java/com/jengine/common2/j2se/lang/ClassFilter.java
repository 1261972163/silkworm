package com.jengine.common2.j2se.lang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by nouuid on 2015/5/7.
 */
public class ClassFilter {

    protected final Log logger = LogFactory.getLog(getClass());

    //boolean/Boolean
    public static boolean isBooleanCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isBoolean(srcClass) ||
                        ClassValidator.isWrapBoolean(srcClass) ||
                        ClassValidator.isString(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //byte/Byte
    public static boolean isByteCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isByte(srcClass) ||
                        ClassValidator.isWrapByte(srcClass) ||
                        ClassValidator.isString(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //char/Character
    public static boolean isCharCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isChar(srcClass) ||
                        ClassValidator.isWrapChar(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //short/Short
    public static boolean isShortCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isShort(srcClass) ||
                        ClassValidator.isInt(srcClass) ||
                        ClassValidator.isLong(srcClass) ||
                        ClassValidator.isFloat(srcClass) ||
                        ClassValidator.isDouble(srcClass) ||
                        ClassValidator.isWrapShort(srcClass) ||
                        ClassValidator.isWrapInt(srcClass) ||
                        ClassValidator.isWrapLong(srcClass) ||
                        ClassValidator.isWrapFloat(srcClass) ||
                        ClassValidator.isWrapDouble(srcClass) ||
                        ClassValidator.isString(srcClass) ||
                        ClassValidator.isBigInteger(srcClass) ||
                        ClassValidator.isBigDecimal(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //int/Integer
    public static boolean isIntCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isShort(srcClass) ||
                        ClassValidator.isInt(srcClass) ||
                        ClassValidator.isLong(srcClass) ||
                        ClassValidator.isFloat(srcClass) ||
                        ClassValidator.isDouble(srcClass) ||
                        ClassValidator.isWrapShort(srcClass) ||
                        ClassValidator.isWrapInt(srcClass) ||
                        ClassValidator.isWrapLong(srcClass) ||
                        ClassValidator.isWrapFloat(srcClass) ||
                        ClassValidator.isWrapDouble(srcClass) ||
                        ClassValidator.isString(srcClass) ||
                        ClassValidator.isBigInteger(srcClass) ||
                        ClassValidator.isBigDecimal(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //long/Long
    public static boolean isLongCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isShort(srcClass) ||
                        ClassValidator.isInt(srcClass) ||
                        ClassValidator.isLong(srcClass) ||
                        ClassValidator.isFloat(srcClass) ||
                        ClassValidator.isDouble(srcClass) ||
                        ClassValidator.isWrapShort(srcClass) ||
                        ClassValidator.isWrapInt(srcClass) ||
                        ClassValidator.isWrapLong(srcClass) ||
                        ClassValidator.isWrapFloat(srcClass) ||
                        ClassValidator.isWrapDouble(srcClass) ||
                        ClassValidator.isString(srcClass) ||
                        ClassValidator.isBigInteger(srcClass) ||
                        ClassValidator.isBigDecimal(srcClass) |
                                ClassValidator.isDate(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //float/Float
    public static boolean isFloatCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isShort(srcClass) ||
                        ClassValidator.isInt(srcClass) ||
                        ClassValidator.isLong(srcClass) ||
                        ClassValidator.isFloat(srcClass) ||
                        ClassValidator.isDouble(srcClass) ||
                        ClassValidator.isWrapShort(srcClass) ||
                        ClassValidator.isWrapInt(srcClass) ||
                        ClassValidator.isWrapLong(srcClass) ||
                        ClassValidator.isWrapFloat(srcClass) ||
                        ClassValidator.isWrapDouble(srcClass) ||
                        ClassValidator.isString(srcClass) ||
                        ClassValidator.isBigInteger(srcClass) ||
                        ClassValidator.isBigDecimal(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //double/Double
    public static boolean isDoubleCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isShort(srcClass) ||
                        ClassValidator.isInt(srcClass) ||
                        ClassValidator.isLong(srcClass) ||
                        ClassValidator.isFloat(srcClass) ||
                        ClassValidator.isDouble(srcClass) ||
                        ClassValidator.isWrapShort(srcClass) ||
                        ClassValidator.isWrapInt(srcClass) ||
                        ClassValidator.isWrapLong(srcClass) ||
                        ClassValidator.isWrapFloat(srcClass) ||
                        ClassValidator.isWrapDouble(srcClass) ||
                        ClassValidator.isString(srcClass) ||
                        ClassValidator.isBigInteger(srcClass) ||
                        ClassValidator.isBigDecimal(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //enum
    public static boolean isEnumCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isEnum(srcClass) ||
                        ClassValidator.isString(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //String
    public static boolean isStringCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isBoolean(srcClass) ||
                        ClassValidator.isByte(srcClass) ||
                        ClassValidator.isChar(srcClass) ||
                        ClassValidator.isShort(srcClass) ||
                        ClassValidator.isInt(srcClass) ||
                        ClassValidator.isLong(srcClass) ||
                        ClassValidator.isFloat(srcClass) ||
                        ClassValidator.isDouble(srcClass) ||
                        ClassValidator.isWrapBoolean(srcClass) ||
                        ClassValidator.isWrapByte(srcClass) ||
                        ClassValidator.isWrapChar(srcClass) ||
                        ClassValidator.isWrapShort(srcClass) ||
                        ClassValidator.isWrapInt(srcClass) ||
                        ClassValidator.isWrapLong(srcClass) ||
                        ClassValidator.isWrapFloat(srcClass) ||
                        ClassValidator.isWrapDouble(srcClass) ||
                        ClassValidator.isEnum(srcClass) ||
                        ClassValidator.isString(srcClass) ||
                        ClassValidator.isBigInteger(srcClass) ||
                        ClassValidator.isBigDecimal(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //Date
    public static boolean isDateCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isShort(srcClass) ||
                        ClassValidator.isInt(srcClass) ||
                        ClassValidator.isLong(srcClass) ||
                        ClassValidator.isFloat(srcClass) ||
                        ClassValidator.isDouble(srcClass) ||
                        ClassValidator.isWrapShort(srcClass) ||
                        ClassValidator.isWrapInt(srcClass) ||
                        ClassValidator.isWrapLong(srcClass) ||
                        ClassValidator.isWrapFloat(srcClass) ||
                        ClassValidator.isWrapDouble(srcClass) ||
                        ClassValidator.isBigInteger(srcClass) ||
                        ClassValidator.isBigDecimal(srcClass) ||
                        ClassValidator.isDate(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //BigInteger/BigDecimal
    public static boolean isBigNumberCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isShort(srcClass) ||
                        ClassValidator.isInt(srcClass) ||
                        ClassValidator.isLong(srcClass) ||
                        ClassValidator.isFloat(srcClass) ||
                        ClassValidator.isDouble(srcClass) ||
                        ClassValidator.isWrapShort(srcClass) ||
                        ClassValidator.isWrapInt(srcClass) ||
                        ClassValidator.isWrapLong(srcClass) ||
                        ClassValidator.isWrapFloat(srcClass) ||
                        ClassValidator.isWrapDouble(srcClass) ||
                        ClassValidator.isString(srcClass) ||
                        ClassValidator.isBigInteger(srcClass) ||
                        ClassValidator.isBigDecimal(srcClass)
                ) {
            return true;
        }

        return false;
    }

    //Custom
    public static boolean isCustomCastableFrom(Class<?> srcClass) {
        if (srcClass == null) {
            return false;
        }

        if (    //过滤出可转换的类型
                ClassValidator.isCustom(srcClass)
                ) {
            return true;
        }

        return false;
    }
}
