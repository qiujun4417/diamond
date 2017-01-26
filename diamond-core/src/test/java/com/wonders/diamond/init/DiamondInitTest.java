package com.wonders.diamond.init;

import com.wonders.diamond.BaseSetup;
import com.wonders.diamond.core.curator.ServiceType;
import com.wonders.diamond.core.init.DiamondInitializer;
import org.junit.Test;

import java.net.SocketException;
import java.sql.SQLException;

/**
 * Created by nick on 2017/1/26.
 */
public class DiamondInitTest extends BaseSetup {

    @Test
    public void diamondInit() throws SocketException, SQLException {
        DiamondInitializer.initializeDiamond("127.0.0.1:2181", ServiceType.CLIENT, druidDataSource, 3997);
    }
}
