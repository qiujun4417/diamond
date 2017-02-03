package com.wonders.diamond.core.heartbeat.subscribe;

import com.google.common.collect.Lists;
import com.wonders.diamond.core.heartbeat.event.DiamondEvent;
import com.wonders.diamond.core.heartbeat.listener.DiamondEventListener;

import java.util.List;

/**
 * Created by ningyang on 2017/2/1.
 */
public class DiamondSubscriber {

    private List<DiamondEventListener> listeners = Lists.newArrayList();

    public void registerListener(DiamondEventListener eventListener){
        listeners.add(eventListener);
    }

    public void handle(DiamondEvent event){
        if(listeners.size()>0){
            for(DiamondEventListener diamondEventListener: listeners){
                diamondEventListener.handleEvent(event);
            }
        }
    }
}
