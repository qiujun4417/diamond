package com.wonders.diamond.core.netty.dispatcher;

import com.wonders.diamond.core.jdbc.SqlTemplate;
import com.wonders.diamond.core.jdbc.factory.SqlTemplateFactory;
import com.wonders.diamond.core.netty.*;

import javax.sql.DataSource;


/**
 * Created by ningyang on 2017/1/14.
 */
public class DiamondRequestDispatcher {

    private DataSource dataSource;
    private DiamondService diamondService;

    public DiamondRequestDispatcher(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public DiamondResponse dispatch(DiamondRequest diamondRequest){
        SqlTemplate sqlTemplate = SqlTemplateFactory.create(dataSource);
        if(DiamondRequestType.CLIENT.equals(diamondRequest.getRequestType())){
            diamondService = new DiamondClientService(sqlTemplate);
        }else if(DiamondRequestType.WEB.equals(diamondRequest.getRequestType())){
            diamondService = new DiamondWebService(sqlTemplate);
        }
        return diamondService.handle(diamondRequest);
    }
}
