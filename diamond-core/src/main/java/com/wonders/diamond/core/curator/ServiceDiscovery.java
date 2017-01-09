package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.instance.DiamondInstance;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by ningyang on 2017/1/4.
 * 服务的发现和注册
 */
public interface ServiceDiscovery<T> extends Closeable{

    public void start();

    public void close();

    public void register(DiamondInstance instance) throws Exception;

    public void update(DiamondInstance instance) throws Exception;

    public void unregister(DiamondInstance instance);

    public DiamondInstance queryInstance(String name, String id) throws Exception;

    public Collection<DiamondInstance> listAll();
}
