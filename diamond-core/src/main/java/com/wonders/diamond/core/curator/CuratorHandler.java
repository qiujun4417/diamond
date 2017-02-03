package com.wonders.diamond.core.curator;


import com.wonders.diamond.core.context.DiamondContext;

/**
 * Created by ningyang on 2017/1/1.
 */
public interface CuratorHandler {

    void addInstance();

    void removeInstance();

    DiamondContext context();

    DiamondServices diamondService();

}
