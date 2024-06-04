package cwb.client.data;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by PC_chen on 2019/3/1.
 */

public class NetFileData {
    private long fileSize = 0;// 文件长度应该long型数据，否则大于2GB的文件大小无法表达
    private String fileName = "$error";// 文件名称，不含目录信息,默认值用于表示文件出错
    private String filePath = ".\\";// 该文件对象所处的目录，默认值为当前相对目录
    private String fileSizeStr = "0";// 文件的大小，用字符串表示，能智能地选择B、KB、MB、GB来表达
    private int isDirectory = 0;// true为文件夹，false为文件
    private String fileModifiedDate = "1970-01-01 00:00:00";// 文件最近修改日期，默认值为1970年基准时间
    private String fileInfo;
    DecimalFormat df = new DecimalFormat("#.00");
    String [] files = new String[]{};

    public NetFileData(String filePath, String fileInfo) {
        this.filePath = filePath;
        this.fileInfo = fileInfo;
        this.files = fileInfo.split(">");
        this.fileName = files[0];
        this.isDirectory = Integer.parseInt(files[3]);
        fileModifiedDate = files[1];

        if(Long.parseLong(files[2])/1024 > 0){
            if(Long.parseLong(files[2])/(1024*1024)>0){
                if(Long.parseLong(files[2])/(1024*1024*1024)>0){
                    /*this.fileSize = Long.parseLong(files[2])/(1024*1024*1024);
                    this.fileSize = Long.parseLong(files[2])/(1024*1024*1024);*/
                    this.fileSizeStr = df.format(Double.parseDouble(files[2])/(1024*1024*1024))+"GB";
                }else {
                    this.fileSize = Long.parseLong(files[2])/(1024*1024);
                    this.fileSizeStr = df.format(Double.parseDouble(files[2])/(1024*1024))+"MB";
                }
            }else{
                this.fileSize = Long.parseLong(files[2])/1024;
                this.fileSizeStr = df.format(Double.parseDouble(files[2])/1024)+"KB";
            }
        }else {
            this.fileSize = Long.parseLong(files[2]);
            this.fileSizeStr = df.format(Double.parseDouble(files[2]))+"B";
        }

    }



    public long getFileSize() {

        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSizeStr() {
        return fileSizeStr;
    }

    public void setFileSizeStr(String fileSizeStr) {
        this.fileSizeStr = fileSizeStr;
    }

    public int isDirectory() {


        return isDirectory;
    }

    public void setDirectory(int directory) {
        isDirectory = directory;
    }

    public String getFileModifiedDate() {

        return fileModifiedDate;
    }

    public void setFileModifiedDate(String fileModifiedDate) {
        this.fileModifiedDate = fileModifiedDate;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }
}
