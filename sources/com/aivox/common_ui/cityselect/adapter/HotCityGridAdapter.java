package com.aivox.common_ui.cityselect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.cityselect.bean.City;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HotCityGridAdapter extends BaseAdapter {
    private List<City> mCities;
    private Context mContext;

    public static class HotCityViewHolder {
        TextView name;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public HotCityGridAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<City> list) {
        this.mCities = list;
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<City> list = this.mCities;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public City getItem(int i) {
        List<City> list = this.mCities;
        if (list == null) {
            return null;
        }
        return list.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view2, ViewGroup viewGroup) {
        HotCityViewHolder hotCityViewHolder;
        if (view2 == null) {
            view2 = LayoutInflater.from(this.mContext).inflate(C1034R.layout.cp_item_hot_city_gridview, viewGroup, false);
            hotCityViewHolder = new HotCityViewHolder();
            hotCityViewHolder.name = (TextView) view2.findViewById(C1034R.id.tv_hot_city_name);
            view2.setTag(hotCityViewHolder);
        } else {
            hotCityViewHolder = (HotCityViewHolder) view2.getTag();
        }
        hotCityViewHolder.name.setText(this.mCities.get(i).getName());
        return view2;
    }
}
