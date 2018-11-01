package com.hackerfj.loansupermarket.view.activity.jiz;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.app.constant.Global;
import com.hackerfj.loansupermarket.model.entity.res.JIZStingsRes;
import com.hackerfj.loansupermarket.util.network.Api;
import com.hackerfj.loansupermarket.util.network.RxHelper;
import com.hackerfj.loansupermarket.util.network.RxSubscriber;
import com.hackerfj.loansupermarket.view.activity.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

public class JIZStingsActivity extends BaseActivity {

    public static int TYPE_LOGIN = 1;
    public static int TYPE_REGISTER = 2;
    private int type = 1;

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.ll_register)
    LinearLayout llRegister;

    @BindView(R.id.tv_title_z)
    TextView tv_title_z;
    @BindView(R.id.et_money_z)
    EditText et_money_z;
    @BindView(R.id.ll_list_z_Restaurant)
    LinearLayout ll_list_z_Restaurant;
    @BindView(R.id.tv_Restaurant) //餐饮
    TextView tv_Restaurant;
    @BindView(R.id.ll_list_z_Tourism)
    LinearLayout ll_list_z_Tourism;
    @BindView(R.id.tv_Tourism) //旅游
    TextView tv_Tourism;
    @BindView(R.id.ll_list_z_Telephone)
    LinearLayout ll_list_z_Telephone;
    @BindView(R.id.tv_Telephone) //话费
    TextView tv_Telephone;
    @BindView(R.id.ll_list_z_entertainment)
    LinearLayout ll_list_z_entertainment;
    @BindView(R.id.tv_entertainment) //娱乐
    TextView tv_entertainment;
    @BindView(R.id.ll_list_z_housing)
    LinearLayout ll_list_z_housing;
    @BindView(R.id.tv_housing) //住房
    TextView tv_housing;
    @BindView(R.id.ll_list_z_traffic)
    LinearLayout ll_list_z_traffic;
    @BindView(R.id.tv_traffic) //交通
    TextView tv_traffic;
    @BindView(R.id.ll_list_z_motion)
    LinearLayout ll_list_z_motion;
    @BindView(R.id.tv_motion) //运动
    TextView tv_motion;
    @BindView(R.id.ll_list_z_Repair)
    LinearLayout ll_list_z_Repair;
    @BindView(R.id.tv_Repair) //维修
    TextView tv_Repair;

    @BindView(R.id.tv_title_s) //收入
    TextView tv_title_s;
    @BindView(R.id.et_money_s) //收入金额
    EditText et_money_s;
    @BindView(R.id.ll_list_s_wages) //工资
    LinearLayout ll_list_s_wages;
    @BindView(R.id.tv_wages)
    TextView tv_wages;
    @BindView(R.id.ll_list_s_Part_time) //兼职
    LinearLayout ll_list_s_Part_time;
    @BindView(R.id.tv_Part_time)
    TextView tv_Part_time;
    @BindView(R.id.ll_list_s_bonus) //奖金
    LinearLayout ll_list_s_bonus;
    @BindView(R.id.tv_bonus)
    TextView tv_bonus;
    @BindView(R.id.ll_list_s_envelopes) //红包
    LinearLayout ll_list_s_envelopes;
    @BindView(R.id.tv_envelopes)
    TextView tv_envelopes;

    private int code = 1;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_jizstings);
    }

    @Override
    protected void initView() {
        llRegister.setVisibility(View.GONE);

    }


    @OnClick({R.id.tv_login, R.id.tv_register , R.id.iv_return , R.id.ll_list_z_Restaurant , R.id.ll_list_z_Tourism , R.id.ll_list_z_Telephone ,
            R.id.ll_list_z_entertainment , R.id.ll_list_z_housing , R.id.ll_list_z_traffic , R.id.ll_list_z_motion , R.id.ll_list_z_Repair ,
            R.id.ll_list_s_wages , R.id.ll_list_s_Part_time , R.id.ll_list_s_bonus , R.id.ll_list_s_envelopes , R.id.iv_Sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                type = TYPE_LOGIN;
                tvLogin.setBackgroundResource(R.drawable.btn_circle_gray);
                tvRegister.setBackgroundResource(R.drawable.btn_circle_golden);
                tvLogin.setTextColor(getResourceColor(R.color.colorGray8b));
                tvRegister.setTextColor(getResourceColor(R.color.colorWhite));
                llLogin.setVisibility(View.VISIBLE);
                llRegister.setVisibility(View.GONE);
                break;
            case R.id.tv_register:
                type = TYPE_REGISTER;
                tvLogin.setBackgroundResource(R.drawable.btn_circle_golden);
                tvRegister.setBackgroundResource(R.drawable.btn_circle_gray);
                tvLogin.setTextColor(getResourceColor(R.color.colorWhite));
                tvRegister.setTextColor(getResourceColor(R.color.colorGray8b));
                llLogin.setVisibility(View.GONE);
                llRegister.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_return:
                finish();
                break;
            case R.id.ll_list_z_Restaurant:
                tv_title_z.setText(tv_Restaurant.getText());
                code = 1;
                break;
            case R.id.ll_list_z_Tourism:
                tv_title_z.setText(tv_Tourism.getText());
                code = 2;
                break;
            case R.id.ll_list_z_Telephone:
                tv_title_z.setText(tv_Telephone.getText());
                code = 3;
                break;
            case R.id.ll_list_z_entertainment:
                tv_title_z.setText(tv_entertainment.getText());
                code = 4;
                break;
            case R.id.ll_list_z_housing:
                tv_title_z.setText(tv_housing.getText());
                code = 5;
                break;
            case R.id.ll_list_z_traffic:
                tv_title_z.setText(tv_traffic.getText());
                code = 6;
                break;
            case R.id.ll_list_z_motion:
                tv_title_z.setText(tv_motion.getText());
                code = 7;
                break;
            case R.id.ll_list_z_Repair:
                tv_title_z.setText(tv_Repair.getText());
                code = 8;
                break;
            case R.id.ll_list_s_wages:
                tv_title_s.setText(tv_wages.getText());
                code = 9;
                break;
            case R.id.ll_list_s_Part_time:
                tv_title_s.setText(tv_Part_time.getText());
                code = 10;
                break;
            case R.id.ll_list_s_bonus:
                tv_title_s.setText(tv_bonus.getText());
                code = 11;
                break;
            case R.id.ll_list_s_envelopes:
                tv_title_s.setText(tv_envelopes.getText());
                code = 12;
                break;
            case R.id.iv_Sure:
                if (type == 1 && null != et_money_z.getText()) {
                    Log.i("上传", Global.getjizId() +type + "--" + code + "--" + et_money_z.getText());
                    Api.getDefault().jizaddall( Global.getjizId(), type , code , et_money_z.getText().toString() )
                            .compose(RxHelper.handleResult())
                            .subscribe(new RxSubscriber<JIZStingsRes>(this) {
                                @Override
                                protected void _onNext(JIZStingsRes s) {
                                    Toast.makeText(JIZStingsActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                @Override
                                protected void _onError(String msg) {
                                    Toast.makeText(JIZStingsActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else if (type == 2 && null != et_money_s.getText()){
                    Log.i("上传", Global.getjizId() + type + "--" + code + "--" + et_money_s.getText());
                    Api.getDefault().jizaddall( Global.getjizId() , type  , code , et_money_s.getText().toString() )
                            .compose(RxHelper.handleResult())
                            .subscribe(new RxSubscriber<JIZStingsRes>(this) {
                                @Override
                                protected void _onNext(JIZStingsRes s) {
                                    Toast.makeText(JIZStingsActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                @Override
                                protected void _onError(String msg) {
                                    Toast.makeText(JIZStingsActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                }
                            });
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
