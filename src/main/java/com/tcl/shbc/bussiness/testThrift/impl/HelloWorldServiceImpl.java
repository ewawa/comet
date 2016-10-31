package com.tcl.shbc.bussiness.testThrift.impl;

import org.apache.thrift.TException;

import com.tcl.shbc.bussiness.testThrift.iface.HelloWorldService.Iface;
import com.tcl.shbc.bussiness.testThrift.iface.RESCODE;
import com.tcl.shbc.bussiness.testThrift.iface.Reponse;
import com.tcl.shbc.bussiness.testThrift.iface.Request;
import com.tcl.shbc.bussiness.testThrift.iface.ServiceException;

public class HelloWorldServiceImpl implements Iface{

	@Override
	public Reponse send(Request request) throws ServiceException, TException {
		String json = request.getParamJSON();
        String serviceName = request.getServiceName();
        System.out.println("得到的json：" + json + " ；得到的serviceName: " + serviceName);

        // 构造返回信息
        Reponse response = new Reponse();
        response.setResponeCode(RESCODE._200);
        response.setResponseJSON("{\"user\":\"yinwenjie\"}");
        return response;
	}

}
