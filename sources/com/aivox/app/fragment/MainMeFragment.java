package com.aivox.app.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.DeviceRightsAdapter;
import com.aivox.app.databinding.FragmentMainMeBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.http.HttpException;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.router.action.SetAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.http.AudioService;
import com.aivox.common.http.UserService;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.DeviceRightsBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.FunctionRightsBean;
import com.aivox.common.model.LeftTimeBean;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.BadgeUtils;
import com.aivox.common.util.LanguageUtils;
import com.aivox.common.view.LanguageSelectView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.SettingTileView;
import com.aivox.common_ui.antishake.AntiShake;
import com.aivox.common_ui.update.UpdateBean;
import com.aivox.common_ui.update.UpdateManager;
import com.blankj.utilcode.util.CollectionUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MainMeFragment extends BaseBindingFragment implements OnViewClickListener {
    private DeviceRightsAdapter mAdapter;
    private FragmentMainMeBinding mBinding;
    private final CompositeDisposable mComDis = new CompositeDisposable();
    private final List<DeviceRightsBean> mList = new ArrayList();
    private boolean mTimeIsAboutUp;
    private UpdateManager mUpdateManager;
    private UserInfo mUserInfo;

    public static MainMeFragment newInstance() {
        return new MainMeFragment();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        String string;
        String nickname;
        String nickname2;
        FragmentMainMeBinding fragmentMainMeBindingInflate = FragmentMainMeBinding.inflate(layoutInflater, viewGroup, false);
        this.mBinding = fragmentMainMeBindingInflate;
        fragmentMainMeBindingInflate.setClickListener(this);
        UserInfo userInfo = new SQLiteDataBaseManager(this.mActivity).getUserInfo();
        this.mUserInfo = userInfo;
        String str = "";
        if (userInfo != null) {
            if (TextUtils.isEmpty(userInfo.getAvatar())) {
                this.mBinding.ivHead.setVisibility(4);
            } else {
                this.mBinding.ivHead.setVisibility(0);
                ImageLoaderFactory.getLoader().displayImage(this.mBinding.ivHead, this.mUserInfo.getAvatar(), C1034R.drawable.bg_purple_round);
            }
            TextView textView = this.mBinding.tvName;
            if (BaseStringUtil.isEmpty(this.mUserInfo.getNickname())) {
                if (BaseStringUtil.isEmpty(this.mUserInfo.getPhone())) {
                    nickname = this.mUserInfo.getEmail();
                } else {
                    nickname = this.mUserInfo.getPhone();
                }
            } else {
                nickname = this.mUserInfo.getNickname();
            }
            textView.setText(nickname);
            TextView textView2 = this.mBinding.tvHead;
            if (BaseStringUtil.isEmpty(this.mUserInfo.getNickname())) {
                if (BaseStringUtil.isEmpty(this.mUserInfo.getEmail())) {
                    nickname2 = "";
                } else {
                    nickname2 = this.mUserInfo.getPhone();
                }
            } else {
                nickname2 = this.mUserInfo.getNickname();
            }
            textView2.setText(nickname2);
        }
        this.mBinding.stvTranscribe.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m282xfd32e0e8(view2);
            }
        });
        this.mBinding.stvTranslate.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m284x8f34cf6a(view2);
            }
        });
        int defaultLangFromLocal = LanguageUtils.getDefaultLangFromLocal(2);
        int defaultLangFromLocal2 = LanguageUtils.getDefaultLangFromLocal(0);
        int defaultLangFromLocal3 = LanguageUtils.getDefaultLangFromLocal(1);
        SettingTileView settingTileView = this.mBinding.stvTranscribe;
        if (defaultLangFromLocal == MyEnum.TRANSLATE_LANGUAGE.NONE.type) {
            string = "";
        } else {
            string = getString(MyEnum.TRANSLATE_LANGUAGE.getLanguage(defaultLangFromLocal).textRes);
        }
        settingTileView.setMsg(string);
        SettingTileView settingTileView2 = this.mBinding.stvTranslate;
        if (defaultLangFromLocal2 != MyEnum.TRANSLATE_LANGUAGE.NONE.type) {
            str = getString(MyEnum.TRANSLATE_LANGUAGE.getLanguage(defaultLangFromLocal2).textRes) + " → " + getString(MyEnum.TRANSLATE_LANGUAGE.getLanguage(defaultLangFromLocal3).textRes);
        }
        settingTileView2.setMsg(str);
        this.mBinding.stvCheckUpdate.setMsg("v" + BaseAppUtils.getVersionName());
        this.mAdapter = new DeviceRightsAdapter(C0726R.layout.item_device_rights);
        this.mBinding.rvDeviceRights.setAdapter(this.mAdapter);
        this.mBinding.rvDeviceRights.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m285x5835c6ab(baseQuickAdapter, view2, i);
            }
        });
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$1$com-aivox-app-fragment-MainMeFragment */
    /* synthetic */ void m282xfd32e0e8(View view2) {
        LanguageUtils.showLangSelectView(this.mContext, 1, true, new LanguageSelectView.LangSelectListener() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda11
            @Override // com.aivox.common.view.LanguageSelectView.LangSelectListener
            public final void onLangSelected(MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
                this.f$0.m281x3431e9a7(translate_language, translate_language2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$0$com-aivox-app-fragment-MainMeFragment */
    /* synthetic */ void m281x3431e9a7(MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
        LanguageUtils.setDefaultLang(this.mContext, 1, translate_language.type, translate_language2.type);
        this.mBinding.stvTranscribe.setMsg(getString(translate_language.textRes));
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$3$com-aivox-app-fragment-MainMeFragment */
    /* synthetic */ void m284x8f34cf6a(View view2) {
        LanguageUtils.showLangSelectView(this.mContext, 2, false, new LanguageSelectView.LangSelectListener() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda14
            @Override // com.aivox.common.view.LanguageSelectView.LangSelectListener
            public final void onLangSelected(MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
                this.f$0.m283xc633d829(translate_language, translate_language2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$2$com-aivox-app-fragment-MainMeFragment */
    /* synthetic */ void m283xc633d829(MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
        LanguageUtils.setDefaultLang(this.mContext, 2, translate_language.type, translate_language2.type);
        this.mBinding.stvTranslate.setMsg(getString(translate_language.textRes) + " → " + getString(translate_language2.textRes));
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$4$com-aivox-app-fragment-MainMeFragment */
    /* synthetic */ void m285x5835c6ab(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (view2.getId() == C0726R.id.cl_bottom) {
            this.mAdapter.switchExpandStatus();
            if (this.mAdapter.isExpand()) {
                this.mAdapter.setNewData(this.mList);
            } else {
                this.mAdapter.setNewData(this.mList.subList(0, 1));
            }
        }
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        this.mBinding = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.mBinding.stvTxtSize.setMsg(getFontMsgBySize(((Float) SPUtil.get(SPUtil.AUDIO_FONT_SIZE, Float.valueOf(17.0f))).floatValue()));
        reqUserinfo();
        reqLeftTimeAndDeviceRights();
        reqUpdate();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mComDis.clear();
    }

    private String getFontMsgBySize(float f) {
        if (f == 17.0f) {
            return getString(C0874R.string.set_textview_size_standard);
        }
        if (f == 13.0f) {
            return getString(C0874R.string.set_textview_size_small);
        }
        if (f == 20.0f) {
            return getString(C0874R.string.set_textview_size_medium);
        }
        if (f == 22.0f) {
            return getString(C0874R.string.set_textview_size_large);
        }
        return getString(C0874R.string.set_textview_size_standard);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqUserinfo() {
        this.mComDis.add(new UserService(this.mActivity).getUserInfo().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2366lambda$reqUserinfo$5$comaivoxappfragmentMainMeFragment((UserInfo) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2367lambda$reqUserinfo$6$comaivoxappfragmentMainMeFragment((Throwable) obj);
            }
        }));
        this.mComDis.add(new UserService(this.mActivity).getFunctionRights().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataHandle.getIns().setFunctionBean((FunctionRightsBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ((Throwable) obj).printStackTrace();
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$reqUserinfo$5$com-aivox-app-fragment-MainMeFragment, reason: not valid java name */
    /* synthetic */ void m2366lambda$reqUserinfo$5$comaivoxappfragmentMainMeFragment(UserInfo userInfo) throws Exception {
        String nickname;
        String nickname2;
        if (this.mActivity == null || this.mActivity.isFinishing() || this.mActivity.isDestroyed()) {
            return;
        }
        this.mUserInfo = userInfo;
        DataHandle.getIns().setHasSetPwd(userInfo.getIsPassword() == 1);
        new SQLiteDataBaseManager(this.mActivity).insertUserInfo(userInfo);
        SPUtil.put(SPUtil.USER_AVATAR, userInfo.getAvatar());
        if (TextUtils.isEmpty(userInfo.getAvatar())) {
            this.mBinding.ivHead.setVisibility(4);
        } else {
            this.mBinding.ivHead.setVisibility(0);
            ImageLoaderFactory.getLoader().displayImage(this.mBinding.ivHead, userInfo.getAvatar(), C1034R.drawable.bg_purple_round);
        }
        TextView textView = this.mBinding.tvName;
        if (BaseStringUtil.isEmpty(this.mUserInfo.getNickname())) {
            if (BaseStringUtil.isEmpty(this.mUserInfo.getPhone())) {
                nickname = this.mUserInfo.getEmail();
            } else {
                nickname = this.mUserInfo.getPhone();
            }
        } else {
            nickname = this.mUserInfo.getNickname();
        }
        textView.setText(nickname);
        TextView textView2 = this.mBinding.tvHead;
        if (BaseStringUtil.isEmpty(this.mUserInfo.getNickname())) {
            if (BaseStringUtil.isEmpty(this.mUserInfo.getEmail())) {
                nickname2 = "";
            } else {
                nickname2 = this.mUserInfo.getPhone();
            }
        } else {
            nickname2 = this.mUserInfo.getNickname();
        }
        textView2.setText(nickname2);
    }

    /* JADX INFO: renamed from: lambda$reqUserinfo$6$com-aivox-app-fragment-MainMeFragment, reason: not valid java name */
    /* synthetic */ void m2367lambda$reqUserinfo$6$comaivoxappfragmentMainMeFragment(Throwable th) throws Exception {
        th.printStackTrace();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda12
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqUserinfo();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqLeftTimeAndDeviceRights() {
        this.mComDis.add(new UserService(this.mActivity).getLeftTime().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m288xa851446f((LeftTimeBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m289x71523bb0((Throwable) obj);
            }
        }));
        this.mComDis.add(new UserService(this.mActivity).getDeviceRights().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m286x1122e260((List) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m287xda23d9a1((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$reqLeftTimeAndDeviceRights$8$com-aivox-app-fragment-MainMeFragment */
    /* synthetic */ void m288xa851446f(LeftTimeBean leftTimeBean) throws Exception {
        int i;
        SPUtil.put(SPUtil.LEFT_CURRENCY_TIME, Integer.valueOf(leftTimeBean.getGeneralTime()));
        SPUtil.put(SPUtil.IS_TRANS_TIME_OUT, Boolean.valueOf(leftTimeBean.getGeneralTime() <= 5));
        if (this.mUserInfo.isVip()) {
            this.mTimeIsAboutUp = leftTimeBean.getGeneralTime() <= 10800;
        } else {
            this.mTimeIsAboutUp = leftTimeBean.getGeneralTime() <= 1800;
        }
        if (!this.mTimeIsAboutUp || System.currentTimeMillis() - ((Long) SPUtil.getWithUid(SPUtil.LATEST_TIME_NOTICE_TIME_STAMP, 0L)).longValue() <= 86400000) {
            return;
        }
        SPUtil.putWithUid(SPUtil.LATEST_TIME_NOTICE_TIME_STAMP, Long.valueOf(System.currentTimeMillis()));
        Context context = this.mContext;
        Integer numValueOf = Integer.valueOf(C0874R.string.reminder);
        if (leftTimeBean.getGeneralTime() <= 1800) {
            i = C0874R.string.time_reminder_30_min;
        } else {
            i = C0874R.string.time_reminder_3_hour;
        }
        DialogUtils.showDialogWithDefBtnAndSingleListener(context, numValueOf, Integer.valueOf(i), null, false, true);
    }

    /* JADX INFO: renamed from: lambda$reqLeftTimeAndDeviceRights$9$com-aivox-app-fragment-MainMeFragment */
    /* synthetic */ void m289x71523bb0(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda0
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqLeftTimeAndDeviceRights();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$reqLeftTimeAndDeviceRights$10$com-aivox-app-fragment-MainMeFragment */
    /* synthetic */ void m286x1122e260(List list) throws Exception {
        if (CollectionUtils.isNotEmpty(list)) {
            this.mList.clear();
            this.mList.addAll(list);
            if (list.size() > 1) {
                this.mBinding.clDeviceFeature.setVisibility(8);
                this.mBinding.rvDeviceRights.setVisibility(0);
                if (this.mAdapter.isExpand()) {
                    this.mAdapter.setNewData(this.mList);
                    return;
                } else {
                    this.mAdapter.setNewData(this.mList.subList(0, 1));
                    return;
                }
            }
            this.mBinding.clDeviceFeature.setVisibility(0);
            this.mBinding.rvDeviceRights.setVisibility(8);
            DeviceRightsBean deviceRightsBean = this.mList.get(0);
            this.mBinding.tvDeviceName.setText(deviceRightsBean.getDeviceName());
            this.mBinding.tvDeviceLeftExpMsg.setText(DateUtil.getDateForHomeList(deviceRightsBean.getDevicePrivilegeExpireAt(), true));
            if (deviceRightsBean.getRecordTimeUnlimited().booleanValue()) {
                this.mBinding.tvDeviceLeftTimeMsg.setText(C0874R.string.device_unlimited_use);
                this.mBinding.ivDeviceInfinity.setVisibility(0);
                return;
            } else {
                this.mBinding.tvDeviceLeftTimeMsg.setText(BaseStringUtil.getHourStr(deviceRightsBean.getGeneralTime().intValue(), "h"));
                this.mBinding.ivDeviceInfinity.setVisibility(8);
                return;
            }
        }
        this.mBinding.clDeviceFeature.setVisibility(8);
    }

    /* JADX INFO: renamed from: lambda$reqLeftTimeAndDeviceRights$11$com-aivox-app-fragment-MainMeFragment */
    /* synthetic */ void m287xda23d9a1(Throwable th) throws Exception {
        th.printStackTrace();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            BaseAppUtils.printErrorMsg(th);
        }
    }

    private void reqUpdate() {
        new AudioService(this.mContext).checkUpdate().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2364lambda$reqUpdate$13$comaivoxappfragmentMainMeFragment((UpdateBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2365lambda$reqUpdate$14$comaivoxappfragmentMainMeFragment((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqUpdate$13$com-aivox-app-fragment-MainMeFragment, reason: not valid java name */
    /* synthetic */ void m2364lambda$reqUpdate$13$comaivoxappfragmentMainMeFragment(final UpdateBean updateBean) throws Exception {
        this.mUpdateManager = new UpdateManager(this.mContext);
        this.mBinding.stvCheckUpdate.changeMsgDotVisibility(this.mUpdateManager.isUpdate(updateBean.getNewVersion()));
        this.mBinding.stvCheckUpdate.setMsg("v" + BaseAppUtils.getVersionName());
        this.mBinding.stvCheckUpdate.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainMeFragment$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2363lambda$reqUpdate$12$comaivoxappfragmentMainMeFragment(updateBean, view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqUpdate$12$com-aivox-app-fragment-MainMeFragment, reason: not valid java name */
    /* synthetic */ void m2363lambda$reqUpdate$12$comaivoxappfragmentMainMeFragment(UpdateBean updateBean, View view2) {
        if (this.mUpdateManager.isUpdate(updateBean.getNewVersion())) {
            this.mUpdateManager.simpleCheck(updateBean);
        } else {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.soft_update_no));
        }
    }

    /* JADX INFO: renamed from: lambda$reqUpdate$14$com-aivox-app-fragment-MainMeFragment, reason: not valid java name */
    /* synthetic */ void m2365lambda$reqUpdate$14$comaivoxappfragmentMainMeFragment(Throwable th) throws Exception {
        th.printStackTrace();
        this.mBinding.stvCheckUpdate.changeMsgDotVisibility(false);
        this.mBinding.stvCheckUpdate.setMsg("v" + BaseAppUtils.getVersionName());
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mContext);
        } else {
            BaseAppUtils.printErrorMsg(th);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        if (!z) {
            BadgeUtils.badgerRemoveAll(getActivity());
        }
        super.onHiddenChanged(z);
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        if (eventBean.getFrom() == 15) {
            DialogUtils.showLoadingDialog(this.mContext);
            reqUserinfo();
            reqLeftTimeAndDeviceRights();
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(view2)) {
            return;
        }
        int id = view2.getId();
        if (id == C0726R.id.cl_head) {
            ARouterUtils.startWithActivity(this.mActivity, AccountAction.SET_USERINFO);
            return;
        }
        if (id == C0726R.id.tv_do_purchase) {
            AppUtils.jumpToPurchase(this.mActivity, this.mUserInfo.isVip());
            return;
        }
        if (id == C0726R.id.stv_storage_usage) {
            ARouterUtils.startWithActivity(this.mActivity, SetAction.SET_STORAGE_SPACE);
            return;
        }
        if (id == C0726R.id.stv_benefits_code) {
            BaseAppUtils.startActivityForWeb(this.mActivity, getString(C0874R.string.h5_path_coupon), getString(C0874R.string.my_page_benefits_code), ARouterUtils.getClass(MainAction.WEB));
            return;
        }
        if (id == C0726R.id.cl_time_left) {
            if (((Integer) SPUtil.get(SPUtil.LEFT_CURRENCY_TIME, 0)).intValue() > 0) {
                ARouterUtils.startWithActivity(this.mActivity, SetAction.SET_TIME_DETAIL);
                return;
            } else {
                AppUtils.jumpToPurchase(this.mActivity, this.mUserInfo.isVip());
                return;
            }
        }
        if (id == C0726R.id.stv_txt_size) {
            ARouterUtils.startWithActivity(this.mActivity, SetAction.SET_AUDIO_FONT_SIZE);
            return;
        }
        if (id == C0726R.id.stv_txt_privacy) {
            BaseAppUtils.startActivityForWeb(this.mActivity, getString(C0874R.string.h5_path_privacy), getString(C0874R.string.set_privacy_policy), ARouterUtils.getClass(MainAction.WEB));
            return;
        }
        if (id == C0726R.id.stv_contact_us) {
            ARouterUtils.startWithActivity(this.mActivity, SetAction.SET_CONTACT_US);
            return;
        }
        if (id == C0726R.id.stv_membership) {
            ARouterUtils.startWithActivity(this.mActivity, SetAction.SET_MEMBERSHIP);
        } else if (id == C0726R.id.stv_check_update) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.soft_update_no));
        } else {
            int i = C0726R.id.stv_vip_feature;
        }
    }
}
