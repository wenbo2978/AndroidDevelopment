package cwb.server.operator;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DIR {

	public DIR() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<String> exeDir(String cmdBody) {
        // TODO Auto-generated method stub
        ArrayList<String> backList=new ArrayList<String>();
        File file;
        
        try {
			file = new File(cmdBody);
			File[] listFiles = file.listFiles();
			for(File mfile:listFiles){
	            String fileName = mfile.getName();
	            long lastModified = mfile.lastModified();
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            String fileDate = dateFormat.format(new Date(lastModified));
	            String fileSize="0";
	            String isDir="1";
	            if(!mfile.isDirectory()){//判断是否为目录
	                isDir="0";
	                fileSize=""+mfile.length();
	            }
	            backList.add(fileName+">"+fileDate+">"+fileSize+">"+isDir+">");
	        }
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("不存在的目录");
			return null;
		}
        
        
        return backList;
    }
	
	public static ArrayList<String> sysS(){
		ArrayList<String> backList=new ArrayList<String>();
		File[] li = new File[]{};
		li = File.listRoots();
		
		for(int i = 0;i<li.length;i++){
			
			backList.add(li[i].getPath() + ">"+"0"+ ">" +"0"+">" +"2"+">");
		
		}
		
		
		return backList;
		
	}
	
	
}
