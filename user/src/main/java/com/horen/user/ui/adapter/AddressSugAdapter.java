package com.horen.user.ui.adapter;
import android.text.TextUtils;
import android.widget.TextView;

import com.baidu.mapapi.search.sug.SuggestionResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.horen.base.util.FormatUtil;
import com.horen.user.R;

import java.util.List;

/**
 * Created by HOREN on 2018/2/26.
 */
public class AddressSugAdapter extends BaseQuickAdapter<SuggestionResult.SuggestionInfo, BaseViewHolder> {
    private String word;
    public AddressSugAdapter(int layoutResId, List<SuggestionResult.SuggestionInfo> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder holder, SuggestionResult.SuggestionInfo item) {
//        public String key;//关键词
//        public String city;//城市
//        public String district;//区
//        public String uid;
//        public String tag;
//        public String address;
//        String s = item.key+">>key\n" + item.city+">>city\n" + item.district+">>district\n" + item.uid+">>uid\n" + item.tag+">>tag\n" + item.address+">>address\n";
        //holder.setText(R.id.tv_address_sug_road, item.key);//检索建议：关键词
        TextView tv = holder.getView(R.id.tv_address_sug_road);
        FormatUtil.setColorText(tv,item.key,word,mContext.getResources().getColor(R.color.color_main_color));
        if (TextUtils.isEmpty(item.district)){
            holder.setText(R.id.tv_address_sug_city, item.city);
        } else {
            holder.setText(R.id.tv_address_sug_city, item.city+"-"+item.district);//检索建议：城市-区
        }
    }

    public void setKeyWord(String word) {
        this.word = word;
    }
}