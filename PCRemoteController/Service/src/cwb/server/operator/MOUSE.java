package cwb.server.operator;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MOUSE extends BaseOperator{

	Robot robot;
	public MOUSE() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> exe(String cmd) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> ackMsg = new ArrayList<String>();
		
		int idx = cmd.indexOf(":");
    	String cmdHead = cmd.substring(0,idx);
    	String cmdBody = cmd.substring(idx + 1);
    	
    	switch(cmdHead){
    		case "clk":
    			execlk(cmdBody);
    			break;
    		case "mov":
    			exemov(cmdBody);
    			break;
    		case "movabs":
    			exemovabs(cmdBody);
    			break;
    		case "rol":
    			exerol(cmdBody);
    			break;
    		default:
    			break;
    	}
		
		robot=new Robot();
		
		return ackMsg;
	}
	
	public void exerol(String cmdBody)throws AWTException{
		robot = new Robot();
		int h = 0;
		try {
			h = Integer.parseInt(cmdBody);
		} catch (Exception e) {
			// TODO: handle exception
			h = 0;
		}
		robot.mouseWheel(h);
	}
	
	public void execlk(String cmdBody) throws AWTException{
		
		String [] f = cmdBody.split("\\+");
		for(int i = 0;i<f.length;i++){
			click(f[i]);
		}
		
		
	}
	
	public void click(String cmdBody) throws AWTException{
		robot = new Robot();
		System.out.println(cmdBody);
		switch(cmdBody){
			case "left":
				robot.mousePress(KeyEvent.BUTTON1_MASK);
				robot.mouseRelease(KeyEvent.BUTTON1_MASK);
				break;
			case "right":
				robot.mousePress(KeyEvent.BUTTON3_MASK);
				robot.mouseRelease(KeyEvent.BUTTON3_MASK);
				break;
			default:
				break;
		}
	}
	
	public void exemovabs(String cmdBody)throws AWTException{
		String [] s = cmdBody.split(",");
		double x = Integer.parseInt(s[0]);
		double y = Integer.parseInt(s[1]);
		robot = new Robot();
		robot.mouseMove(new Double(x).intValue(), new Double(y).intValue());
	}
	
	public void exemov(String cmdBody) throws AWTException{
		
		String [] s = cmdBody.split(",");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Point point = MouseInfo.getPointerInfo().getLocation();
		double x = Integer.parseInt(s[0]) + point.x;
		double y = Integer.parseInt(s[1]) + point.y;
		//System.out.println(d.getWidth() + " , " + d.getHeight());
		
		try {
			if(x<0){
				x = 0;
			}
			if(x > d.getWidth()){
				x = d.getWidth() - 1;
			}
			if(y<0){
				y = 0;
			}
			if(y > d.getHeight()){
				y = d.getHeight() - 1;
			}
			robot = new Robot();
			robot.mouseMove(new Double(x).intValue(), new Double(y).intValue());
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
