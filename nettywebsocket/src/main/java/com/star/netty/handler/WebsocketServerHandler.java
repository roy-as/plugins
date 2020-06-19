package com.star.netty.handler;

import com.star.common.constant.Constants;
import com.star.netty.ConnectionCenter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class WebsocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private String path;

    private WebSocketServerHandshaker handShaker = null;

    public WebsocketServerHandler(String path) {
        this.path = path;
    }

    private static final AtomicLong COUNTER = new AtomicLong(0);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            this.handleHttpRequest(ctx, (FullHttpRequest) msg);
            COUNTER.getAndAdd(1L);
        } else if (msg instanceof WebSocketFrame) {
            this.handleWebsocketRequest(ctx, (WebSocketFrame)msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 处理握手
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        Map<String, List<String>> parameters = new QueryStringDecoder(request.uri()).parameters();
        List<String> token = Optional.ofNullable(parameters.get("token")).orElse(new ArrayList<>());
        if (!request.decoderResult().isSuccess() ||
            !Constants.WEBSOCKET.equalsIgnoreCase(request.headers().get("Upgrade")) ||
            CollectionUtils.isEmpty(token)) {
            httpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 构造握手响应
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                getWebsocketLocation(request), null, true, 5 * 1024 * 1024
        );
        handShaker = wsFactory.newHandshaker(request);
        if (null == handShaker) {// 版本不支持
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), request);
            ctx.fireChannelRead(request.retain()); //继续传播
        }
        log.info("shake hand successful,token is {}", token);
    }

    private void handleWebsocketRequest( ChannelHandlerContext ctx, WebSocketFrame frame) {
        if(frame instanceof CloseWebSocketFrame) {//check websocket is close
            Object token = ctx.channel().attr(AttributeKey.valueOf("token")).get();
            ConnectionCenter.remove(token.toString());
            handShaker.close(ctx.channel(), (CloseWebSocketFrame)frame.retain());
            return;
        }
        if(frame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) frame).text();
            log.info("params is text:{}",text);
            // 处理具体逻辑
            ctx.channel().write(new TextWebSocketFrame(((TextWebSocketFrame) frame).text() + "回复啦!!!"));
            return;
        }
        //处理二进制消息
        if(frame instanceof BinaryWebSocketFrame) {
            ctx.write(frame.retain());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        super.exceptionCaught(ctx, e);
        log.error("request error", e);
        ctx.close();
    }

    private static void httpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        if (response.status().code() != Constants.SUCCUSS_CODE) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(response, response.content().readableBytes());
        }
        ChannelFuture future = ctx.channel().writeAndFlush(response);
        if (!HttpUtil.isKeepAlive(request) || response.status().code() != Constants.SUCCUSS_CODE) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private String getWebsocketLocation(FullHttpRequest request) {
        String location = request.headers().get(HttpHeaderNames.HOST) + path;
        return Constants.WEBSOCKET_PROTOCOL + location;
    }

    public static void pushMsg(String token, String msg) {
        Channel channel = ConnectionCenter.getChannel(token);
        channel.write(new TextWebSocketFrame(msg));
    }
}
