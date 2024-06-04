package cwb.client.operator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cwb.client.data.NetFileData;
import cwb.client.socket.CmdClientSocket;
import cwb.client.view.NetFileListAdapter;

/**
 * Created by PC_chen on 2019/3/1.
 */

public class ShowRemoteFileHandler extends Handler {
    private Context context;
    private ListView listView;
    public NetFileListAdapter listAdapter;
    public ShowRemoteFileHandler(Context context, ListView listView) {
        super();
        this.context = context;
        this.listView = listView;
    }

    public ShowRemoteFileHandler() {

    }



    @Override
    public void handleMessage(Message msg) {
        Bundle bundle = msg.getData();
        ArrayList<NetFileData> list = (ArrayList<NetFileData>)bundle.getSerializable(CmdClientSocket.KEY_SERVER_ACK_MSG);

        Log.d("time","jubin" + new CmdClientSocket().MISS_MESAGE);
            if(list == null){
                if(new CmdClientSocket().MISS_MESAGE == true) {
                    Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(context,"不存在的目录或者不支持的命令",Toast.LENGTH_SHORT).show();
            }else {
                NetFileData net1 = new NetFileData("","..>>0>1>");
                list.add(0,net1);
                NetFileData net2 = new NetFileData("","...>>0>1>");
                list.add(0,net2);
                listAdapter = new NetFileListAdapter(context,list);
                listView.setAdapter(listAdapter);
            }





    }
}
