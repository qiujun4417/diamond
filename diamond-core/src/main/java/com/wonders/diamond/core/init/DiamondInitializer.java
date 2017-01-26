package com.wonders.diamond.core.init;

import com.wonders.diamond.core.curator.CuratorFactory;
import com.wonders.diamond.core.curator.ServiceType;
import com.wonders.diamond.core.init.table.TableCreator;
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

        TableCreator.tableInit(dataSource);
        CuratorFactory.create(zkHost, serviceType, port).addInstance();
        NettyServer.initNettyServer(port, dataSource);
    }
}
