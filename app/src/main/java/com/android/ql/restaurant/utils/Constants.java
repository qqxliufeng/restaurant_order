package com.android.ql.restaurant.utils;

import android.os.Environment;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class Constants {
    public static final String BASE_IP = "http://canting.581vv.com/";

    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String APP_PATH = BASE_PATH + "/restaurant/";
    public static final String IMAGE_PATH = APP_PATH + "/img/";
    public static final String FILE_PROVIDE_PATH = "com.android.ql.restaurant.fileProvider";

    private static final String APP_TOKEN = "d5a131f90e76db03df4eff4154e56dbe";

    public static String md5Token() {
        return APP_TOKEN;
//        try {
//            if (!TextUtils.isEmpty(md5Token)) {
//                return md5Token;
//            }
//            MessageDigest digest = MessageDigest.getInstance("md5");
//            byte[] bs = digest.digest(APP_TOKEN.getBytes());
//            String hexString = "";
//            for (byte b : bs) {
//                int temp = b & 255;
//                if (temp < 16 && temp >= 0) {
//                    hexString = hexString + "0" + Integer.toHexString(temp);
//                } else {
//                    hexString = hexString + Integer.toHexString(temp);
//                }
//            }
//            return md5Token = hexString;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
    }
}
