package cwb.client.operator;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by PC_chen on 2019/3/2.
 */

public class ShowNonUiUpdateCmdHandler extends Handler {
    Context context;

    public ShowNonUiUpdateCmdHandler(Context context) {
        this.context = context;
    }

    public ShowNonUiUpdateCmdHandler() {
    }

    @Override
    public void handleMessage(Message msg) {
        Bundle bundle = msg.getData();
        String m = (String)bundle.get("OPEN");
        Toast.makeText(context,m,Toast.LENGTH_SHORT).show();

        super.handleMessage(msg);
    }
}
