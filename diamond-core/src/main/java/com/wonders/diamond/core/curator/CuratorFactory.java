package com.wonders.diamond.core.curator;

import com.wonders.diamond.core.context.DiamondContext;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

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
     * @param path
     * @return
     */
     public static CuratorHandler create(String zkHost, String path, ServiceType serviceType){
         if(handler==null){
             synchronized (CuratorFactory.class){
                 if(handler!=null)
                     return handler;
                 handler = new CuratorHandlerImpl(createClient(zkHost,path), new DiamondContext(), serviceType);
                 return handler;
             }
         }
         return handler;
     }

    /**
     * 创建curator client对象
     * @param zkHost
     * @param path
     * @return
     */
    private static CuratorFramework createClient(String zkHost, String path){
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, Integer.MAX_VALUE);
        return CuratorFrameworkFactory.builder()
                .connectString(zkHost)
                .retryPolicy(retryPolicy).connectionTimeoutMs(3000)
                .sessionTimeoutMs(2000)
                .canBeReadOnly(true)
                .namespace(path)
                .build();
    }

}
