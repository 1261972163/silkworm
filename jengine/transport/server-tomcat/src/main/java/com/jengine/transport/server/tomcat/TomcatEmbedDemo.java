package com.jengine.transport.server.tomcat;

import org.apache.catalina.LifecycleException;
import org.junit.Test;

/**
 * content
 *
 * @author nouuid
 * @date 6/6/2017
 * @since 0.1.0
 */
public class TomcatEmbedDemo {
    private int startPort = 80;
    private int shutdownPort = 81;

    @Test
    public void test() throws LifecycleException {
//        String catalina_path = TomcatEmbedDemo.class.getResource("/").getPath() + "tomcat/catalina";
//        String base_path = TomcatEmbedDemo.class.getResource("/").getPath() + "tomcat/base";
//        String context_path = TomcatEmbedDemo.class.getResource("/").getPath() + "tomcat/context";
//        String doc_base_path = TomcatEmbedDemo.class.getResource("/").getPath() + "tomcat/doc";
//        //设置工作目录
//        Tomcat tomcat = new Tomcat();
//        tomcat.setHostname("localhost");
//        tomcat.setPort(startPort);
//        //设置工作目录,其实没什么用,tomcat需要使用这个目录进行写一些东西
//        tomcat.setBaseDir(catalina_path);
//
//        //设置程序的目录信息
//        tomcat.getHost().setAppBase(base_path);
//        // Add AprLifecycleListener
//        StandardServer server = (StandardServer) tomcat.getServer();
//        AprLifecycleListener listener = new AprLifecycleListener();
//        server.addLifecycleListener(listener);
//        //注册关闭端口以进行关闭
//        tomcat.getServer().setPort(shutdownPort);
//
//        //加载上下文
//        StandardContext standardContext = new StandardContext();
//        standardContext.setPath(context_path);//contextPath
//        standardContext.setDocBase(doc_base_path);//文件目录位置
//        standardContext.addLifecycleListener(new Tomcat.DefaultWebXmlListener());
//        //保证已经配置好了。
//        standardContext.addLifecycleListener(new Tomcat.FixContextListener());
//        standardContext.setSessionCookieName("t-session");
//        tomcat.getHost().addChild(standardContext);
//
//        //启动tomcat，并让tomcat在关闭端口上监听。如果没有最后一句，程序将直接结束，保证监听之后，tomcat将一直监听关闭事件，待有关闭事件之后才结束当前程序。
//        tomcat.start();
////        tomcat.getServer().await();
//        //所以如果想要关闭当前的tomcat，只需要向关闭端口发送一些信息即可，见shutdown
//
//        shutdown();
    }

    private void shutdown() {
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                Socket socket = null;
//                OutputStream stream = null;
//                try {
//                    socket = new Socket("localhost", shutdownPort);
//                    stream = socket.getOutputStream();
//                    for(int i = 0;i < "shutdown".length();i++)
//                        stream.write("shutdown".charAt(i));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        stream.flush();
//                        stream.close();
//                        socket.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                // 或者
////                tomcat.stop();
//            }
//        });

    }
}
