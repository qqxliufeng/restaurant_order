package com.android.ql.restaurant.data;


import android.content.Context;
import android.text.TextUtils;

import com.android.ql.restaurant.utils.PreferenceUtils;

/**
 * Created by lf on 18.2.10.
 *
 * @author lf on 18.2.10
 */

public class UserInfo {



    public static final String USER_ID_FLAG = "user_id";

    public static final String LOGOUT_FLAG = "user_logout_flag";

    public static String loginToken = "NONE";

    public static void resetLoginSuccessDoActionToken() {
        loginToken = "NONE";
    }

    private UserInfo() {}

    private static UserInfo instance;

    public static UserInfo getInstance() {
        if (instance == null) {
            synchronized (UserInfo.class) {
                if (instance == null) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }


    private String user_id;
    private String user_phone;
    private String user_pass;
    private String user_code;
    private String user_nickname;
    private String user_pic;
    private String user_y_sum;
    private String user_w_sum;
    private String user_is_rank;
    private String user_is_vehicle;
    private String kephone;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getUser_y_sum() {
        return user_y_sum;
    }

    public void setUser_y_sum(String user_y_sum) {
        this.user_y_sum = user_y_sum;
    }

    public String getUser_w_sum() {
        return user_w_sum;
    }

    public void setUser_w_sum(String user_w_sum) {
        this.user_w_sum = user_w_sum;
    }

    public String getUser_is_rank() {
        return user_is_rank;
    }

    public void setUser_is_rank(String user_is_rank) {
        this.user_is_rank = user_is_rank;
    }

    public String getUser_is_vehicle() {
        return user_is_vehicle;
    }

    public void setUser_is_vehicle(String user_is_vehicle) {
        this.user_is_vehicle = user_is_vehicle;
    }

    public String getKephone() {
        return kephone;
    }

    public void setKephone(String kephone) {
        this.kephone = kephone;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(user_id);
    }

    public void loginOut() {
        user_id = null;
        instance = null;
    }

    public void exitApp() {
        if (instance != null) {
            instance = null;
        }
    }

    public static void clearUserCache(Context context) {
        PreferenceUtils.setPrefString(context, USER_ID_FLAG, "");
    }

    public static boolean isCacheUserId(Context context) {
        return PreferenceUtils.hasKey(context, USER_ID_FLAG) && !TextUtils.isEmpty(PreferenceUtils.getPrefString(context, USER_ID_FLAG, ""));
    }

    public static String getUserIdFromCache(Context context) {
        return PreferenceUtils.getPrefString(context, USER_ID_FLAG, "");
    }

}