package com.wonders.diamond.core.netty;

/**
 * Created by ningyang on 2017/1/14.
 */
public interface DiamondService {

    DiamondResponse handle(DiamondRequest diamondRequest);
}
