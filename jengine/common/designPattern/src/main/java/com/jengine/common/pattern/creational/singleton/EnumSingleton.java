package com.jengine.common.pattern.creational.singleton;

/**
 * @author nouuid
 * @date 4/15/2016
 * @description
 * enum class
 * thread safe
 */

public class EnumSingleton {
    public enum EnumType {
        enumSingletonFactory;

        private EnumSingleton enumSingleton;

        private EnumType() {
            enumSingleton = new EnumSingleton();
        }

        public EnumSingleton getInstance() {
            return enumSingleton;
        }
    }

    public static EnumSingleton getInstance() {
        return EnumType.enumSingletonFactory.getInstance();
    }
}
