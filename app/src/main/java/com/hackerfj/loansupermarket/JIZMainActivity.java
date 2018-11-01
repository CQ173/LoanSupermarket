package com.hackerfj.loansupermarket;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.StringUtils;

import com.hackerfj.loansupermarket.app.constant.Global;
import com.hackerfj.loansupermarket.util.StartActivityUtil;
import com.hackerfj.loansupermarket.view.activity.base.BaseActivity;
import com.hackerfj.loansupermarket.view.activity.jiz.JIZLoginActivity;
import com.hackerfj.loansupermarket.view.activity.jiz.JIZSetUpActivity;
import com.hackerfj.loansupermarket.view.fragment.jiz.JIZHomeFragment;
import com.hackerfj.loansupermarket.view.fragment.jiz.JIZLoanFragment;
import com.hackerfj.loansupermarket.view.fragment.jiz.JIZMeFragment;
import com.hackerfj.loansupermarket.view.widget.BanViewPager;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class JIZMainActivity extends BaseActivity implements JIZSetUpActivity.Refreshfragment{

    private String token;
    @BindView(R.id.vp_main)
    BanViewPager vpMain;
    @BindView(R.id.rb_weixin_weixin)
    LinearLayout rb_weixin_weixin;
    @BindView(R.id.rb_weixin_contact)
    LinearLayout rb_weixin_contact;
    @BindView(R.id.rb_weixin_find)
    LinearLayout rb_weixin_find;
    @BindView(R.id.iv_a)
    ImageView iv_a;
    @BindView(R.id.tv_a)
    TextView tv_a;
    @BindView(R.id.iv_b)
    ImageView iv_b;
    @BindView(R.id.tv_b)
    TextView tv_b;
    @BindView(R.id.iv_c)
    ImageView iv_c;
    @BindView(R.id.tv_c)
    TextView tv_c;
    List<Fragment> fragmentList=new ArrayList<>();

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_jizmain);
    }

    @Override
    protected void initView() {
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions( Manifest.permission.GET_ACCOUNTS
                                , Manifest.permission.INTERNET
                                , Manifest.permission.SEND_SMS)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {

                    }
                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });
        JIZSetUpActivity setUpActivity = new JIZSetUpActivity();
        //实例化碎片
        JIZLoanFragment loanFragment = new JIZLoanFragment();
        JIZHomeFragment homeFragment = new JIZHomeFragment();
        JIZMeFragment meFragment = new JIZMeFragment();
        fragmentList.add(loanFragment);
        fragmentList.add(homeFragment);
        fragmentList.add(meFragment);
        setUpActivity.setClickListener( this );
        vpMain.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    @OnClick({R.id.rb_weixin_weixin , R.id.rb_weixin_contact , R.id.rb_weixin_find})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.rb_weixin_weixin:
                vpMain.setCurrentItem(0 , true);
                iv_a.setImageResource(R.drawable.icon_home_page);
                tv_a.setTextColor(getResources().getColor(R.color.color_FE6B6B));
                iv_b.setImageResource(R.drawable.icon_loan_a);
                tv_b.setTextColor(getResources().getColor(R.color.color_797978));
                iv_c.setImageResource(R.drawable.icon_my_a);
                tv_c.setTextColor(getResources().getColor(R.color.color_797978));
                break;
            case R.id.rb_weixin_contact:
                if (StringUtils.isEmpty(token)){
                    StartActivityUtil.start( getContext() , JIZLoginActivity.class);
                }else {
                    vpMain.setCurrentItem(1, false);
                    iv_a.setImageResource(R.drawable.icon_home_page_a);
                    tv_a.setTextColor(getResources().getColor(R.color.color_797978));
                    iv_b.setImageResource(R.drawable.icon_loan);
                    tv_b.setTextColor(getResources().getColor(R.color.color_FE6B6B));
                    iv_c.setImageResource(R.drawable.icon_my_a);
                    tv_c.setTextColor(getResources().getColor(R.color.color_797978));
                }
                break;
            case R.id.rb_weixin_find:
                if (StringUtils.isEmpty(token)){
                    StartActivityUtil.start( getContext() , JIZLoginActivity.class);
                }else {
                    vpMain.setCurrentItem(2, false);
                    iv_a.setImageResource(R.drawable.icon_home_page_a);
                    tv_a.setTextColor(getResources().getColor(R.color.color_797978));
                    iv_b.setImageResource(R.drawable.icon_loan_a);
                    tv_b.setTextColor(getResources().getColor(R.color.color_797978));
                    iv_c.setImageResource(R.drawable.icon_my);
                    tv_c.setTextColor(getResources().getColor(R.color.color_FE6B6B));
                }
                break;
        }
    }

    @Override
    public void onexitClick() {
        onResume();
    }


    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=fragmentList.get(position) ;
            return fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initEvent() {
        initEvents();
    }

    private void initEvents() {
        token = Global.getjizToken();

    }

    @Override
    protected void onResume() {
        super.onResume();
        token = Global.getjizToken();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 双击返回键退出程序
     */
    // 保存点击的时间
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
