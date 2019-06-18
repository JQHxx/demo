package wenran.com.baselibrary.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import wenran.com.baselibrary.base.BaseApplication;
import wenran.com.baselibrary.callback.IStandardCallBack;

/**
 * Created by Crowhine on 2019/2/18
 *
 * @author Crowhine
 */
public class FileUtil {

    /**
     * url转换成file
     */
    public void downloadImage(final String url, final IStandardCallBack iStandardCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File imageFile = null;
                try {
                    FutureTarget<File> target = Glide.with(BaseApplication.app)
                            .asFile()
                            .load(url)
                            .submit();
                    imageFile = target.get();
                    // 首先保存图片
                    File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();

                    File appDir = new File(pictureFolder, "Beauty");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);
                    copyFile(imageFile.getPath(), destFile.getPath());
                    iStandardCallBack.success(destFile);
                } catch (Exception e) {
                    iStandardCallBack.failure(null, e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // 保存图片到手机
    public void download(final String url, final IStandardCallBack iStandardCallBack) {

        new AsyncTask<Void, Integer, File>() {

            @Override
            protected File doInBackground(Void... params) {
                File file = null;
                try {
                    FutureTarget<File> future = Glide
                            .with(BaseApplication.app)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    file = future.get();

                    // 首先保存图片
                    File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();

                    File appDir = new File(pictureFolder, "Beauty");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);

//                    FileUtil.copy(file, destFile);
                    copyFile(file.getPath(), destFile.getPath());
//                    // 最后通知图库更新
                    BaseApplication.app.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));
                    iStandardCallBack.success(destFile);

                } catch (Exception e) {
                    iStandardCallBack.success(e.getMessage());
                }
                return file;
            }

            @Override
            protected void onPostExecute(File file) {

//                Toast.makeText(MyApplication.app, "saved in Pictures/GankBeauty", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
    }

    /**
     * 复制单个文件
     *
     * @param oldPath$Name String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @param newPath$Name String 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
     * @return <code>true</code> if and only if the file was copied;
     * <code>false</code> otherwise
     */
    public boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            if (!oldFile.exists()) {
                Log.e("--Method--", "copyFile:  oldFile not exist.");
                return false;
            } else if (!oldFile.isFile()) {
                Log.e("--Method--", "copyFile:  oldFile not file.");
                return false;
            } else if (!oldFile.canRead()) {
                Log.e("--Method--", "copyFile:  oldFile cannot read.");
                return false;
            }

        /* 如果不需要打log，可以使用下面的语句
        if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
            return false;
        }
        */

            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);    //读入原文件
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
