package com.hackerfj.loansupermarket.view.activity.jiz;

import android.view.View;
import android.widget.Toast;

import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.view.activity.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.OnClick;

public class JIZAboutActivity extends BaseActivity {
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_jizabout);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.fl_gx, R.id.iv_return})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.fl_gx:
                Toast.makeText(this, "已是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_return:
                finish();
                break;
        }
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
