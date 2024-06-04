package cwb.client.operator;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by PC_chen on 2019/3/29.
 */

public class CheckLocalDownloadFolder {

    public static String check(){
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/";
        File file = new File(dir);
        if (!file.exists()){
            boolean fl = file.mkdirs();
            if(fl){
                Log.d("load","文件创建成功");
            }
        }

        return dir;
    }
}
