package com.star.netty;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接管理中心
 */
public class ConnectionCenter {

    public final static ConcurrentHashMap<String, Channel> connections= new ConcurrentHashMap<>();

    public static void save(String key, Channel channel) {
        connections.put(key, channel);
    }

    public static void remove(String key) {
        connections.remove(key);
    }

    public static Channel getChannel(String token) {
        return connections.get(token);
    }
}
