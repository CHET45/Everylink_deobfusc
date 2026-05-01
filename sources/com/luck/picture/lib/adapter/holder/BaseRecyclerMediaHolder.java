package com.luck.picture.lib.adapter.holder;

import android.content.Context;
import android.graphics.ColorFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.C2114R;
import com.luck.picture.lib.adapter.PictureImageGridAdapter;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.AnimUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.luck.picture.lib.utils.ValueOf;

/* JADX INFO: loaded from: classes3.dex */
public class BaseRecyclerMediaHolder extends RecyclerView.ViewHolder {
    public View btnCheck;
    private ColorFilter defaultColorFilter;
    public boolean isHandleMask;
    public boolean isSelectNumberStyle;
    public ImageView ivPicture;
    private PictureImageGridAdapter.OnItemClickListener listener;
    public Context mContext;
    private ColorFilter maskWhiteColorFilter;
    private ColorFilter selectColorFilter;
    public SelectorConfig selectorConfig;
    public TextView tvCheck;

    public static BaseRecyclerMediaHolder generate(ViewGroup viewGroup, int i, int i2, SelectorConfig selectorConfig) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(i2, viewGroup, false);
        if (i == 1) {
            return new CameraViewHolder(viewInflate);
        }
        if (i == 3) {
            return new VideoViewHolder(viewInflate, selectorConfig);
        }
        if (i == 4) {
            return new AudioViewHolder(viewInflate, selectorConfig);
        }
        return new ImageViewHolder(viewInflate, selectorConfig);
    }

    public BaseRecyclerMediaHolder(View view2) {
        super(view2);
    }

    public BaseRecyclerMediaHolder(View view2, SelectorConfig selectorConfig) {
        super(view2);
        this.selectorConfig = selectorConfig;
        Context context = view2.getContext();
        this.mContext = context;
        this.defaultColorFilter = StyleUtils.getColorFilter(context, C2114R.color.ps_color_20);
        this.selectColorFilter = StyleUtils.getColorFilter(this.mContext, C2114R.color.ps_color_80);
        this.maskWhiteColorFilter = StyleUtils.getColorFilter(this.mContext, C2114R.color.ps_color_half_white);
        SelectMainStyle selectMainStyle = this.selectorConfig.selectorStyle.getSelectMainStyle();
        this.isSelectNumberStyle = selectMainStyle.isSelectNumberStyle();
        this.ivPicture = (ImageView) view2.findViewById(C2114R.id.ivPicture);
        this.tvCheck = (TextView) view2.findViewById(C2114R.id.tvCheck);
        this.btnCheck = view2.findViewById(C2114R.id.btnCheck);
        boolean z = true;
        if (selectorConfig.selectionMode == 1 && selectorConfig.isDirectReturnSingle) {
            this.tvCheck.setVisibility(8);
            this.btnCheck.setVisibility(8);
        } else {
            this.tvCheck.setVisibility(0);
            this.btnCheck.setVisibility(0);
        }
        if (selectorConfig.isDirectReturnSingle || (selectorConfig.selectionMode != 1 && selectorConfig.selectionMode != 2)) {
            z = false;
        }
        this.isHandleMask = z;
        int adapterSelectTextSize = selectMainStyle.getAdapterSelectTextSize();
        if (StyleUtils.checkSizeValidity(adapterSelectTextSize)) {
            this.tvCheck.setTextSize(adapterSelectTextSize);
        }
        int adapterSelectTextColor = selectMainStyle.getAdapterSelectTextColor();
        if (StyleUtils.checkStyleValidity(adapterSelectTextColor)) {
            this.tvCheck.setTextColor(adapterSelectTextColor);
        }
        int selectBackground = selectMainStyle.getSelectBackground();
        if (StyleUtils.checkStyleValidity(selectBackground)) {
            this.tvCheck.setBackgroundResource(selectBackground);
        }
        int[] adapterSelectStyleGravity = selectMainStyle.getAdapterSelectStyleGravity();
        if (StyleUtils.checkArrayValidity(adapterSelectStyleGravity)) {
            if (this.tvCheck.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.tvCheck.getLayoutParams()).removeRule(21);
                for (int i : adapterSelectStyleGravity) {
                    ((RelativeLayout.LayoutParams) this.tvCheck.getLayoutParams()).addRule(i);
                }
            }
            if (this.btnCheck.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.btnCheck.getLayoutParams()).removeRule(21);
                for (int i2 : adapterSelectStyleGravity) {
                    ((RelativeLayout.LayoutParams) this.btnCheck.getLayoutParams()).addRule(i2);
                }
            }
            int adapterSelectClickArea = selectMainStyle.getAdapterSelectClickArea();
            if (StyleUtils.checkSizeValidity(adapterSelectClickArea)) {
                ViewGroup.LayoutParams layoutParams = this.btnCheck.getLayoutParams();
                layoutParams.width = adapterSelectClickArea;
                layoutParams.height = adapterSelectClickArea;
            }
        }
    }

    public void bindData(final LocalMedia localMedia, final int i) {
        localMedia.position = getAbsoluteAdapterPosition();
        selectedMedia(isSelected(localMedia));
        if (this.isSelectNumberStyle) {
            notifySelectNumberStyle(localMedia);
        }
        if (this.isHandleMask && this.selectorConfig.isMaxSelectEnabledMask) {
            dispatchHandleMask(localMedia);
        }
        String path = localMedia.getPath();
        if (localMedia.isEditorImage()) {
            path = localMedia.getCutPath();
        }
        loadCover(path);
        this.tvCheck.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                BaseRecyclerMediaHolder.this.btnCheck.performClick();
            }
        });
        this.btnCheck.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                int iOnSelected;
                if (localMedia.isMaxSelectEnabledMask() || BaseRecyclerMediaHolder.this.listener == null || (iOnSelected = BaseRecyclerMediaHolder.this.listener.onSelected(BaseRecyclerMediaHolder.this.tvCheck, i, localMedia)) == -1) {
                    return;
                }
                if (iOnSelected == 0) {
                    if (BaseRecyclerMediaHolder.this.selectorConfig.isSelectZoomAnim) {
                        if (BaseRecyclerMediaHolder.this.selectorConfig.onItemSelectAnimListener != null) {
                            BaseRecyclerMediaHolder.this.selectorConfig.onItemSelectAnimListener.onSelectItemAnim(BaseRecyclerMediaHolder.this.ivPicture, true);
                        } else {
                            AnimUtils.selectZoom(BaseRecyclerMediaHolder.this.ivPicture);
                        }
                    }
                } else if (iOnSelected == 1 && BaseRecyclerMediaHolder.this.selectorConfig.isSelectZoomAnim && BaseRecyclerMediaHolder.this.selectorConfig.onItemSelectAnimListener != null) {
                    BaseRecyclerMediaHolder.this.selectorConfig.onItemSelectAnimListener.onSelectItemAnim(BaseRecyclerMediaHolder.this.ivPicture, false);
                }
                BaseRecyclerMediaHolder baseRecyclerMediaHolder = BaseRecyclerMediaHolder.this;
                baseRecyclerMediaHolder.selectedMedia(baseRecyclerMediaHolder.isSelected(localMedia));
            }
        });
        this.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                if (BaseRecyclerMediaHolder.this.listener == null) {
                    return false;
                }
                BaseRecyclerMediaHolder.this.listener.onItemLongClick(view2, i);
                return false;
            }
        });
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (localMedia.isMaxSelectEnabledMask() || BaseRecyclerMediaHolder.this.listener == null) {
                    return;
                }
                if ((PictureMimeType.isHasImage(localMedia.getMimeType()) && BaseRecyclerMediaHolder.this.selectorConfig.isEnablePreviewImage) || BaseRecyclerMediaHolder.this.selectorConfig.isDirectReturnSingle || ((PictureMimeType.isHasVideo(localMedia.getMimeType()) && (BaseRecyclerMediaHolder.this.selectorConfig.isEnablePreviewVideo || BaseRecyclerMediaHolder.this.selectorConfig.selectionMode == 1)) || (PictureMimeType.isHasAudio(localMedia.getMimeType()) && (BaseRecyclerMediaHolder.this.selectorConfig.isEnablePreviewAudio || BaseRecyclerMediaHolder.this.selectorConfig.selectionMode == 1)))) {
                    BaseRecyclerMediaHolder.this.listener.onItemClick(BaseRecyclerMediaHolder.this.tvCheck, i, localMedia);
                } else {
                    BaseRecyclerMediaHolder.this.btnCheck.performClick();
                }
            }
        });
    }

    protected void loadCover(String str) {
        if (this.selectorConfig.imageEngine != null) {
            this.selectorConfig.imageEngine.loadGridImage(this.ivPicture.getContext(), str, this.ivPicture);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x006d, code lost:
    
        if (com.luck.picture.lib.config.PictureMimeType.isHasImage(r4.getMimeType()) == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x008b, code lost:
    
        if (com.luck.picture.lib.config.PictureMimeType.isHasVideo(r4.getMimeType()) == false) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void dispatchHandleMask(com.luck.picture.lib.entity.LocalMedia r4) {
        /*
            r3 = this;
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.getSelectCount()
            if (r0 <= 0) goto L98
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            java.util.ArrayList r0 = r0.getSelectedResult()
            boolean r0 = r0.contains(r4)
            if (r0 != 0) goto L98
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            boolean r0 = r0.isWithVideoImage
            r1 = 2147483647(0x7fffffff, float:NaN)
            r2 = 1
            if (r0 == 0) goto L3a
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.selectionMode
            if (r0 != r2) goto L2d
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.getSelectCount()
            if (r0 != r1) goto L98
            goto L8d
        L2d:
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.getSelectCount()
            com.luck.picture.lib.config.SelectorConfig r1 = r3.selectorConfig
            int r1 = r1.maxSelectNum
            if (r0 != r1) goto L98
            goto L8d
        L3a:
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            java.lang.String r0 = r0.getResultFirstMimeType()
            boolean r0 = com.luck.picture.lib.config.PictureMimeType.isHasVideo(r0)
            if (r0 == 0) goto L70
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.selectionMode
            if (r0 != r2) goto L4d
            goto L5d
        L4d:
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.maxVideoSelectNum
            if (r0 <= 0) goto L58
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.maxVideoSelectNum
            goto L5c
        L58:
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.maxSelectNum
        L5c:
            r1 = r0
        L5d:
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.getSelectCount()
            if (r0 == r1) goto L8d
            java.lang.String r0 = r4.getMimeType()
            boolean r0 = com.luck.picture.lib.config.PictureMimeType.isHasImage(r0)
            if (r0 == 0) goto L98
            goto L8d
        L70:
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.selectionMode
            if (r0 != r2) goto L77
            goto L7b
        L77:
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r1 = r0.maxSelectNum
        L7b:
            com.luck.picture.lib.config.SelectorConfig r0 = r3.selectorConfig
            int r0 = r0.getSelectCount()
            if (r0 == r1) goto L8d
            java.lang.String r0 = r4.getMimeType()
            boolean r0 = com.luck.picture.lib.config.PictureMimeType.isHasVideo(r0)
            if (r0 == 0) goto L98
        L8d:
            android.widget.ImageView r0 = r3.ivPicture
            android.graphics.ColorFilter r1 = r3.maskWhiteColorFilter
            r0.setColorFilter(r1)
            r4.setMaxSelectEnabledMask(r2)
            goto L9c
        L98:
            r0 = 0
            r4.setMaxSelectEnabledMask(r0)
        L9c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.dispatchHandleMask(com.luck.picture.lib.entity.LocalMedia):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectedMedia(boolean z) {
        if (this.tvCheck.isSelected() != z) {
            this.tvCheck.setSelected(z);
        }
        if (this.selectorConfig.isDirectReturnSingle) {
            this.ivPicture.setColorFilter(this.defaultColorFilter);
        } else {
            this.ivPicture.setColorFilter(z ? this.selectColorFilter : this.defaultColorFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSelected(LocalMedia localMedia) {
        LocalMedia compareLocalMedia;
        boolean zContains = this.selectorConfig.getSelectedResult().contains(localMedia);
        if (zContains && (compareLocalMedia = localMedia.getCompareLocalMedia()) != null && compareLocalMedia.isEditorImage()) {
            localMedia.setCutPath(compareLocalMedia.getCutPath());
            localMedia.setCut(!TextUtils.isEmpty(compareLocalMedia.getCutPath()));
            localMedia.setEditorImage(compareLocalMedia.isEditorImage());
        }
        return zContains;
    }

    private void notifySelectNumberStyle(LocalMedia localMedia) {
        this.tvCheck.setText("");
        for (int i = 0; i < this.selectorConfig.getSelectCount(); i++) {
            LocalMedia localMedia2 = this.selectorConfig.getSelectedResult().get(i);
            if (TextUtils.equals(localMedia2.getPath(), localMedia.getPath()) || localMedia2.getId() == localMedia.getId()) {
                localMedia.setNum(localMedia2.getNum());
                localMedia2.setPosition(localMedia.getPosition());
                this.tvCheck.setText(ValueOf.toString(Integer.valueOf(localMedia.getNum())));
            }
        }
    }

    public void setOnItemClickListener(PictureImageGridAdapter.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }
}
