package cwb.server.operator;

import java.awt.Robot;
import java.util.ArrayList;

import cwb.server.map.VisualKeyMap;

public class KEY extends BaseOperator{
	
	private Robot robot;

	public KEY() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> exe(String cmdBody) throws Exception{
		ArrayList<String> ackMsg = new ArrayList<String>();
		System.out.println(cmdBody);
        robot=new Robot();
        int splitIdx = cmdBody.indexOf(",");

        if (splitIdx < 1) {
            int splitIdx2 = cmdBody.indexOf("+");
            if(splitIdx2<1){
                singleKeyPress(cmdBody);
            }else{
                simpleComboKeyPress(cmdBody);
            }
        }else{
            String keyPressStr=cmdBody.substring(0, splitIdx);
            String keyReleaseStr=cmdBody.substring(splitIdx+1);
            comboKeyPress(keyPressStr,keyReleaseStr);
        }

        ackMsg.add("key:"+cmdBody);
        return ackMsg;
	}
	
	private void simpleComboKeyPress(String keyPressStr){
        String[] keyPressArray = keyPressStr.split("\\+");

        for(int i=0;i<keyPressArray.length;i++){
            int keycode = VisualKeyMap.getVisualKey(keyPressArray[i]);
            robot.keyPress(keycode);

        }
        for(int i=keyPressArray.length-1;i>=0;i--){
            int keycode = VisualKeyMap.getVisualKey(keyPressArray[i]);
            robot.keyRelease(keycode);  
        }
    }
	
	
	private  void comboKeyPress(String keyPressStr, String keyReleaseStr) {
        // TODO Auto-generated method stub
        String[] keyPressArray = keyPressStr.split("\\+");
        String[] keyReleaseArray = keyReleaseStr.split("\\+");
        for(int i=0;i<keyPressArray.length;i++){
            int keycode = VisualKeyMap.getVisualKey(keyPressArray[i]);
            robot.keyPress(keycode);

        }
        for(int i=0;i<keyReleaseArray.length;i++){
            int keycode = VisualKeyMap.getVisualKey(keyReleaseArray[i]);
            robot.keyRelease(keycode);
        }
    }

    private void singleKeyPress(String cmdBody) {
        // TODO Auto-generated method stub
        int keycode = VisualKeyMap.getVisualKey(cmdBody);
        robot.keyPress(keycode);
        robot.keyRelease(keycode);

    }
	
}
