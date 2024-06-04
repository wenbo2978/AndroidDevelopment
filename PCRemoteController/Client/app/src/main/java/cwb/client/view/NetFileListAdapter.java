package cwb.client.view;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc_chen.myapplication_kcsj_cwb.R;

import java.util.ArrayList;

import cwb.client.data.NetFileData;

/**
 * Created by PC_chen on 2019/3/1.
 */

public class NetFileListAdapter extends ArrayAdapter<NetFileData> {

    ImageView iv;
    TextView filename;
    TextView filedate;
    TextView filesize;

    public ArrayList<NetFileData> fileDatas;
    public Context context;
    String body;

    public NetFileListAdapter(Context context,ArrayList<NetFileData> fileDatas) {
        super(context,android.R.layout.simple_list_item_1,fileDatas);
        this.fileDatas = fileDatas;
        this.context = context;
        this.body = body;
        //Log.d("133",fileDatas.size() + "");
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.file_adapter,null,false);
        NetFileData data = fileDatas.get(position);
        /*Log.d("133",position + "");
        Log.d("133",data);*/
        //NetFileData netFileData = new NetFileData(body,data);
        iv = v.findViewById(R.id.file_img);
        filename = v.findViewById(R.id.file_name);
        filedate = v.findViewById(R.id.file_date);
        filesize = v.findViewById(R.id.file_size);
        filename.setText(data.getFileName());
        filedate.setText(data.getFileModifiedDate());
        if(data.isDirectory() == 1){
            iv.setImageResource(R.drawable.wenjianjia);
            filesize.setText("");
        }else if(data.isDirectory() == 0){
            iv.setImageResource(R.drawable.wenjian);
            filesize.setText(data.getFileSizeStr());
        }else {
            iv.setImageResource(R.drawable.cipan);
            filesize.setText("");
        }

        return v;
    }



}
