package com.wonders.diamond.core.heartbeat;

import com.wonders.diamond.core.curator.ServiceDiscovery;
import com.wonders.diamond.core.heartbeat.subscribe.DiamondSubscriber;
import com.wonders.diamond.core.instance.DiamondInstance;

/**
 * Created by ningyang on 2017/2/3.
 */
public class DiamondHeartBeatBuilder {

    private DiamondHeartBeat heartBeat = new DiamondHeartBeat();

    public DiamondHeartBeatBuilder builder(){
        return this;
    }

    public DiamondHeartBeatBuilder serviceDiscovery(ServiceDiscovery<DiamondInstance> serviceDiscovery){
        this.heartBeat.setServiceDiscovery(serviceDiscovery);
        return this;
    }

    public DiamondHeartBeatBuilder subscriber(DiamondSubscriber subscriber){
        this.heartBeat.setDiamondSubscriber(subscriber);
        return this;
    }

    public DiamondHeartBeatBuilder remoteClient(RemoteClient remoteClient){
        this.heartBeat.setRemoteClient(remoteClient);
        return this;
    }

    public DiamondHeartBeat build(){
        return heartBeat;
    }
}

