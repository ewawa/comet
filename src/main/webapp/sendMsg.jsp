<%@page import="com.tcl.shbc.bussiness.longComet.MessageSender"%>

<%  
	MessageSender sender = MessageSender.getInstance();  
    sender.sendMsg("Test");  
%>
