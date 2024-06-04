package cwb.client.operator;

import android.app.AlertDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cwb.client.data.HotKeyData;
import cwb.client.socket.CmdClientSocket;

/**
 * Created by PC_chen on 2019/3/9.
 */

public class HotKeyDialog extends AlertDialog{
    private Context context;
    private ArrayList<HotKeyData> hotKeyList;//热键列表，用于HotKeyGridAdapter填充数据
    private String title;//对话框的标题
    private CmdClientSocket cmdClientSocket;//用于HotKeyGridAdapter的视图点击触发cmdClientSocket向远程端发送命令

    public HotKeyDialog(Context context, ArrayList<HotKeyData> hotKeyList, String title, CmdClientSocket cmdClientSocket) {
        super(context);
        this.context = context;
        this.hotKeyList = hotKeyList;
        this.title = title;
        this.cmdClientSocket = cmdClientSocket;
    }


    public void show(){


    }
}
