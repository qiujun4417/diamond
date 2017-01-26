package com.wonders.diamond.core;

import java.util.Date;

/**
 * Created by ningyang on 2017/1/14.
 */
public class Configuration {

    private App app;
    private String profile;
    private String configKey;
    private String value;
    private Date createTime;
    private Date updateTime;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
