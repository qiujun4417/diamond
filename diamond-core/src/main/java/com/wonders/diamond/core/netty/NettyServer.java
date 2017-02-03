package com.wonders.diamond.core.netty;

import com.alibaba.druid.pool.DruidDataSource;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.nio.channels.spi.SelectorProvider;
import java.sql.SQLException;
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
            bootstrap.channel(NioServerSocketChannel.class);
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

    public static void main(String[] args) throws SQLException {

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&autoReconnect=true&rewriteBatchedStatements=TRUE&zeroDateTimeBehavior=convertToNull&useSSL=false");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("3202547c");
        druidDataSource.setFilters("stat");
        druidDataSource.setInitialSize(10);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(200);
        druidDataSource.setMaxWait(60000L);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000L);
        druidDataSource.setMinEvictableIdleTimeMillis(300000L);
        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(true);
        druidDataSource.setTestOnReturn(false);
        initNettyServer(3397, druidDataSource);
    }
}
