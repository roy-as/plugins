package com.star.netty.handler;

import com.star.netty.ConnectionCenter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import java.util.List;
import java.util.Map;

public class ConnectionHandler  extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        Map<String, List<String>> parameters = new QueryStringDecoder(request.uri()).parameters();
        String token = parameters.get("token").get(0);
        // 保存连接
        ConnectionCenter.save(token, ctx.channel());
    }
}
