package com.horen.partner.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.horen.partner.R;

/**
 * Author:Steven
 * Time:2018/9/5 14:19
 * Description:This isLeanTextView
 */
public class LeanTextView extends AppCompatTextView {
    public int getmDegrees() {
        return mDegrees;
    }

    public void setmDegrees(int mDegrees) {
        this.mDegrees = mDegrees;
        invalidate();
    }

    private int mDegrees;

    public LeanTextView(Context context) {
        super(context, null);
    }

    public LeanTextView(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.textViewStyle);
        this.setGravity(Gravity.CENTER);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LeanTextView);
        mDegrees = a.getDimensionPixelSize(R.styleable.LeanTextView_degree, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        canvas.rotate(mDegrees, this.getWidth() / 2f, this.getHeight() / 2f);
        super.onDraw(canvas);
        canvas.restore();
    }

}
