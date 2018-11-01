package com.hackerfj.loansupermarket.view.activity.jiz;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.app.constant.Global;
import com.hackerfj.loansupermarket.app.constant.SharePref;
import com.hackerfj.loansupermarket.model.entity.res.GetHomePageRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZTuiRes;
import com.hackerfj.loansupermarket.util.DataCleanManager;
import com.hackerfj.loansupermarket.util.StartActivityUtil;
import com.hackerfj.loansupermarket.util.network.Api;
import com.hackerfj.loansupermarket.util.network.RxHelper;
import com.hackerfj.loansupermarket.util.network.RxSubscriber;
import com.hackerfj.loansupermarket.view.activity.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class JIZSetUpActivity extends BaseActivity {

    //------****** 缓存相关****----------
    private final int CLEAN_SUC=1001;
    private final int CLEAN_FAIL=1002;

    @BindView(R.id.ll_Exit_logon)
    LinearLayout ll_Exit_logon;
    @BindView(R.id.tvCache)
    TextView tvCache;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_jizsetup);
    }

    @Override
    protected void initView() {
        File file = this.getExternalCacheDir();
        try {
            tvCache.setText(DataCleanManager.getCacheSize(file));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @OnClick({R.id.ll_Exit_logon , R.id.ll_feedback , R.id.ll_about , R.id.iv_return , R.id.ll_cache})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.ll_Exit_logon:
                Exitlogon();
                break;
            case R.id.ll_feedback:
                StartActivityUtil.start(this , JIZFeedbackActivity.class);
                break;
            case R.id.ll_about:
                StartActivityUtil.start(this , JIZAboutActivity.class);
                break;
            case R.id.iv_return:
                finish();
                break;
            case R.id.ll_cache:
                DataCleanManager.cleanInternalCache(this);
                DataCleanManager.cleanExternalCache(this);
                Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

        public void Exitlogon(){
            Log.i("gettoken" , Global.getjizToken()+"");
            if (Global.getjizToken()!= null){
            Api.getDefault().jizexitlogon()
                    .compose(RxHelper.handleResult())
                    .subscribe(new RxSubscriber<JIZTuiRes>(this) {
                        @Override
                        protected void _onNext(JIZTuiRes s) {
                            SharePref.getInstance().clear();
                            Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
                            ll_Exit_logon.setVisibility(View.GONE);
                            refreshfragment.onexitClick( );
                            finish();
                        }
                        @Override
                        protected void _onError(String msg) {
                            Toast.makeText(getContext() , "退出失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

//    传递到mainactivity
    public interface Refreshfragment{
        void onexitClick();
    }

    private Refreshfragment refreshfragment;

    public void setClickListener(Refreshfragment listener){
        this.refreshfragment = listener;
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
