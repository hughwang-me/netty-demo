package com.uwjx.netty.server.handler;

import com.uwjx.netty.server.core.CtxManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@ChannelHandler.Sharable
public class LogHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private CtxManager ctxManager;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.warn("日志处理 -> channelRegistered");
        String welcomeMsg = "恭喜 你已经注册上线成功，你是:" + ctx.channel().id().asShortText();
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(welcomeMsg.getBytes().length);
        byteBuf.writeBytes(welcomeMsg.getBytes(CharsetUtil.UTF_8));

        log.warn("LogServerHandler -> '向设备发送消息' -> {}" , welcomeMsg);

        final ChannelFuture f = ctx.writeAndFlush(byteBuf);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                log.warn("LogServerHandler -> '向设备发送消息完毕' -> ChannelFutureListener operationComplete");
//                ctx.close(); //关闭连接
            }
        });

        ctxManager.add(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.warn("日志处理 -> channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.warn("日志处理 -> channelRead");
        ByteBuf byteBuf = (ByteBuf) msg;
        String receiveMsg = byteBuf.toString(CharsetUtil.UTF_8);
        log.warn("服务端接收到消息 -> {}", receiveMsg);
        byteBuf.release();

        if(receiveMsg.equals("listAll")){
            ctxManager.listAllChannels();
        }else if(receiveMsg.equals("listActive")){
            ctxManager.listActiveChannels();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.warn("日志处理 -> channelReadComplete");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.warn("日志处理 -> channelWritabilityChanged");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("日志处理 -> exceptionCaught : {}" , cause.getMessage());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("日志处理 -> channelInactive");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.warn("日志处理 -> channelUnregistered");
        ctxManager.remove(ctx);
    }
}
