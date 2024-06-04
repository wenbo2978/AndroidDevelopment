package cwb.client.data;

import java.util.ArrayList;

/**
 * Created by PC_chen on 2019/3/9.
 */

public class MVS {

    public MVS() {

    }

    public ArrayList<HotKeyData> MvData(){
        ArrayList<HotKeyData> def = new ArrayList<>();
        def.add(new HotKeyData("退出程序","key:VK_ALT+VK_F4"));
        def.add(new HotKeyData("切换进程","key:VK_ALT+vk_tab"));
        def.add(new HotKeyData("全屏","key:vk_f11"));
        def.add(new HotKeyData("退出","key:vk_escape"));
        def.add(new HotKeyData("快进","key:VK_CTRL+VK_SHIFT+VK_F"));
        def.add(new HotKeyData("回退","key:VK_CTRL+VK_SHIFT+VK_B"));
        def.add(new HotKeyData("暂停","key:VK_CTRL+VK_P"));
        def.add(new HotKeyData("停止播放","key:VK_CTRL+VK_S"));
        def.add(new HotKeyData("静音","key:VK_f7"));
        def.add(new HotKeyData("减小音量","key:VK_f8"));
        def.add(new HotKeyData("增加音量","key:VK_f9"));
        return def;
    }
}
