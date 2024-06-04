package cwb.client.data;

import java.util.ArrayList;

/**
 * Created by PC_chen on 2019/3/9.
 */

public class PPTS {

    public PPTS() {
    }

    public ArrayList<HotKeyData> PPtData(){
        ArrayList<HotKeyData> def = new ArrayList<>();
        def.add(new HotKeyData("退出程序","key:VK_ALT+VK_F4"));
        def.add(new HotKeyData("切换进程","key:VK_ALT+vk_tab"));
        def.add(new HotKeyData("全屏","key:vk_f5"));
        def.add(new HotKeyData("从头播放","key:vk_f5"));
        def.add(new HotKeyData("退出","key:vk_escape"));
        def.add(new HotKeyData("第一张","key:VK_HOME"));
        def.add(new HotKeyData("下一张","key:VK_PAGE_DOWN"));
        def.add(new HotKeyData("上一张","key:VK_PAGE_UP"));
        def.add(new HotKeyData("最后一张","key:VK_END"));
        def.add(new HotKeyData("黑屏/正常","key:VK_B"));
        return def;
    }
}
