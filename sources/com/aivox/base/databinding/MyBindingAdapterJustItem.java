package com.aivox.base.databinding;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.util.BaseAppUtils;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class MyBindingAdapterJustItem<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ViewDataBinding binding;
    private int layoutId;
    private Context mContext;
    public List<T> mData;
    public OnItemClickListener mOnItemClickListener;
    public OnItemLongClickListener onItemLongClickListener;
    Random mRandom = new Random(System.currentTimeMillis());
    boolean isDeleteAble = true;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }

    public MyBindingAdapterJustItem(Context context, int i) {
        this.mContext = context;
        this.layoutId = i;
    }

    public void setmDate(List<T> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<T> list = this.mData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.binding = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), this.layoutId, viewGroup, false);
        return new ItemBindingViewHolder(this.binding);
    }

    public T getItem(int i) {
        List<T> list = this.mData;
        if (list == null) {
            return null;
        }
        return list.get(i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public static void showImage1(ImageView imageView, String str, Drawable drawable) {
        ImageLoaderFactory.getLoader().displayImage(imageView, str, drawable);
    }

    public static void showImage1(ImageView imageView, int i, Drawable drawable) {
        ImageLoaderFactory.getLoader().displayImage(imageView, i, drawable);
    }

    public void add(T t) {
        this.mData.add(t);
        notifyItemInserted(this.mData.size() - 1);
    }

    public void add(int i, T t) {
        this.mData.add(i, t);
        notifyItemInserted(i);
    }

    public void remove(int i, int i2) {
        if (this.mData.size() != 0 && this.isDeleteAble) {
            this.isDeleteAble = false;
            this.mData.remove(i);
            notifyItemRemoved(i2);
            notifyItemRangeChanged(i2, getItemCount());
            new Thread(new Runnable() { // from class: com.aivox.base.databinding.MyBindingAdapterJustItem$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m330x894c2bea();
                }
            }).start();
        }
    }

    /* JADX INFO: renamed from: lambda$remove$0$com-aivox-base-databinding-MyBindingAdapterJustItem */
    /* synthetic */ void m330x894c2bea() {
        try {
            Thread.sleep(120L);
            this.isDeleteAble = true;
        } catch (InterruptedException e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }
}
