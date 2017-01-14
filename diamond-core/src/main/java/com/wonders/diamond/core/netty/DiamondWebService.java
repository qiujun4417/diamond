package com.wonders.diamond.core.netty;

import com.wonders.diamond.core.jdbc.SqlTemplate;

/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondWebService implements DiamondService {

    private SqlTemplate sqlTemplate;

    public DiamondWebService(SqlTemplate sqlTemplate){
        this.sqlTemplate = sqlTemplate;
    }

    @Override
    public DiamondResponse handle(DiamondRequest diamondRequest) {
        return null;
    }
}
