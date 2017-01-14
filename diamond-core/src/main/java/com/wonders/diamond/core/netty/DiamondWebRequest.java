package com.wonders.diamond.core.netty;

import com.wonders.diamond.core.Configuration;

import java.util.List;

/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondWebRequest extends DiamondRequest {

    private List<Configuration> configurations;

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }
}
