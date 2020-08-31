package com.uwjx.netty.client.client;

import com.uwjx.netty.client.handler.LogHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientChannelInitializer extends ChannelInitializer<Channel> {

    @Autowired
    LogHandler logHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(logHandler);
    }
}
