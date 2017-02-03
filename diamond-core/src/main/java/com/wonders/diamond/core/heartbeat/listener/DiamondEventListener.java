package com.wonders.diamond.core.heartbeat.listener;

import com.wonders.diamond.core.heartbeat.event.DiamondEvent;

/**
 * Created by ningyang on 2017/2/1.
 */
public interface DiamondEventListener {

    void handleEvent(DiamondEvent diamondEvent);

}
