package com.horen.user.utils;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;


/**
 * Created by BuZhiheng on 2016/4/5.
 * Desc 相机,图片处理
 */
public class PictureUtil {
    public static final int PIC_CAMERA = 1;

    /**
     * 调用系统拍照
     *
     * @param mContext 上线文
     */
    public static String startCamera(AppCompatActivity mContext) {
        /**
         * 调起手机拍照功能,拍照完毕 返回图片绝对路径
         * */
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.MediaColumns.DISPLAY_NAME,
                "picture" + new Date().toString());
        values.put(MediaStore.Images.ImageColumns.DESCRIPTION, "this is a picture");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        Uri imageFilePath = mContext.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, imageFilePath);
        mContext.startActivityForResult(camera, PIC_CAMERA);
        return FileUtil.getRealFilePathFromUri(mContext, imageFilePath);
    }
}