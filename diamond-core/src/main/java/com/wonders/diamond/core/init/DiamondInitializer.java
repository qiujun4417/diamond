package com.wonders.diamond.core.init;

import com.alibaba.druid.pool.DruidDataSource;
import com.wonders.diamond.core.curator.CuratorFactory;
import com.wonders.diamond.core.curator.CuratorHandler;
import com.wonders.diamond.core.curator.ServiceDiscovery;
import com.wonders.diamond.core.curator.ServiceType;
import com.wonders.diamond.core.heartbeat.DiamondHeartBeat;
import com.wonders.diamond.core.heartbeat.DiamondHeartBeatBuilder;
import com.wonders.diamond.core.heartbeat.RemoteClient;
import com.wonders.diamond.core.heartbeat.listener.DiamondEventListener;
import com.wonders.diamond.core.heartbeat.listener.DiamondEventListenerImpl;
import com.wonders.diamond.core.heartbeat.subscribe.DiamondSubscriber;
import com.wonders.diamond.core.init.table.TableCreator;
import com.wonders.diamond.core.instance.DiamondInstance;
import com.wonders.diamond.core.jdbc.SqlTemplate;
import com.wonders.diamond.core.jdbc.factory.SqlTemplateFactory;
import com.wonders.diamond.core.netty.DiamondService;
import com.wonders.diamond.core.netty.NettyServer;

import javax.sql.DataSource;
import java.net.SocketException;
import java.sql.SQLException;

/**
 * Created by ningyang on 2017/1/14.
 * @author ningyang
 *
 */
public class DiamondInitializer {

    /***
     * 初始化diamond
     * @param zkHost
     * @param serviceType
     * @param dataSource
     * @param port
     * @throws SocketException
     * @throws SQLException
     */
    public static void initializeDiamond(String zkHost, ServiceType serviceType, DataSource dataSource, int port) throws SocketException, SQLException {

        SqlTemplate sqlTemplate = SqlTemplateFactory.create(dataSource);
        TableCreator.tableInit(sqlTemplate);
        CuratorHandler handler = CuratorFactory.create(zkHost, serviceType, port);
        handler.addInstance();
        ServiceDiscovery<DiamondInstance> serviceDiscovery = handler.diamondService().getServiceDiscovery();
        DiamondSubscriber diamondSubscriber = new DiamondSubscriber();
        DiamondEventListener listener = new DiamondEventListenerImpl(serviceDiscovery);
        diamondSubscriber.registerListener(listener);
        DiamondHeartBeat heartBeat = new DiamondHeartBeatBuilder().
                builder().
                remoteClient(new RemoteClient()).serviceDiscovery(serviceDiscovery).
                subscriber(diamondSubscriber).
                build();
        heartBeat.heartBeat();
        NettyServer.initNettyServer(port, dataSource);
    }

    public static void main(String[] args){

        try {
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
            initializeDiamond("127.0.0.1:2181", ServiceType.CLIENT, druidDataSource, 3997);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
