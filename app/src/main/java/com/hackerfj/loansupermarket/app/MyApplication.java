package com.hackerfj.loansupermarket.app;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.app.constant.SharePref;
import com.hackerfj.loansupermarket.util.CrashLogUtil;
import com.hackerfj.loansupermarket.util.FrescoUtil;
import com.hackerfj.loansupermarket.util.ToastUtil;
import com.hackerfj.loansupermarket.util.log.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


/**
 * author:  付杰
 * date:    2017/9/13
 * description: 全局初始化操作
 *
 */

public class MyApplication extends Application {

    private OSS oss;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this, null, null, UMConfigure.DEVICE_TYPE_PHONE, null);
        initUmengInfo();
        //内存泄露检测
//        if (LeakCanary.isInAnalyzerProcess(this)) return;
//        refWatcher = LeakCanary.install(this);

        instance = this;
        //初始化SP存储
        SharePref.init(this);
        //初始化吐司
        ToastUtil.init(this);
        //初始化Log打印，true表示显示打印，false表示不显示打印
        LogUtil.init(true);
        //初始化崩溃输出
        CrashLogUtil.getInstance().init(this);
        //初始化Fresco
        FrescoUtil.getInstance().initializeFresco(this);
//        EventBusUtil.openIndex();//开启Index加速
        initOSSClient();

    }

    /**
     * refresh
     */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    public OSS getOss() {
        return oss;
    }

    /**
     * 初始化OSS
     */
    private void initOSSClient() {
        String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
        OSSCredentialProvider credentialProvider = new
                OSSPlainTextAKSKCredentialProvider("LTAIrWCBKY77P8Kz", "TrbFjCCZmE3TQXBQBs4uICgMktfrPz");
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(30 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(30 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
    }

    //初始化友盟
    private void initUmengInfo() {
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(instance, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }


    /**
     * 内存泄露检测
     */
//    private RefWatcher refWatcher;
//
//    public static RefWatcher getRefWatcher(Context context) {
//        MyApplication application = (MyApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }


}
