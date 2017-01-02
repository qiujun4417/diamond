package com.wonders.diamond.core.instance;

/**
 * Created by ningyang on 2017/1/2.
 * @author ningyang
 */
public class DiamondInstanceBuilder {

    private static DiamondInstance instance;

    public static DiamondInstance create(){
        instance = new DiamondInstance();
        return instance;
    }

    public static DiamondInstance address(String address){
        instance.setAddress(address);
        return instance;
    }

    public static DiamondInstance port(Integer port){
        instance.setPort(port);
        return instance;
    }

    public static DiamondInstance id(String id){
        instance.setId(id);
        return instance;
    }

    public static DiamondInstance name(String name){
        instance.setId(name);
        return instance;
    }

    public static DiamondInstance type(String type){
        instance.setType(type);
        return instance;
    }

    public static DiamondInstance registerTime(long registerTime){
        instance.setRegisterTime(registerTime);
        return instance;
    }
}

