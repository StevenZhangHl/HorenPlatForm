package com.horen.base.widget.flowlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horen.base.R;
import com.horen.base.app.BaseApp;
import com.horen.base.bean.OrderPopupBean;
import com.horen.base.listener.OnInitSelectedPosition;
import com.horen.base.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;

    private int currentPosition = 0; // 默认为0

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int line = (int) Math.ceil(mDataList.size() / 3.0);
        LinearLayout.LayoutParams lp;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_select_tag, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        OrderPopupBean t = (OrderPopupBean) mDataList.get(position);
        // 设置textview的宽度
        lp = (LinearLayout.LayoutParams) textView.getLayoutParams();
        float textWidth = (DisplayUtil.getScreenWidth(BaseApp.getAppContext()) -
                DisplayUtil.dip2px(20) * 2f - DisplayUtil.dip2px(20) * 2f) / 3f;
        lp.width = (int) textWidth;
        if (position <= (3 * (line - 1)) - 1) {
            lp.setMargins(DisplayUtil.dip2px(20), DisplayUtil.dip2px(20), 0, 0);
        } else {
            lp.setMargins(DisplayUtil.dip2px(20), DisplayUtil.dip2px(10), 0, DisplayUtil.dip2px(20));
        }
        textView.setLayoutParams(lp);
        if (position == currentPosition) {
            textView.setSelected(true);
        }
        //Tag标签
        textView.setText(t.getName());
        return view;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        if (position % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setSelectPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
