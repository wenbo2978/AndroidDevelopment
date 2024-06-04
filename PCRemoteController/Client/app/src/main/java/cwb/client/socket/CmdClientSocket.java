package cwb.client.socket;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import cwb.client.data.NetFileData;
import cwb.client.operator.CheckLocalDownloadFolder;
import cwb.client.operator.ShowRemoteFileHandler;

/**
 * Created by PC_chen on 2019/3/1.
 */

public class CmdClientSocket {
    private String ip;
    private int port = 8019;

    private int time_out=10000;
    private Handler handler;
    private Socket socket;
    public static final String KEY_SERVER_ACK_MSG = "KEY_SERVER_ACK_MSG";
    private OutputStreamWriter writer;
    private BufferedReader bufferedReader;
    String cmdBody;
    String mess = "";
    String hot = "";
    String hot_cmd;
    public static boolean MISS_MESAGE = false;
    private SQLiteDatabase db;
    String fileName = "1111";


    public CmdClientSocket(String ip, int port, Handler handler) {
        this.ip = ip;
        this.port = port;
        this.handler = handler;
    }

    public CmdClientSocket() {
    }

    private void connect() throws IOException{
        InetSocketAddress address = new InetSocketAddress(ip,port);
        //Log.d("time","啦啦啦啦啦啦啦");
        socket=new Socket();
        socket.connect(address,time_out);
        socket.setSoTimeout(8000);

    }



    private void writeCmd(String cmd) throws IOException {
        BufferedOutputStream os=new BufferedOutputStream(socket.getOutputStream());
        writer = new OutputStreamWriter(os,"GB2312");
        writer.write("1\n");
        writer.write(cmd+"\n");
        writer.flush();
    }
    private void writeCmd(String []cmds) throws IOException {
        BufferedOutputStream os=new BufferedOutputStream(socket.getOutputStream());
        writer = new OutputStreamWriter(os,"GB2312");
        writer.write(cmds.length+"\n");
        for(int i=0;i<cmds.length;i++){
            writer.write(cmds[i]+"\n");
        }

        writer.flush();
    }


    private String readSocketMsg_1()throws IOException{

        InputStreamReader isr=new InputStreamReader(socket.getInputStream(),"GB2312");
        bufferedReader=new BufferedReader(isr);
        String s = bufferedReader.readLine();
        return s;
    }

    private String readSocketMsg_hot_1()throws IOException{

        InputStreamReader isr=new InputStreamReader(socket.getInputStream(),"GB2312");
        bufferedReader=new BufferedReader(isr);
        String s = bufferedReader.readLine();
        return s;
    }

    private ArrayList<String> readSocketLoad() throws IOException{
        ArrayList<String> list = new ArrayList<>();
        InputStreamReader isr=new InputStreamReader(socket.getInputStream(),"GB2312");
        bufferedReader=new BufferedReader(isr);
        int count = Integer.parseInt(bufferedReader.readLine());
        Log.d("load","总量"+count);
        String s= bufferedReader.readLine();
        String path = CheckLocalDownloadFolder.check() + fileName;
        File file = new File(path);
        OutputStream stream = new FileOutputStream(file);
        double i = 0;
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(stream));
        byte[] buffer;
        while (s!= null){
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            double res = i / count;
            //Log.d("load",res + "");
            bundle.putString("res",String.format("%.2f",res));
            i ++;
            list.add(s);
            buffer = s.getBytes();
            dos.write(buffer);
            s = bufferedReader.readLine();

        }
        dos.close();
        Log.d("load","下载完成");
        return list;
    }

    private ArrayList<NetFileData> readSocketMsg() throws IOException {
        ArrayList<NetFileData> msgList=new ArrayList<>();
        InputStreamReader isr=new InputStreamReader(socket.getInputStream(),"GB2312");
        bufferedReader=new BufferedReader(isr);
        String numStr = bufferedReader.readLine();
        if(numStr.equals("不支持的命令")||numStr.equals("不存在的目录")){
            Log.d("mis","111111");
            return null;
        }

        int linNum = Integer.parseInt(numStr);
        for (int i = 0; i <linNum ; i++) {
            String s = bufferedReader.readLine();
            NetFileData netFileData = new NetFileData(cmdBody,s);
            msgList.add(netFileData);
        }
        return msgList;
    }

    private void close() throws IOException {
        bufferedReader.close();
        writer.close();
        socket.close();
    }

    private void doCmdTask(String [] cmds){
        try{
            connect();

            writeCmd(cmds);

        }catch (IOException e){
            MISS_MESAGE = true;
        }
    }

    private void doCmdTask(String cmd){
        int a = cmd.indexOf(":");
        cmdBody = cmd.substring(a+1);
        String cmdHead = "";
        ArrayList<String> msgListload = new ArrayList<>();
        ArrayList<NetFileData> msgList=new ArrayList<>();
        try {
            connect();

            writeCmd(cmd);

            cmdHead = cmd.substring(0,cmd.indexOf(":"));
            if(cmdHead.equals("dir")){
                msgList = readSocketMsg();
                close();
            }else if(cmdHead.equals("dlf")){

                /*fileName = cmd.substring(cmd.lastIndexOf("/") + 1);
                msgListload = readSocketLoad();
                close();*/
            }
            else if (cmdHead.equals("opn")){
                mess = readSocketMsg_1();
                close();
            }else if(cmdHead.equalsIgnoreCase("key")){
                hot = readSocketMsg_hot_1();
                close();
            }else if(cmdHead.equalsIgnoreCase("movabs")||cmdHead.equalsIgnoreCase("clk")||cmdHead.equalsIgnoreCase("mov")||cmdHead.equalsIgnoreCase("rol")){
                //close();
            }else if(cmdHead.equalsIgnoreCase("cps")){

            }else if(cmdHead.equalsIgnoreCase("fs")){

            }else {

            }


        } catch (IOException e) {
            MISS_MESAGE = true;
            Log.d("timeout","111111" + MISS_MESAGE);

            e.printStackTrace();

        }
       if(cmdHead.equals("clk")||cmdHead.equals("mov")||cmdHead.equals("rol")||cmdHead.equals("key")||cmdHead.equals("movabs")||cmdHead.equals("cps")||cmdHead.equals("fs")){

       }else {
           Message message = handler.obtainMessage();
           Bundle bundle = new Bundle();
           if(MISS_MESAGE == true){
               bundle.putSerializable("TIME_OUT","连接超时");
           }else {
               if(cmdHead.equals("dir")){
                   bundle.putSerializable(KEY_SERVER_ACK_MSG,msgList);

               }else if(cmdHead.equalsIgnoreCase("dlf")){
                   bundle.putSerializable("load",msgListload);
                   bundle.putString("ip",ip);
                   bundle.putString("port",String.valueOf(port));
               }
               else if (cmdHead.equals("opn")){
                   bundle.putString("OPEN",mess);
               }else if (cmdHead.equalsIgnoreCase("key")){
                   bundle.putString("HOT",hot);
               }
           }

           message.setData(bundle);
           handler.sendMessage(message);
       }


    }

    public void work(final String [] cmds){
        MISS_MESAGE = false;



            new Thread(new Runnable() {
                @Override
                public void run() {
                    doCmdTask(cmds);
                }
            }).start();

    }



    public void work(final String cmd){
        MISS_MESAGE = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doCmdTask(cmd);
            }
        }).start();
    }
}
