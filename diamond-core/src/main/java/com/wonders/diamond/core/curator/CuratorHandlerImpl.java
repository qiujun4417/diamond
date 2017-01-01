package com.wonders.diamond.core.curator;

import org.apache.curator.framework.CuratorFramework;

/**
 * Created by ningyang on 2017/1/1.
 */
public class CuratorHandlerImpl implements CuratorHandler{

    private CuratorFramework client;

    public CuratorHandlerImpl(CuratorFramework client){
        this.client = client;
    }
}
