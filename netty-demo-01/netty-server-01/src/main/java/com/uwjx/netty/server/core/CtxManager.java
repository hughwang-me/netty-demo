package com.uwjx.netty.server.core;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CtxManager {

    private Map<String , ChannelHandlerContext> clients = new HashMap<>();

    public void add(ChannelHandlerContext context){
        clients.put(context.channel().id().asShortText() , context);
    }

    public ChannelHandlerContext get(String channelId){
        return clients.getOrDefault(channelId, null);
    }

    public void remove(String channelId){
        clients.remove(channelId);
    }

    public void remove(ChannelHandlerContext channelHandlerContext){
        clients.remove(channelHandlerContext.channel().id().asShortText());
    }

    public void listAllChannels(){
        clients.forEach((client, context) -> {
            log.warn("客户端 id : {}" , client);
        });
    }

    public void listActiveChannels(){
        clients.forEach((client, context) -> {
            if(context.channel().isActive()){
                log.warn("激活的客户端 id : {}" , client);
            }
        });
    }
}
