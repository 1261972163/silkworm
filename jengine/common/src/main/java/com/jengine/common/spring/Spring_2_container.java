package com.jengine.common.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 2. Spring容器可以分为两种类型:
 *      BeanFactory         （org.springframework.beans.factory.BeanFactory接口定义）是最简答的容器，提供了基本的DI支持。
 *                          最常用的BeanFactory实现就是XmlBeanFactory类，它根据XML文件中的定义加载beans，
 *                          该容器从XML文件读取配置元数据并用它去创建一个完全配置的系统或应用。
 *      ApplicationContext  （org.springframework.context.ApplicationContext）基于BeanFactory之上构建，并提供面向应用的服务。
 *
 * 3. ApplicationContext的实现
 *      ClassPathXmlApplicationContext      从类路径下的XML配置文件中加载上下文定义，把应用上下文定义文件当做类资源。
 *      FileSystemXmlApplicationContext     读取文件系统下的XML配置文件并加载上下文定义。
 *      XmlWebApplicationContext            读取Web应用下的XML配置文件并装载上下文定义。
 *
 * 4. beans标签中相关属性
 *      default-init-method
 *      default-destory-method
 *      default-autowire            默认为none，应用于Spring配置文件中的所有Bean，注意这里不是指Spring应用上下文，因为你可以定义多个配置文件
 *
 * 5. bean标签中的属性：
 *      id
 *      name
 *      class
 *      init-method         Bean实例化后会立刻调用的方法
 *      destory-method      Bean从容器移除和销毁前，会调用的方法
 *      factory-method      运行我们调用一个指定的静态方法，从而代替构造方法来创建一个类的实例。
 *      scope               Bean的作用域，包括singleton(默认)，prototype(每次调用都创建一个实例), request, session, global-session
 *                          （注意spring中的单例bean不是线程安全的）
 *      autowired           自动装配 byName, byType, constructor, autodetect
 *                          (首先尝试使用constructor自动装配，如果没有发现与构造器相匹配的Bean时，Spring将尝试使用byType自动装配)
 *
 * 6. Bean的生命周期
 *
 *
 * @author nouuid
 * @date 8/19/2016
 * @description
 */
public class Spring_2_container {

    public void test1() {

    }

    @Test
    public void load() throws InterruptedException {
        System.out.println("before appContext.");
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/context2-1.xml");
        System.out.println("after appContext.");
        Thread.sleep(30*1000);
    }

}



