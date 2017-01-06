package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.instance.DiamondInstance;
import org.apache.curator.framework.CuratorFramework;

import java.util.Collection;

/**
 * Created by ningyang on 2017/1/4.
 */
public class ServiceDiscoveryImpl implements ServiceDiscovery{

    private CuratorFramework client;

    private DiamondInstance instance;

    public ServiceDiscoveryImpl(){

    }

    public ServiceDiscoveryImpl(CuratorFramework client, DiamondInstance instance){
        this.client = client;
        this.instance = instance;
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
