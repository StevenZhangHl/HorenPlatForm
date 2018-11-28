package com.cyy.company.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyy.company.R;
import com.cyy.company.bean.AccountManager;
import com.cyy.company.ui.activity.me.EditAccountActivity;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/10/17/15:24
 * @description :账号管理
 * @github :https://github.com/chenyy0708
 */
public class AccountManagerAdapter extends BaseQuickAdapter<AccountManager.CustomerUsersBean.ListBean, BaseViewHolder> {

    private AccountListener accountListener;

    public AccountManagerAdapter(@Nullable List<AccountManager.CustomerUsersBean.ListBean> data, AccountListener accountListener) {
        super(R.layout.item_account_manager, data);
        this.accountListener = accountListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AccountManager.CustomerUsersBean.ListBean item) {
        final SwipeMenuLayout menuLayout = helper.getView(R.id.menu_layout);
        // 姓名
        helper.setText(R.id.tv_name, item.getUser_nickname());
        // 登陆账号
        helper.setText(R.id.tv_account, item.getUser_name());
        // 联系电话
        helper.setText(R.id.tv_phone, item.getUser_mobile());
        // 账号密码
        helper.setText(R.id.tv_pwd, "********");
        // 新增时间
        helper.setText(R.id.tv_time, item.getCreate_date());
        // 启用/停用
        if (item.getStatus().equals("1")) { // 停用
            helper.setText(R.id.tv_status, "停用");
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.color_EB4));
            // 侧滑菜单图标
            helper.setImageResource(R.id.iv_account_enable, R.drawable.icon_account_enable);
        } else if (item.getStatus().equals("2")) { //  启用
            helper.setText(R.id.tv_status, "启用");
            helper.setTextColor(R.id.tv_status, ContextCompat.getColor(mContext, R.color.base_text_color_light));
            helper.setImageResource(R.id.iv_account_enable, R.drawable.icon_account_disable);
        }

        // 编辑
        helper.setOnClickListener(R.id.iv_account_edit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuLayout.smoothClose();
                EditAccountActivity.startAction(mContext, item);
            }
        });
        // 启用停用
        helper.setOnClickListener(R.id.iv_account_enable, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuLayout.smoothClose();
                accountListener.onEnableListener(helper.getLayoutPosition());
            }
        });
        helper.setOnClickListener(R.id.item_content, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditAccountActivity.startAction(mContext, item);
            }
        });
    }

    /**
     * 账号启用停用监听
     */
    public interface AccountListener {
        void onEnableListener(int position);
    }
}
