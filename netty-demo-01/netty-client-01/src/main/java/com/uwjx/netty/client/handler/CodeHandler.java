package com.uwjx.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CodeHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.warn("CodeHandler 接收到消息");
        ByteBuf byteBuf = (ByteBuf) msg;
        String receiveMsg = byteBuf.toString(CharsetUtil.UTF_8);
//        byteBuf.release();
        log.warn("CodeHandler channelRead -> {}" , receiveMsg);
        String text = "处理后的消息内容";
        ByteBuf processedMsg = ByteBufAllocator.DEFAULT.buffer(text.getBytes().length);
        processedMsg.writeBytes(text.getBytes(CharsetUtil.UTF_8));

        super.channelRead(ctx , processedMsg);
    }

}
