package com.wonders.diamond.core.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondNettyChannelHandler extends ChannelInitializer<NioSocketChannel> {

    private DiamondRequestHandler diamondRequestHandler;

    public DiamondNettyChannelHandler(DiamondRequestHandler diamondRequestHandler){
        this.diamondRequestHandler = diamondRequestHandler;
    }
    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        CorsConfig.Builder builder = new CorsConfig.Builder();
        builder.allowCredentials().allowNullOrigin().allowedRequestMethods(HttpMethod.OPTIONS,
                HttpMethod.HEAD, HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH);
        CorsConfig corsConfig = builder.build();
        ch.pipeline().addLast(new HttpResponseEncoder());
        ch.pipeline().addLast(new HttpRequestDecoder());
        ch.pipeline().addLast(new HttpObjectAggregator(1048576));
        ch.pipeline().addLast(new ChunkedWriteHandler());
        ch.pipeline().addLast(new DiamondHttpHandler());
        ch.pipeline().addLast(new DiamondBusinessHandler(diamondRequestHandler));
        ch.pipeline().addLast(new CorsHandler(corsConfig));
    }
}
