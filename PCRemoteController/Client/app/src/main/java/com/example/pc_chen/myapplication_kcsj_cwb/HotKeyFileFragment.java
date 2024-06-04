package com.example.pc_chen.myapplication_kcsj_cwb;


import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.TooltipCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

import cwb.client.data.HotKeyData;
import cwb.client.database.DataBaseHelper;

import cwb.client.operator.ShowNonUiUpdateCmdHandler;
import cwb.client.socket.CmdClientSocket;
import cwb.client.view.HotKeyCursorAdapter;

public class HotKeyFileFragment extends android.support.v4.app.Fragment{

    String ip;
    int port;
    ListView lv;
    ArrayList<HotKeyData> list = new ArrayList<>();
    Button btc,btok,btup;
    EditText edk,edv;
    String key;
    String value;
    HotKeyCursorAdapter hotKeyCursorAdapter;
    Cursor cursor;
    SQLiteDatabase db;
    long Context_id;
    ShowNonUiUpdateCmdHandler handler;
    public static final int OPT_MO = 12344;
    String cpString;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_hot_key, container, false);
        btc = view.findViewById(R.id.hot_bt_cl);
        btok = view.findViewById(R.id.hot_bt_ok);
        btup = view.findViewById(R.id.bt_updata);
        edk = view.findViewById(R.id.hot_hot_key);
        edv = view.findViewById(R.id.hot_hot_value);
        db = new DataBaseHelper(getContext()).getWritableDatabase();
        String sql = String.format("select * from %s",DataBaseHelper.TABLE_NAME_HOT);
        cursor = db.rawQuery(sql,null);
        lv = view.findViewById(R.id.lv_hot);
        hotKeyCursorAdapter = new HotKeyCursorAdapter(getContext(),cursor);
        lv.setAdapter(hotKeyCursorAdapter);
        registerForContextMenu(lv);
        setIP_PORT();
        btup.setEnabled(false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        btup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = edk.getText().toString();
                String value = edv.getText().toString();
                ContentValues cv = DataBaseHelper.getContentValues_hot(key,value);
                db.update(DataBaseHelper.TABLE_NAME_HOT,cv,"_id=?",new String[]{String.valueOf(Context_id)});
                btup.setEnabled(false);
                btok.setEnabled(true);
                hotKeyCursorAdapter.getCursor().requery();
                edk.setText("");
                edv.setText("");
            }
        });

        btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edk.setText("");
                edv.setText("");
            }
        });

        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = edk.getText().toString();
                value = edv.getText().toString();
                ContentValues cv = DataBaseHelper.getContentValues_hot(key,value);
                db.insert(DataBaseHelper.TABLE_NAME_HOT,null,cv);
                hotKeyCursorAdapter.getCursor().requery();
            }
        });


        return view;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater((getContext())).inflate(R.menu.hot_context,menu);
        AdapterView.AdapterContextMenuInfo contextMenuInfo = ( AdapterView.AdapterContextMenuInfo)menuInfo;
        Context_id = contextMenuInfo.id;
        int a = cursor.getPosition();
        cursor.moveToPosition(a);
        String V = cursor.getString(cursor.getColumnIndex(DataBaseHelper.HOT_VALUE));
        //Log.d("value",V);
        String ccc = V.substring(0,V.indexOf(":"));
        if(ccc.equalsIgnoreCase("fs")){
            menu.add(0,OPT_MO,menu.size(),"展开功能");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.con_edit:
                edit();
                break;
            case R.id.con_commit:
                commit();
                break;
            case R.id.con_delete:
                delete();
                break;
            case OPT_MO:
                cp();
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void cp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_hot_cp,null,false);
        final EditText ed_cp = v.findViewById(R.id.ed_cp);


        builder.setTitle("搜索框").setView(v).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cpString = ed_cp.getText().toString();
                CmdClientSocket cmdClientSocket = new CmdClientSocket(ip,port,handler);
                cmdClientSocket.work("cps:" + cpString);


            }
        }).setNegativeButton("取消",null).show();
    }

    public void edit(){
        btup.setEnabled(true);
        btok.setEnabled(false);
        String sql = String.format("select * from %s where _id = %d",DataBaseHelper.TABLE_NAME_HOT,Context_id);
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        String key = c.getString(c.getColumnIndex(DataBaseHelper.HOT_KEY));
        String value = c.getString(c.getColumnIndex(DataBaseHelper.HOT_VALUE));
        edk.setText(key);
        edv.setText(value);
    }

    public void commit(){
        String sql = String.format("select * from %s where _id = %d",DataBaseHelper.TABLE_NAME_HOT,Context_id);
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();

        String value = c.getString(c.getColumnIndex(DataBaseHelper.HOT_VALUE));
        Log.d("cmd",value);


        CmdClientSocket cmdClientSocket = new CmdClientSocket(ip,port,handler);
        cmdClientSocket.work(value);
    }

    public void delete(){
        db.delete(DataBaseHelper.TABLE_NAME_HOT,"_id=?",new String []{String.valueOf(Context_id)});
        hotKeyCursorAdapter.getCursor().requery();
    }

    public void setIP_PORT(){
        String sql = String.format("select * from %s",DataBaseHelper.TABLE_NAME);
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                String ip1 = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_IP));
                String port1 = cursor.getString(cursor.getColumnIndex(DataBaseHelper.KEY_PORT));

                port = Integer.parseInt(port1);
                ip = ip1;
            }while (cursor.moveToNext());
        }
    }
}
