package com.wonders.diamond.core.context;

import com.wonders.diamond.core.instance.DiamondInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ningyang on 2017/1/4.
 * 创建diamond上下文对象包含client和web端的列表
 */
public class DiamondContext {

    public DiamondContext(){
        this.clints = new ArrayList<>();
        this.webs = new ArrayList<>();
    }

    private List<DiamondInstance> clints;

    private List<DiamondContext> webs;

    public List<DiamondInstance> getClints() {
        return clints;
    }

    public void setClints(List<DiamondInstance> clints) {
        this.clints = clints;
    }

    public List<DiamondContext> getWebs() {
        return webs;
    }

    public void setWebs(List<DiamondContext> webs) {
        this.webs = webs;
    }
}
