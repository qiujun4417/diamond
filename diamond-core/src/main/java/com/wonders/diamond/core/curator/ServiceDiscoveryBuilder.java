package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.instance.DiamondInstance;
import com.wonders.diamond.core.serializer.JsonInstanceSerializer;
import org.apache.curator.framework.CuratorFramework;

/**
 * Created by ningyang on 2017/1/6.
 */
public class ServiceDiscoveryBuilder<T> {

    private CuratorFramework client;
    private JsonInstanceSerializer<T> serializer;
    private String basePath;
    private DiamondInstance instance;
    private Class<T> payloadClass;

    public static <T> ServiceDiscoveryBuilder<T> builder(Class<T> paloadClass){

        return new ServiceDiscoveryBuilder<>(paloadClass);
    }

    private ServiceDiscoveryBuilder(Class<T> payloadClass){
        this.payloadClass = payloadClass;
    }

    public ServiceDiscoveryBuilder<T> client(CuratorFramework client){
        this.client = client;
        return this;
    }

    public ServiceDiscoveryBuilder<T> basePath(String basePath){
        this.basePath = basePath;
        return this;
    }

    public ServiceDiscoveryBuilder<T> serializer(JsonInstanceSerializer serializer){
        this.serializer = serializer;
        return this;
    }

    public ServiceDiscoveryBuilder<T> instance(DiamondInstance instance){
        this.instance = instance;
        return this;
    }

    public ServiceDiscovery<T> build(){
        if(serializer==null)
            serializer(new JsonInstanceSerializer(payloadClass));
        return new ServiceDiscoveryImpl<>(client, instance, basePath, serializer);
    }

}
