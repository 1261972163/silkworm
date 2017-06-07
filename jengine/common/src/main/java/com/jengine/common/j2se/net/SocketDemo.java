package com.jengine.common.j2se.net;

import org.junit.Test;
import java.io.IOException;

/**
 * OSI   : 物理层 -> 数据链路层 -> 网络层 -> 会话层 -> 传输层 -> 表示层 -> 应用层
 * TCP/IP: 物理层 -> 数据链路层 -> 网络层 -> 传输层 -> 应用层
 * 协议  : 接口、线缆 -> Ethernet、802.3、PPP -> IP -> TCP/UDP -> HTTP、Telnet、FTP、TFTP
 * PDU   : Bit -> Frame -> Packet -> Segment -> 应用层数据
 *
 * Socket是网络上运行的程序之间双向通信链路的终结点，是TCP/UDP的基础。
 * Socket = IP + Port
 *
 *********
 * TCP编程：
 * ServerSocket（服务端） + Socket（客户端）
 *********
 *
 * TCP服务端：
 * ① 创建ServerSocket对象，绑定监听端口
 * ② 通过accept()方法监听客户端请求Socket
 * ③ 连接建立后，通过输入流读取客户端发送的请求信息
 * ④ 通过输出流向客户端发送响应信息
 * ⑤ 关闭相关资源
 *
 * TCP客户端：
 * ① 创建Socket对象，指明需要连接的服务器的地址和端口号
 * ② 连接建立后，通过输出流想服务器端发送请求信息
 * ③ 通过输入流获取服务器响应的信息
 * ④ 关闭响应资源
 *
 **********
 * UDP编程：
 **********
 *
 * UDP服务端：
 * ① 创建DatagramSocket，指定端口号
 * ② 创建DatagramPacket
 * ③ 接受客户端发送的数据信息
 * ④ 读取数据
 *
 * UDP客户端：
 * ① 定义发送信息
 * ② 创建DatagramPacket，包含将要发送的信息
 * ③ 创建DatagramSocket
 * ④ 发送数据
 *
 * @author bl07637
 * @date 9/23/2016
 * @since 0.1.0
 */
public class SocketDemo {


    @Test
    public void tcpServer() throws IOException {
        TCPServer tcpServer = new TCPServer();
        tcpServer.start();
    }

    @Test
    public void tcpClient() throws IOException {
        TCPClient tcpClient = new TCPClient();
        tcpClient.start();
    }
}
