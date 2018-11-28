package com.horen.user.ui.activity.accout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.library.SuperButton;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.horen.base.app.HRConstant;
import com.horen.base.base.AppManager;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.bean.LoginBean;
import com.horen.base.bean.UploadBean;
import com.horen.base.bean.UserLevelBean;
import com.horen.base.constant.ARouterPath;
import com.horen.base.constant.EventBusCode;
import com.horen.base.constant.MsgEvent;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.ImageLoader;
import com.horen.base.util.PhotoPickerHelper;
import com.horen.base.util.SPUtils;
import com.horen.base.util.UserHelper;
import com.horen.base.widget.SheetDialog;
import com.horen.user.R;
import com.horen.user.api.ApiUser;
import com.horen.user.api.UserApiPram;
import com.horen.user.ui.activity.ClipImageActivity;
import com.horen.user.utils.FileUtil;
import com.horen.user.utils.PictureUtil;
import com.zhihu.matisse.Matisse;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

/**
 * @author :ChenYangYi
 * @date :2018/09/04/16:09
 * @description :基本信息
 * @github :https://github.com/chenyy0708
 */
@Route(path = ARouterPath.USER_INFO)
public class UserInfoActivity extends BaseActivity implements View.OnClickListener, OnOperItemClickL {

    private LinearLayout mLlUserHeader;
    private CircleImageView mCivUser;
    private TextView mTvUserAccount;
    private LinearLayout mLlNickName;
    private TextView mTvNickName;
    private LinearLayout mLlPhoneNumber;
    private TextView mTvPhone;
    private LinearLayout mLlAuthorization;
    private SuperButton mSbtLogout;
    private SheetDialog logOutDilaog;
    private SheetDialog selectPictureDialog;
    private LinearLayout ll_user_level;
    private File file;
    private String imgUrl;
    private LinearLayout ll_modify_password;
    private ImageView iv_liquid_level_icon;
    private TextView tv_liquid_level_name;
    private TextView tv_liquid_balance;
    private RelativeLayout rl_liquid;
    private ImageView iv_fresh_level_icon;
    private TextView tv_fresh_level_name;
    private TextView tv_fresh_balance;
    private RelativeLayout rl_fresh;
    private ImageView iv_parts_level_icon;
    private TextView tv_parts_level_name;
    private TextView tv_parts_balance;
    private RelativeLayout rl_parts;
    private LinearLayout ll_company;
    private TextView tv_company;

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_user_info;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showTitle("基本信息");
        mLlUserHeader = (LinearLayout) findViewById(R.id.ll_user_header);
        mCivUser = (CircleImageView) findViewById(R.id.civ_user);
        mTvUserAccount = (TextView) findViewById(R.id.tv_user_account);
        mLlNickName = (LinearLayout) findViewById(R.id.ll_nick_name);
        mTvNickName = (TextView) findViewById(R.id.tv_nick_name);
        mLlPhoneNumber = (LinearLayout) findViewById(R.id.ll_phone_number);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mLlAuthorization = (LinearLayout) findViewById(R.id.ll_authorization);
        mSbtLogout = (SuperButton) findViewById(R.id.sbt_logout);
        ll_user_level = (LinearLayout) findViewById(R.id.ll_user_level);
        ll_modify_password = (LinearLayout) findViewById(R.id.ll_modify_password);
        mSbtLogout.setOnClickListener(this);
        mLlPhoneNumber.setOnClickListener(this);
        mLlUserHeader.setOnClickListener(this);
        mLlNickName.setOnClickListener(this);
        ll_modify_password.setOnClickListener(this);
        // 退出登陆弹框
        logOutDilaog = new SheetDialog(mContext, new String[]{"退出登录"}, null)
                .isTitleShow(false)
                .cancelText("取消");
        logOutDilaog.setOnOperItemClickL(this);
        // 选择相册图片弹框
        selectPictureDialog = new SheetDialog(mContext, new String[]{"相册选择", "拍照"}, null)
                .isTitleShow(false)
                .cancelText("取消");
        iv_liquid_level_icon = (ImageView) findViewById(R.id.iv_liquid_level_icon);
        tv_liquid_level_name = (TextView) findViewById(R.id.tv_liquid_level_name);
        tv_liquid_balance = (TextView) findViewById(R.id.tv_liquid_balance);
        rl_liquid = (RelativeLayout) findViewById(R.id.rl_liquid);
        ll_company = (LinearLayout) findViewById(R.id.ll_company);
        tv_company = (TextView) findViewById(R.id.tv_company);
        iv_fresh_level_icon = (ImageView) findViewById(R.id.iv_fresh_level_icon);
        tv_fresh_level_name = (TextView) findViewById(R.id.tv_fresh_level_name);
        tv_fresh_balance = (TextView) findViewById(R.id.tv_fresh_balance);
        rl_fresh = (RelativeLayout) findViewById(R.id.rl_fresh);
        iv_parts_level_icon = (ImageView) findViewById(R.id.iv_parts_level_icon);
        tv_parts_level_name = (TextView) findViewById(R.id.tv_parts_level_name);
        tv_parts_balance = (TextView) findViewById(R.id.tv_parts_balance);
        rl_parts = (RelativeLayout) findViewById(R.id.rl_parts);
        mSbtLogout.setOnClickListener(this);
        mLlPhoneNumber.setOnClickListener(this);
        mLlUserHeader.setOnClickListener(this);
        mLlNickName.setOnClickListener(this);
        rl_fresh.setOnClickListener(this);
        rl_liquid.setOnClickListener(this);
        rl_parts.setOnClickListener(this);
        ll_company.setOnClickListener(this);
        initUserInfo();
        // 用户端隐藏退出登陆、显示企业信息等
        if (UserHelper.getUserInfo().getLoginInfo().getCompany_class().equals("2")
                || UserHelper.getUserInfo().getLoginInfo().getCompany_class().equals("7")) {
            mSbtLogout.setVisibility(View.GONE);
            ll_company.setVisibility(View.VISIBLE);
            tv_company.setText(UserHelper.getUserInfo().getLoginInfo().getCompany_name());
        }
    }

    private LoginBean.LoginInfoBean infoBean;

    private void initUserInfo() {
        // 头像
        ImageLoader.load(mContext, UserHelper.getUserInfo().getLoginInfo().getPhoto(), mCivUser);
        infoBean = UserHelper.getUserInfo().getLoginInfo();
        if (infoBean != null) {
            if ("3".equals(infoBean.getCompany_class()) || ("5".equals(infoBean.getCompany_class()) && SPUtils.getSharedBooleanData(this, HRConstant.USER_SELET_PARTNER))) {
                ll_user_level.setVisibility(View.VISIBLE);
            } else {
                ll_user_level.setVisibility(View.GONE);
            }
            mTvPhone.setText(infoBean.getUser_mobile());
            mTvUserAccount.setText(infoBean.getUser_name());
            mTvNickName.setText(infoBean.getUser_nickname());
        }
        getLevelData();
    }

    private UserLevelBean mUserLevelBean;

    private void getLevelData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mUserLevelBean = (UserLevelBean) bundle.get("levelInfo");
            if (mUserLevelBean != null) {
                setLiquidData(mUserLevelBean.getLiquid());
                setPartsData(mUserLevelBean.getParts());
                setFreshData(mUserLevelBean.getFresh());
            }
        }

    }

    /**
     * 液体数据
     *
     * @param liquid
     */
    private void setLiquidData(UserLevelBean.LiquidBean liquid) {
        if ("0".equals(liquid.getNext())) {
            tv_liquid_balance.setText("已达最高等级");
        } else {
            tv_liquid_balance.setText("距下一级差" + liquid.getNext() + "业绩");
        }
        int level = Integer.parseInt(liquid.getLevel());
        String levelStr = "";
        switch (level) {
            case 1:
                levelStr = "一级";
                iv_liquid_level_icon.setImageResource(R.mipmap.icon_large_fluid_one);
                break;
            case 2:
                levelStr = "二级";
                iv_liquid_level_icon.setImageResource(R.mipmap.icon_large_fluid_two);
                break;
            case 3:
                levelStr = "三级";
                iv_liquid_level_icon.setImageResource(R.mipmap.icon_large_fluid_three);
                break;
            case 4:
                levelStr = "四级";
                iv_liquid_level_icon.setImageResource(R.mipmap.icon_large_fluid_four);
                break;
        }
        tv_liquid_level_name.setText("液体等级：" + levelStr);
    }

    /**
     * 汽配数据
     *
     * @param parts
     */
    private void setPartsData(UserLevelBean.PartsBean parts) {
        if ("0".equals(parts.getNext())) {
            tv_parts_balance.setText("已达最高等级");
        } else {
            tv_parts_balance.setText("距下一级差" + parts.getNext() + "业绩");
        }
        int level = Integer.parseInt(parts.getLevel());
        String levelStr = "";
        switch (level) {
            case 1:
                levelStr = "一级";
                iv_parts_level_icon.setImageResource(R.mipmap.icon_large_automobiellevel_one);
                break;
            case 2:
                levelStr = "二级";
                iv_parts_level_icon.setImageResource(R.mipmap.icon_large_automobiellevel_two);
                break;
            case 3:
                levelStr = "三级";
                iv_parts_level_icon.setImageResource(R.mipmap.icon_large_automobiellevel_three);
                break;
            case 4:
                levelStr = "四级";
                iv_parts_level_icon.setImageResource(R.mipmap.icon_large_automobiellevel_four);
                break;
        }
        tv_parts_level_name.setText("汽配等级：" + levelStr);
    }

    /**
     * 生鲜数据
     *
     * @param fresh
     */
    private void setFreshData(UserLevelBean.FreshBean fresh) {
        if ("0".equals(fresh.getNext())) {
            tv_fresh_balance.setText("已达最高等级");
        } else {
            tv_fresh_balance.setText("距下一级差" + fresh.getNext() + "业绩");
        }
        int level = Integer.parseInt(fresh.getLevel());
        String levelStr = "";
        switch (level) {
            case 1:
                levelStr = "一级";
                iv_fresh_level_icon.setImageResource(R.mipmap.icon_large_fresh_one);
                break;
            case 2:
                levelStr = "二级";
                iv_fresh_level_icon.setImageResource(R.mipmap.icon_large_fresh_two);
                break;
            case 3:
                levelStr = "三级";
                iv_fresh_level_icon.setImageResource(R.mipmap.icon_large_fresh_three);
                break;
            case 4:
                levelStr = "四级";
                iv_fresh_level_icon.setImageResource(R.mipmap.icon_large_fresh_four);
                break;
        }
        tv_fresh_level_name.setText("生鲜等级：" + levelStr);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sbt_logout) { // 退出登陆
            logOutDilaog.show();
        } else if (view.getId() == R.id.ll_phone_number) { // 更换手机号
            ChangePhoneActivity.startActivity(mContext, infoBean.getUser_mobile(), ChangePhoneActivity.PHONE_NUMBER);
        } else if (view.getId() == R.id.ll_nick_name) { // 更换姓名
            ChangeContactsActivity.startActivity(mContext, infoBean.getUser_nickname(), ChangeContactsActivity.NAME);
        } else if (view.getId() == R.id.ll_modify_password) { // 修改密码
            ResetpsdActivity.startAction(mContext, "修改登录密码");
        } else if (view.getId() == R.id.ll_user_header) { // 更换头像
            selectPictureDialog.show();
            selectPictureDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0: // 相册
                            PhotoPickerHelper.start(UserInfoActivity.this, 1);
                            break;
                        case 1: // 拍照
                            //创建拍照存储的图片文件
                            imgUrl = PictureUtil.startCamera(UserInfoActivity.this);
                            break;
                    }
                    selectPictureDialog.dismiss();
                }
            });
        } else if (view == rl_liquid) {
            UserLevelDetailActivity.startLevelDetailActivity(this, "液体", mUserLevelBean);
        } else if (view == rl_fresh) {
            UserLevelDetailActivity.startLevelDetailActivity(this, "生鲜", mUserLevelBean);
        } else if (view == rl_parts) {
            UserLevelDetailActivity.startLevelDetailActivity(this, "汽配", mUserLevelBean);
        } else if (view.getId() == R.id.ll_company) { // 所属企业
            CompanyInfoActivity.startActivity(mContext);
        }
    }

    @Override
    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: // 退出登录
                // 退出登陆
                logOutDilaog.dismiss();
                UserHelper.logOut();
                AppManager.getAppManager().finishAllActivity();
                // 打开登陆页面
                ARouter.getInstance().build(ARouterPath.USER_LOGIN).navigation();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoPickerHelper.PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {  // 从相册选择图片
            Uri imageUri = Matisse.obtainResult(data).get(0);
            // 裁剪图片
            ClipImageActivity.startActivity(UserInfoActivity.this, imageUri);
        } else if (requestCode == PictureUtil.PIC_CAMERA && resultCode == RESULT_OK) { // 调用系统拍照
            ClipImageActivity.startActivity(UserInfoActivity.this, Uri.fromFile(new File(imgUrl)));
        } else if (requestCode == ClipImageActivity.CLIP_IMAG && resultCode == RESULT_OK) { // 裁剪图片
            if (data != null) {
                uploadUserHeaderImage(FileUtil.getRealFilePathFromUri(mContext, data.getData()));
            }
        }
    }

    /**
     * 上传裁剪之后的图片到服务器
     *
     * @param path 本地图片地址
     */
    private void uploadUserHeaderImage(String path) {
        // 上传图片到服务器
        final List<File> mFiles = new ArrayList<>();
        mRxManager.add(Observable.just(Arrays.asList(path))
                .compose(RxHelper.uploadFile(mFiles))
                .subscribeWith(new BaseObserver<UploadBean>(mContext, true) {
                    @Override
                    protected void onSuccess(UploadBean uploadBean) {
                        if (uploadBean.success()) {
                            saveAvatar(uploadBean.getData().get(0).getWatchUrl());
                            // 删除本地压缩图片
                            for (File mFile : mFiles) {
                                mFile.delete();
                            }
                        }
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

    /**
     * 保存用户头像
     */
    private void saveAvatar(final String photoUrl) {
        mRxManager.add(ApiUser.getInstance().savePhotoUrl(UserApiPram.savePhotoUrl(photoUrl))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>(mContext, true) {
                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        // 保存用户头像
                        ImageLoader.load(mContext, photoUrl, mCivUser);
                        // 保存新头像
                        LoginBean userInfo = UserHelper.getUserInfo();
                        userInfo.getLoginInfo().setPhoto(photoUrl);
                        // 更新本地新头像
                        UserHelper.saveUserInfo(userInfo);
                        EventBus.getDefault().post(new MsgEvent(EventBusCode.REFERSH_USER_INFO));
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 更新新号码、昵称等信息
        if (mTvPhone != null)
            mTvPhone.setText(UserHelper.getUserInfo().getLoginInfo().getUser_mobile());
        if (mTvNickName != null)
            mTvNickName.setText(UserHelper.getUserInfo().getLoginInfo().getUser_nickname());
    }
}
