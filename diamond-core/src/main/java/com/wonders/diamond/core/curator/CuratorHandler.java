package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.instance.DiamondInstance;

/**
 * Created by ningyang on 2017/1/1.
 */
public interface CuratorHandler {

    void addInstance(DiamondInstance diamondInstance);

    void removeInstance(DiamondInstance diamondInstance);
}
