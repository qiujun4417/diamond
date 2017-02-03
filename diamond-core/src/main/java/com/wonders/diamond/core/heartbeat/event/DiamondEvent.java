package com.wonders.diamond.core.heartbeat.event;

import com.wonders.diamond.core.instance.DiamondInstance;

import java.util.List;

/**
 * Created by ningyang on 2017/2/1.
 */
public class DiamondEvent {

    private EventType eventType;

    private List<DiamondInstance> needHandleNodes;

    public DiamondEvent(EventType eventType){
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public List<DiamondInstance> getNeedHandleNodes() {
        return needHandleNodes;
    }

    public void setNeedHandleNodes(List<DiamondInstance> needHandleNodes) {
        this.needHandleNodes = needHandleNodes;
    }
}
