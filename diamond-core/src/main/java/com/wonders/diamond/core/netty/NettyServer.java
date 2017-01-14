package com.wonders.diamond.core.netty;

import com.google.common.base.StandardSystemProperty;
import com.wonders.diamond.core.netty.dispatcher.DiamondRequestDispatcher;
import com.wonders.diamond.core.netty.exception.NettyServerStartUpException;
import com.wonders.diamond.core.netty.handler.DiamondNettyChannelHandler;
import com.wonders.diamond.core.netty.handler.DiamondRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadFactory;

/**
 * Created by ningyang on 2017/1/14.
 * @author ningyang
 * netty server
 */
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public static void initNettyServer(int serverPort, DataSource dataSource){
        ThreadFactory threadFactory = new DefaultThreadFactory("diamond thread pool");
        int processorNum = Runtime.getRuntime().availableProcessors();
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup;
        EventLoopGroup workerGroup;
        DiamondRequestDispatcher dispatcher = new DiamondRequestDispatcher(dataSource);
        DiamondRequestHandler requestHandler = new DiamondRequestHandler(dispatcher);
        if(StandardSystemProperty.OS_NAME.equals("Linux")){
            bossGroup = new EpollEventLoopGroup(1);
            workerGroup = new EpollEventLoopGroup();
            bootstrap.channel(EpollServerSocketChannel.class);
        }else {
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup(processorNum*2, threadFactory, SelectorProvider.provider());
        }
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.childHandler(new DiamondNettyChannelHandler(requestHandler));
        bootstrap.option(ChannelOption.TCP_NODELAY, true)
                 .option(ChannelOption.SO_REUSEADDR, true)
                 .option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.localAddress(serverPort);
        ChannelFuture future = bootstrap.bind().awaitUninterruptibly();
        if(future.cause()!=null){
            logger.error(ExceptionUtils.getStackTrace(future.cause()));
            throw new NettyServerStartUpException("start netty server error", future.cause());
        }
        logger.info("start netty server success on port " + serverPort);
    }
}
