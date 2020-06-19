package com.star.config;

import com.star.Exception.NettyConfigErrorException;
import com.star.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;

@Configuration
@Slf4j
public class NettyConfiguration {

    @Autowired
    private NettyProperty nettyProperty;

    @Autowired
    @Qualifier("threadPool")
    private ExecutorService service;

    @Autowired
    private NettyServer nettyServer;

    @PostConstruct
    public void initNettyServer() {
        Integer port = nettyProperty.getPort();
        String contentPath =  nettyProperty.getContentPath();
        if(null == port || StringUtils.isEmpty(contentPath)) {
            throw new NettyConfigErrorException("netty port or contextPath could not be null");
        }
        //启动netty服务
       service.execute(() ->{
           try {
               nettyServer.createServer(port, contentPath);
           }catch (Exception e) {
               log.error("create netty server error", e);
           }
       });
    }


}
