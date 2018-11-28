package com.horen.service.ui.activity.bill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.util.NumberUtil;
import com.horen.service.R;
import com.horen.service.bean.BillMainBean;

/**
 * @author :ChenYangYi
 * @date :2018/09/03/13:56
 * @description :账单详情 已完成
 * @github :https://github.com/chenyy0708
 */
public class BillCompleteActivity extends BaseActivity {

    private TextView mTvMoneyTitle;
    private TextView mTvMoney;
    private TextView mTvOrderName;
    private TextView mTvTime;
    private BillMainBean.BillListBean bean;


    public static void startActivity(Context context, BillMainBean.BillListBean bean) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        intent.setClass(context, BillCompleteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_bill_complete_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvMoneyTitle = (TextView) findViewById(R.id.tv_money_title);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mTvOrderName = (TextView) findViewById(R.id.tv_order_name);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        bean = (BillMainBean.BillListBean) getIntent().getSerializableExtra("bean");
        // 根据不同类型，显示不同的布局
        switch (bean.getCost_type()) {
            case "3": // 耗材服务费
                showWhiteTitle("耗材服务费", R.color.white);
                break;
            case "2": // 产品服务费
                showWhiteTitle("产品服务费", R.color.white);
                break;
            case "4": // 维修费
                showWhiteTitle("维修费", R.color.white);
                break;
            case "5": // 运输费用
                showWhiteTitle("运输费", R.color.white);
                break;
            default:
                break;
        }
        getTitleBar().setBackgroundResource(R.color.white);
        setData();
    }

    private void setData() {
        // 总金额
        mTvMoney.setText(NumberUtil.formitNumber(Double.valueOf(bean.getTotal_ar())));
        // 完成时间
        mTvTime.setText(bean.getUdpater_time());
    }
}
