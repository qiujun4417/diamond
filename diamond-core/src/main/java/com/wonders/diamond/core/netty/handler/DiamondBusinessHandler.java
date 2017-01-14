package com.wonders.diamond.core.netty.handler;

import com.wonders.diamond.core.netty.DiamondRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

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
        diamondRequestHandler.handleDiamondRequest(diamondRequest);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }
}
