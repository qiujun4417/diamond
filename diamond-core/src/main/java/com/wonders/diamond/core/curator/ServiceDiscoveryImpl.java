package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.context.DiamondContext;
import com.wonders.diamond.core.instance.DiamondInstance;
import com.wonders.diamond.core.serializer.JsonInstanceSerializer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by ningyang on 2017/1/4.
 */
public class ServiceDiscoveryImpl<T> implements ServiceDiscovery<T>{

    private Logger log = LoggerFactory.getLogger(ServiceDiscoveryImpl.class);

    private final CuratorFramework client;

    private final DiamondInstance instance;

    private final DiamondContext context = new DiamondContext();

    private final String basePath;

    private final JsonInstanceSerializer<T> serializer;

    private final ConnectionStateListener connectionStateListener = (client1, newState) -> {
        if ( (newState == ConnectionState.RECONNECTED) || (newState == ConnectionState.CONNECTED) )
        {
            try
            {
                log.debug("Re-registering due to reconnection");
//                    reRegisterServices();
            }
            catch ( Exception e )
            {
                ThreadUtils.checkInterrupted(e);
                log.error("Could not re-register instances after reconnection", e);
            }
        }
    };

    public ServiceDiscoveryImpl(CuratorFramework client, DiamondInstance instance, String basePath,
                                JsonInstanceSerializer serializer){
        this.client = client;
        this.instance = instance;
        this.basePath = basePath;
        this.serializer = serializer;
    }

    @Override
    public void start() {

    }

    @Override
    public void close() {

    }

    @Override
    public void register(DiamondInstance instance) {

    }

    @Override
    public void update(DiamondInstance instance) {

    }

    @Override
    public void unregister(DiamondInstance instance) {

    }

    @Override
    public DiamondInstance queryInstance(String name, String id) {
        return null;
    }

    @Override
    public Collection<DiamondInstance> listAll() {
        return null;
    }
}
