package mapsoft.com.myretrofit;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Even_Kwok on 2016/9/16.
 */
public class FileHelper {

    private Context context;
    private String SDPATH;//SD卡路径
    private String FILESPATH;//文件路径

    public FileHelper(Context context) {
        this.context = context;
        this.SDPATH = Environment.getExternalStorageDirectory().getPath() + "/";
        this.FILESPATH = this.context.getFilesDir().getPath() + "/";
    }

    /**
     * 判断SDCard是否存在？是否可以进行读写
     */
    public boolean SDCardState() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {//表示SDCard存在并且可以读写
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取SDCard文件路径
     */
    public String SDCardPath() {
        if (SDCardState()) {//如果SDCard存在并且可以读写
            SDPATH = Environment.getExternalStorageDirectory().getPath() + "/";
            return SDPATH;
        } else {
            return null;
        }
    }

    public String getPlayDir() {
        if (SDPATH != null) {
            return SDPATH + "mapsoft/play/";
        } else {
            return null;
        }
    }

    /**
     * 删除一个文件
     *
     * @param file
     * @return
     */
    public boolean delFile(File file) {
        if (file.isDirectory())
            return false;
        return file.delete();
    }

    /**
     * 获取图软更新目录
     *
     * @return
     */
    public String getUpdateDir() {
        if (SDPATH != null) {
            return SDPATH + "mapsoft/update/";
        } else {
            return null;
        }
    }

    /**
     * 删除一个目录中文件（可以是非空目录）
     *
     * @param dir
     */
    public boolean delDir(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                delDir(file);//递归
            }
        }
        return true;
    }
}