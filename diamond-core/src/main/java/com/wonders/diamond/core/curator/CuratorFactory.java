package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.context.DiamondContext;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.net.SocketException;

/**
 * Created by ningyang on 2017/1/1.
 */
public class CuratorFactory {

    private static CuratorHandler handler;

    private CuratorFactory(){

    }

    /**
     * 单例创建zookeeper处理对象
     * @param zkHost
     * @return
     */
     public static CuratorHandler create(String zkHost, ServiceType serviceType, int servicePort) throws SocketException {
         if(handler==null){
             synchronized (CuratorFactory.class){
                 if(handler!=null)
                     return handler;
                 CuratorFramework client = createClient(zkHost);
                 DiamondContext context = new DiamondContext(client);
                 handler = new CuratorHandlerImpl(client, serviceType, context, servicePort);
                 return handler;
             }
         }
         return handler;
     }

    /**
     * 创建curator client对象
     * @param zkHost
     * @return
     */
    private static CuratorFramework createClient(String zkHost){
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, Integer.MAX_VALUE);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkHost, 2000, 3000, retryPolicy);
        client.start();
        return client;
    }

}
