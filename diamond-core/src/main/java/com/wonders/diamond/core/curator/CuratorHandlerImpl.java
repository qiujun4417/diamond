package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.context.DiamondContext;
import com.wonders.diamond.core.netty.DiamondService;
import com.wonders.diamond.core.utils.CloseableUtils;
import org.apache.curator.framework.CuratorFramework;

import java.net.SocketException;

/**
 * Created by ningyang on 2017/1/1.
 */
public class CuratorHandlerImpl implements CuratorHandler{

    private final CuratorFramework client;

    private DiamondContext context;

    private DiamondServices diamondService;

    private ServiceType serviceType;

    public CuratorHandlerImpl(CuratorFramework client, ServiceType serviceType, DiamondContext context, int servicePort) throws SocketException {
        this.client = client;
        this.context = context;
        this.serviceType = serviceType;
        diamondService = new DiamondServices(this.client, this.serviceType, this.context, servicePort);
    }

    @Override
    public void addInstance() {
        diamondService.start();
    }

    @Override
    public void removeInstance() {
        CloseableUtils.closeQuietly(diamondService);
    }

    @Override
    public DiamondContext context() {
        return context;
    }

    @Override
    public DiamondServices diamondService() {
        return diamondService;
    }

}
