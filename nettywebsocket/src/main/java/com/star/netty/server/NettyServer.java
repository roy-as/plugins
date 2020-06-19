package com.star.netty.server;

import com.star.netty.initialize.WebsocketServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * netty服务端
 */
@Component
@Slf4j
public class NettyServer {

    public void createServer(Integer port, String contextPath) throws InterruptedException {
        // 负责接收请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 负责不同连接的读写请求，采用轮询
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            // 用于接收客户端的连接，以及为已接收的连接创建子通道
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childHandler(new WebsocketServerInitializer(contextPath))
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，同步等待成功
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("create netty server successful,port is {}!",port);
            // 等待服务器监听端口关闭
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
