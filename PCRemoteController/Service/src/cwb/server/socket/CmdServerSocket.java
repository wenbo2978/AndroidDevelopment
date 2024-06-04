package cwb.server.socket;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;

import cwb.server.operator.CMD;
import cwb.server.operator.CPS;
import cwb.server.operator.DIR;
import cwb.server.operator.DLF;
import cwb.server.operator.FS;
import cwb.server.operator.KEY;
import cwb.server.operator.MOUSE;

public class CmdServerSocket {

	String ccc = "";
	int port=8019;
    static int connect_count=0;
    ArrayList<String>  msgBackList = new ArrayList<>();
	public CmdServerSocket(int port) {
		super();
		this.port = port;
		// TODO Auto-generated constructor stub
	}
	
	
	
	public CmdServerSocket() {
		
		// TODO Auto-generated constructor stub
	}



	private void printLocalIp(ServerSocket serverSocket) {
        try {
            System.out.println("服务端命令端口prot=" + serverSocket.getLocalPort());
            Enumeration<NetworkInterface> interfaces = null;
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while (addresss.hasMoreElements()) {
                    InetAddress nextElement = addresss.nextElement();
                    String hostAddress = nextElement.getHostAddress();
                    System.out.println("本机IP地址为：" + hostAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void work(){
	      
		try {
			ServerSocket serverSocket=new ServerSocket(port);
	        printLocalIp(serverSocket);
	        while(true){

	            System.out.println("Waiting client to connect.....");
	            Socket socket = serverSocket.accept();
	            System.out.println("Client connected from: "
	                    + socket.getRemoteSocketAddress().toString());
	          
	            
	            ArrayList<String> cmdList=readSocketMsg(socket);
	           
	            
	        	for(int i = 0;i < cmdList.size();i++){
	        		String cmd = cmdList.get(i);
	        		int idx = cmd.indexOf(":");
		        	String cmdHead = cmd.substring(0,idx);
		        	String cmdBody = cmd.substring(idx + 1);
		        	ccc = cmdHead;
		       
		        	System.out.println(ccc);
		            switch (ccc) {
		    		case "dir":
		    			switch (cmdBody) {
						case "...":
							System.out.println("显示磁盘");
							msgBackList = DIR.sysS();
							writebackMsg(socket);
							break;
						default:
							msgBackList = DIR.exeDir(cmdBody);
							if(msgBackList == null){
								missCatalog(socket);
							}else {
								writebackMsg(socket);
							}
							break;
						}
		    			break;
		    		case "opn":
		    			open(cmdBody);
		    			msgback(socket);
		    			break;
		    		case "dlf":
		    			new DLF().exe(cmdBody);
		    			break;
		    		case "cmd":
		    			msgBackList = new CMD().exe(cmd);
		    			msgback(socket);
		    			break;
		    		case "cps":
		    			msgBackList = new CPS().exe(cmdBody);
		    			msgback(socket);
		    			break;
		    		case "key":
		    			msgBackList = new KEY().exe(cmdBody);
		    			msgback(socket);
		    			break;
		    		case "clk":
		    			msgBackList = new MOUSE().exe(cmd);
		    			msgback(socket);
		    			break;
		    		case "mov":
		    			msgBackList = new MOUSE().exe(cmd);
		    			msgback(socket);
		    			break;
		    		case "movabs":
		    			msgBackList = new MOUSE().exe(cmd);
		    			System.out.println("movabs");
		    			msgback(socket);
		    			break;
		    		case "rol":
		    			msgBackList = new MOUSE().exe(cmd);
		    			msgback(socket);
		    			break;
		    		case "fs":
		    			new FS().exef(cmdBody);
		    			break;
		    		default:
		    			missDir(socket);
		    			break;
		    		}
	        	}
	            
	            
	            socket.close();
	            System.out.println("当前Socket服务结束");
	        }
		} catch (Exception e) {
			// TODO: handle exception
			cmdFail(e.toString());
			
		}
       
	}
	
	private void loadmsgback(Socket socket,String cmdBody) throws Exception{
		
    	
	}
	
	private void missCatalog(Socket socket) throws IOException{
		OutputStream os = socket.getOutputStream();
    	OutputStreamWriter osw = new OutputStreamWriter(os, "GB2312");
    	
    	BufferedWriter bw = new BufferedWriter(osw);
    	bw.write("不存在的目录");
    	
    	bw.flush();
	}
	
	private void missDir(Socket socket) throws IOException{
		OutputStream os = socket.getOutputStream();
    	OutputStreamWriter osw = new OutputStreamWriter(os, "GB2312");
    	
    	BufferedWriter bw = new BufferedWriter(osw);
    	bw.write("不支持的命令");
    	
    	bw.flush();
	}
	
	private void msgback(Socket socket) throws IOException{
		OutputStream os = socket.getOutputStream();
    	OutputStreamWriter osw = new OutputStreamWriter(os, "GB2312");
    	
    	BufferedWriter bw = new BufferedWriter(osw);
    	bw.write("文件打开成功");
    	
    	bw.flush();
	}
	private void open(String path){
		Desktop desk = Desktop.getDesktop();
		
		try {
			File file = new File(path);
			desk.open(file);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());  
		}
	}
	
	private void cmdFail(String e){
		msgBackList.clear();
		msgBackList.add(e);
	}
	
	private ArrayList<String> readSocketMsg(Socket socket) throws IOException{
    	ArrayList<String> cmdList = new ArrayList<>();
    	
    	InputStream is = socket.getInputStream();
    	InputStreamReader isr = new InputStreamReader(is,"GB2312");
    	BufferedReader br = new BufferedReader(isr);
    	String numStr = br.readLine();
    	int lineNum = Integer.parseInt(numStr);
    	for(int i = 0; i<lineNum;i ++){
    		cmdList.add(br.readLine());
    	}
    	
    	return cmdList;
    }
	
	
    
    private void writebackMsg(Socket socket) throws IOException{
    	OutputStream os = socket.getOutputStream();
    	OutputStreamWriter osw = new OutputStreamWriter(os, "GB2312");
    	
    	BufferedWriter bw = new BufferedWriter(osw);
    	bw.write(msgBackList.size() + "\n");
    	
    	bw.flush();
    	for(int i = 0; i < msgBackList.size(); i ++){
    		bw.write(msgBackList.get(i) + "\n");
    		
    	}
    	bw.flush();
    	
    	
    }
    
}
