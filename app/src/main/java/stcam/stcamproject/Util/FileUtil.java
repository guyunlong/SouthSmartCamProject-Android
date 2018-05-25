package stcam.stcamproject.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * �����Բ����ļ������ʵ�SD��һЩ����
 * @author gyl
 *
 */
public class FileUtil {
    private final static String TAG = "FileUtil";


    public static boolean checkSD() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
    /**
     * ��ȡSD����Ŀ¼·��
     * @return ��ʾ��Ŀ¼·���ַ�����ĩβ������/������"/sdcard"),��SD��δ��װ���򷵻�null
     */
    public static String getSDRootPath() {
        if(!checkSD()) {
            return null;
        } else {
            return Environment.getExternalStorageDirectory().getPath();
        }
    }

    /**
     * ����Ŀ¼
     * @param dirPath Ŀ¼·����ĩβ������/������"/sdcard"��
     * @return true - �����ɹ���false - ����ʧ��
     */
    public static boolean makeDir(String dirPath) {
        boolean isSuccessful = true;
        File dir = new File(dirPath);
        if(!dir.exists()) {
            isSuccessful = dir.mkdirs();
        }
        return isSuccessful;
    }

    /**
     * ��jpg��ʽ����Bitmap
     * @param filePath �������ļ�·������·������չ����
     * @param bitmap
     * @return true - ����ɹ��� false - ����ʧ��
     */
    public static boolean saveBitmapInJPG(String filePath, Bitmap bitmap) {
        File f = new File(filePath);
        if(f.exists()) {
            return true;
        }
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static class CompratorByLastModified implements Comparator<File> {
        public int compare(File f1, File f2) {
            long diff = f1.lastModified() - f2.lastModified();
            if (diff > 0) {
                return -1;
            } else if (diff == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * ��ȡͼƬ����ͼ
     * @param path ͼƬ������·��
     * @param width ����ͼ����λ������
     * @param height ����ͼ�ߣ���λ������
     * @return ͼƬ����ͼ��Bitmap���������쳣������null
     */
    public static Bitmap getThumbnail(String path, int width, int height) {
        Bitmap bitmap = null;
        Bitmap thumbnail = null;

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            bitmap = BitmapFactory.decodeFile(path, options);
            thumbnail = ThumbnailUtils.extractThumbnail(bitmap, width, height);
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return thumbnail;
    }

    /**
     * ɾ��ָ��·���µ������ļ�
     * @param path ָ����·��
     */
    public static void delDirectFiles(String path) {
        File file = new File(path);
        if(!file.exists()) { return; }
        File[] files = file.listFiles();
        for(int i = 0; i < files.length; i++) {
            files[i].delete();
        }
    }

    /**
     * ɾ��ָ���ļ�
     * @param path ָ����·��
     */
    public static void delFiles(String path) {
        File file = new File(path);
        if(!file.exists()) { return; }
        file.delete();
    }

    public static boolean isFileExist(String path) {
        File file = new File(path);
        if(file.exists()) {

            return true;
        }
        else{
            return false;
        }

    }
    public static String pathSnapShot(){
        String rootPath = FileUtil.getSDRootPath();
        if (rootPath != null){
            String stPath = rootPath+"/STCam";
            boolean ret = FileUtil.makeDir(stPath);
            if (ret){
                String snapPath = stPath+"/snapshot/";
                ret = FileUtil.makeDir(snapPath);
                if (ret){
                    return snapPath;
                }
            }

        }
        return null;

    }
    public static String pathRecord(){
        String rootPath = FileUtil.getSDRootPath();
        if (rootPath != null){
            String stPath = rootPath+"/STCam";
            boolean ret = FileUtil.makeDir(stPath);
            if (ret){
                String recordPath = stPath+"/record/";
                ret = FileUtil.makeDir(recordPath);
                if (ret){
                    return recordPath;
                }
            }

        }
        return null;
    }
    public static String generatePathSnapShotFileName(String sn){
        String snapPath = FileUtil.pathSnapShot();
        if (snapPath!=null){
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date = sDateFormat.format(new java.util.Date());
            String fileName = snapPath+sn+date+".jpg";
            return fileName;
        }
        return null;
    }
    public static String generatePathRecordFileName(String sn){
        String recordPath = FileUtil.pathRecord();
        if (recordPath!=null){
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date = sDateFormat.format(new java.util.Date());
            String fileName = recordPath+sn+date+".mp4";
            return fileName;
        }
        return null;
    }


    /**
     * �����չ�����õ�ͼƬ��ʽ���ļ�
     * @param fName  �ļ���
     * @return
     */
    public static boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // ��ȡ��չ��
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     * ���ļ��л�ĳ���豸��ͼƬ��Դ
     * @return
     */
    public static List<String> getImagePathFromPath(String filePath,String SN) {
        // ͼƬ�б�
        List<String> imagePathList = new ArrayList<String>();
        // �õ�sd����image�ļ��е�·��   File.separator(/)

        // �õ���·���ļ��������е��ļ�
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        // �����е��ļ�����ArrayList��,����������ͼƬ��ʽ���ļ�
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (!file.getPath().contains(SN)){
                continue;
            }
            if (FileUtil.checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        // ���صõ���ͼƬ�б�
        return imagePathList;
    }




}
