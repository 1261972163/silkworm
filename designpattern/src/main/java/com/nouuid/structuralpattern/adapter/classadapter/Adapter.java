package com.nouuid.structuralpattern.adapter.classadapter;

/**
 * �ṩ��������Ľӿ�
 * Created by nouuid on 2015/5/25.
 */
public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        System.out.println("��������Ľӿ�");
        super.specificRequest();
    }

}
