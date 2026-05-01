package com.aivox.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.aivox.app.C0726R;
import com.aivox.app.activity.MyPhotoBrowseActivity;
import com.aivox.app.databinding.ActivityMyPhotoBrowseBinding;
import com.aivox.base.C0874R;
import com.aivox.base.img.imageloader.GlideApp;
import com.aivox.base.img.imageloader.GlideRequests;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.GalleryUtils;
import com.aivox.base.util.LayoutUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.model.ConferenceSummary;
import com.aivox.common.model.Transcribe;
import com.aivox.common.p003db.entity.GlassImageEntity;
import com.aivox.common.p003db.maneger.GlassImageDbManager;
import com.aivox.common_ui.C1034R;
import com.blankj.utilcode.util.BarUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.lcodecore.tkrefreshlayout.C2015R;
import java.io.File;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class MyPhotoBrowseActivity extends BaseFragmentActivity {
    private int curIndex;
    private GlassImageDbManager glassImageDbManager;
    private List<Transcribe.TagImgBean> imageUrlList;
    private boolean isFullScreen = false;
    private ActivityMyPhotoBrowseBinding mBinding;
    private MyPagerAdapter myPagerAdapter;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        initData();
    }

    private void initData() {
        int i;
        ActivityMyPhotoBrowseBinding activityMyPhotoBrowseBinding = (ActivityMyPhotoBrowseBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_my_photo_browse);
        this.mBinding = activityMyPhotoBrowseBinding;
        LayoutUtil.fitSystemInsets(activityMyPhotoBrowseBinding.titleView, true);
        this.glassImageDbManager = GlassImageDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.mBinding.titleView.setRightListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2155lambda$initData$0$comaivoxappactivityMyPhotoBrowseActivity(view2);
            }
        });
        this.mBinding.viewPager.setOffscreenPageLimit(3);
        this.mBinding.viewPager.setPageMargin(10);
        if (getIntent().getSerializableExtra("conferenceSummary") != null) {
            this.imageUrlList = ((ConferenceSummary) getIntent().getSerializableExtra("conferenceSummary")).getImageList();
        } else {
            this.imageUrlList = ((Transcribe) getIntent().getSerializableExtra("transcribe")).getImageList();
        }
        boolean booleanExtra = getIntent().getBooleanExtra("showDelete", true);
        boolean booleanExtra2 = getIntent().getBooleanExtra("showIndicator", true);
        this.mBinding.titleView.setRightIconVisible(booleanExtra ? 0 : 8);
        this.mBinding.indicator.setVisibility(0);
        this.myPagerAdapter = new MyPagerAdapter(this.imageUrlList);
        this.mBinding.viewPager.setAdapter(this.myPagerAdapter);
        this.mBinding.viewPager.setCurrentItem(getIntent().getIntExtra("pos", 0));
        if (booleanExtra2) {
            this.mBinding.indicator.setText(String.format(Locale.getDefault(), "%d/%d", Integer.valueOf(this.mBinding.viewPager.getCurrentItem() + 1), Integer.valueOf(this.imageUrlList.size())));
        } else {
            this.mBinding.indicator.setVisibility(8);
        }
        this.curIndex = this.mBinding.viewPager.getCurrentItem();
        this.mBinding.container.setOnTouchListener(new View.OnTouchListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.m2156lambda$initData$1$comaivoxappactivityMyPhotoBrowseActivity(view2, motionEvent);
            }
        });
        this.mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float f, int i3) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                int i3;
                MyPhotoBrowseActivity.this.curIndex = i2;
                MyPhotoBrowseActivity.this.mBinding.indicator.setText(String.format(Locale.getDefault(), "%d/%d", Integer.valueOf(i2 + 1), Integer.valueOf(MyPhotoBrowseActivity.this.imageUrlList.size())));
                MyPhotoBrowseActivity.this.myPagerAdapter.pauseOtherVideos(i2);
                GlideRequests glideRequestsWith = GlideApp.with(MyPhotoBrowseActivity.this.context);
                if (MyPhotoBrowseActivity.this.glassImageDbManager.queryIsFavorite(((Transcribe.TagImgBean) MyPhotoBrowseActivity.this.imageUrlList.get(MyPhotoBrowseActivity.this.curIndex)).getId())) {
                    i3 = C1034R.drawable.ic_gallery_favorited;
                } else {
                    i3 = C1034R.drawable.ic_gallery_favorite;
                }
                glideRequestsWith.load(Integer.valueOf(i3)).into(MyPhotoBrowseActivity.this.mBinding.ivFavorite);
            }
        });
        boolean booleanExtra3 = getIntent().getBooleanExtra("isGallery", false);
        this.mBinding.glFunction.setVisibility(booleanExtra3 ? 0 : 8);
        if (booleanExtra3) {
            GlideRequests glideRequestsWith = GlideApp.with(this.context);
            if (this.glassImageDbManager.queryIsFavorite(this.imageUrlList.get(this.curIndex).getId())) {
                i = C1034R.drawable.ic_gallery_favorited;
            } else {
                i = C1034R.drawable.ic_gallery_favorite;
            }
            glideRequestsWith.load(Integer.valueOf(i)).into(this.mBinding.ivFavorite);
            this.mBinding.llSave.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2157lambda$initData$2$comaivoxappactivityMyPhotoBrowseActivity(view2);
                }
            });
            this.mBinding.llFavorite.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2158lambda$initData$3$comaivoxappactivityMyPhotoBrowseActivity(view2);
                }
            });
            this.mBinding.llDelete.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2160lambda$initData$5$comaivoxappactivityMyPhotoBrowseActivity(view2);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$initData$0$com-aivox-app-activity-MyPhotoBrowseActivity, reason: not valid java name */
    /* synthetic */ void m2155lambda$initData$0$comaivoxappactivityMyPhotoBrowseActivity(View view2) {
        File file = new File(this.imageUrlList.get(this.curIndex).getImageUrl());
        if (this.imageUrlList.get(this.curIndex).getImageUrl().toLowerCase().endsWith(".mp4")) {
            GalleryUtils.saveVideoToGallery(this, file, file.getName());
        } else {
            GalleryUtils.saveImageToGallery(this, file, file.getName());
        }
        ToastUtil.showShort(Integer.valueOf(C0874R.string.save_success));
    }

    /* JADX INFO: renamed from: lambda$initData$1$com-aivox-app-activity-MyPhotoBrowseActivity, reason: not valid java name */
    /* synthetic */ boolean m2156lambda$initData$1$comaivoxappactivityMyPhotoBrowseActivity(View view2, MotionEvent motionEvent) {
        return this.mBinding.viewPager.dispatchTouchEvent(motionEvent);
    }

    /* JADX INFO: renamed from: lambda$initData$2$com-aivox-app-activity-MyPhotoBrowseActivity, reason: not valid java name */
    /* synthetic */ void m2157lambda$initData$2$comaivoxappactivityMyPhotoBrowseActivity(View view2) {
        File file = new File(this.imageUrlList.get(this.curIndex).getImageUrl());
        if (this.imageUrlList.get(this.curIndex).getImageUrl().toLowerCase().endsWith(".mp4")) {
            GalleryUtils.saveVideoToGallery(this, file, file.getName());
        } else {
            GalleryUtils.saveImageToGallery(this, file, file.getName());
        }
        ToastUtil.showShort(Integer.valueOf(C0874R.string.save_success));
    }

    /* JADX INFO: renamed from: lambda$initData$3$com-aivox-app-activity-MyPhotoBrowseActivity, reason: not valid java name */
    /* synthetic */ void m2158lambda$initData$3$comaivoxappactivityMyPhotoBrowseActivity(View view2) {
        int i;
        this.glassImageDbManager.updateFavorite(this.imageUrlList.get(this.curIndex).getId(), !this.glassImageDbManager.queryIsFavorite(this.imageUrlList.get(this.curIndex).getId()));
        GlideRequests glideRequestsWith = GlideApp.with(this.context);
        if (this.glassImageDbManager.queryIsFavorite(this.imageUrlList.get(this.curIndex).getId())) {
            i = C1034R.drawable.ic_gallery_favorited;
        } else {
            i = C1034R.drawable.ic_gallery_favorite;
        }
        glideRequestsWith.load(Integer.valueOf(i)).into(this.mBinding.ivFavorite);
    }

    /* JADX INFO: renamed from: lambda$initData$5$com-aivox-app-activity-MyPhotoBrowseActivity, reason: not valid java name */
    /* synthetic */ void m2160lambda$initData$5$comaivoxappactivityMyPhotoBrowseActivity(View view2) {
        DialogUtils.showDeleteDialog(this, Integer.valueOf(C0874R.string.delete_files_title), Integer.valueOf(C0874R.string.confirm_delete_files_prompt), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$$ExternalSyntheticLambda0
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m2159lambda$initData$4$comaivoxappactivityMyPhotoBrowseActivity(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, null, true, false, C0874R.string.delete, C0874R.string.cancel, 0);
    }

    /* JADX INFO: renamed from: lambda$initData$4$com-aivox-app-activity-MyPhotoBrowseActivity, reason: not valid java name */
    /* synthetic */ void m2159lambda$initData$4$comaivoxappactivityMyPhotoBrowseActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        GlassImageEntity glassImageEntityQuery = this.glassImageDbManager.query(this.imageUrlList.get(this.curIndex).getId());
        if (!TextUtils.isEmpty(glassImageEntityQuery.getImagePath()) && !glassImageEntityQuery.getImagePath().startsWith("http")) {
            if (glassImageEntityQuery.getImagePath().endsWith(".mp4")) {
                FileUtils.deleteFile(new File(glassImageEntityQuery.getImagePath().replace(".mp4", ".jpg")).getPath());
            }
            FileUtils.deleteFile(new File(glassImageEntityQuery.getImagePath()).getPath());
        }
        this.glassImageDbManager.delete(Long.valueOf(this.imageUrlList.get(this.curIndex).getId()));
        this.imageUrlList.remove(this.curIndex);
        this.myPagerAdapter.notifyDataSetChanged();
        if (this.imageUrlList.isEmpty()) {
            finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        this.myPagerAdapter.pauseAllVideos();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.myPagerAdapter.releaseAll();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean z = configuration.orientation == 2;
        this.isFullScreen = z;
        if (z) {
            hideSystemUI();
        } else {
            showSystemUI();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(4102);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(0);
    }

    public void toggleScreenOrientation() {
        if (this.isFullScreen) {
            setRequestedOrientation(-1);
        } else {
            setRequestedOrientation(0);
        }
    }

    public class MyPagerAdapter extends PagerAdapter {
        private final SparseArray<View> mActiveViews = new SparseArray<>();
        private final List<Transcribe.TagImgBean> mediaList;

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view2, Object obj) {
            return view2 == obj;
        }

        public MyPagerAdapter(List<Transcribe.TagImgBean> list) {
            this.mediaList = list;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.mediaList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View viewInflate;
            Transcribe.TagImgBean tagImgBean = this.mediaList.get(i);
            if (tagImgBean.getImageUrl() != null && tagImgBean.getImageUrl().toLowerCase().endsWith(".mp4")) {
                viewInflate = LayoutInflater.from(MyPhotoBrowseActivity.this).inflate(C0726R.layout.item_adapter_video, viewGroup, false);
                VideoViewHolder videoViewHolder = new VideoViewHolder(viewInflate);
                videoViewHolder.bind(tagImgBean.getImageUrl());
                viewInflate.setTag(videoViewHolder);
            } else {
                viewInflate = LayoutInflater.from(MyPhotoBrowseActivity.this).inflate(C0726R.layout.item_adapter_image, viewGroup, false);
                PhotoView photoView = (PhotoView) viewInflate.findViewById(C0726R.id.photo_view);
                loadImage(tagImgBean.getImageUrl(), photoView);
                photoView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$MyPagerAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f$0.m155xbdb512b5(view2);
                    }
                });
            }
            viewGroup.addView(viewInflate);
            this.mActiveViews.put(i, viewInflate);
            return viewInflate;
        }

        /* JADX INFO: renamed from: lambda$instantiateItem$0$com-aivox-app-activity-MyPhotoBrowseActivity$MyPagerAdapter */
        /* synthetic */ void m155xbdb512b5(View view2) {
            MyPhotoBrowseActivity.this.finish();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            View view2 = (View) obj;
            if (view2.getTag() instanceof VideoViewHolder) {
                ((VideoViewHolder) view2.getTag()).release();
            }
            this.mActiveViews.remove(i);
            viewGroup.removeView(view2);
        }

        private void loadImage(Object obj, ImageView imageView) {
            if (obj == null || imageView == null) {
                return;
            }
            if (obj instanceof String) {
                ImageLoaderFactory.getLoader().displayImage(imageView, (String) obj, C2015R.drawable.anim_loading_view);
            } else if (obj instanceof Integer) {
                ImageLoaderFactory.getLoader().displayImage(imageView, ((Integer) obj).intValue(), C1034R.mipmap.ic_img_load_fail);
            } else if (obj instanceof Uri) {
                ImageLoaderFactory.getLoader().displayImage(imageView, (Uri) obj, C1034R.mipmap.ic_img_load_fail);
            }
        }

        public void pauseOtherVideos(int i) {
            for (int i2 = 0; i2 < this.mActiveViews.size(); i2++) {
                int iKeyAt = this.mActiveViews.keyAt(i2);
                if (iKeyAt != i) {
                    View view2 = this.mActiveViews.get(iKeyAt);
                    if (view2.getTag() instanceof VideoViewHolder) {
                        ((VideoViewHolder) view2.getTag()).pause();
                    }
                }
            }
        }

        public void pauseAllVideos() {
            for (int i = 0; i < this.mActiveViews.size(); i++) {
                View viewValueAt = this.mActiveViews.valueAt(i);
                if (viewValueAt.getTag() instanceof VideoViewHolder) {
                    ((VideoViewHolder) viewValueAt.getTag()).pause();
                }
            }
        }

        public void releaseAll() {
            for (int i = 0; i < this.mActiveViews.size(); i++) {
                View viewValueAt = this.mActiveViews.valueAt(i);
                if (viewValueAt.getTag() instanceof VideoViewHolder) {
                    ((VideoViewHolder) viewValueAt.getTag()).release();
                }
            }
            this.mActiveViews.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VideoViewHolder {
        ImageView btmPause;
        ImageView btmPlay;
        ConstraintLayout clRoot;
        Group groupBottom;
        View itemView;
        SeekBar seekBar;
        TextView tvTimeCur;
        TextView tvTimeMax;
        VideoView videoView;
        Handler updateHandler = new Handler(Looper.getMainLooper());
        int autoHideTime = 0;
        private final Runnable updateTimeRunnable = new Runnable() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity.VideoViewHolder.1
            @Override // java.lang.Runnable
            public void run() {
                if (VideoViewHolder.this.videoView == null || !VideoViewHolder.this.videoView.isPlaying()) {
                    return;
                }
                int currentPosition = VideoViewHolder.this.videoView.getCurrentPosition();
                VideoViewHolder.this.seekBar.setProgress(currentPosition);
                VideoViewHolder.this.tvTimeCur.setText(DateUtil.numberToTime(currentPosition / 1000));
                if (VideoViewHolder.this.groupBottom.getVisibility() == 0) {
                    VideoViewHolder.this.autoHideTime += 1000;
                    if (VideoViewHolder.this.autoHideTime > 3000) {
                        VideoViewHolder.this.groupBottom.setVisibility(4);
                        VideoViewHolder.this.autoHideTime = 0;
                    }
                }
                VideoViewHolder.this.updateHandler.postDelayed(this, 1000L);
            }
        };

        public VideoViewHolder(View view2) {
            this.itemView = view2;
            this.clRoot = (ConstraintLayout) view2.findViewById(C0726R.id.cl_root);
            this.videoView = (VideoView) this.itemView.findViewById(C0726R.id.video_view);
            this.seekBar = (SeekBar) this.itemView.findViewById(C0726R.id.sb_progress);
            this.btmPlay = (ImageView) this.itemView.findViewById(C0726R.id.play_start_iv);
            this.btmPause = (ImageView) this.itemView.findViewById(C0726R.id.play_stop_iv);
            this.groupBottom = (Group) this.itemView.findViewById(C0726R.id.group_bottom);
            this.tvTimeCur = (TextView) this.itemView.findViewById(C0726R.id.tv_time_cur);
            this.tvTimeMax = (TextView) this.itemView.findViewById(C0726R.id.tv_time_max);
            this.itemView.findViewById(C0726R.id.cl_control).setPadding(0, 0, 0, BarUtils.getNavBarHeight());
        }

        public void bind(String str) {
            if (str == null) {
                return;
            }
            this.videoView.setVideoURI(Uri.parse(str));
            this.clRoot.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$VideoViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m156x2ef64e55(view2);
                }
            });
            this.btmPlay.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$VideoViewHolder$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m157xc334bdf4(view2);
                }
            });
            this.btmPause.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$VideoViewHolder$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m158x57732d93(view2);
                }
            });
            this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity.VideoViewHolder.2
                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    if (z) {
                        VideoViewHolder.this.videoView.seekTo(i);
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                    VideoViewHolder.this.updateHandler.removeCallbacks(VideoViewHolder.this.updateTimeRunnable);
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (VideoViewHolder.this.videoView.isPlaying()) {
                        VideoViewHolder.this.updateHandler.post(VideoViewHolder.this.updateTimeRunnable);
                    }
                }
            });
            this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$VideoViewHolder$$ExternalSyntheticLambda3
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    this.f$0.m159xebb19d32(mediaPlayer);
                }
            });
            this.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$VideoViewHolder$$ExternalSyntheticLambda4
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer) {
                    this.f$0.m160x7ff00cd1(mediaPlayer);
                }
            });
            this.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.aivox.app.activity.MyPhotoBrowseActivity$VideoViewHolder$$ExternalSyntheticLambda5
                @Override // android.media.MediaPlayer.OnErrorListener
                public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    return MyPhotoBrowseActivity.VideoViewHolder.lambda$bind$5(mediaPlayer, i, i2);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$bind$0$com-aivox-app-activity-MyPhotoBrowseActivity$VideoViewHolder */
        /* synthetic */ void m156x2ef64e55(View view2) {
            this.autoHideTime = 0;
            Group group = this.groupBottom;
            group.setVisibility(group.getVisibility() != 4 ? 4 : 0);
        }

        /* JADX INFO: renamed from: lambda$bind$1$com-aivox-app-activity-MyPhotoBrowseActivity$VideoViewHolder */
        /* synthetic */ void m157xc334bdf4(View view2) {
            play();
        }

        /* JADX INFO: renamed from: lambda$bind$2$com-aivox-app-activity-MyPhotoBrowseActivity$VideoViewHolder */
        /* synthetic */ void m158x57732d93(View view2) {
            pause();
        }

        /* JADX INFO: renamed from: lambda$bind$3$com-aivox-app-activity-MyPhotoBrowseActivity$VideoViewHolder */
        /* synthetic */ void m159xebb19d32(MediaPlayer mediaPlayer) {
            mediaPlayer.setLooping(false);
            this.seekBar.setMax(mediaPlayer.getDuration());
            this.tvTimeCur.setText(DateUtil.numberToTime(0));
            this.tvTimeMax.setText(DateUtil.numberToTime(mediaPlayer.getDuration() / 1000));
            this.videoView.seekTo(0);
            this.btmPlay.setVisibility(0);
            this.btmPause.setVisibility(4);
        }

        /* JADX INFO: renamed from: lambda$bind$4$com-aivox-app-activity-MyPhotoBrowseActivity$VideoViewHolder */
        /* synthetic */ void m160x7ff00cd1(MediaPlayer mediaPlayer) {
            pause();
            this.videoView.seekTo(0);
            this.seekBar.setProgress(0);
            this.tvTimeCur.setText(DateUtil.numberToTime(0));
            this.groupBottom.setVisibility(0);
        }

        static /* synthetic */ boolean lambda$bind$5(MediaPlayer mediaPlayer, int i, int i2) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.video_play_failed));
            return true;
        }

        public void play() {
            this.btmPlay.setVisibility(4);
            this.btmPause.setVisibility(0);
            this.videoView.start();
            this.updateHandler.post(this.updateTimeRunnable);
            this.autoHideTime = 0;
        }

        public void pause() {
            this.btmPlay.setVisibility(0);
            this.btmPause.setVisibility(4);
            if (this.videoView.isPlaying()) {
                this.videoView.pause();
            }
            this.updateHandler.removeCallbacks(this.updateTimeRunnable);
            this.groupBottom.setVisibility(0);
        }

        public void release() {
            this.updateHandler.removeCallbacksAndMessages(null);
            VideoView videoView = this.videoView;
            if (videoView != null) {
                videoView.stopPlayback();
            }
        }
    }
}
