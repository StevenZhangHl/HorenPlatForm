package com.horen.user.ui.activity.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.horen.base.base.BaseActivity;
import com.horen.user.R;
import com.horen.user.ui.adapter.AddressSugAdapter;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOREN on 2018/2/26.
 * <p>
 * 地址模糊搜索页面
 * 返回地址字符串 "RESULT_ADDRESS" (resultcode=100)
 * 后续可根据需要在intent里设置更多返回字段
 */
public class AddressSugActivity extends BaseActivity implements TextWatcher, SearchView.OnQueryTextListener {
    public static final String RESULT_ADDRESS = "RESULT_ADDRESS";
    public static final String RESULT_LAT = "RESULT_LAT";
    public static final String RESULT_LNG = "RESULT_LNG";
    public static final String DEFAULT_ADDRESS = "DEFAULT_ADDRESS";

    public static final int RESULT_CODE = 100;
    private RecyclerView recyclerView;
    private SearchView editText;
    private Button btn;
    private ImageView ivClear;
    private AddressSugAdapter adapter;
    private SuggestionSearch search;
    private List<SuggestionResult.SuggestionInfo> list = new ArrayList<>();
    /**
     * 是否开启软键盘
     */
    private boolean isOpenKeyBord;


    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, AddressSugActivity.class);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_address_sug;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView(Bundle bundle) {
        setWhiteStatusBar(R.color.white);
        recyclerView = (RecyclerView) findViewById(R.id.rv_address_sug);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressSugAdapter(R.layout.user_item_address_sug, new ArrayList<SuggestionResult.SuggestionInfo>());
        recyclerView.setAdapter(adapter);
        ivClear = (ImageView) findViewById(R.id.iv_address_sug_clear);
        editText = (SearchView) findViewById(R.id.et_address_sug);
        editText.setOnQueryTextListener(this);
        // 修改字体大小
        TextView textView = (TextView) editText.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setTextSize(14);//字体、提示字体大小
        btn = (Button) findViewById(R.id.btn_address_sug);
        adapter.bindToRecyclerView(recyclerView);
        OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
            public void onGetSuggestionResult(SuggestionResult res) {
                if (res == null || res.getAllSuggestions() == null) {
                    adapter.setNewData(null);//搜索结果为空,显示空页面
                    return;
                }
                list = res.getAllSuggestions();
                adapter.setKeyWord(editText.getQuery().toString().trim());
                adapter.setNewData(list);//更新检索列表
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                SuggestionResult.SuggestionInfo info = list.get(position);
                String lat = info.pt == null ? "0" : info.pt.latitude + "";
                String lng = info.pt == null ? "0" : info.pt.longitude + "";
                String add = TextUtils.isEmpty(info.district) ? info.city + info.key : info.city + info.district + info.key;
                result(add, lat, lng);
                //第二种返回，用户根据百度检索，选择返回地址
            }
        });
//        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        search = SuggestionSearch.newInstance();
        search.setOnGetSuggestionResultListener(listener);
        Intent intent = getIntent();
        if (intent.getStringExtra(DEFAULT_ADDRESS) != null) {
            editText.setQuery(intent.getStringExtra(DEFAULT_ADDRESS), false);
        } else {
            editText.setQuery("", false);
        }
        editText.onActionViewExpanded();
        // 监听软键盘关闭打开
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                isOpenKeyBord = isOpen;
            }
        });
        // 监听列表滚动，关闭软键盘
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //正在被外部拖拽,一般为用户正在用手指滚动
                if(newState  == RecyclerView.SCROLL_STATE_DRAGGING ) {
                    if (isOpenKeyBord) {
                        InputMethodManager imm = (InputMethodManager) mContext
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                }
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_address_sug_back) {
            finish();
        } else if (id == R.id.btn_address_sug) {
//            result(editText.getQuery().toString(), "", "");//第一种返回，用户输入什么返回什么地址,无经纬度
            search(editText.getQuery().toString());
        } else if (id == R.id.iv_address_sug_clear) {
            editText.setQuery("", false);
            ivClear.setVisibility(View.GONE);
        }
    }

    public void result(String result, String lat, String lng) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_ADDRESS, result);
        intent.putExtra(RESULT_LAT, lat);
        intent.putExtra(RESULT_LNG, lng);
        setResult(RESULT_CODE, intent);
        finish();
        //返回地址字符串 "RESULT_ADDRESS" (resultcode=100)
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        search(newText);
        return false;
    }

    /**
     * POI检索
     *
     * @param newText 地址
     */
    private void search(String newText) {
        String add = newText.trim();
        if (TextUtils.isEmpty(add)) {
            ivClear.setVisibility(View.GONE);
            adapter.setNewData(null);
            btn.setEnabled(false);
            btn.setTextColor(ContextCompat.getColor(mContext, R.color.color_999));
        } else {
            ivClear.setVisibility(View.VISIBLE);
            search.requestSuggestion(new SuggestionSearchOption().keyword(add).city("中国"));
            btn.setEnabled(true);
            btn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }
    }
}