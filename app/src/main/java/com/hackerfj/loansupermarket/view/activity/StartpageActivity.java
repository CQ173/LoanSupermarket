package com.hackerfj.loansupermarket.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hackerfj.loansupermarket.JIZMainActivity;
import com.hackerfj.loansupermarket.MainActivity;
import com.hackerfj.loansupermarket.app.constant.Global;
import com.hackerfj.loansupermarket.app.constant.SharePref;
import com.hackerfj.loansupermarket.model.entity.res.JudgeRes;
import com.hackerfj.loansupermarket.util.StartActivityUtil;
import com.hackerfj.loansupermarket.util.network.Api;
import com.hackerfj.loansupermarket.util.network.RxHelper;
import com.hackerfj.loansupermarket.util.network.RxSubscriber;
import com.umeng.analytics.MobclickAgent;

public class StartpageActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gohome();
    }

    public void gohome(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        judge();
    }

    public void judge(){
        Api.getDefault().getjudge()
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<JudgeRes>(this) {
                    @Override
                    protected void _onNext(JudgeRes s) {
                        //在onCreate方法中写入
                        SharedPreferences sp=getSharedPreferences("elts", Context.MODE_PRIVATE);
                        int count=sp.getInt("elts", 0);
                        if(count==0){
                        //第一次进入
                            //Token
                            Global.saveUid(s.getUid());
                            SharedPreferences.Editor et=sp.edit();
                            et.putInt("elts",1);
                            et.commit();
                        }else{
                            if (s.getUid() != Global.getUid()){
                                SharePref.getInstance().clear();
                                Global.saveUid(s.getUid());
                            }
                        }

                        if ( 1 == s.getUid()){
                            StartActivityUtil.start( StartpageActivity.this , JIZMainActivity.class);
                            finish();
                        }else {
                            StartActivityUtil.start(StartpageActivity.this , MainActivity.class);
                            finish();
                        }
                    }
                    @Override
                    protected void _onError(String msg) {

                    }
                });
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
