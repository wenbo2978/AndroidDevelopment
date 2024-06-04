package cwb.server.operator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class DownloadTest extends Thread {
	
	Socket s;
	
	public DownloadTest(Socket s){
		this.s = s;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//serverSocket=new ServerSocket(port);
		try {
			int count = 0;
			InputStream is = s.getInputStream();
	    	InputStreamReader isr = new InputStreamReader(is,"GB2312");
	    	BufferedReader br = new BufferedReader(isr);
	    	String cmdBody = br.readLine();
	    	System.out.println(cmdBody);
			
	    	byte [] buffer = new byte[1024];
			File fileOut=new File(cmdBody);
			if(!fileOut.exists()){
				System.out.println("文件不存在");
				return;
			}else{
				System.out.println("开始下载");
				OutputStream out = s.getOutputStream();
				
				
				FileInputStream fis = new FileInputStream(fileOut);
				DataInputStream dis = new DataInputStream(new BufferedInputStream(fis));
				DataOutputStream ps = new DataOutputStream(out);
				
			
			
				int lenth;
				int i= 0;
				ps.writeLong(fileOut.length());
				while((lenth = fis.read(buffer))!=-1){
					ps.write(buffer,0,lenth);
					
					
					
				}
			
				ps.flush();
				
				out.close();
				dis.close();
				s.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
