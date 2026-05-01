package com.aivox.base.databinding;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.base.C0874R;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.util.BaseAppUtils;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class MyBindingAdapter_header<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ViewDataBinding binding;
    private int headerLayoutId;
    private int itemLayoutId;
    private Context mContext;
    public List<T> mData;
    public OnItemClickListener mOnItemClickListener;
    public OnNullViewItemClickListener onNullViewItemClickListener;
    private boolean mShowFooter = true;
    private boolean mShowNull = false;
    private boolean mShowHeader = true;
    Random mRandom = new Random(System.currentTimeMillis());
    boolean isDeleteAble = true;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }

    public MyBindingAdapter_header(Context context, int i, int i2) {
        this.mContext = context;
        this.headerLayoutId = i;
        this.itemLayoutId = i2;
    }

    public void setmDate(List<T> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int i = (this.mShowFooter ? 1 : 0) + (this.mShowHeader ? 1 : 0);
        List<T> list = this.mData;
        return list == null ? i : i + list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (i == 0 && this.mShowHeader) {
            return 4;
        }
        List<T> list = this.mData;
        if (list == null || list.size() == 0) {
            return 3;
        }
        if (this.mShowFooter && i + 1 == getItemCount()) {
            return isShowNull() ? 5 : 2;
        }
        return 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 4) {
            this.binding = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), this.headerLayoutId, viewGroup, false);
            return new HeaderBindingViewHolder(this.binding);
        }
        if (i == 1) {
            this.binding = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), this.itemLayoutId, viewGroup, false);
            return new ItemBindingViewHolder(this.binding);
        }
        if (i == 2) {
            ViewDataBinding viewDataBindingInflate = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), C0874R.layout.footer, viewGroup, false);
            this.binding = viewDataBindingInflate;
            viewDataBindingInflate.getRoot().setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            return new FooterViewHolder(this.binding);
        }
        if (i == 5) {
            ViewDataBinding viewDataBindingInflate2 = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), C0874R.layout.include_null, viewGroup, false);
            this.binding = viewDataBindingInflate2;
            viewDataBindingInflate2.getRoot().setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            return new FooterViewHolder(this.binding);
        }
        ViewDataBinding viewDataBindingInflate3 = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), C0874R.layout.include_null_view, viewGroup, false);
        this.binding = viewDataBindingInflate3;
        viewDataBindingInflate3.getRoot().setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return new NullViewHolder(this.binding);
    }

    public T getItem(int i) {
        List<T> list = this.mData;
        if (list == null) {
            return null;
        }
        return list.get(i);
    }

    public boolean isShowHeader() {
        return this.mShowHeader;
    }

    public void isShowHeader(boolean z) {
        this.mShowHeader = z;
    }

    public void isShowFooter(boolean z) {
        this.mShowFooter = z;
    }

    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    public void isShowNull(boolean z) {
        this.mShowNull = z;
    }

    public boolean isShowNull() {
        return this.mShowNull;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnNullViewItemClickListener(OnNullViewItemClickListener onNullViewItemClickListener) {
        this.onNullViewItemClickListener = onNullViewItemClickListener;
    }

    public static void showImage1(ImageView imageView, String str, Drawable drawable) {
        ImageLoaderFactory.getLoader().displayImage(imageView, str, drawable);
    }

    public void add(T t) {
        int iNextInt = this.mRandom.nextInt(this.mData.size() + 1);
        this.mData.add(iNextInt, t);
        notifyItemInserted(iNextInt);
    }

    public void remove(int i) {
        if (this.mData.size() != 0 && this.isDeleteAble) {
            this.isDeleteAble = false;
            this.mData.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, getItemCount());
            new Thread(new Runnable() { // from class: com.aivox.base.databinding.MyBindingAdapter_header$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m331x54d844a1();
                }
            }).start();
        }
    }

    /* JADX INFO: renamed from: lambda$remove$0$com-aivox-base-databinding-MyBindingAdapter_header */
    /* synthetic */ void m331x54d844a1() {
        try {
            Thread.sleep(120L);
            this.isDeleteAble = true;
        } catch (InterruptedException e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }
}
