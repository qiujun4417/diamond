package com.wonders.diamond.core.netty.handler;

import com.wonders.diamond.core.netty.DiamondRequest;
import com.wonders.diamond.core.netty.DiamondResponse;
import com.wonders.diamond.core.netty.dispatcher.DiamondRequestDispatcher;

/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondRequestHandler {

    private DiamondRequestDispatcher dispatcher;

    public DiamondRequestHandler(DiamondRequestDispatcher dispatcher){
        this.dispatcher = dispatcher;
    }

    public DiamondResponse handleDiamondRequest(DiamondRequest diamondRequest){
        return dispatcher.dispatch(diamondRequest);
    }
}
