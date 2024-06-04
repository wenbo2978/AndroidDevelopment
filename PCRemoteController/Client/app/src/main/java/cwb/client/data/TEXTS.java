package cwb.client.data;

import java.util.ArrayList;

/**
 * Created by PC_chen on 2019/3/16.
 */

public class TEXTS {
    public TEXTS() {
    }

    public ArrayList<HotKeyData> TextsData(){
        ArrayList<HotKeyData> def = new ArrayList<>();
        def.add(new HotKeyData("退出程序","key:VK_ALT+VK_F4"));
        def.add(new HotKeyData("切换进程","key:VK_ALT+vk_tab"));
        return def;
    }
}
