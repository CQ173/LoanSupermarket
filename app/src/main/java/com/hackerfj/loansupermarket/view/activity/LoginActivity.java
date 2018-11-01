package com.hackerfj.loansupermarket.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hackerfj.loansupermarket.MainActivity;
import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.app.constant.Global;
import com.hackerfj.loansupermarket.model.entity.res.Param;
import com.hackerfj.loansupermarket.model.entity.res.UserinfoRes;
import com.hackerfj.loansupermarket.util.Constants;
import com.hackerfj.loansupermarket.util.CountdownUtil;
import com.hackerfj.loansupermarket.util.MD5Util;
import com.hackerfj.loansupermarket.util.ToastUtil;
import com.hackerfj.loansupermarket.util.network.Api;
import com.hackerfj.loansupermarket.util.network.RxHelper;
import com.hackerfj.loansupermarket.util.network.RxSubscriber;
import com.hackerfj.loansupermarket.view.activity.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private static String channelNamee;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_getcode)
    TextView tv_getcode;
    @BindView(R.id.tv_login)
    TextView tv_login;

    private String phone;
    private String phonecode;
    private String channelNumber;
    //获取验证码
    private String sign = "^^YdDc^^+~!@#$$#@!~.{(||)}";
    //登录
    private String login = "**yDdC**yUCq\"|{(.NIUBI.)}|\"";

    private CountdownUtil countdownUtil;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        countdownUtil = new CountdownUtil( tv_getcode , 60000 , 1000);
        /**
         * 在需要的地方调用上述方法
         */
        channelNumber = getAppMetaData(getBaseContext(), "UMENG_CHANNEL");//获取app当前的渠道号
        Log.i("channelNumber:", channelNumber);
    }
    @OnClick({R.id.tv_getcode , R.id.tv_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_getcode:
                if ("".equals(et_phone.getText().toString())){
                    ToastUtil.show("请输入手机号码!");
                }else if (et_phone.getText().toString().trim().length() != 11) {
                    ToastUtil.show("账号格式错误!");
                }else {
                    getcode();
                }
                break;
            case R.id.tv_login:
                userlogin(et_phone.getText().toString() , MD5Util.md5Password(et_code.getText().toString() + phone+login) ,et_code.getText().toString());
                break;
        }
    }

    /**
     * 获取验证码
     */
    public void getcode(){
        phone = et_phone.getText().toString();
//        Log.i("userloog--" ,"sign--"+ MD5Util.md5Password(phone+sign) );
        Api.getDefault().getiphonecode(phone, MD5Util.md5Password(phone+sign))
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<Param>(this) {
                    @Override
                    protected void _onNext(Param s) {
//                        Log.i("code:", s.getCode());
                        countdownUtil.start();
                    }

                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    /**
     * 登录
     * @param mobile
     * @param sign
     * @param verifyCode
     */
    public void userlogin(String mobile , String sign  ,String verifyCode){
        Api.getDefault().reloadlogin(mobile, sign , verifyCode ,channelNumber)
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<UserinfoRes>(this) {
                    @Override
                    protected void _onNext(UserinfoRes list) {
                        //Token
                        Global.saveToken(list.getToken());
                        //用户id
                        Global.saveID(list.getId());
                        //username
                        Global.saveUsername(list.getUsername());
//                        editor.commit();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        ToastUtil.show("登录成功!");

                        finish();
                    }

                    @Override
                    protected void _onError(String msg) {
                        ToastUtil.show("登录失败!");
                    }
                });
    }
    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelNumber = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode) {
            case Constants.CODE_LOGPWD:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
