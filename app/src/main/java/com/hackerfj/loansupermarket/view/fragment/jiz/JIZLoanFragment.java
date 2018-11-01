package com.hackerfj.loansupermarket.view.fragment.jiz;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.StringUtils;
import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.app.constant.Global;
import com.hackerfj.loansupermarket.model.entity.res.JIZAllRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZSangeRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZStingsRes;
import com.hackerfj.loansupermarket.util.StartActivityUtil;
import com.hackerfj.loansupermarket.util.network.Api;
import com.hackerfj.loansupermarket.util.network.RxHelper;
import com.hackerfj.loansupermarket.util.network.RxSubscriber;
import com.hackerfj.loansupermarket.view.activity.LoginActivity;
import com.hackerfj.loansupermarket.view.activity.jiz.JIZLoginActivity;
import com.hackerfj.loansupermarket.view.activity.jiz.JIZStingsActivity;
import com.hackerfj.loansupermarket.view.adapter.jiz.JIZLoanHomepageAdapter;
import com.hackerfj.loansupermarket.view.fragment.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class JIZLoanFragment extends BaseFragment {

    @BindView(R.id.rv_red_bag)
    RecyclerView rvLoanList;
    @BindView(R.id.tv_three)
    TextView tv_three;
    @BindView(R.id.tv_one)
    TextView tv_one;
    @BindView(R.id.tv_tow)
    TextView tv_tow;
    @BindView(R.id.tv_nologin)
    TextView tv_nologin;
    private JIZLoanHomepageAdapter JIZLoanHomepageAdapter;

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_jizload;
    }

    @Override
    protected void initView() {
        onResume();
        if (StringUtils.isEmpty(Global.getjizToken())){
            rvLoanList.setVisibility(View.GONE);
            tv_nologin.setVisibility(View.VISIBLE);
        }else {
            tv_nologin.setVisibility(View.GONE);
            getHomeaa();
        }
    }


    @OnClick({ R.id.ll_ed , R.id.iv_jiyb})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.ll_ed:
                if (StringUtils.isEmpty(Global.getjizToken())){
                    StartActivityUtil.start(mActivity , JIZLoginActivity.class);
                }else {
                    setpopup();
                }
                break;
            case R.id.iv_jiyb:
                if (StringUtils.isEmpty(Global.getjizToken())){
                    StartActivityUtil.start(mActivity , JIZLoginActivity.class);
                }else {
                    StartActivityUtil.start(mActivity , JIZStingsActivity.class);
                }
                break;
        }
    }

    public void setpopup(){
        View parent = ((ViewGroup)mActivity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(getContext(), R.layout.popup_jizedtext, null);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        final PopupWindow popWindow = new PopupWindow(popView,width,height);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);// 设置同意在外点击消失
        TextView tv_Determine = popView.findViewById(R.id.tv_Determine);
        tv_Determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
        EditText et_addmoeny = popView.findViewById(R.id.et_addmoeny);
        TextView tv_Sure = popView.findViewById(R.id.tv_Sure);
        tv_Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_addmoeny.getText().toString()!=null) {
                    Api.getDefault().jizaddmoney(Global.getjizId() , et_addmoeny.getText().toString())
                            .compose(RxHelper.handleResult())
                            .subscribe(new RxSubscriber<JIZStingsRes>(mActivity) {
                                @Override
                                protected void _onNext( JIZStingsRes s) {
                                    Toast.makeText(mActivity, "添加成功", Toast.LENGTH_SHORT).show();
                                    popWindow.dismiss();
                                    onResume();
                                }

                                @Override
                                protected void _onError(String msg) {
                                    Toast.makeText(mActivity, "添加失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(mActivity, "请填写金额", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent , Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 获取首页数据
     */
    private void getHomeaa(){
        Api.getDefault().jizgetHomeInfo(Global.getjizId() , 0)
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<List<JIZAllRes>>(mActivity) {
                    @Override
                    protected void _onNext(List<JIZAllRes> list) {
                        JIZLoanHomepageAdapter = new JIZLoanHomepageAdapter(getContext(), list);
                        rvLoanList.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvLoanList.setAdapter(JIZLoanHomepageAdapter);
                        rvLoanList.setNestedScrollingEnabled(false);
                        JIZLoanHomepageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    @Override
    protected void obtainData() {
        if (Global.getjizToken() != null) {
            allmoneyha();
        }else {
            tv_tow.setText("0");
            tv_three.setText("0");
            tv_one.setText("0");
        }
    }

    public void allmoneyha(){
        Api.getDefault().jizallmoney(Global.getjizId())
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<JIZSangeRes>(mActivity) {
                    @Override
                    protected void _onNext(JIZSangeRes s) {
                        tv_tow.setText(s.getIncome1());
                        tv_three.setText(s.getIncome2());
                        tv_one.setText(s.getIncome3());
                    }

                    @Override
                    protected void _onError(String msg) {

                    }
                });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getContext());
        if (StringUtils.isEmpty(Global.getjizToken())){
            rvLoanList.setVisibility(View.GONE);
            tv_nologin.setVisibility(View.VISIBLE);
        }else {
            tv_nologin.setVisibility(View.GONE);
            getHomeaa();
        }
        if (Global.getjizToken() != null) {
            allmoneyha();
        }else {
            tv_tow.setText("0");
            tv_three.setText("0");
            tv_one.setText("0");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }
}
