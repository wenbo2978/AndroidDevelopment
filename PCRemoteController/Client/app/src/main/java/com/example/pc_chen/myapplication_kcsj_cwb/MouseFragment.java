package com.example.pc_chen.myapplication_kcsj_cwb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cwb.client.database.DataBaseHelper;
import cwb.client.operator.ShowNonUiUpdateCmdHandler;
import cwb.client.socket.CmdClientSocket;

public class MouseFragment extends Fragment {


    TextView tv_mouse;
    String ip = "192.168.42.233";
    int port = 0;
    String cmd;
    Button bt_l;
    Button bt_r;
    ShowNonUiUpdateCmdHandler handler;
    TextView tv_rol;
    SQLiteDatabase db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mouse,container,false);
        db = new DataBaseHelper(getContext()).getWritableDatabase();
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

        Log.d("dis",ip + "         "+port);
        /*********************************************************************************************/
        cmd = "clk:left";
        CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, handler);
        cmdClientSocket.work(cmd);

        /******************************************************************************************/

        bt_l = view.findViewById(R.id.bt_left);
        bt_r = view.findViewById(R.id.bt_right);
        tv_rol = view.findViewById(R.id.tv_rol);
        tv_mouse = view.findViewById(R.id.tv_mouse);
        final GestureDetector mGestureDetector = new GestureDetector(getContext(), new MousePadOnGestureListener());
        final GestureDetector gestureDetector = new GestureDetector(getContext(),new MouseRolOnGestureListener());
        bt_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmd = "clk:left";
                CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,handler);
                cmdClientSocket.work(cmd);
            }
        });

        bt_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmd = "clk:right";
                CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, handler);
                cmdClientSocket.work(cmd);
            }
        });



       tv_mouse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d("dis",MotionEventCompat.getActionMasked(event) + "lll");
                mGestureDetector.onTouchEvent(event);


                return true;
            }
        });

        tv_rol.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        return view;
    }

    class MouseRolOnGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if((int)-distanceY>0){
                cmd = "rol:2";
            }else {
                cmd = "rol:-2";
            }
            CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, handler);
            cmdClientSocket.work(cmd);
            return true;
        }
    }


    class MousePadOnGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            String cmd="clk:left+left";
            Log.d("dis","double");
            CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,handler);
            cmdClientSocket.work(cmd);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            String cmd="clk:left";
            Log.d("dis","double");
            CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port,handler);
            cmdClientSocket.work(cmd);

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            /*Log.d("disx:",""+distanceX);
            Log.d("disy:",""+distanceY);
            Log.d("dis","onSc");*/
            cmd="mov:"+(int)-distanceX+","+(int)-distanceY;//手势方向与鼠标控制方向相反，对值取反
            CmdClientSocket cmdClientSocket = new CmdClientSocket(ip, port, handler);
            cmdClientSocket.work(cmd);
            return true;
        }
    }
}


