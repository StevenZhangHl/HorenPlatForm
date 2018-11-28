package com.cyy.company.ui.activity.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cb.ratingbar.CBRatingBar;
import com.cyy.company.R;
import com.cyy.company.api.ApiCompany;
import com.cyy.company.api.CompanyParams;
import com.cyy.company.bean.EvalNotify;
import com.cyy.company.bean.EvaluationBean;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.BaseEntry;
import com.horen.base.constant.EventBusCode;
import com.horen.base.rx.BaseObserver;
import com.horen.base.rx.RxHelper;
import com.horen.base.util.ImageLoader;
import com.horen.base.widget.HRTitle;

import org.simple.eventbus.EventBus;

/**
 * @author :ChenYangYi
 * @date :2018/10/16/14:08
 * @description :评价
 * @github :https://github.com/chenyy0708
 */
public class EvaluationActivity extends BaseActivity {

    private HRTitle mToolBar;
    private ImageView mIvPhoto;
    private TextView mTvOrderNumber;
    private CBRatingBar mCbProductService;
    private CBRatingBar mCbLogisticsService;
    private CBRatingBar mCbService;
    private EditText mEtEvaluation;

    /**
     * 订单号
     */
    private String order_id;
    /**
     * 产品服务
     */
    private int eval_product;
    /**
     * 物流服务
     */
    private int eval_logistics;
    /**
     * 服务态度
     */
    private int eval_attitude;
    /**
     * 评价状态
     */
    private String eval_status;

    /**
     * @param order_id    订单号
     * @param photo       产品图片
     * @param eval_status 评价状态
     */
    public static void startAction(Context context, String order_id, String photo, String eval_status, int position) {
        Intent intent = new Intent();
        intent.putExtra("order_id", order_id);
        intent.putExtra("photo", photo);
        intent.putExtra("eval_status", eval_status);
        intent.putExtra("position", position);
        intent.setClass(context, EvaluationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluation;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRTitle) findViewById(R.id.tool_bar);
        mIvPhoto = (ImageView) findViewById(R.id.iv_photo);
        mTvOrderNumber = (TextView) findViewById(R.id.tv_order_number);
        mCbProductService = (CBRatingBar) findViewById(R.id.cb_product_service);
        mCbLogisticsService = (CBRatingBar) findViewById(R.id.cb_logistics_service);
        mCbService = (CBRatingBar) findViewById(R.id.cb_service);
        mEtEvaluation = (EditText) findViewById(R.id.et_evaluation);
        mToolBar.bindActivity(this, R.color.color_f5);
        order_id = getIntent().getStringExtra("order_id");
        eval_status = getIntent().getStringExtra("eval_status");
        mTvOrderNumber.setText("订单号: " + order_id);
        // 监听评分
        initListener();
        // 发表评价
        mToolBar.setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断是否填写评价
                if (eval_attitude == 0 | eval_logistics == 0 | eval_product == 0) {
                    showToast("请对产品进行评价");
                    return;
                }
                saveOrderEvaluation();
            }
        });
        ImageLoader.load(mContext, getIntent().getStringExtra("photo"), mIvPhoto);
        // 判断是否已评价  评价状态(0:未评价, 1:已评价)
        if (eval_status.equals("0")) { // 未评价

        } else { // 已评价，调用接口，获取评分和内容
            mToolBar.setRightTextVisible(View.GONE);
            mCbProductService.setCanTouch(false);
            mCbLogisticsService.setCanTouch(false);
            mCbService.setCanTouch(false);
            mEtEvaluation.setEnabled(false);
            getOrderEvaluationInfo();
        }
    }

    private void initListener() {
        mCbProductService.setOnStarTouchListener(new CBRatingBar.OnStarTouchListener() {
            @Override
            public void onStarTouch(int i) {
                eval_product = i;
            }
        });
        mCbLogisticsService.setOnStarTouchListener(new CBRatingBar.OnStarTouchListener() {
            @Override
            public void onStarTouch(int i) {
                eval_logistics = i;
            }
        });
        mCbService.setOnStarTouchListener(new CBRatingBar.OnStarTouchListener() {
            @Override
            public void onStarTouch(int i) {
                eval_attitude = i;
            }
        });
    }

    /**
     * 发表评价
     */
    private void saveOrderEvaluation() {
        mRxManager.add(ApiCompany.getInstance()
                .saveOrderEvaluation(CompanyParams.saveOrderEvaluation(order_id, eval_product
                        , eval_logistics, eval_attitude, mEtEvaluation.getText().toString().trim()
                        , eval_status))
                .compose(RxHelper.handleResult())
                .subscribeWith(new BaseObserver<BaseEntry>(mContext, true) {
                    @Override
                    protected void onSuccess(BaseEntry baseEntry) {
                        // 发送评价成功通知
                        EventBus.getDefault().post(new EvalNotify(getIntent().getIntExtra("position", 0), order_id),
                                EventBusCode.EVAL_COMPLETE);
                        showToast("评价成功");
                        finish();
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

    /**
     * 获取评价信息
     */
    private void getOrderEvaluationInfo() {
        mRxManager.add(ApiCompany.getInstance()
                .getOrderEvaluationInfo(CompanyParams.getOrderId(order_id))
                .compose(RxHelper.<EvaluationBean>getResult())
                .subscribeWith(new BaseObserver<EvaluationBean>(mContext, true) {
                    @Override
                    protected void onSuccess(EvaluationBean bean) {
                        EvaluationBean.PdListBean pdListBean = bean.getPdList().get(0);
                        mCbProductService.setStarProgress(Float.valueOf(pdListBean.getEval_product()));
                        mCbLogisticsService.setStarProgress(Float.valueOf(pdListBean.getEval_logistics()));
                        mCbService.setStarProgress(Float.valueOf(pdListBean.getEval_attitude()));
                        mEtEvaluation.setText(TextUtils.isEmpty(pdListBean.getEval_remarks()) ?
                                "无" : pdListBean.getEval_remarks());
                    }

                    @Override
                    protected void onError(String message) {
                        showToast(message);
                    }
                }));
    }

}
