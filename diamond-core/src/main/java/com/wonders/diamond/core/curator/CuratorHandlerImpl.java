package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.context.DiamondContext;
import com.wonders.diamond.core.instance.DiamondInstance;
import org.apache.curator.framework.CuratorFramework;

/**
 * Created by ningyang on 2017/1/1.
 */
public class CuratorHandlerImpl implements CuratorHandler{

    private final CuratorFramework client;

    private DiamondContext context;

    public CuratorHandlerImpl(CuratorFramework client) {
        this.client = client;
    }

    @Override
    public void addInstance(DiamondInstance diamondInstance) {

    }

    @Override
    public void removeInstance(DiamondInstance diamondInstance) {

    }

}
