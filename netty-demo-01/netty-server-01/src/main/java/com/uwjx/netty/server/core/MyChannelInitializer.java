package com.uwjx.netty.server.core;

import com.uwjx.netty.server.handler.LogHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyChannelInitializer extends ChannelInitializer<Channel> {

    @Autowired
    LogHandler logHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        log.warn("MyChannelInitializer -> initChannel");
        ch.pipeline().addLast(logHandler);
    }
}
