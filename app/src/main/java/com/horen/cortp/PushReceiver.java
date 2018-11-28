package com.horen.cortp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.cyy.company.ui.activity.CompanyMainActivity;
import com.cyy.company.ui.activity.me.MessageActivity;
import com.horen.base.app.HRConstant;
import com.horen.base.base.AppManager;
import com.horen.base.util.LogUtils;
import com.horen.base.util.SPUtils;
import com.horen.base.util.UserHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class PushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                SPUtils.setSharedStringData(context, HRConstant.REGISTRATION_ID, regId);
                LogUtils.d("[registrationID] : " + regId);
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                LogUtils.d("[PushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                LogUtils.d("[PushReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                LogUtils.d("[PushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                LogUtils.d("[PushReceiver] 用户点击打开了通知");
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                LogUtils.d("[PushReceiver]" + extras);
                jumpActivity(context, extras);
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                LogUtils.d("[PushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            }
        } catch (Exception e) {

        }

    }

    /**
     * 根据服务器推送的额外的信息，打开对应页面，暂时是消息中心页面
     *
     * @param extras
     */
    private void jumpActivity(Context context, String extras) {
        if (UserHelper.isLogin()) {
            //判断app进程是否存活,标识是是否打开主Activity
            if (AppManager.getAppManager().isOpenActivity(CompanyMainActivity.class)) {
                //如果存活的话，就直接启动消息Activity
                MessageActivity.startAction(context, extras);
            } else {
                //如果app进程已经被杀死，先重新启动app SplashActivity
                //传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入参数跳转到MessageActivity中去了
                Intent launchIntent = context.getPackageManager().
                        getLaunchIntentForPackage(context.getPackageName());
                launchIntent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                launchIntent.putExtra(HRConstant.IS_OPEN_PUSH, true);
                launchIntent.putExtra(HRConstant.PUSH_EXTRA, extras);
                context.startActivity(launchIntent);
            }
        }
    }

    /**
     * 打印所有的 intent extra 数据
     *
     * @param bundle
     * @return
     */
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtils.i("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtils.e("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    /**
     * send msg to MessageActivity
     *
     * @param bundle
     */
    private void processCustomMessage(Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        //发送消息
//        EventBus.getDefault().post(new MsgEvent().setType(MsgEvent.SHOW_MESSAGE).setObj(bundle));
    }
}
