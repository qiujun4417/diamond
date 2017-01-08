package com.wonders.diamond.curator;

import com.wonders.diamond.core.curator.CuratorFactory;
import com.wonders.diamond.core.curator.CuratorHandler;
import com.wonders.diamond.core.curator.ServiceType;
import org.junit.Before;
import org.junit.Test;

import java.net.SocketException;

/**
 * Created by ningyang on 2017/1/8.
 * 测试curator client
 */
public class CuratorClientTest {

    private CuratorHandler curatorHandler;

    @Before
    public void setup() throws SocketException {
        curatorHandler = CuratorFactory.create("127.0.0.1:2181", ServiceType.CLIENT);
    }

    @Test
    public void registerServerTest(){
        curatorHandler.addInstance();
    }
}
