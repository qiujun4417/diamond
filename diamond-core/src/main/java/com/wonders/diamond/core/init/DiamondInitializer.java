package com.wonders.diamond.core.init;

import com.wonders.diamond.core.curator.CuratorFactory;
import com.wonders.diamond.core.curator.ServiceType;
import com.wonders.diamond.core.netty.NettyServer;

import javax.sql.DataSource;
import java.net.SocketException;

/**
 * Created by ningyang on 2017/1/14.
 * @author ningyang
 *
 */
public class DiamondInitializer {

    public static void initializeDiamond(String zkHost, ServiceType serviceType, DataSource dataSource, int port) throws SocketException {
        CuratorFactory.create(zkHost, serviceType, port).addInstance();
        NettyServer.initNettyServer(port, dataSource);
    }
}
