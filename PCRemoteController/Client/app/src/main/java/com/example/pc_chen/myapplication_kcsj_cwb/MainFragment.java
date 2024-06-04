package com.example.pc_chen.myapplication_kcsj_cwb;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import cwb.client.data.HotKeyData;
import cwb.client.data.JPGS;
import cwb.client.data.MVS;
import cwb.client.data.NetFileData;
import cwb.client.data.PPTS;
import cwb.client.data.TEXTS;
import cwb.client.database.DataBaseHelper;
import cwb.client.operator.HotKeyHandler;
import cwb.client.operator.ShowDownLoadHandler;
import cwb.client.operator.ShowNonUiUpdateCmdHandler;
import cwb.client.operator.ShowRemoteFileHandler;
import cwb.client.socket.CmdClientSocket;
import cwb.client.socket.Download;
import cwb.client.view.GrideViewAdapter;

public class MainFragment extends Fragment {

    ListView lv;
    Button bt,bt_hide;
    EditText ed_ip;
    EditText ed_port;
    EditText ed_cmd;
    LinearLayout ll;
    TextView tv_catalog;
    private static final int OPT_SEND_ID = 123;
    private static final int OPT_LOAD = 111;
    String cl;
    GrideViewAdapter grideViewAdapter;
    GridView gv;
    Spinner spinner;
    SQLiteDatabase db;
    ArrayList<String> listip = new ArrayList<>();
    int load_pos;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_main,container,false);

        db = new DataBaseHelper(getContext()).getWritableDatabase();
        lv = view.findViewById(R.id.file_list);
        tv_catalog = view.findViewById(R.id.tv_catalog);
        ll = view.findViewById(R.id.thehole);
        bt = view.findViewById(R.id.button);
        bt_hide = view.findViewById(R.id.hide);
        registerForContextMenu(lv);
        spinner = view.findViewById(R.id.spinner);
        ed_ip = view.findViewById(R.id.ed_ip);
        ed_port = view.findViewById(R.id.ed_port);
        ed_cmd = view.findViewById(R.id.ed_cmd);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        bt_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.setVisibility(View.GONE);
            }
        });
        //Cursor cursor = db.rawQuery("select * from" + DataBaseHelper.TABLE_NAME,null);
        String sql = String.format("select * from %s",DataBaseHelper.TABLE_NAME);
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String ip = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_IP));
                String port = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_PORT));

                String ss = ip + " " + port;
                listip.add(ss);
            }while (cursor.moveToNext());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,listip);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = listip.get(position);
                String []ss = s.split(" ");
                ed_ip.setText(ss[0]);
                ed_port.setText(ss[1]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NetFileData fileData = (NetFileData) parent.getItemAtPosition(position);

                if(fileData.isDirectory() == 2){
                    String filePath = fileData.getFileName();
                    ShowRemoteFileHandler showRemoteFileHandler = new ShowRemoteFileHandler(getContext(), lv);//会更新ListView的句柄
                    CmdClientSocket cmdClientSocket = new CmdClientSocket(ed_ip.getText().toString().trim(),Integer.parseInt(ed_port.getText().toString().trim()),showRemoteFileHandler);
                    cmdClientSocket.work("dir:"+filePath);
                }else{
                    String filePath="";
                    String pwd = fileData.getFilePath();
                    if(fileData.isDirectory()>=1){
                        if(fileData.getFileName().equals("...")){
                            filePath = "...";
                        }else if(fileData.getFileName().equals("..")){
                            int ex = tv_catalog.getText().toString().lastIndexOf("/");
                            if(ex>0){
                                filePath = tv_catalog.getText().toString().substring(0,ex);
                            }else {
                                int ex2 = tv_catalog.getText().toString().lastIndexOf("\\");
                                filePath = tv_catalog.getText().toString().substring(0,ex2+1);
                            }


                        }else {
                            if(pwd.endsWith("/")|pwd.endsWith("\\")){
                                filePath=pwd+fileData.getFileName();
                            }else{
                                filePath=pwd+ File.separator+fileData.getFileName();
                            }
                        }
                        ShowRemoteFileHandler showRemoteFileHandler = new ShowRemoteFileHandler(getContext(), lv);//会更新ListView的句柄
                        CmdClientSocket cmdClientSocket = new CmdClientSocket(ed_ip.getText().toString().trim(),Integer.parseInt(ed_port.getText().toString().trim()),showRemoteFileHandler);
                        cmdClientSocket.work("dir:"+filePath);
                        tv_catalog.setText(filePath);
                    }else {
                        filePath=pwd+ File.separator+fileData.getFileName();
                        ShowNonUiUpdateCmdHandler showNonUiUpdateCmdHandler = new ShowNonUiUpdateCmdHandler(getContext());
                        CmdClientSocket cmdClientSocket = new CmdClientSocket(ed_ip.getText().toString().trim(),Integer.parseInt(ed_port.getText().toString().trim()),showNonUiUpdateCmdHandler);
                        cmdClientSocket.work("opn:" + filePath);
                    }

                }



            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ip = ed_ip.getText().toString().trim();

                int port = Integer.parseInt(ed_port.getText().toString().trim());

                String cmd = ed_cmd.getText().toString().trim();
                insert(ip,port);


                if(cmd.substring(0,cmd.indexOf(":")).equalsIgnoreCase("key")){
                    HotKeyHandler hotKeyHandler = new HotKeyHandler(getContext());
                    CmdClientSocket cmdClientSocket = new CmdClientSocket(ip,port,hotKeyHandler);
                    cmdClientSocket.work(cmd);
                }else {
                    ShowRemoteFileHandler fileHandler = new ShowRemoteFileHandler(getContext(),lv);
                    CmdClientSocket cmdClientSocket = new CmdClientSocket(ip,port,fileHandler);

                    cmdClientSocket.work(cmd);
                    tv_catalog.setText(cmd.substring(cmd.indexOf(":") + 1));
                }


            }
        });
        return view;
    }

    public int ifIsExist(String ip){
        String sql = String.format("select * from %s where %s = ?",DataBaseHelper.TABLE_NAME,DataBaseHelper.KEY_IP);
        Cursor cursor = db.rawQuery(sql,new String[]{ip});
        cursor.moveToLast();
        return cursor.getPosition();
    }

    public void insert(String ip,int port){
        Log.d("db","" + ifIsExist(String.valueOf(ip)));
        db = new DataBaseHelper(getContext()).getWritableDatabase();
        if(ifIsExist(String.valueOf(ip))!=-1){
            Log.d("db","此消息已有记录，不再插入");
            db.delete(DataBaseHelper.TABLE_NAME,DataBaseHelper.KEY_IP  + "= ?",new String[]{ip});
        }

            ContentValues cv = DataBaseHelper.getContentValues(ip,String.valueOf(port));
            long cvd = db.insert(DataBaseHelper.TABLE_NAME,null,cv);
            Log.d("db","信息插入成功");

    }

    private void initData(){

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo = ( AdapterView.AdapterContextMenuInfo)menuInfo;
        int po = contextMenuInfo.position;
        initData();
        NetFileData n = (NetFileData)lv.getItemAtPosition(po);
        int ex = n.getFileName().lastIndexOf(".");

        load_pos = po;

        if(ex >0){
            cl = n.getFileName().substring(ex+1);

            menu.add(0,OPT_SEND_ID,menu.size(),"展开功能");
            menu.add(1,OPT_LOAD,menu.size(),"下载");
            Toast.makeText(getContext(),n.getFileName().substring(ex+1),Toast.LENGTH_SHORT).show();
        }


        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void load(){
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                Long s = bundle.getLong("j");
                Log.d("load",s + "");
                progressBar.setProgress(s.intValue());
                if(s == 100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        NetFileData n = (NetFileData)lv.getItemAtPosition(load_pos);
        Toast.makeText(getContext(),n.getFilePath() + n.getFileName(),Toast.LENGTH_SHORT).show();


        CmdClientSocket cmdClientSocket = new CmdClientSocket(ed_ip.getText().toString().trim(),Integer.parseInt(ed_port.getText().toString().trim()),handler);
        cmdClientSocket.work("dlf:" + n.getFilePath()+ File.separator+n.getFileName());

        Download download = new Download(ed_ip.getText().toString().trim(),handler,n.getFilePath()+ File.separator+n.getFileName(),n.getFileName());
        download.start();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case OPT_SEND_ID:
                Dialog();
                break;
            case OPT_LOAD:
                load();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.gv_layout,null,false);
        gv = v.findViewById(R.id.gv_view);
        String ip = ed_ip.getText().toString().trim();

        int port = Integer.parseInt(ed_port.getText().toString().trim());

        HotKeyHandler hotKeyHandler = new HotKeyHandler(getContext());
        CmdClientSocket cmdClientSocket = new CmdClientSocket(ip,port,hotKeyHandler);
        ArrayList<HotKeyData> re;
        if(cl.equals("ppt")||cl.equals("txt")||cl.equals("mp4")||cl.equals("jpg")){
            if (cl.equals("ppt")){
                PPTS p = new PPTS();
                re = p.PPtData();
                grideViewAdapter = new GrideViewAdapter(getContext(),re,cmdClientSocket);
            }else if(cl.equals("txt")){
                TEXTS t = new TEXTS();
                re = t.TextsData();
                grideViewAdapter = new GrideViewAdapter(getContext(),re,cmdClientSocket);
            }else if(cl.equals("mp4")){
                MVS m = new MVS();
                re = m.MvData();
                grideViewAdapter = new GrideViewAdapter(getContext(),re,cmdClientSocket);
            }else if(cl.equalsIgnoreCase("jpg")){
                JPGS j = new JPGS();
                re = j.JpgData();
                grideViewAdapter = new GrideViewAdapter(getContext(),re,cmdClientSocket);
            }

            gv.setAdapter(grideViewAdapter);

            builder.setTitle("更多功能").setView(v).setNegativeButton("CANCLE",null);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }

    }


}
