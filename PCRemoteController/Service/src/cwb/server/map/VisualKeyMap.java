package cwb.server.map;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class VisualKeyMap {
	 private static HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
	    private static final VisualKeyMap VISUAL_KEY_MAP = new VisualKeyMap();

	    private VisualKeyMap() {
	        hashMap.put("VK_0", KeyEvent.VK_0);
	       
	        hashMap.put("VK_1", KeyEvent.VK_1);
	        hashMap.put("VK_2", KeyEvent.VK_2);
	        hashMap.put("VK_3", KeyEvent.VK_3);
	        hashMap.put("VK_4", KeyEvent.VK_4);
	        hashMap.put("VK_5", KeyEvent.VK_5);
	        hashMap.put("VK_6", KeyEvent.VK_6);
	        hashMap.put("VK_7", KeyEvent.VK_7);
	        hashMap.put("VK_8", KeyEvent.VK_8);
	        hashMap.put("VK_9", KeyEvent.VK_9);
	        hashMap.put("VK_ESCAPE", KeyEvent.VK_ESCAPE);
	        hashMap.put("VK_ALT", KeyEvent.VK_ALT);
	        hashMap.put("VK_WINDOW", KeyEvent.VK_WINDOWS);
	        hashMap.put("VK_B", KeyEvent.VK_B);
	        hashMap.put("VK_S", KeyEvent.VK_S);
	        hashMap.put("VK_P", KeyEvent.VK_P);
	        hashMap.put("VK_E", KeyEvent.VK_E);
	        hashMap.put("VK_L", KeyEvent.VK_L);	
	        hashMap.put("VK_I", KeyEvent.VK_I);
	        hashMap.put("VK_F", KeyEvent.VK_F);	
	        hashMap.put("VK_F11", KeyEvent.VK_F11);
	        hashMap.put("VK_F10", KeyEvent.VK_F10);
	        hashMap.put("VK_F4", KeyEvent.VK_F4);
	        hashMap.put("VK_F5", KeyEvent.VK_F5);
	        hashMap.put("VK_F7", KeyEvent.VK_F7);
	        hashMap.put("VK_F8", KeyEvent.VK_F8);
	        hashMap.put("VK_F9", KeyEvent.VK_F9);
	        hashMap.put("VK_HOME", KeyEvent.VK_HOME);
	        hashMap.put("VK_END", KeyEvent.VK_END);	 
	        hashMap.put("VK_CTRL", KeyEvent.VK_CONTROL);	
	        hashMap.put("VK_TAB", KeyEvent.VK_TAB);
	        hashMap.put("VK_PAGE_UP",KeyEvent.VK_PAGE_UP);
	        hashMap.put("VK_PAGE_DOWN",KeyEvent.VK_PAGE_DOWN);
	        hashMap.put("VK_SHIFT", KeyEvent.VK_SHIFT);
	        
	    }


	    public static int getVisualKey(String key) {
	        
	        return hashMap.get(key.toUpperCase());
	    }
}
