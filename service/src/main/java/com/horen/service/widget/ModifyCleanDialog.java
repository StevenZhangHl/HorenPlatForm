package com.horen.service.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.flyco.dialog.widget.base.BaseDialog;
import com.horen.base.bean.BaseEntry;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.rx.RxManager;
import com.horen.base.util.ToastUitl;
import com.horen.service.R;
import com.horen.service.api.Api;
import com.horen.service.api.ServiceParams;
import com.horen.service.bean.ServiceListBean;
import com.horen.service.listener.ModifyCleanListener;

/**
 * @author :ChenYangYi
 * @date :2018/08/17/14:45
 * @description : 修改清洗数量dialog
 * @github :https://github.com/chenyy0708
 */
public class ModifyCleanDialog extends BaseDialog<ModifyCleanDialog> implements TextWatcher {

    private TextView mTvTitle;
    private SuperButton mStbLeft;
    private SuperButton mStbRight;

    private ModifyCleanListener onModifyClean;
    /**
     * 清洗数
     */
    private ServiceListBean.ServiceBean serviceBean;
    private EditText etCleanCount;
    private final RxManager mRxManager;

    public ModifyCleanDialog(Context context, ServiceListBean.ServiceBean serviceBean) {
        super(context);
        this.serviceBean = serviceBean;
        mRxManager = new RxManager();
    }

    @Override
    public View onCreateView() {
        // 屏幕宽度
        widthScale(0.85f);
        View inflate = View.inflate(mContext, R.layout.service_dialog_modify_clean, null);
        mTvTitle = inflate.findViewById(R.id.tv_title);
        etCleanCount = inflate.findViewById(R.id.et_clean_count);
        mStbLeft = inflate.findViewById(R.id.stb_left);
        mStbRight = inflate.findViewById(R.id.stb_right);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        etCleanCount.setText(String.valueOf(serviceBean.getService_qty()));
        etCleanCount.addTextChangedListener(this);
        // 光标设置在最后
        etCleanCount.setSelection(etCleanCount.getText().toString().length());
        mStbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mStbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用清洗接口
                mRxManager.add(Api.getInstance()
                        .addRtpWash(ServiceParams.addRtpWash(serviceBean.getProduct_id(), etCleanCount.getText().toString().trim()))
                        .compose(RxHelper.handleResult())
                        .subscribeWith(new BaseObserver<BaseEntry>() {
                            @Override
                            protected void onSuccess(BaseEntry baseEntry) {
                                dismiss();
                                if (onModifyClean != null) {
                                    // 减去已经清洗数量 = 新的未清洗数量
                                    onModifyClean.onModifyCleanCount(serviceBean.getService_qty() - Integer.valueOf(etCleanCount.getText().toString().trim()));
                                }
                            }

                            @Override
                            protected void onError(String message) {
                                ToastUitl.showShort(message);
                            }
                        }));


            }
        });
    }

    /**
     * 清洗数量
     */
    public ModifyCleanDialog setOnModifyClean(ModifyCleanListener onModifyClean) {
        this.onModifyClean = onModifyClean;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //不能输入0开始的两位数
        if (charSequence.toString().length() > 1) {
            if (charSequence.toString().startsWith("0")) {
                etCleanCount.setText("0");
            }
        }
        // 输入空
        if (TextUtils.isEmpty(charSequence.toString())) {
            return;
        }
        // 输入数量不能大于原始最大数量
        if (Integer.valueOf(charSequence.toString()) > serviceBean.getService_qty()) {
            etCleanCount.setText(String.valueOf(serviceBean.getService_qty()));
            etCleanCount.setSelection(etCleanCount.getText().toString().trim().length());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void dismiss() {
        mRxManager.clear();
        super.dismiss();
    }
}
