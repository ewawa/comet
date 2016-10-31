package com.tcl.shbc.bussiness.testThrift.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

import com.tcl.shbc.bussiness.testThrift.iface.HelloWorldService;
import com.tcl.shbc.bussiness.testThrift.iface.Reponse;
import com.tcl.shbc.bussiness.testThrift.iface.Request;

public class HelloClient {
	public static final void main(String[] args) throws Exception {
		// 服务器所在的IP和端口
		TSocket transport = new TSocket("127.0.0.1", 9111);
		TProtocol protocol = new TBinaryProtocol(transport);

		// 准备调用参数
		Request request = new Request("{\"param\":\"field1\"}", "\\mySerivce\\queryService");
		HelloWorldService.Client client = new HelloWorldService.Client(protocol);

		// 准备传输
		transport.open();
		// 正式调用接口
		Reponse reponse = client.send(request);
		// 一定要记住关闭
		transport.close();
		System.out.println("response = " + reponse);
	}
}
