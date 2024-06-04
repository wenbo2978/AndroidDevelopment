package cwb.client.operator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by PC_chen on 2019/3/9.
 */

public class HotKeyHandler extends Handler {

    private Context context;

    public HotKeyHandler(Context context) {
        this.context =context;
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle bundle = msg.getData();
        String m = (String)bundle.get("HOT");
        //Toast.makeText(context,m,Toast.LENGTH_SHORT).show();
        super.handleMessage(msg);
    }
}
