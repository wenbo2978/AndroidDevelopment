package cwb.server.operator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class DLF{
	ServerSocket serverSocket;
	int port = 8080;
	public DLF() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public void exe(String cmdBody) throws Exception {
		// TODO Auto-generated method stub

		
		ServerSocket ss = new ServerSocket(port);
		
		Socket socket = ss.accept();
		
		new DownloadTest(socket).start();
		
		ss.close();
	}
	
	
	
	

}
