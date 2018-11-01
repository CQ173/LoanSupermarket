package com.hackerfj.loansupermarket.view.fragment.jiz;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.app.constant.Global;
import com.hackerfj.loansupermarket.model.entity.res.JIZTunjiRes;
import com.hackerfj.loansupermarket.util.network.Api;
import com.hackerfj.loansupermarket.util.network.RxHelper;
import com.hackerfj.loansupermarket.util.network.RxSubscriber;
import com.hackerfj.loansupermarket.view.adapter.jiz.JIZFragmentAdapter;
import com.hackerfj.loansupermarket.view.fragment.base.BaseFragment;
import com.hackerfj.loansupermarket.view.widget.PieChartView;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;

public class JIZHomeFragment extends BaseFragment {

    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.pieChartView)
    PieChartView pieChartView;
    private OneFragment fragment1;
    private TwoFragment fragment2;
    private ArrayList<Fragment> fragmentsList = new ArrayList<>();
    //添加tablayout的内容
    private ArrayList<String> title = new ArrayList<>();

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_jizhome;
    }

    @Override
    protected void initView() {
        Log.i("getid" , Global.getjizId()+"");
        onResume();
        alladtjt();
        //为tablayout添加内容
        title.add("支出");
        title.add("收入");

        //tablayout中的标题下划线的颜色
        tabLayout.setSelectedTabIndicatorColor((Color.parseColor("#71CDF5")));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 40, 40);
            }
        });
        fragment1 = new OneFragment();
        fragment2 = new TwoFragment();

        fragmentsList.add(fragment1);
        fragmentsList.add(fragment2);

        JIZFragmentAdapter myPagerAdapter = new JIZFragmentAdapter(getFragmentManager(), fragmentsList, title);
        tabLayout.setTabsFromPagerAdapter(myPagerAdapter);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void obtainData() {

    }

    public void alladtjt(){
        Api.getDefault().jizalltjt(1)
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<JIZTunjiRes>(mActivity) {
                    @Override
                    protected void _onNext(JIZTunjiRes s) {
                        PieChartView.PieItemBean a = new PieChartView.PieItemBean("餐饮", s.getA1());
                        PieChartView.PieItemBean b = new PieChartView.PieItemBean("旅行", s.getA2());
                        PieChartView.PieItemBean c = new PieChartView.PieItemBean("话费", s.getA3());
                        PieChartView.PieItemBean d = new PieChartView.PieItemBean("娱乐", s.getA4());
                        PieChartView.PieItemBean e = new PieChartView.PieItemBean("住房", s.getA5());
                        PieChartView.PieItemBean f = new PieChartView.PieItemBean("交通", s.getA6());
                        PieChartView.PieItemBean g = new PieChartView.PieItemBean("运动", s.getA7());
                        PieChartView.PieItemBean h = new PieChartView.PieItemBean("维修", s.getA8());
                        PieChartView.PieItemBean[] items = new PieChartView.PieItemBean[]{ a, b, c, d , e, f, g, h};
                        pieChartView.setPieItems(items);
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
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

}
