package com.zhide.app.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.zhide.app.model.VersionModel;


/**
 * Created by hasee on 2017/12/19.
 */

public class ApplicationHolder {

    private Context mContext;
    private IWXAPI msgApi;
    private static final Handler mainHandler = new Handler();
    public static ApplicationHolder instance = null;

    private ApplicationHolder() {

    }

    public static ApplicationHolder getInstance() {
        if (instance == null) {
            instance = new ApplicationHolder();
        }
        return instance;
    }

    public IWXAPI getMsgApi() {
        Log.d("xyc", "getMsgApi: msgApi2="+msgApi);
        return msgApi;
    }

    public void setMsgApi(IWXAPI msgApi) {
        this.msgApi = msgApi;
    }

    public void setAppContext(Context context) {
        if (context == null) {
            Log.e("ApplicationHolder", "try to set null application, return");
            return;
        }
        mContext = context;
    }

    public Context getAppContext() {
        if (mContext == null) {
            Log.e("ApplicationHolder",
                    "Global ApplicationContext is null, Please call ApplicationHolder.setmApplication(application) at the onCreate() method of Activity and Application");
        }
        return mContext;
    }

    public VersionModel getVersionModel() {
        // 获取packagemanager的实例
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        VersionModel versionModel = new VersionModel();
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            versionModel.setVersionCode(packInfo.versionCode);
            versionModel.setVersionName(packInfo.versionName);
            versionModel.setLastUpdateTime(packInfo.lastUpdateTime);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionModel;
    }

    // 获取主线程
    public void postMainRunnable(Runnable runnable) {
        mainHandler.post(runnable);
    }

}
