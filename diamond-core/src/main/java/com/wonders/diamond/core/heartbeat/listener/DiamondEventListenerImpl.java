package com.wonders.diamond.core.heartbeat.listener;

import com.wonders.diamond.core.context.DiamondContext;
import com.wonders.diamond.core.curator.ServiceDiscovery;
import com.wonders.diamond.core.heartbeat.event.DiamondEvent;
import com.wonders.diamond.core.heartbeat.event.EventType;
import com.wonders.diamond.core.instance.DiamondInstance;
import com.wonders.diamond.core.netty.DiamondRequestType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ningyang on 2017/2/3.
 */
public class DiamondEventListenerImpl implements DiamondEventListener{

    private ServiceDiscovery<DiamondInstance> serviceDiscovery;

    public DiamondEventListenerImpl(ServiceDiscovery serviceDiscovery){
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public void handleEvent(DiamondEvent diamondEvent) {
        List<DiamondInstance> needToHandleNodes = diamondEvent.getNeedHandleNodes();
        if(needToHandleNodes.size()<=0){
            return;
        }
        List<DiamondInstance> clients = needToHandleNodes.stream().
                filter(a->DiamondRequestType.CLIENT.name().equals(a.getType())).
                collect(Collectors.toList());
        List<DiamondInstance> webs = needToHandleNodes.stream().filter(a->DiamondRequestType.WEB.name().equals(a.getType())).collect(Collectors.toList());
        DiamondContext context = serviceDiscovery.context();
        if(EventType.DIAMOND_OFFLIEN.equals(diamondEvent.getEventType())){
            context.getClints().removeAll(clients);
            context.getWebs().removeAll(webs);
        }else if(EventType.DIAMOND_ONLIEN.equals(diamondEvent.getEventType())){
            context.getClints().addAll(clients);
            context.getWebs().addAll(webs);
        }
    }
}
