package com.cyy.company.widget;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cyy.company.R;
import com.cyy.company.bean.OrderProducts;
import com.cyy.company.listener.ProductSelectListener;
import com.cyy.company.ui.fragment.order.SelectRtpFragment;
import com.horen.base.base.BaseFAdapter;
import com.horen.base.widget.HRBottomTab;

import java.util.ArrayList;
import java.util.List;

import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 * @author :ChenYangYi
 * @date :2018/10/22/09:31
 * @description :下单选择物品Dialog
 * @github :https://github.com/chenyy0708
 */
public class OrderSelectRtpDialog extends BaseBottomDialog {

    private ViewPager mViewPager;
    private TextView mTvName;

    private ProductSelectListener listener;
    private View mViewDiver;

    public static OrderSelectRtpDialog newInstance() {
        return new OrderSelectRtpDialog();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_order_select_rtp;
    }

    @Override
    public void bindView(View dialogView) {
        OrderProducts orderProducts = (OrderProducts) getArguments().getSerializable("products");
        // 分类为箱子和耗材
        List<OrderProducts.PdListBean> mBoxData = new ArrayList<>();
        List<OrderProducts.PdListBean> mSuppliesData = new ArrayList<>();
        for (OrderProducts.PdListBean pdListBean : orderProducts.getPdList()) {
            if (pdListBean.getFlag_product().equals("1")) {
                mBoxData.add(pdListBean);
            } else if (pdListBean.getFlag_product().equals("2")) {
                mSuppliesData.add(pdListBean);
            }
        }

        List<Fragment> mFragments = new ArrayList<>();
        SelectRtpFragment boxFragment = SelectRtpFragment.newInstance(new OrderProducts(mBoxData));
        boxFragment.setListener(new ProductSelectListener() {
            @Override
            public void onSelectRtp(OrderProducts.PdListBean bean) {
                listener.onSelectRtp(bean);
                dismiss();
            }
        });
        boxFragment.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy -= dy;
                if (totalDy < 0) {
                    mViewDiver.setVisibility(View.VISIBLE);
                } else {
                    mViewDiver.setVisibility(View.INVISIBLE);
                }
            }
        });
        mFragments.add(boxFragment);
        SelectRtpFragment suppliesFragment = SelectRtpFragment.newInstance(new OrderProducts(mSuppliesData));
        suppliesFragment.setListener(new ProductSelectListener() {
            @Override
            public void onSelectRtp(OrderProducts.PdListBean bean) {
                listener.onSelectRtp(bean);
                dismiss();
            }
        });
        suppliesFragment.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy -= dy;
                if (totalDy < 0) {
                    mViewDiver.setVisibility(View.VISIBLE);
                } else {
                    mViewDiver.setVisibility(View.INVISIBLE);
                }
            }
        });
        mFragments.add(suppliesFragment);
        mTvName = (TextView) dialogView.findViewById(R.id.tv_name);
        mViewPager = (ViewPager) dialogView.findViewById(R.id.view_pager);
        mViewDiver = (View) dialogView.findViewById(R.id.view_diver);
        HRBottomTab bottomTab = (HRBottomTab) dialogView.findViewById(R.id.bottom_tab);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(new BaseFAdapter(getChildFragmentManager(), mFragments, new String[]{"箱子", "耗材"}));
        //设置默认选中tab
        bottomTab.bindViewPgaer(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mTvName.setText("箱子");
                } else if (position == 1) {
                    mTvName.setText("耗材");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public float getDimAmount() {
        return 0.4f;
    }

    public void setListener(ProductSelectListener listener) {
        this.listener = listener;
    }
}
