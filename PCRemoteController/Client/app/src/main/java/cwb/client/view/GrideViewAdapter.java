package cwb.client.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pc_chen.myapplication_kcsj_cwb.R;

import java.util.ArrayList;

import cwb.client.data.HotKeyData;
import cwb.client.socket.CmdClientSocket;

/**
 * Created by PC_chen on 2019/3/9.
 */

public class GrideViewAdapter extends ArrayAdapter <HotKeyData>{
    Context context;
    ArrayList<HotKeyData> list;
    TextView tv;
    CmdClientSocket cmdClientSocket;

    public GrideViewAdapter(@NonNull Context context, ArrayList<HotKeyData> list, CmdClientSocket cmdClientSocket) {
        super(context,android.R.layout.simple_list_item_1,list);
        this.context = context;
        this.list = list;
        this.cmdClientSocket = cmdClientSocket;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.gv_content_layout,null,false);
        tv = v.findViewById(R.id.tv_c);
        tv.setText(list.get(position).getKey());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmdClientSocket.work(list.get(position).getValue());
            }
        });
        return v;
    }
}
