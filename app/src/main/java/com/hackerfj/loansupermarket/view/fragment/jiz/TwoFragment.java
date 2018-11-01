package com.hackerfj.loansupermarket.view.fragment.jiz;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hackerfj.loansupermarket.R;
import com.hackerfj.loansupermarket.app.constant.Global;
import com.hackerfj.loansupermarket.model.entity.res.GetHomebannerRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZAllRes;
import com.hackerfj.loansupermarket.util.network.Api;
import com.hackerfj.loansupermarket.util.network.RxHelper;
import com.hackerfj.loansupermarket.util.network.RxSubscriber;
import com.hackerfj.loansupermarket.view.adapter.HomeFragAdapter;
import com.hackerfj.loansupermarket.view.adapter.jiz.JIZHomeFragAdapter;
import com.hackerfj.loansupermarket.view.fragment.base.BaseFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;

public class TwoFragment extends BaseFragment {
    @BindView(R.id.rv_view)
    RecyclerView rvLoanList;

    private JIZHomeFragAdapter homeFragAdapter;

    @Override
    protected int setContentLayout() {
        return R.layout.fragment_jiztwo;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void obtainData() {
        //设置RecycleView显示的布局
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvLoanList.setLayoutManager(manager);
        Api.getDefault().jizgetHomeInfo(Global.getjizId() , 2 )
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscriber<List<JIZAllRes>>(mActivity) {
                    @Override
                    protected void _onNext(List<JIZAllRes> list) {
                        homeFragAdapter = new JIZHomeFragAdapter(getContext(), list);
                        rvLoanList.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvLoanList.setAdapter(homeFragAdapter);
                        rvLoanList.setNestedScrollingEnabled(false);
                        homeFragAdapter.notifyDataSetChanged();
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
}
