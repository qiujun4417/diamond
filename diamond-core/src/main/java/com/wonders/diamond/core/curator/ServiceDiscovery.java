package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.instance.DiamondInstance;

import java.util.Collection;

/**
 * Created by ningyang on 2017/1/4.
 * 服务的发现和注册
 */
public interface ServiceDiscovery {

    public void start();

    public void close();

    public void register(DiamondInstance instance);

    public void update(DiamondInstance instance);

    public void unregister(DiamondInstance instance);

    public DiamondInstance queryInstance(String name, String id);

    public Collection<DiamondInstance> listAll();
}
