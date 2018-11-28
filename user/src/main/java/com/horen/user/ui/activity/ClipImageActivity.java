package com.horen.user.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.horen.base.widget.HRToolbar;
import com.horen.user.R;
import com.jaeger.library.StatusBarUtil;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 头像裁剪Activity
 */
public class ClipImageActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView btnCancel;
    private TextView btnOk;

    private HRToolbar mToolBar;
    private TextView mLeftTv;
    private TextView mToolBarTitleTv;
    private ImageView mRightIv;
    private TextView mRightTv;

    /**
     * 裁剪图片
     */
    public static final int CLIP_IMAG = 1001;

    private CropImageView mCropImageView;

    public static void startActivity(Activity activity, Uri imageUri) {
        Intent intent = new Intent();
        intent.setData(imageUri);
        intent.setClass(activity, ClipImageActivity.class);
        activity.startActivityForResult(intent, CLIP_IMAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_clip_image);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_main_color), 0);
        initView(savedInstanceState);
    }

    /**
     * 初始化组件
     *
     * @param savedInstanceState
     */
    public void initView(Bundle savedInstanceState) {
        mToolBar = (HRToolbar) findViewById(R.id.tool_bar);
        mCropImageView = (CropImageView) findViewById(R.id.cropImageView);
        btnCancel = (TextView) findViewById(R.id.btn_cancel);
        btnOk = (TextView) findViewById(R.id.bt_ok);
        //设置点击事件监听器
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        if (savedInstanceState == null) {
            Uri data = getIntent().getData();
            mCropImageView.setImageUriAsync(data);
        }
        setSimpleToolbar(mToolBar.getToolbar());
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_cancel) {
            finish();
        } else if (v.getId() == R.id.bt_ok) {
            generateUriAndReturn();
        }
    }


    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap = mCropImageView.getCroppedImage(500, 500);
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    protected void setSimpleToolbar(@NonNull Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
