package cwb.client.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import cwb.client.data.NetFileData;
import cwb.client.operator.CheckLocalDownloadFolder;

/**
 * Created by PC_chen on 2019/3/30.
 */

public class Download extends Thread {

    long filesize;
    private OutputStreamWriter writer;
    private String ip;
    private int port = 8080;
    Handler handler;
    String filepath;
    private BufferedReader bufferedReader;
    private String cmd;
    private String fileName;
    public Download(String ip, Handler handler,String filepath,String fileName) {
        this.ip = ip;
        this.handler = handler;
        this.filepath = filepath;
        this.fileName = fileName;
    }

    @Override
    public void run() {

        Socket socket;

        try {
            Message message = new Message();
            Bundle bundle = message.getData();
            sleep(3000);
            socket = new Socket(ip, port);
            BufferedOutputStream os=new BufferedOutputStream(socket.getOutputStream());
            writer = new OutputStreamWriter(os,"GB2312");

            writer.write(filepath+"\n");
            writer.flush();
            InputStream in = socket.getInputStream();
            String path = CheckLocalDownloadFolder.check() + fileName;
            File file = new File(path);
            OutputStream stream = new FileOutputStream(file);
            DataInputStream dis = new DataInputStream(
                    new BufferedInputStream(in));
            DataOutputStream dos = new DataOutputStream(
                    new BufferedOutputStream(stream));
            filesize = dis.readLong() -1;
            byte[] buffer = new byte[1024];

            int downLoadFileSize = 0;

            while (true) {
                int read = 0;
                if (dis != null) {
                    read = dis.read(buffer);
                    downLoadFileSize += read;
                }
                if (read == -1) {
                    break;
                }
                Message msg = Message.obtain();
                bundle.putLong("j", 100*downLoadFileSize/filesize);
                bundle.putString("w", "wait");
                msg.setData(bundle);
                handler.sendMessage(msg);
                dos.write(buffer, 0, read);
            }
            in.close();
            dos.close();
            socket.close();
            Log.d("load","下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
