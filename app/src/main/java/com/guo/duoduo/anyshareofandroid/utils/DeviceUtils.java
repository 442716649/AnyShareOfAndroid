package com.guo.duoduo.anyshareofandroid.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.media.ExifInterface;
import android.os.Build;


/**
 * Created by 郭攀峰 on 2015/9/15.
 */
public class DeviceUtils
{

    public static boolean isHTC()
    {
        boolean ishtc = false;
        if (Build.MODEL.contains("htc") || Build.MODEL.contains("HTC"))
        {
            ishtc = true;
        }
        return ishtc;
    }
    /**
     * 是否需要检测外置sd卡路径 (android4.4版本不能读写)
     *
     * @return
     */
    public static boolean needCheckExtSDCard() {
        int sdk_int = android.os.Build.VERSION.SDK_INT;
        if (sdk_int == 19 || sdk_int == 20)
            return true;
        return false;
    }
    /**
     * 获取外置SD卡路径
     *
     * @return 应该就一条记录或空
     */
    public static String getExtSDCardPath() {
        List<String> lResult = new ArrayList();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard")) {
                    String[] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory()) {
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        if (lResult.size() == 0)
            return null;
        else
            return lResult.get(0);
    }

    public static String convertByte(long size)
    {
        DecimalFormat df = new DecimalFormat("###.##");
        float f;
        if (size < 1024 * 1024)
        {
            f = (float) ((float) size / (float) 1024);
            return (df.format(new Float(f).doubleValue()) + "KB");
        }
        else
        {
            f = (float) ((float) size / (float) (1024 * 1024));
            return (df.format(new Float(f).doubleValue()) + "MB");
        }
    }

    public static String getFileName(String path)
    {
        return new File(path).getName();
    }

    /** get file size M K B G etc... */
    public static String getFileSize(long fileS)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024)
        {
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576)
        {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        }
        else if (fileS < 1073741824)
        {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        }
        else
        {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static String removeSpecial(String before)
    {
        String after = null;
        after = before.replace(" ", "").replace("\u003f", "").replace("\u002F", "")
                .replace("\u003A", "").replace("\u002A", "").replace("\u007C", "")
                .replace("\u003E", "").replace("\u003C", "").replace("\\", "")
                .replace("\"", "");
        return after;
    }

    /**
     * 对字符串进行md5加密
     *
     */
    public static String md5Encrypt(String srcStr)
    {
        try
        {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bt = srcStr.getBytes();
            md5.update(bt);
            String destStr = toHex(md5.digest());
            return destStr;
        }
        catch (NoSuchAlgorithmException e)
        {
            return null;
        }
    }

    public static String toHex(byte[] bytes)
    {
        StringBuffer buf = new StringBuffer("");
        String tmp = null;

        for (int i = 0; i < bytes.length; i++)
        {
            tmp = (Integer.toHexString(bytes[i] & 0xFF));
            if (tmp.length() == 1)
            {
                buf.append("0");
            }
            buf.append(tmp);
        }
        return buf.toString();
    }

    public static int getExifOrientation(String filepath)
    {
        int degree = 0;
        ExifInterface exif = null;
        try
        {
            exif = new ExifInterface(filepath);
        }
        catch (IOException ex)
        {
        }
        if (exif != null)
        {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1)
            {
                switch (orientation)
                {
                    case ExifInterface.ORIENTATION_ROTATE_90 :
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180 :
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270 :
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

}
