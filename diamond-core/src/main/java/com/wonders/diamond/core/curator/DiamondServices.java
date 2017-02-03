package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.context.DiamondContext;
import com.wonders.diamond.core.instance.DiamondInstance;
import com.wonders.diamond.core.instance.DiamondInstanceBuilder;
import com.wonders.diamond.core.utils.CloseableUtils;
import com.wonders.diamond.core.utils.IDGen;
import org.apache.curator.framework.CuratorFramework;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketException;

/**
 *
 * Created by nick on 2017/1/5.
 *
 */
public class DiamondServices implements Closeable{

    private DiamondInstance instance;

    private DiamondContext context;

    private ServiceDiscovery serviceDiscovery;

    private CuratorFramework client;

    private final String basePath = "/discovery/diamond";

    public DiamondServices(CuratorFramework client, ServiceType serviceType, DiamondContext context, int port) throws SocketException {
        this.client = client;
        this.context = context;
        instance = new DiamondInstanceBuilder()
                .builder()
                .id(IDGen.uuid())
                .name(serviceType.name())
                .type("diamond" + serviceType.name())
                .port(port)
                .registerTime(System.currentTimeMillis())
                .build();

        serviceDiscovery = ServiceDiscoveryBuilder.builder(DiamondInstance.class)
                .basePath(basePath)
                .client(this.client)
                .context(this.context)
                .instance(instance)
                .build();
    }

    public void start(){
        serviceDiscovery.start();
    }

    public ServiceDiscovery<DiamondInstance> getServiceDiscovery(){
        return serviceDiscovery;
    }

    @Override
    public void close() throws IOException {
        CloseableUtils.closeQuietly(serviceDiscovery);
    }
}
