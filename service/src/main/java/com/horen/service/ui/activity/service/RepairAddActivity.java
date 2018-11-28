package com.horen.service.ui.activity.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.base.AppManager;
import com.horen.base.base.BaseActivity;
import com.horen.base.constant.EventBusCode;
import com.horen.base.ui.ScanActivity;
import com.horen.base.util.CollectionUtils;
import com.horen.base.util.FormatUtil;
import com.horen.base.util.PhotoPickerHelper;
import com.horen.base.widget.HRToolbar;
import com.horen.base.widget.MessageDialog;
import com.horen.base.widget.RippleButton;
import com.horen.service.R;
import com.horen.service.bean.RepairDetailBean;
import com.horen.service.mvp.contract.RepairAddContract;
import com.horen.service.mvp.model.RepairAddModel;
import com.horen.service.mvp.presenter.RepairAddPresenter;
import com.horen.service.ui.activity.adapter.PhotoPickerAdapter;
import com.horen.service.utils.ServiceIdUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhihu.matisse.Matisse;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ChenYangYi
 * @date :2018/08/20/15:05
 * @description :新增报修
 * @github :https://github.com/chenyy0708
 */
public class RepairAddActivity extends BaseActivity<RepairAddPresenter, RepairAddModel> implements View.OnClickListener, PhotoPickerAdapter.OnPickerListener, RepairAddContract.View, TextWatcher, PhotoPickerAdapter.onDeleteListener {

    /**
     * 打开相册请求码
     */
    public static final int PHOTO_REQUEST_CODE = 100;

    private HRToolbar mToolBar;
    private EditText mEtBoxNumber;
    private ImageView mIvScan;
    private EditText mEtDamageLocaon;
    private RecyclerView mRvPhotoPicker;
    private RippleButton mRbtSave;
    private PhotoPickerAdapter pickerAdapter;
    private EditText mEtRemake;
    private RepairDetailBean.ServiceListBean repairBean;
    private TextView mTvRemakeCount;
    private String orderallot_id;
    private boolean isAddSpace;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RepairAddActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String orderallot_id) {
        Intent intent = new Intent();
        intent.putExtra("orderallot_id", orderallot_id);
        intent.setClass(context, RepairAddActivity.class);
        context.startActivity(intent);
    }

    /**
     * 编辑维修信息
     */
    public static void startActivity(Context context, RepairDetailBean.ServiceListBean repairBean) {
        Intent intent = new Intent();
        intent.putExtra("repairBean", repairBean);
        intent.setClass(context, RepairAddActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_repair_add;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRToolbar) findViewById(R.id.tool_bar);
        mEtBoxNumber = (EditText) findViewById(R.id.et_box_number);
        mEtRemake = (EditText) findViewById(R.id.et_remake);
        mEtRemake.setHint("请输入需求信息");
        mIvScan = (ImageView) findViewById(R.id.iv_scan);
        View mDamageLocation = findViewById(R.id.view_damage_location);
        mEtDamageLocaon = (EditText) findViewById(R.id.et_damage_locaon);
        mTvRemakeCount = (TextView) findViewById(R.id.tv_remake_count);
        mRvPhotoPicker = (RecyclerView) findViewById(R.id.rv_photo_picker);
        mRbtSave = (RippleButton) findViewById(R.id.rbt_save);
        // 保存
        mRbtSave.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRbtSave.showLoadingButton();
                mPresenter.addRtpRepair(mEtBoxNumber.getText().toString().trim(),
                        mEtRemake.getText().toString().trim(), pickerAdapter.getmImageUrlList(), repairBean);
            }
        });
        mEtBoxNumber.addTextChangedListener(this);
        mDamageLocation.setOnClickListener(this);
        mIvScan.setOnClickListener(this);
        initToolbar(mToolBar.getToolbar(), true);
        initRecyclerView();
        // 获取编辑信息
        repairBean = (RepairDetailBean.ServiceListBean) getIntent().getSerializableExtra("repairBean");
        orderallot_id = getIntent().getStringExtra("orderallot_id");
        // 编辑维修信息
        if (repairBean != null) {
            setEditRepairData();
        }
    }

    /**
     * 维修编辑
     */
    private void setEditRepairData() {
        // 隐藏扫码二维码图片
        mIvScan.setVisibility(View.GONE);
        mToolBar.setTitle(getString(R.string.service_edit_repair));
        // 箱号id
        mEtBoxNumber.setText(repairBean.getCtnr_sn());
        // 不可编辑
        mEtBoxNumber.setEnabled(false);
        // 损坏位置,拼接损坏位置
        StringBuilder builder = new StringBuilder();
        // 用分号隔开
        for (int i = 0; i < repairBean.getPositionList().size(); i++) {
            builder.append(repairBean.getPositionList().get(i).getPosition());
            // 集合的长度大于1并且不是最后一条 拼接;
            if (repairBean.getPositionList().size() > 1 && repairBean.getPositionList().size() - 1 != i) {
                builder.append("、");
            }
        }
        mEtDamageLocaon.setText(builder.toString());
        // 备注
        mEtRemake.setText(repairBean.getRemark());
        if (!CollectionUtils.isNullOrEmpty(repairBean.getPositionList()))
            if (!CollectionUtils.isNullOrEmpty(repairBean.getPositionList()))
                pickerAdapter.setNewData(repairBean.getPositionList().get(0).getImgList());
        checkBtStatus();
        // 删除
        mToolBar.setRightText(getString(R.string.service_delete));
        mToolBar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageDialog(mContext)
                        .showTitle("删除")
                        .isShowContent(false)
                        .setButtonTexts("否", "是")
                        .setButtonTextsColors(ContextCompat.getColor(mContext, R.color.color_333), ContextCompat.getColor(mContext, R.color.service_color_EB4))
                        .setOnClickListene(new MessageDialog.OnClickListener() {
                            @Override
                            public void onLeftClick() {

                            }

                            @Override
                            public void onRightClick() {
                                // 删除
                                mRbtSave.showLoadingButton();
                                mPresenter.delRepair(repairBean.getService_id());
                            }
                        })
                        .show();
            }
        });
    }

    private void initRecyclerView() {
        mEtRemake.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTvRemakeCount.setText(charSequence.length() + "/30");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mRvPhotoPicker.setLayoutManager(new GridLayoutManager(mContext, 4));
        pickerAdapter = new PhotoPickerAdapter(mContext, 9, getString(R.string.service_photo_upload));
        mRvPhotoPicker.setAdapter(pickerAdapter);
        pickerAdapter.setOnPickerListener(this);
        pickerAdapter.setOnDeleteListener(this);
        // 监听软键盘关闭打开
        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            mRbtSave.setVisibility(View.GONE);
                            if (mTvRemakeCount != null) {
                                if (mEtRemake.isFocused()) {
                                    mTvRemakeCount.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mRbtSave.setVisibility(View.VISIBLE);
                                }
                            }, 100);
                            if (mTvRemakeCount != null) {
                                mTvRemakeCount.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_scan) {
            ScanActivity.startActivityForResult(this);
        } else if (view.getId() == R.id.view_damage_location) {
            // 获取箱号对应损坏位置信息
            mPresenter.getRtpInfo(mEtBoxNumber.getText().toString().trim(), mEtDamageLocaon.getText().toString());
        }
    }

    @Override
    public void onPicker(int position) {
        PhotoPickerHelper.start(this, 9 - pickerAdapter.getmImageUrlList().size(), PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 从相册选择图片
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> photoPaths = Matisse.obtainPathResult(data);
            pickerAdapter.addData(photoPaths);
        } else if (requestCode == ScanActivity.REQUEST && data != null) {
            Bundle bundle = data.getExtras();
            if (null == bundle) {
                return;
            }
            // 二维码扫描结果
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                mEtBoxNumber.setText(result);
                mEtBoxNumber.setSelection(mEtBoxNumber.getText().toString().length());
            }
        }
        checkBtStatus();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /**
     * 再次输入箱号，应该清空损坏位置信息，和选择图片信息
     */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mEtDamageLocaon.setText("");
        pickerAdapter.setNewData(new ArrayList<String>());
        // 清空上一个箱子信息
        mPresenter.clear();
        checkBtStatus();
        if (charSequence.length() > 9) {
            // 不包含空格
            if (!FormatUtil.isContainSpaces(charSequence.toString())) {
                mEtBoxNumber.setText(FormatUtil.formatBoxNumber(charSequence.toString()));
                mEtBoxNumber.setSelection(mEtBoxNumber.getText().length());
            }
            isAddSpace = true;
        } else if (charSequence.length() == 9 && !isAddSpace) {
            mEtBoxNumber.setText(FormatUtil.formatBoxNumber(charSequence.toString()));
            mEtBoxNumber.setSelection(mEtBoxNumber.getText().length());
        } else if (charSequence.length() < 9) {
            isAddSpace = false;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * 选择损坏位置
     *
     * @param location 损坏位置
     */
    @Override
    public void onSelectDamageLocation(String location) {
        mEtDamageLocaon.setText(location);
        checkBtStatus();
    }

    @Override
    public void saveSuccess(String service_id) {
        mRbtSave.showGreenButton();
        if (repairBean != null) {
            showToast("修改成功");
        } else {
            showToast("新增成功");
        }
        // 保存服务id到本地
        ServiceIdUtils.addData(service_id);
        // 已经打开维修列表页面，直接关闭
        if (AppManager.getAppManager().isOpenActivity(RepairListActivity.class)) {
            finish();
            // 通知页面刷新数据
            EventBus.getDefault().post(EventBusCode.REFRESH_REPAIR_LIST, EventBusCode.REFRESH_REPAIR_LIST);
        } else {
            // 没有打开维修列表，打开页面
            RepairListActivity.startActivity(mContext, orderallot_id);
            finish();
        }
    }

    @Override
    public void delRepairSuccess(String service_id) {
        mRbtSave.showGreenButton();
        // 本地删除该条维修记录
        ServiceIdUtils.deleteData(service_id);
        finish();
        // 通知页面刷新数据
        EventBus.getDefault().post(EventBusCode.REFRESH_REPAIR_LIST, EventBusCode.REFRESH_REPAIR_LIST);
    }

    /**
     * 检查保存按钮的状态，需要检查三个属性：箱号是否为空、损坏位置是否为空、选择照片是否为空
     */
    private void checkBtStatus() {
        if (!TextUtils.isEmpty(mEtBoxNumber.getText().toString().trim()) && !TextUtils.isEmpty(mEtDamageLocaon.getText().toString().trim()) && pickerAdapter.getmImageUrlList().size() > 0) {
            mRbtSave.showGreenButton();
        } else {
            mRbtSave.showGrayButton();
        }
    }

    @Override
    public void onDeletePhoto() {
        checkBtStatus();
    }

    @Override
    public void onError(String msg) {
        mRbtSave.showRedButton(msg);
    }
}
