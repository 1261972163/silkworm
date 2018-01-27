package com.jengine.transport.protocol.netty.remote;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nouuid
 * @description
 * @date 10/13/16
 */
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private ServerBootstrap serverBootstrap;

    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void open() throws Throwable {
        doOpen();
    }

    protected void doOpen() throws Throwable {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            serverBootstrap = new ServerBootstrap();
            NettyServerHandler nettyServerHandler = new NettyServerHandler();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            NettyCodecAdapter nettyCodecAdapter = new NettyCodecAdapter();
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            pipeline.addLast("decoder", nettyCodecAdapter.getDecoder());
                            pipeline.addLast("encoder", nettyCodecAdapter.getEncoder());
                            pipeline.addLast("handler", nettyServerHandler);
                            logger.info("Client[" + channel.remoteAddress() + "] is connected.");
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            logger.info("Server is started.");
            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.info("Server is closed.");
        }
    }
}
