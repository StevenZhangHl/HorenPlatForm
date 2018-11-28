package com.cyy.company.ui.fragment.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.MessageLog;
import com.cyy.company.enums.OrgType;
import com.cyy.company.ui.activity.me.AccountManagerActivity;
import com.cyy.company.ui.activity.me.AddressBookActivity;
import com.cyy.company.ui.activity.me.BillManagerActivity;
import com.cyy.company.ui.activity.me.MessageActivity;
import com.cyy.company.ui.activity.me.OrderListActivity;
import com.cyy.company.ui.activity.me.ReturnBoxListActivity;
import com.cyy.company.ui.activity.me.ReturnIntegralActivity;
import com.horen.base.base.BaseFragment;
import com.horen.base.bean.LoginBean;
import com.horen.base.constant.ARouterPath;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.ImageLoader;
import com.horen.base.util.UserHelper;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author :ChenYangYi
 * @date :2018/09/04/15:24
 * @description :个人中心
 * @github :https://github.com/chenyy0708
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mLlUser;
    private CircleImageView mCivUser;
    private TextView mTvSnack;
    private TextView mTvPhone;
    private LinearLayout mLlBill;
    private LinearLayout mLlAccount;
    private LinearLayout mLlAddress;
    private LinearLayout mLlMessage;
    private TextView mTvTitle;
    private ImageView mIvSettings;
    private LinearLayout mLlRentOrder;
    private LinearLayout mLlReturnOrder;

    private boolean isInit;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_company_me;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        isInit = true;
        mLlRentOrder = (LinearLayout) rootView.findViewById(R.id.ll_rent_order);
        mLlReturnOrder = (LinearLayout) rootView.findViewById(R.id.ll_return_order);
        mLlUser = (LinearLayout) rootView.findViewById(R.id.ll_user);
        mCivUser = (CircleImageView) rootView.findViewById(R.id.civ_user);
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        mIvSettings = (ImageView) rootView.findViewById(R.id.iv_settings);
        mTvSnack = (TextView) rootView.findViewById(R.id.tv_snack);
        mTvPhone = (TextView) rootView.findViewById(R.id.tv_phone);
        mLlBill = (LinearLayout) rootView.findViewById(R.id.ll_bill);
        mLlAccount = (LinearLayout) rootView.findViewById(R.id.ll_account);
        mLlAddress = (LinearLayout) rootView.findViewById(R.id.ll_address);
        mLlMessage = (LinearLayout) rootView.findViewById(R.id.ll_message);
        mLlUser.setOnClickListener(this);
        mIvSettings.setOnClickListener(this);
        mLlMessage.setOnClickListener(this);
        mLlAddress.setOnClickListener(this);
        mLlRentOrder.setOnClickListener(this);
        mLlReturnOrder.setOnClickListener(this);
        mLlAccount.setOnClickListener(this);
        mLlBill.setOnClickListener(this);

        initUserInfo();
        // 上游显示订单，下游显示还箱点
        if (UserHelper.getUserInfo().getLoginInfo().getFlag_data().equals(OrgType.ONE.getPosition())) { // 上游
            rootView.findViewById(R.id.ll_return_box_integral).setVisibility(View.GONE);
            rootView.findViewById(R.id.ll_order).setVisibility(View.VISIBLE);
            // 只有管理员才显示账号管理
            if (UserHelper.getUserInfo().getLoginInfo().getFlag_orgadmin().equals("1")) { // 管理员
                rootView.findViewById(R.id.ll_account).setVisibility(View.VISIBLE);
            } else { // 操作员
                rootView.findViewById(R.id.ll_account).setVisibility(View.GONE);
            }
        } else if (UserHelper.getUserInfo().getLoginInfo().getFlag_data().equals(OrgType.TWO.getPosition())) { // 下游
            rootView.findViewById(R.id.ll_order).setVisibility(View.GONE);
            rootView.findViewById(R.id.ll_return_box_integral).setOnClickListener(this);
            rootView.findViewById(R.id.ll_return_box_order).setOnClickListener(this);
            rootView.findViewById(R.id.ll_return_box_integral).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.ll_return_box_order).setVisibility(View.VISIBLE);
            // 隐藏账单管理、账号管理
            rootView.findViewById(R.id.ll_bill).setVisibility(View.GONE);
            rootView.findViewById(R.id.ll_account).setVisibility(View.GONE);
        }
        geMessageLogList();
    }

    private void initUserInfo() {
        LoginBean.LoginInfoBean infoBean = UserHelper.getUserInfo().getLoginInfo();
        ImageLoader.load(_mActivity, UserHelper.getUserInfo().getLoginInfo().getPhoto(), mCivUser);
        mTvSnack.setText(infoBean.getUser_nickname());
        mTvPhone.setText(infoBean.getUser_mobile());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_user) {
            // 模拟用户端
            ARouter.getInstance().build(ARouterPath.USER_INFO).navigation();
        } else if (view.getId() == R.id.iv_settings) { // 设置页面
            ARouter.getInstance().build(ARouterPath.USER_SETTING).navigation();
        } else if (view.getId() == R.id.ll_message) { // 消息中心
            MessageActivity.startAction(_mActivity);
        } else if (view.getId() == R.id.ll_address) { // 地址簿
            AddressBookActivity.startAction(_mActivity);
        } else if (view.getId() == R.id.ll_rent_order) { // 租箱订单
            OrderListActivity.startAction(_mActivity, 0);
        } else if (view.getId() == R.id.ll_return_order) { // 还箱订单
            OrderListActivity.startAction(_mActivity, 1);
        } else if (view.getId() == R.id.ll_account) { // 账号管理
            AccountManagerActivity.startAction(_mActivity);
        } else if (view.getId() == R.id.ll_bill) { // 账单管理
            BillManagerActivity.startAction(_mActivity);
        } else if (view.getId() == R.id.ll_return_box_integral) { // 还箱点
            ReturnIntegralActivity.startAction(_mActivity);
        } else if (view.getId() == R.id.ll_return_box_order) { // 还箱订单
            startActivity(ReturnBoxListActivity.class);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && isInit) // 获取最新的未读消息数
            geMessageLogList();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isInit && !isHidden())// 获取最新的未读消息数
            geMessageLogList();
    }

    /**
     * 获取最新的未读消息数
     */
    private void geMessageLogList() {
        mRxManager.add(ApiCompany.getInstance()
                .geMessageLogList(CompanyParams.getCompanyId())
                .compose(RxHelper.<MessageLog>getResult())
                .subscribeWith(new BaseObserver<MessageLog>() {
                    @Override
                    protected void onSuccess(MessageLog messageLog) {
                        // 未读消息
                        int messageCount = 0;
                        messageCount += messageLog.getPageInfo().getMobile();
                        messageCount += messageLog.getPageInfo().getMsg();
                        if (messageCount != 0)
                            setText(R.id.tv_message_count, "未读: " + messageCount + "条");
                        // 还箱点
                        setText(R.id.tv_return_box_integral, messageLog.getPageInfo().getCount() + "点");

                    }

                    @Override
                    protected void onError(String message) {

                    }
                }));
    }
}
