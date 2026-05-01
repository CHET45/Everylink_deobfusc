package com.luck.picture.lib.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.C2114R;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnAlbumItemClickListener;
import com.luck.picture.lib.style.AlbumWindowStyle;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class PictureAlbumAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<LocalMediaFolder> albumList;
    private OnAlbumItemClickListener onAlbumItemClickListener;
    private final SelectorConfig selectorConfig;

    public PictureAlbumAdapter(SelectorConfig selectorConfig) {
        this.selectorConfig = selectorConfig;
    }

    public void bindAlbumData(List<LocalMediaFolder> list) {
        this.albumList = new ArrayList(list);
    }

    public List<LocalMediaFolder> getAlbumList() {
        List<LocalMediaFolder> list = this.albumList;
        return list != null ? list : new ArrayList();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int layoutResource = InjectResourceSource.getLayoutResource(viewGroup.getContext(), 6, this.selectorConfig);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        if (layoutResource == 0) {
            layoutResource = C2114R.layout.ps_album_folder_item;
        }
        return new ViewHolder(layoutInflaterFrom.inflate(layoutResource, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final LocalMediaFolder localMediaFolder = this.albumList.get(i);
        String folderName = localMediaFolder.getFolderName();
        int folderTotalNum = localMediaFolder.getFolderTotalNum();
        String firstImagePath = localMediaFolder.getFirstImagePath();
        boolean z = false;
        viewHolder.tvSelectTag.setVisibility(localMediaFolder.isSelectTag() ? 0 : 4);
        LocalMediaFolder localMediaFolder2 = this.selectorConfig.currentLocalMediaFolder;
        View view2 = viewHolder.itemView;
        if (localMediaFolder2 != null && localMediaFolder.getBucketId() == localMediaFolder2.getBucketId()) {
            z = true;
        }
        view2.setSelected(z);
        if (PictureMimeType.isHasAudio(localMediaFolder.getFirstMimeType())) {
            viewHolder.ivFirstImage.setImageResource(C2114R.drawable.ps_audio_placeholder);
        } else if (this.selectorConfig.imageEngine != null) {
            this.selectorConfig.imageEngine.loadAlbumCover(viewHolder.itemView.getContext(), firstImagePath, viewHolder.ivFirstImage);
        }
        viewHolder.tvFolderName.setText(viewHolder.itemView.getContext().getString(C2114R.string.ps_camera_roll_num, folderName, Integer.valueOf(folderTotalNum)));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.PictureAlbumAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view3) {
                if (PictureAlbumAdapter.this.onAlbumItemClickListener == null) {
                    return;
                }
                PictureAlbumAdapter.this.onAlbumItemClickListener.onItemClick(i, localMediaFolder);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.albumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFirstImage;
        TextView tvFolderName;
        TextView tvSelectTag;

        public ViewHolder(View view2) {
            super(view2);
            this.ivFirstImage = (ImageView) view2.findViewById(C2114R.id.first_image);
            this.tvFolderName = (TextView) view2.findViewById(C2114R.id.tv_folder_name);
            this.tvSelectTag = (TextView) view2.findViewById(C2114R.id.tv_select_tag);
            AlbumWindowStyle albumWindowStyle = PictureAlbumAdapter.this.selectorConfig.selectorStyle.getAlbumWindowStyle();
            int albumAdapterItemBackground = albumWindowStyle.getAlbumAdapterItemBackground();
            if (albumAdapterItemBackground != 0) {
                view2.setBackgroundResource(albumAdapterItemBackground);
            }
            int albumAdapterItemSelectStyle = albumWindowStyle.getAlbumAdapterItemSelectStyle();
            if (albumAdapterItemSelectStyle != 0) {
                this.tvSelectTag.setBackgroundResource(albumAdapterItemSelectStyle);
            }
            int albumAdapterItemTitleColor = albumWindowStyle.getAlbumAdapterItemTitleColor();
            if (albumAdapterItemTitleColor != 0) {
                this.tvFolderName.setTextColor(albumAdapterItemTitleColor);
            }
            int albumAdapterItemTitleSize = albumWindowStyle.getAlbumAdapterItemTitleSize();
            if (albumAdapterItemTitleSize > 0) {
                this.tvFolderName.setTextSize(albumAdapterItemTitleSize);
            }
        }
    }

    public void setOnIBridgeAlbumWidget(OnAlbumItemClickListener onAlbumItemClickListener) {
        this.onAlbumItemClickListener = onAlbumItemClickListener;
    }
}
