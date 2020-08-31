package com.uwjx.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LogHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.warn("LogServerHandler -> 设备连接激活");
        String welcomeMsg = "我连接激活了，我是:" + ctx.channel().id().asShortText();
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(welcomeMsg.getBytes().length);
        byteBuf.writeBytes(welcomeMsg.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(byteBuf);
        log.warn("客户端向服务端发送消息完毕 : {}" , welcomeMsg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("LogServerHandler -> channelInactive");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.warn("LogServerHandler -> 设备注册完成");

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.warn("LogServerHandler -> 设备注销完成");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.warn("LogServerHandler -> 设备读取数据");
        ByteBuf byteBuf = (ByteBuf) msg;
        String receiveMsg = byteBuf.toString(CharsetUtil.UTF_8);
        log.warn("客户端接收到消息 -> {}", receiveMsg);
        byteBuf.release();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.warn("LogServerHandler -> 设备读取数据完成");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.warn("LogServerHandler -> channelWritabilityChanged");
    }
}
