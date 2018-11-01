package com.hackerfj.loansupermarket.view.fragment.jiz;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.app.constant.Global;
import com.hackerfj.loansupermarket.util.StartActivityUtil;
import com.hackerfj.loansupermarket.view.activity.jiz.JIZSetUpActivity;
import com.hackerfj.loansupermarket.view.fragment.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

public class JIZMeFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.tv_username)
    TextView tv_username;

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_jizme;
    }

    @Override
    protected void initView() {
        tv_username.setText(Global.getUsername());
    }


    @OnClick({R.id.ll_me_recharge , R.id.ll_setup })
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_me_recharge:
                setpopup();
                break;
            case R.id.ll_setup:
                StartActivityUtil.start(mActivity , JIZSetUpActivity.class);
                break;
        }
    }

    public void setpopup(){
        View parent = ((ViewGroup)mActivity.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(getContext(), R.layout.popup_conversation, null);
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
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent , Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }

}
