package cwb.client.operator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * Created by PC_chen on 2019/3/29.
 */

public class ShowDownLoadHandler extends Handler {

    Context context;
    String ip;
    int port;
    String path;

    public ShowDownLoadHandler(Context context) {
        this.context = context;
    }


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        path = CheckLocalDownloadFolder.check();
        Bundle bundle = msg.getData();
        ArrayList<String> list = (ArrayList<String>)bundle.getSerializable("load");
        /*ip = bundle.getString("ip");
        port = Integer.parseInt(bundle.getString("port"));*/


        File f = new File(path);
        if (!f.exists()){
            Log.d("load","文件不存在");
        }else {


        }
    }
}
