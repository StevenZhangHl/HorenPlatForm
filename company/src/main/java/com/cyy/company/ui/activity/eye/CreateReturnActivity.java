package com.cyy.company.ui.activity.eye;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.cyy.company.R;
import com.cyy.company.ui.fragment.order.DownReturnBoxFragment;
import com.cyy.company.ui.fragment.order.ReturnBoxFragment;
import com.horen.base.app.HRConstant;
import com.horen.base.base.AppManager;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.HRTitle;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author :ChenYangYi
 * @date :2018/10/30/11:24
 * @description :创建还箱
 * @github :https://github.com/chenyy0708
 */
public class CreateReturnActivity extends SupportActivity {
    private HRTitle mToolBar;
    private FrameLayout mFlContainer;

    public static void startAction(Context context, String type) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.setClass(context, CreateReturnActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_return);
        AppManager.getAppManager().addActivity(this);
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mToolBar.bindActivity(this, R.color.white);
        if (UserHelper.checkDownStream()) {
            mToolBar.setIsClose(false);
        }
        if (getIntent().getStringExtra("type").equals(HRConstant.HUNDRED_KILOMETERS)) {
            mToolBar.setIsClose(false);
        } else {
            mToolBar.setIsClose(true);
        }
        mFlContainer = (FrameLayout) findViewById(R.id.fl_container);
        loadMultipleRootFragment(
                R.id.fl_container,
                0,
                UserHelper.checkDownStream() ? DownReturnBoxFragment.newInstance(getIntent().getStringExtra("type")) : //  下游还箱和上游还箱页面逻辑不同
                        ReturnBoxFragment.newInstance(getIntent().getStringExtra("type"))
        );
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }
}
