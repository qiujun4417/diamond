package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.context.DiamondContext;
import com.wonders.diamond.core.instance.DiamondInstance;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by nick on 2017/1/5.
 */
public class DiamondServices implements Closeable{

    private DiamondInstance diamondInstance;

    private DiamondContext diamondContext;

    private ServiceDiscovery serviceDiscovery;

    @Override
    public void close() throws IOException {

    }
}
