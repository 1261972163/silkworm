package com.jengine.transport.protocol.netty.httpserver;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;

/**
 * content
 *
 * @author bl07637
 * @date 12/15/2016
 * @since 0.1.0
 */
public class HttpClient {
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    URI uri = null;
    String host = null;
    ChannelFuture f = null;

    public void connect(String host, int port) throws Exception {
        workerGroup = new NioEventLoopGroup();
        this.host = host;

        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                ch.pipeline().addLast(new HttpResponseDecoder());
                // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                ch.pipeline().addLast(new HttpRequestEncoder());
                ch.pipeline().addLast(new HttpClientInboundHandler());
            }
        });

        // Start the client.
        f = b.connect(host, port).sync(); // 线程同步阻塞等待连接到服务器指定端口

        uri = new URI("http://" + host + ":8844");
    }

    public void send(String msg) throws Exception {
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

        // 构建http请求
        request.headers().set(HttpHeaders.Names.HOST, host);
        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
        // 发送http请求
        f.channel().write(request);
        f.channel().flush();
//            f.channel().closeFuture().sync();
    }

    public void close() {
        workerGroup.shutdownGracefully();
    }


}