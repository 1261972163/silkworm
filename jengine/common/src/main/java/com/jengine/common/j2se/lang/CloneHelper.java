package com.jengine.common.j2se.lang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * clone helper
 * Created by nouuid on 2015/5/14.
 */
public class CloneHelper {

    public static final Log logger = LogFactory.getLog(CloneHelper.class);

    /**
     * array of needless clone classes
     */
    static Class[] needlessCloneClasses = new Class[]{
            Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class,
            String.class, Void.class, Object.class, Class.class
    };

    /**
     * is this class  needless to clone
     *
     * @param c
     * @return isNeedlessClone
     */
    private static boolean isNeedlessClone(Class c) {
        if (c.isPrimitive()) {//primitive
            return true;
        }
        for (Class tmp : needlessCloneClasses) {//wrapped primitive
            if (c.equals(tmp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * create a new object based on the provided object class
     *
     * @param value
     * @return new object
     * @throws IllegalAccessException
     */
    private static Object createObject(Object value) throws IllegalAccessException {
        try {
            return value.getClass().newInstance();
        } catch (InstantiationException e) {
            return null;
        }
    }

    /**
     * clone
     *
     * @param value Object
     * @param level =0, the same Object
     *              <0, has the same value, but not the same Object
     *              >0, before level comes to 0, new Objct has the same value to provided Object
     * @return a new Object
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static Object clone(Object value, int level) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        if (null == value) {
            return null;
        }
        if (level == 0) {
            return value;
        }
        Class clazz = value.getClass();
        if (isNeedlessClone(clazz)) {
            return value;
        }
        level--;
        if (value instanceof Collection) {//Collection
            Collection tmp = (Collection) clazz.newInstance();
            for (Object v : (Collection) value) {
                tmp.add(clone(v, level));
            }
            value = tmp;
        } else {
            if (clazz.isArray()) {//Array
                //primitive array
                if (int[].class.equals(clazz)) {
                    int[] old = (int[]) value;
                    value = (int[]) Arrays.copyOf(old, old.length);
                } else if (short[].class.equals(clazz)) {
                    short[] old = (short[]) value;
                    value = (short[]) Arrays.copyOf(old, old.length);
                } else if (char[].class.equals(clazz)) {
                    char[] old = (char[]) value;
                    value = (char[]) Arrays.copyOf(old, old.length);
                } else if (float[].class.equals(clazz)) {
                    float[] old = (float[]) value;
                    value = (float[]) Arrays.copyOf(old, old.length);
                } else if (double[].class.equals(clazz)) {
                    double[] old = (double[]) value;
                    value = (double[]) Arrays.copyOf(old, old.length);
                } else if (long[].class.equals(clazz)) {
                    long[] old = (long[]) value;
                    value = (long[]) Arrays.copyOf(old, old.length);
                } else if (boolean[].class.equals(clazz)) {
                    boolean[] old = (boolean[]) value;
                    value = (boolean[]) Arrays.copyOf(old, old.length);
                } else if (byte[].class.equals(clazz)) {
                    byte[] old = (byte[]) value;
                    value = (byte[]) Arrays.copyOf(old, old.length);
                } else {
                    Object[] old = (Object[]) value;
                    Object[] tmp = (Object[]) Arrays.copyOf(old, old.length, old.getClass());
                    for (int i = 0; i < old.length; i++) {
                        tmp[i] = clone(old[i], level);
                    }
                    value = tmp;
                }
            } else if (value instanceof Map) {//Map
                Map tmp = (Map) clazz.newInstance();
                Map org = (Map) value;
                for (Object key : org.keySet()) {
                    tmp.put(key, clone(org.get(key), level));
                }
                value = tmp;
            } else {
                Object tmp = createObject(value);
                if (tmp == null) {
                    return value;
                }
                Set<Field> fields = new HashSet<Field>();
                while (clazz != null && !clazz.equals(Object.class)) {
                    fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                    clazz = clazz.getSuperclass();
                }
                for (Field field : fields) {
                    if (!Modifier.isFinal(field.getModifiers())) {
                        field.setAccessible(true);
                        field.set(tmp, clone(field.get(value), level));
                    }
                }
                value = tmp;
            }
        }
        return value;
    }

    /**
     * shallow clone
     *
     * @param value Object
     * @return Object
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static Object clone(Object value) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        return clone(value, 1);
    }

    /**
     * deep clone
     *
     * @param value Object
     * @return Object
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static Object deepClone(Object value) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        return clone(value, -1);
    }

    /**
     * deep clone
     *
     * @param obj Object
     * @return Object
     */
    public static Object deepClone2(Object obj) {
        if (null == obj) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        Object object = null;
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            object = oi.readObject();
        } catch (ClassNotFoundException | IOException e) {
            logger.error("", e);
        }
        return object;
    }
}
