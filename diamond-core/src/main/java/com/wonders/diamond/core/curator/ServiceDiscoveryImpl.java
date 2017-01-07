package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.context.DiamondContext;
import com.wonders.diamond.core.instance.DiamondInstance;
import com.wonders.diamond.core.serializer.JsonInstanceSerializer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.utils.ThreadUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;


/**
 * Created by ningyang on 2017/1/4.
 */
public class ServiceDiscoveryImpl<T> implements ServiceDiscovery<T>{

    private Logger log = LoggerFactory.getLogger(ServiceDiscoveryImpl.class);

    private final CuratorFramework client;

    private final DiamondInstance instance;

    private DiamondContext context;

    private final String basePath;

    private final JsonInstanceSerializer<T> serializer;

    private final ConnectionStateListener connectionStateListener = (client1, newState) -> {
        if ( (newState == ConnectionState.RECONNECTED) || (newState == ConnectionState.CONNECTED) )
        {
            try
            {
                log.debug("Re-registering due to reconnection");
                    registerService();
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
        registerService();
        client.getConnectionStateListenable().addListener(connectionStateListener);
    }


    private void registerService(){

        if(instance!=null){
            synchronized (this){
                try {
                    internalRegisterService(instance);
                } catch (IOException e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                } catch (Exception e) {
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }

    private String pathForInstance(){
        return basePath + "/" + instance.getType() + "/" +
                instance.getName() + "/" + instance.getId();
    }

    protected void internalRegisterService(DiamondInstance instance) throws Exception {
        String path = pathForInstance();
        byte[] data = serializer.serialize(instance);
        final int MAX_TRIES = 2;
        boolean isDone = false;
        for ( int i = 0; !isDone && (i < MAX_TRIES); ++i )
        {
            try
            {
                CreateMode mode = CreateMode.EPHEMERAL_SEQUENTIAL;

                client.create().creatingParentContainersIfNeeded().withMode(mode).forPath(path, data);
                isDone = true;
            }
            catch ( KeeperException.NodeExistsException e )
            {
                client.delete().forPath(path);  // must delete then re-create so that watchers fire

            } catch (Exception e2) {
                log.error("error ",e2);
            }
        }
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

    public DiamondContext getContext() {
        return context;
    }

    public void setDiamondContext(DiamondContext context){
        this.context = context;
    }
}
