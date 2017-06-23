package com.wonders.diamond.core.netty;

import com.wonders.diamond.core.jdbc.SqlTemplate;

/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondClientService implements DiamondService{

    private SqlTemplate sqlTemplate;

    public DiamondClientService(SqlTemplate sqlTemplate){
        this.sqlTemplate = sqlTemplate;
    }

    @Override
    public DiamondResponse handle(DiamondRequest diamondRequest) {
        DiamondResponse response = new DiamondResponse();
        response.setCode(10);
        response.setMsg("success");
        return response;
    }
}
