package com.wonders.diamond.core.netty.handler;

import com.wonders.diamond.core.exceptions.BaseException;
import com.wonders.diamond.core.netty.DiamondRequest;
import com.wonders.diamond.core.netty.DiamondResponse;
import com.wonders.diamond.core.utils.JsonConvertor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondBusinessHandler extends SimpleChannelInboundHandler<DiamondRequest>{

    private DiamondRequestHandler diamondRequestHandler;

    public DiamondBusinessHandler(DiamondRequestHandler diamondRequestHandler){
        this.diamondRequestHandler = diamondRequestHandler;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DiamondRequest diamondRequest) throws Exception {
        DiamondResponse diamondResponse = diamondRequestHandler.handleDiamondRequest(diamondRequest);
        printMessage(ctx, diamondResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }

    private void printMessage(ChannelHandlerContext ctx, DiamondResponse response){
        String str = JsonConvertor.toJson(response);
        ByteBuf content = Unpooled.copiedBuffer(str, CharsetUtil.UTF_8);
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                HTTP_1_1,
                HttpResponseStatus.OK,
                content
        );
        fullHttpResponse.headers().add(CONTENT_TYPE, "application/json; charset=UTF-8");
        // Close the connection as soon as the error message is sent.
        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}
