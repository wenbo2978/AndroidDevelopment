package cwb.client.data;

import java.util.ArrayList;

/**
 * Created by PC_chen on 2019/3/16.
 */

public class JPGS {

    public JPGS() {
    }

    public ArrayList<HotKeyData> JpgData(){
        ArrayList<HotKeyData> def = new ArrayList<>();
        def.add(new HotKeyData("退出程序","key:VK_ALT+VK_F4"));
        def.add(new HotKeyData("切换进程","key:VK_ALT+vk_tab"));
        def.add(new HotKeyData("全屏","key:vk_f5"));
        def.add(new HotKeyData("退出","key:vk_escape"));
        /*def.add(new HotKeyData("上一张","key:VK_HOME"));
        def.add(new HotKeyData("下一张","key:VK_END"));*/

        return def;
    }
}
