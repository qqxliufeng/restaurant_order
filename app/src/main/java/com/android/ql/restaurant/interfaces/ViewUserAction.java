package com.android.ql.restaurant.interfaces;


import com.android.ql.lf.carapp.action.IViewUserAction;
import com.android.ql.restaurant.application.MyApplication;
import com.android.ql.restaurant.data.TicketBean;
import com.android.ql.restaurant.data.UserInfo;
import com.android.ql.restaurant.utils.PreferenceUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lf on 18.2.8.
 *
 * @author lf on 18.2.8
 */

public class ViewUserAction implements IViewUserAction {

    @Override
    public boolean onLogin(@NotNull JSONObject result) {
        try {
            JSONObject userInfoResult = result.optJSONObject("data");
            UserInfo.getInstance().setUser_id(userInfoResult.optString("user_id"));
            UserInfo.getInstance().setUser_nickname(userInfoResult.optString("user_nickname"));
            UserInfo.getInstance().setUser_phone(userInfoResult.optString("user_phone"));
            UserInfo.getInstance().setUser_pic(userInfoResult.optString("user_pic"));
            UserInfo.getInstance().setUser_rank(userInfoResult.optString("user_rank"));
            UserInfo.getInstance().setUser_as(userInfoResult.optString("user_as"));
            UserInfo.getInstance().setUser_type(userInfoResult.optString("user_type"));
            JSONObject ticketInfoResult = result.optJSONObject("ticket");
            if (ticketInfoResult != null) {
                UserInfo.getInstance().getTicketBean().setTicket_shop(ticketInfoResult.optString("ticket_shop"));
                UserInfo.getInstance().getTicketBean().setTicket_dates(ticketInfoResult.optString("ticket_dates"));
                UserInfo.getInstance().getTicketBean().setTicket_table(ticketInfoResult.optString("ticket_table"));
                UserInfo.getInstance().getTicketBean().setTicket_id(ticketInfoResult.optLong("ticket_id"));
                UserInfo.getInstance().getTicketBean().setTicket_letter(ticketInfoResult.optString("ticket_letter"));
                UserInfo.getInstance().getTicketBean().setTicket_count(ticketInfoResult.optInt("ticket_count"));
            }
            JPushInterface.setAlias(MyApplication.getInstance(), 0, UserInfo.getInstance().getUser_as());
            PreferenceUtils.setPrefString(MyApplication.application, UserInfo.USER_ID_FLAG, UserInfo.getInstance().getUser_id());
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public boolean onLogout() {
        UserInfo.getInstance().loginOut();
        UserInfo.clearUserCache(MyApplication.application);
        return true;
    }

    @Override
    public void onRegister(@NotNull JSONObject result) {
    }

    @Override
    public void onForgetPassword(@NotNull JSONObject result) {
    }

    @Override
    public void onResetPassword(@NotNull JSONObject result) {
    }

    @Override
    public boolean modifyInfoForName(@NotNull String name) {
        try {
            UserInfo.getInstance().setUser_nickname(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean modifyInfoForPic(@NotNull String result) {
        try {
            UserInfo.getInstance().setUser_pic(result);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
