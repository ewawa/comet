package com.tcl.shbc.bussiness.servlet30;

public class Javascript {
	private String script;  
	  
    public Javascript(String func) {  
        script = "<script type='text/javascript'>" + "\n" + "window.parent."  
                + func + "\n" + "</script>" + "\n";  
    }  
  
    public String getScript() {  
        return script;  
    }  
}
