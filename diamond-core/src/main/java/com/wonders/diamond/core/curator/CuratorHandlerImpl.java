package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.instance.DiamondInstance;
import org.apache.curator.framework.CuratorFramework;

/**
 * Created by ningyang on 2017/1/1.
 */
public class CuratorHandlerImpl implements CuratorHandler{

    private CuratorFramework client;

    public CuratorHandlerImpl(CuratorFramework client){
        this.client = client;
    }

    @Override
    public void registerDiamondClient(DiamondInstance diamondInstance) {

    }

    @Override
    public void removeDiamondClient(DiamondInstance diamondInstance) {

    }
}
