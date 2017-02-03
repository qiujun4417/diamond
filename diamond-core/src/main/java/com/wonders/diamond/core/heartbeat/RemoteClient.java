package com.wonders.diamond.core.heartbeat;

import com.google.common.net.InetAddresses;
import com.wonders.diamond.core.instance.DiamondInstance;

import java.io.IOException;

/**
 * Created by ningyang on 2017/2/3.
 */
public class RemoteClient {

    static int TIMEOUT = 30000;

    public boolean isReachable(DiamondInstance instance) throws IOException {
        StringBuffer sb = new StringBuffer();
        String address = sb.append(instance.getAddress()).
                            append(":").
                            append(instance.getPort()).
                            toString();
        boolean status = InetAddresses.forString(address).isReachable(TIMEOUT);
        return status;
    }
}
