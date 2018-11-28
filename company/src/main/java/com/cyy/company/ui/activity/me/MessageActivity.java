package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.MessageLog;
import com.cyy.company.bean.PushMsg;
import com.cyy.company.ui.fragment.me.MsgLogisticsFragment;
import com.cyy.company.ui.fragment.me.MsgNoticeFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.horen.base.app.HRConstant;
import com.horen.base.base.AppManager;
import com.horen.base.base.BaseFragmentAdapter;
import com.horen.base.bean.TabEntity;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.rx.RxManager;
import com.horen.base.util.GsonUtil;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;
import com.horen.base.widget.HRViewPager;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 消息中心
 */
public class MessageActivity extends SupportActivity {
    private CommonTabLayout tabLayout;

    List<SupportFragment> mFragments = new ArrayList<>();

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    private HRTitle toolbar;

    /**
     * 存储初始化TabLayout所需要的数据
     */

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private HRViewPager mViewPager;
    private RxManager mRxManager;

    public static void startAction(Context context) {
        startAction(context, FIRST);
    }

    public static void startAction(Context context, int position) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.setClass(context, MessageActivity.class);
        context.startActivity(intent);
    }

    /**
     * 适用于极光推送
     *
     * @param context
     * @param extraJson
     */
    public static void startAction(Context context, String extraJson) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(HRConstant.PUSH_EXTRA, extraJson);
        intent.putExtra(HRConstant.IS_OPEN_PUSH, true);
        intent.setClass(context, MessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        AppManager.getAppManager().addActivity(this);
        mFragments.add(MsgLogisticsFragment.newInstance());
        mFragments.add(MsgNoticeFragment.newInstance());
        mRxManager = new RxManager();
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void initView() {
        toolbar = (HRTitle) findViewById(R.id.tool_bar);
        toolbar.bindActivity(this, R.color.white);
        tabLayout = (CommonTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (HRViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, new String[]{"物流类", "通知类"}));
        mViewPager.setOffscreenPageLimit(2);
        mTabEntities.add(new TabEntity("物流类", R.drawable.icon_arrow_right, R.drawable.icon_arrow_right));
        mTabEntities.add(new TabEntity("通知类", R.drawable.icon_arrow_right, R.drawable.icon_arrow_right));
        tabLayout.setTabData(mTabEntities);
        // 下游只需要物流类通知
        if (UserHelper.checkDownStream()) {
            tabLayout.setVisibility(View.GONE);
        }
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        int position = getIntent().getIntExtra("position", FIRST);
        mViewPager.setCurrentItem(position);
        // 获取推送额外信息
        boolean booleanExtra = getIntent().getBooleanExtra(HRConstant.IS_OPEN_PUSH, false);
        if (booleanExtra) { // 推送打开订单详情
            PushMsg pushMsg = GsonUtil.getGson().fromJson(getIntent().getStringExtra(HRConstant.PUSH_EXTRA), PushMsg.class);
            // 通知类还是物流类
            if (pushMsg.getType().equals("1")) { // 物流类
                mViewPager.setCurrentItem(FIRST);
                // 订单号打开订单
                // 订单详情
                OrderDetailActivity.startAction(this, pushMsg.getOrder_id()
                        , "", position, "消息中心");
            } else if (pushMsg.getType().equals("2")) { // 通知类
                mViewPager.setCurrentItem(SECOND);
                tabLayout.setCurrentTab(SECOND);
            }
        }
        geMessageLogList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        mRxManager.clear();
    }

    /**
     * 刷新未读消息数
     */
    @Subscriber(tag = EventBusCode.REFRESH_MSG_COUNT)
    private void refreshMsgCount(String tag) {
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
                        if (messageLog.getPageInfo().getMobile() != 0) { // 物流未读消息数量
                            tabLayout.showDot(0);
                        } else {
                            tabLayout.hideMsg(0);
                        }
                        if (messageLog.getPageInfo().getMsg() != 0) { // 通知未读消息数量
                            tabLayout.showDot(1);
                        } else {
                            tabLayout.hideMsg(1);
                        }
                    }

                    @Override
                    protected void onError(String message) {
                    }
                }));
    }
}
