package com.wonders.diamond.core.netty;

import java.util.Date;

/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondClientRequest extends DiamondRequest {

    private String appId;
    private boolean isUpdateAll;
    private Date requestTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean isUpdateAll() {
        return isUpdateAll;
    }

    public void setUpdateAll(boolean updateAll) {
        isUpdateAll = updateAll;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
}
