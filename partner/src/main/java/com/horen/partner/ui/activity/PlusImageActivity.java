package com.horen.partner.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.partner.R;
import com.horen.base.base.BaseActivity;
import com.horen.partner.adapter.ViewPagerAdapter;
import com.horen.partner.ui.activity.customer.AddPotentialCustomerActivity;

import java.util.ArrayList;

public class PlusImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager viewPager; //展示图片的ViewPager
    private TextView positionTv; //图片的位置，第几张图片
    private ArrayList<String> imgList; //图片的数据源
    private int mPosition; //第几张图片
    private ViewPagerAdapter mAdapter;
    private ImageView iv_back;
    private TextView iv_delete;

    @Override
    public int getLayoutId() {
        return R.layout.activity_plus_image;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        imgList = getIntent().getStringArrayListExtra("imageList");
        mPosition = getIntent().getIntExtra("imagePosition", 0);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        positionTv = (TextView) findViewById(R.id.position_tv);
        iv_back = (ImageView) findViewById(R.id.back_iv);
        iv_delete = (TextView) findViewById(R.id.delete_iv);
        iv_back.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);

        mAdapter = new ViewPagerAdapter(this, imgList);
        viewPager.setAdapter(mAdapter);
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
    }

    //删除图片
    private void deletePic() {
        imgList.remove(mPosition); //从数据源移除删除的图片
        setPosition();
    }

    //设置当前位置
    private void setPosition() {
        positionTv.setText(mPosition + 1 + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }

    //返回上一个页面
    private void back() {
        Intent intent = getIntent();
        intent.putStringArrayListExtra("imageList", imgList);
        setResult(AddPotentialCustomerActivity.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        positionTv.setText(position + 1 + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        if (v == iv_back) {
            back();
        }
        if (v == iv_delete) {
            deletePic();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
