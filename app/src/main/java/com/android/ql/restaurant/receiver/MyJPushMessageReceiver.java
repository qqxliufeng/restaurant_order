package com.android.ql.restaurant.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.ql.restaurant.data.PostTicketBean;
import com.android.ql.restaurant.data.UserInfo;
import com.android.ql.restaurant.ui.activity.MainActivity;
import com.android.ql.restaurant.ui.activity.SplashActivity;
import com.android.ql.restaurant.utils.RxBus;

import cn.jpush.android.api.JPushInterface;

public class MyJPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (UserInfo.getInstance().isLogin()) {
                context.startActivity(new Intent(context, MainActivity.class));
            } else {
                Intent pendingIntent = new Intent(context, SplashActivity.class);//将要跳转的界面
                context.startActivity(pendingIntent);
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//                builder.setAutoCancel(true);//点击后消失
//                builder.setSmallIcon(R.mipmap.ic_launcher);//设置通知栏消息标题的头像
//                builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
//                builder.setContentTitle(context.getResources().getString(R.string.app_name));
//                builder.setContentText("您的排隊有了新變化，請");
//                builder.setAutoCancel(true);
//                PendingIntent intentPend = PendingIntent.getActivity(context, 0, pendingIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//                builder.setContentIntent(intentPend);
//                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                manager.notify(0, builder.build());
            }
        }
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            RxBus.getDefault().post(new PostTicketBean());
        }
    }
}
