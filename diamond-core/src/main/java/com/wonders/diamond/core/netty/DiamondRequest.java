package com.wonders.diamond.core.netty;

/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondRequest {

    private DiamondRequestType requestType;


    public DiamondRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(DiamondRequestType requestType) {
        this.requestType = requestType;
    }
}
