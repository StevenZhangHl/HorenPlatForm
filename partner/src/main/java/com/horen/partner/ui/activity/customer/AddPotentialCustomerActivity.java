package com.horen.partner.ui.activity.customer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.horen.base.app.HRConstant;
import com.horen.base.base.BaseActivity;
import com.horen.base.bean.TypeBean;
import com.horen.base.constant.Constants;
import com.horen.base.util.Glide4Engine;
import com.horen.base.util.KeybordS;
import com.horen.base.util.MatcherUtils;
import com.horen.base.util.ToastUitl;
import com.horen.base.widget.MyGridView;
import com.horen.base.widget.PWEditText;
import com.horen.base.widget.RippleButton;
import com.horen.partner.R;
import com.horen.partner.adapter.ShowPhotoAdapter;
import com.horen.partner.bean.CustomerBean;
import com.horen.partner.event.EventConstans;
import com.horen.base.constant.MsgEvent;
import com.horen.partner.mvp.contract.AddPotentialCustomerContract;
import com.horen.partner.mvp.model.AddPotentialCustomerModel;
import com.horen.partner.mvp.presenter.AddPotentialCustomerPresenter;
import com.horen.partner.ui.activity.PlusImageActivity;
import com.xw.repo.XEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AddPotentialCustomerActivity extends BaseActivity<AddPotentialCustomerPresenter, AddPotentialCustomerModel> implements AddPotentialCustomerContract.View, View.OnClickListener {
    private final int REQUEST_CODE_CHOOSE = 1;
    private final int REQUEST_CODE_EDIT_IMAGE = 2;
    public static final int RESULT_CODE_VIEW_IMG = 11; //查看大图页面的结果码
    private PWEditText et_company_name;
    private EditText et_company_address;
    private EditText tv_industry;
    private PWEditText et_email;
    private PWEditText et_contact;
    private PWEditText et_phone;
    private AppCompatEditText et_needs;
    private TextView tv_needs_info_txt;
    private MyGridView photo_recycleview;
    private ShowPhotoAdapter showPhotoAdapter;
    private RippleButton bt_submit_needs;
    private RelativeLayout rl_industry;
    private TextView tv_str_count;
    private LinearLayout ll_add_customer_root;
    private NestedScrollView add_customer_scrollview;
    private TextView tv_tip;
    /**
     * 公司名称
     */
    private LinearLayout ll_company_name;
    /**
     * 选择省市区
     */
    private RelativeLayout rl_city;
    private TextView tv_city;
    /**
     * 当前显示的所有图片
     */
    private List<String> imageBeanList = new ArrayList<>();
    private String mSelectIndustryId;
    private String customerId;
    private String provinceId;
    private String cityId;
    private String areaId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_customer;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        photo_recycleview = findViewById(R.id.photo_recycleview);
        bt_submit_needs = (RippleButton) findViewById(R.id.bt_subimit_needs);
        tv_industry = (EditText) findViewById(R.id.tv_industry);
        et_company_address = (EditText) findViewById(R.id.et_company_address);
        et_company_name = (PWEditText) findViewById(R.id.et_company_name);
        et_contact = (PWEditText) findViewById(R.id.et_contact);
        et_email = (PWEditText) findViewById(R.id.et_email);
        et_phone = (PWEditText) findViewById(R.id.et_phone);
        et_needs = (AppCompatEditText) findViewById(R.id.et_needs);
        tv_needs_info_txt = (TextView) findViewById(R.id.tv_needs_info_txt);
        rl_industry = (RelativeLayout) findViewById(R.id.rl_industry);
        ll_company_name = (LinearLayout) findViewById(R.id.ll_company_name);
        rl_city = (RelativeLayout) findViewById(R.id.rl_city);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_str_count = (TextView) findViewById(R.id.tv_str_count);
        ll_add_customer_root = (LinearLayout) findViewById(R.id.ll_add_customer_root);
        add_customer_scrollview = (NestedScrollView) findViewById(R.id.add_customer_scrollview);
        tv_tip = (TextView)findViewById(R.id.tv_tip);
        showPhotoAdapter = new ShowPhotoAdapter(this, imageBeanList);
        photo_recycleview.setAdapter(showPhotoAdapter);
        photo_recycleview.setOnItemClickListener(new OnItemChildClickListener());
        rl_industry.setOnClickListener(this);
        rl_city.setOnClickListener(this);
        mPresenter.getIndustryData();
        initEditTextChangeListener();
        getIntentData();
        mPresenter.getCityData();
        bt_submit_needs.setOnGreenBTClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    private String customerType;
    private CustomerBean info;

    private void getIntentData() {
        info = (CustomerBean) getIntent().getSerializableExtra(Constants.PARTNER_CUSTOMER_INFO);
        customerType = getIntent().getStringExtra(Constants.PARTEER_CUSTOMER_TYPE);
        if (info != null) {
            showWhiteTitle("修改基本信息");
            tv_tip.setVisibility(View.INVISIBLE);
            customerId = info.getCustomer_id();
            mSelectIndustryId = info.getCustomer_industry();
            provinceId = info.getState_id();
            cityId = info.getCity_id();
            areaId = info.getCounty_id();
            tv_industry.setText(info.getIndustry_name());
            et_company_address.setText(info.getStreet());
            et_contact.setText(info.getCustomer_contact());
            et_email.setText(info.getCustomer_mail());
            et_company_name.setText(info.getCustomer_name());
            et_phone.setText(info.getCustomer_tel());
            tv_city.setText(info.getState_id() + info.getCity_id() + info.getCounty_id());
            if ("potential".equals(customerType)) {
                et_needs.setText(info.getRequirements());
                if (info.getPhoto_urls() != null) {
                    imageBeanList.addAll(info.getPhoto_urls());
                    showPhotoAdapter.setNewData(info.getPhoto_urls());
                }
            } else {
                ll_company_name.setBackgroundColor(getResources().getColor(R.color.bg));
                et_company_name.setFocusable(false);
                et_company_name.setClickable(false);
                tv_needs_info_txt.setVisibility(View.GONE);
                et_needs.setVisibility(View.GONE);
                photo_recycleview.setVisibility(View.GONE);
            }
        } else {
            showWhiteTitle("新增客户");
        }
    }

    private void initEditTextChangeListener() {
        tv_industry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_company_name.setOnXTextChangeListener(new com.xw.repo.XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_needs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 50) {
                    tv_str_count.setText("50 / 50");
                    return;
                }
                tv_str_count.setText(et_needs.getText().toString().length() + " / 50");
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_phone.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_email.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_contact.setOnXTextChangeListener(new XEditText.OnXTextChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_company_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateContent();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_needs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    tv_str_count.setVisibility(View.INVISIBLE);
                } else {
                    tv_str_count.setVisibility(View.VISIBLE);
                    tv_str_count.setText(et_needs.getText().toString().length() + " / 50");
//                    KeybordS.pullKeywordTop(AddPotentialCustomerActivity.this, R.id.ll_add_customer_root, R.id.tv_str_count, R.id.add_customer_scrollview);
                }
            }
        });
    }

    /**
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private int scrollToPosition = 0;

    /**
     * 滚动需要的可见view
     *
     * @param root
     * @param scrollToView
     */
    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {

                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);

                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;

                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition += (scrollHeight);

                        } else {
                            //键盘隐藏
                            scrollToPosition = 0;
                        }
                        root.scrollTo(0, scrollToPosition);

                    }
                });
    }

    /**
     * 更新保存按钮状态
     */
    private void updateContent() {
        String customer_address = et_company_address.getText().toString().trim();
        String customer_contact = et_company_name.getText().toString().trim();
        String customer_industry = tv_industry.getText().toString();
        String customer_mail = et_email.getText().toString().trim();
        String customer_name = et_contact.getText().toString().trim();
        String customer_tel = et_phone.getText().toString().trim();
        String requirements = et_needs.getText().toString().trim();
        String customer_city = tv_city.getText().toString();
        if (customer_industry.length() == 0) {
            bt_submit_needs.setEnabled(false);
            bt_submit_needs.showGrayButton();
            return;
        }
        if (customer_city.length() == 0) {
            bt_submit_needs.setEnabled(false);
            bt_submit_needs.showGrayButton();
            return;
        }
        if (customer_address.length() == 0) {
            bt_submit_needs.setEnabled(false);
            bt_submit_needs.showGrayButton();
            return;
        }
        if (customer_name.length() == 0) {
            bt_submit_needs.setEnabled(false);
            bt_submit_needs.showGrayButton();
            return;
        }
        if (customer_contact.length() == 0) {
            bt_submit_needs.setEnabled(false);
            bt_submit_needs.showGrayButton();
            return;
        }
        if (customer_mail.length() == 0) {
            bt_submit_needs.setEnabled(false);

            bt_submit_needs.showGrayButton();
            return;
        }
        if (customer_tel.length() == 0) {
            bt_submit_needs.setEnabled(false);
            bt_submit_needs.showGrayButton();
            return;
        }
        if ("potential".equals(customerType)) {
            if (requirements.length() == 0) {
                bt_submit_needs.setEnabled(false);
                bt_submit_needs.showGrayButton();
                return;
            }
        }
        bt_submit_needs.setEnabled(true);
        bt_submit_needs.showGreenButton();
    }

    @Override
    public void onClick(View view) {
        if (view == rl_industry) {
            if (KeybordS.isSoftInputShow(this)) {
                KeybordS.closeAllKeybord(AddPotentialCustomerActivity.this);
            }
            mPresenter.showSelectIndustryDialog(this, mSelectIndustryId);
        }
        if (view == rl_city) {
            if (KeybordS.isSoftInputShow(this)) {
                KeybordS.closeAllKeybord(AddPotentialCustomerActivity.this);
            }
            mPresenter.showSelectCityPicker();
        }
    }

    private void submitData() {
        String country_id = "";
        String customer_address = et_company_address.getText().toString().trim();
        String customer_contact = et_contact.getText().toString().trim();
        String customer_industry = mSelectIndustryId;
        String customer_mail = et_email.getText().toString().trim();
        String customer_name = et_company_name.getText().toString().trim();
        String customer_tel = et_phone.getText().toString().trim();
        String requirements = et_needs.getText().toString().trim();
        String photo_desc = "";
        if ("请选择行业类别".equals(customer_industry)) {
            ToastUitl.showShort("请选择行业");
            return;
        }
        if (TextUtils.isEmpty(provinceId) || TextUtils.isEmpty(cityId) || TextUtils.isEmpty(areaId)) {
            ToastUitl.showShort("请选择地区");
            return;
        }
        if (customer_address.length() == 0) {
            ToastUitl.showShort("公司详细地址不能为空");
            return;
        }
        if (customer_name.length() == 0) {
            ToastUitl.showShort("公司名称不能为空");
            return;
        }
        if (customer_contact.length() == 0) {
            ToastUitl.showShort("联系人不能为空");
            return;
        }
        if (customer_tel.length() == 0) {
            ToastUitl.showShort("联系人手机号不能为空");
            return;
        }
        if (!MatcherUtils.isMobilePhone(customer_tel)) {
            ToastUitl.showShort("请输入正确的手机号");
            return;
        }
        if (customer_mail.length() == 0) {
            ToastUitl.showShort("联系人邮箱不能为空");
            return;
        }
        if (!MatcherUtils.isEmail(customer_mail)) {
            ToastUitl.showShort("请输入正确的邮箱");
            return;
        }
        imageBeanList = showPhotoAdapter.getBeanList();
        if ("potential".equals(customerType)) {
            if (requirements.length() == 0) {
                ToastUitl.showShort("需求信息不能为空");
                return;
            }
            bt_submit_needs.showLoadingButton();
            if (info != null) {
                mPresenter.editCustomerInfo(customerId, provinceId, cityId, areaId, customer_address, customer_contact, customer_industry, customer_name, customer_mail, customer_tel, requirements, imageBeanList);
            } else {
                mPresenter.uploadPotentialInfo(imageBeanList, provinceId, cityId, country_id, areaId, customer_address, customer_contact, customer_industry, customer_mail, customer_name, customer_tel, photo_desc, requirements);
            }

        } else {
            bt_submit_needs.showLoadingButton();
            mPresenter.editCustomerInfo(customerId, provinceId, cityId, areaId, customer_address, customer_contact, customer_industry, customer_name, customer_mail, customer_tel, requirements, imageBeanList);
        }

    }

    @Override
    public void submitSuccess(String result) {
        ToastUitl.showShort(result);
        showToast(result);
        EventBus.getDefault().post(new MsgEvent(EventConstans.ADD_CUSTOMTER_SUCCESS));
        finish();
    }

    @Override
    public void updateSuccess(String result) {
        ToastUitl.showShort(result);
        EventBus.getDefault().post(new MsgEvent(EventConstans.UPDATE_CUSTOMER_SUCCESS));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void getSeclectIndustryData(TypeBean typeBean) {
        tv_industry.setText(typeBean.getTypeName());
        mSelectIndustryId = typeBean.getTypeId();
    }

    @Override
    public void setSelectCityInfo(String province, String city, String area) {
        if (province.equals(city)) {
            tv_city.setText(province + area);
        } else {
            tv_city.setText(province + city + area);
        }
        provinceId = province;
        cityId = city;
        areaId = area;
    }

    @Override
    public void submitFail(String result) {
        bt_submit_needs.showRedButton(result);
    }

    private class OnItemChildClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == adapterView.getChildCount() - 1) {
                if (imageBeanList.size() == 6) {
                    ToastUitl.showShort("最多选6张图");
                    viewPluImg(i);
                } else {
                    startSelectView(imageBeanList.size());
                }
            } else {
                viewPluImg(i);
            }
        }
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra("imageList", (ArrayList<String>) imageBeanList);
        intent.putExtra("imagePosition", position);
        startActivityForResult(intent, REQUEST_CODE_EDIT_IMAGE);
    }

    /**
     * 拉起图片选择和相机拍照
     *
     * @param size
     */
    private void startSelectView(int size) {
        Matisse.from(AddPotentialCustomerActivity.this)
                .choose(MimeType.ofImage())
                .countable(true)
                .maxSelectable(6 - size)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, HRConstant.FILE_PROVIDER))
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize())
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    List<String> mSelected;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainPathResult(data);
            if (mSelected.size() + imageBeanList.size() > 6) {
                ToastUitl.showShort("最多选6张图片,请重新选择");
                return;
            }
            Log.i("Matisse", "mSelected: " + mSelected);
            imageBeanList.addAll(mSelected);
            showPhotoAdapter.setNewData(imageBeanList);
            mSelected.clear();
        }
        if (requestCode == REQUEST_CODE_EDIT_IMAGE) {
            imageBeanList = data.getStringArrayListExtra("imageList");
            showPhotoAdapter.setNewData(imageBeanList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.removeHandler();
    }
}
