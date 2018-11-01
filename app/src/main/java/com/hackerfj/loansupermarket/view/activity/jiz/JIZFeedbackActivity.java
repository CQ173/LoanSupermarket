package com.hackerfj.loansupermarket.view.activity.jiz;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.view.activity.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

public class JIZFeedbackActivity extends BaseActivity {

    @BindView(R.id.ex_a)
    EditText ex_a;
    @BindView(R.id.ex_b)
    EditText ex_b;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_jizfeedback);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.iv_return , R.id.iv_Sure})
    public void onclick(View view){
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_Sure:
                if (ex_a.getText().toString().length() < 1){
                    Toast.makeText(this, "请输入手机号或邮箱", Toast.LENGTH_SHORT).show();
                }else  if (ex_b.getText().toString().length() < 1){
                    Toast.makeText(this, "请输入建议或意见", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
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
