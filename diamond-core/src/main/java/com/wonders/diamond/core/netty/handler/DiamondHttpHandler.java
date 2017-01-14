package com.wonders.diamond.core.netty.handler;

import com.wonders.diamond.core.exceptions.BaseException;
import com.wonders.diamond.core.netty.DiamondRequest;
import com.wonders.diamond.core.utils.JsonConvertor;
import com.wonders.diamond.core.utils.UriUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondHttpHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(DiamondHttpHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            FullHttpRequest request = (FullHttpRequest)msg;
            if(!request.getDecoderResult().isSuccess()){
                writeMessage(ctx, BAD_REQUEST);
                return;
            }
            ByteBuf buf = request.content();
            int readable = buf.readableBytes();
            byte[] bytes = new byte[readable];
            buf.readBytes(bytes);
            String contentStr = UriUtils.decode(new String(bytes,"UTF-8"),"UTF-8");
            DiamondRequest diamondRequest = JsonConvertor.toObject(contentStr, DiamondRequest.class);
            ctx.fireChannelRead(diamondRequest);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(ExceptionUtils.getStackTrace(cause));
        if (ctx.channel().isActive()) {
            writeMessage(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private void writeMessage(ChannelHandlerContext ctx, HttpResponseStatus status){
        BaseException exception = new BaseException(1000, status.toString());
        String str = JsonConvertor.toJson(exception);
        ByteBuf content = Unpooled.copiedBuffer(str, CharsetUtil.UTF_8);
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                HTTP_1_1,
                status,
                content
        );
        fullHttpResponse.headers().add(CONTENT_TYPE, "application/json; charset=UTF-8");
        // Close the connection as soon as the error message is sent.
        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}
