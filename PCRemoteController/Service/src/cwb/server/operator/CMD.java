package cwb.server.operator;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.ArrayList;

public class CMD extends BaseOperator{
	
	

	public CMD() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> exe(String cmd) throws Exception {
		// TODO Auto-generated method stubÁúÖé
		
		//System.out.println(cmdBody);
		int idx = cmd.indexOf(":");
    	String cmdHead = cmd.substring(0,idx);
    	String cmdBody = cmd.substring(idx + 1);
    	
		Runtime.getRuntime().exec("cmd /c start " + cmdBody);
					
		System.out.println("´ò¿ªÍøÒ³");
		
		return null;
	}

}
