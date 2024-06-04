package cwb.server.operator;

public class FS {
	
	public FS(){
		
	}
	
	
	public void exef(String cmdBody){
		
		System.out.println(cmdBody);
		String s [] = cmdBody.split(";");
		
		new Thread(){
			
			public void run() {
				for(int i = 0;i < s.length;i++){
					int idx = s[i].indexOf(":");
					String cmdHeads = s[i].substring(0,idx);
		        	String cmdBodys = s[i].substring(idx + 1);
		        	System.out.println(cmdHeads + "1223123");
		        	System.out.println(cmdBodys + "1213");
		        	try {
		        		switch (cmdHeads) {
			        	case "cmd":
			    			new CMD().exe(s[i]);
			    			break;
			    		case "cps":
			    			new CPS().exe(cmdBodys);
			    			break;
			    		case "key":
			    			new KEY().exe(cmdBodys);
			    			break;
			    		case "movabs":
			    			new MOUSE().exe(s[i]);
			    			
			    			break;
			    		default:
			    			break;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
		        	try {
						sleep(6000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
				}
			};
		}.start();
	}

}
