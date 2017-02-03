package com.wonders.diamond.core.heartbeat;

import com.google.common.collect.Lists;
import com.wonders.diamond.core.curator.ServiceDiscovery;
import com.wonders.diamond.core.heartbeat.event.DiamondEvent;
import com.wonders.diamond.core.heartbeat.event.EventType;
import com.wonders.diamond.core.heartbeat.subscribe.DiamondSubscriber;
import com.wonders.diamond.core.instance.DiamondInstance;
import com.wonders.diamond.core.netty.DiamondRequestType;
import com.wonders.diamond.core.utils.NamedThreadFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by ningyang on 2017/1/1.
 */
public class DiamondHeartBeat {

    private ServiceDiscovery<DiamondInstance> serviceDiscovery;

    private DiamondSubscriber diamondSubscriber;

    private RemoteClient remoteClient;

    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(1, new NamedThreadFactory("diamond heartBeat thread"));

    private final Logger logger = LoggerFactory.getLogger(DiamondHeartBeat.class);
    public ServiceDiscovery<DiamondInstance> getServiceDiscovery() {
        return serviceDiscovery;
    }

    public void setServiceDiscovery(ServiceDiscovery<DiamondInstance> serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public DiamondSubscriber getDiamondSubscriber() {
        return diamondSubscriber;
    }

    public void setDiamondSubscriber(DiamondSubscriber diamondSubscriber) {
        this.diamondSubscriber = diamondSubscriber;
    }

    public RemoteClient getRemoteClient() {
        return remoteClient;
    }

    public void setRemoteClient(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    public void heartBeat(){
        service.scheduleWithFixedDelay(() -> {
            try {
                Collection<DiamondInstance> instances = serviceDiscovery.listAll();
                serviceDiscovery.context().setClints(instances.
                        stream().
                        filter(a-> DiamondRequestType.CLIENT.name().equals(a.getType())).
                        collect(Collectors.toList()));
                serviceDiscovery.context().setWebs(instances.
                        stream().
                        filter(a-> DiamondRequestType.WEB.name().equals(a.getType())).
                        collect(Collectors.toList()));
                serviceDiscovery.context().getClints().stream().forEach(a->run(a));
            } catch (Exception e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    private void run(DiamondInstance instance){
        try {
            if(!remoteClient.isReachable(instance)){
                DiamondEvent diamondEvent = new DiamondEvent(EventType.DIAMOND_OFFLIEN);
                List<DiamondInstance> needToHandleNodes = Lists.newArrayList();
                needToHandleNodes.add(instance);
                diamondEvent.setNeedHandleNodes(needToHandleNodes);
                diamondSubscriber.handle(diamondEvent);
            }
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
}
