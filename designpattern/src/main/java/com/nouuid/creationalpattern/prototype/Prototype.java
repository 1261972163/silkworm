package com.nouuid.creationalpattern.prototype;

import java.io.*;

/**
 * 声明一个克隆自身的接口
 * Created by nouuid on 2015/5/13.
 */
public class Prototype implements Cloneable, Serializable {

//    public static final Long serialVersionUID = -1L;

    private String name;

    private Component component;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    /**
     * 浅复制
     *
     * @return
     */
    @Override
    public Prototype clone() {
        Prototype prototype = null;
        try {
            prototype = (Prototype) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return prototype;
    }

    /**
     * 深度复制(推荐使用序列化的方式)
     *
     * @return
     */
    protected Object deepClone() {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        Object object = null;
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(this);
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            object = oi.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + name.hashCode();
        result = 37* result + component.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Prototype other = (Prototype) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
