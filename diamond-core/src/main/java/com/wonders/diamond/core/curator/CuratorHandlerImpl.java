package com.wonders.diamond.core.curator;

import com.sun.xml.internal.ws.Closeable;
import com.wonders.diamond.core.context.DiamondContext;
import com.wonders.diamond.core.instance.DiamondInstance;
import org.apache.curator.framework.CuratorFramework;

/**
 * Created by ningyang on 2017/1/1.
 */
public class CuratorHandlerImpl implements CuratorHandler, Closeable{

    private final CuratorFramework client;

    private final DiamondContext context;

    private DiamondInstance diamondInstance;

    private final ServiceType serviceType;

    public CuratorHandlerImpl(CuratorFramework client, DiamondContext context, ServiceType serviceType){
        this.client = client;
        this.context = context;
        this.serviceType = serviceType;
    }

    @Override
    public void registerDiamondClient(DiamondInstance diamondInstance) {

    }

    @Override
    public void removeDiamondClient(DiamondInstance diamondInstance) {

    }

    @Override
    public void close() {

    }
}
