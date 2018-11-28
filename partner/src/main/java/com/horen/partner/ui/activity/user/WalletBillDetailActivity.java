package com.horen.partner.ui.activity.user;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.horen.base.base.BaseActivity;
import com.horen.base.util.NumberUtil;
import com.horen.partner.R;
import com.horen.partner.bean.WalletBillBean;

public class WalletBillDetailActivity extends BaseActivity {

    private TextView tv_company_name;
    private TextView tv_total_money;
    private TextView tv_apply_date;
    private TextView tv_remaik;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_bill_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showWhiteTitle("资金明细", R.color.white);
        getTitleBar().setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_total_money = (TextView) findViewById(R.id.tv_total_money);
        tv_apply_date = (TextView) findViewById(R.id.tv_apply_date);
        tv_remaik = (TextView) findViewById(R.id.tv_remaik);
        setViewData();
    }

    private void setViewData() {
        WalletBillBean.ListBean info = (WalletBillBean.ListBean) getIntent().getSerializableExtra("walletBillDetail");
        if (info != null) {
            String typeTitle = "";
            if ("A".equals(info.getType())) {
                typeTitle = "提现申请-申请中";
                tv_total_money.setTextColor(ContextCompat.getColor(this, R.color.color_333));
            }
            if ("B".equals(info.getType())) {
                typeTitle = info.getType_title();
            }
            if ("C".equals(info.getType())) {
                tv_total_money.setTextColor(ContextCompat.getColor(this, R.color.color_333));
                typeTitle = "提现申请-处理完成";
            }
            tv_company_name.setText(typeTitle);
            tv_total_money.setText("¥ " + NumberUtil.formitNumberTwoPoint(info.getAmount()));
            tv_apply_date.setText(info.getDate());
            tv_remaik.setText("备注：" + info.getRemak());
        }
    }

}
