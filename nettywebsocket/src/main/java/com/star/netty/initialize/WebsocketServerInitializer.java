package com.star.netty.initialize;

import com.star.netty.handler.ConnectionHandler;
import com.star.netty.handler.WebsocketServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class WebsocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private String path;

    public WebsocketServerInitializer(String path){
        this.path = path;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline ch = socketChannel.pipeline();
        ch.addLast(new HttpServerCodec());
        ch.addLast(new HttpObjectAggregator(65536));
        ch.addLast(new WebsocketServerHandler(path));
        ch.addLast(new ConnectionHandler());
    }
}
