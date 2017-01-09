package com.wonders.diamond.core.utils;

import com.wonders.diamond.core.curator.CuratorFactory;
import com.wonders.diamond.core.curator.CuratorHandler;
import com.wonders.diamond.core.curator.ServiceType;

import java.net.SocketException;

/**
 * Created by ningyang on 2017/1/9.
 */
public class CuratorClientExample {

     public static void main(String[] args) throws SocketException {

         CuratorHandler handler = CuratorFactory.create("127.0.0.1:2181", ServiceType.CLIENT, 100);
         Thread t1 = new Thread(() -> {
             handler.addInstance();
         });
         t1.start();
         try {
             Thread.sleep(20000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

}
