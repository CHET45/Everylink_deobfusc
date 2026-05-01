package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.aivox.base.C0874R;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.DateScrollSelectViewLayoutBinding;
import com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener;
import com.github.gzuliyujiang.wheelview.widget.WheelView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* JADX INFO: loaded from: classes.dex */
public class DateScrollSelectView extends BaseDialogViewWrapper {
    private final DateScrollSelectViewLayoutBinding mBinding;
    private final List<Integer> mDayList;
    private int mDayPosition;
    private final List<String> mMonthList;
    private final List<Integer> mYearList;

    public interface DateSelectListener {
        void onDateSelected(String str);
    }

    public DateScrollSelectView(Context context, final DateSelectListener dateSelectListener) {
        super(context);
        ArrayList arrayList = new ArrayList();
        this.mMonthList = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mYearList = arrayList2;
        this.mDayList = new ArrayList();
        this.mDayPosition = 0;
        DateScrollSelectViewLayoutBinding dateScrollSelectViewLayoutBindingInflate = DateScrollSelectViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = dateScrollSelectViewLayoutBindingInflate;
        int i = Calendar.getInstance().get(1);
        List list = (List) IntStream.rangeClosed(i - 100, i).boxed().collect(Collectors.toList());
        arrayList.addAll(Arrays.asList(context.getString(C0874R.string.birthday_month_january), context.getString(C0874R.string.birthday_month_february), context.getString(C0874R.string.birthday_month_march), context.getString(C0874R.string.birthday_month_april), context.getString(C0874R.string.birthday_month_may), context.getString(C0874R.string.birthday_month_june), context.getString(C0874R.string.birthday_month_july), context.getString(C0874R.string.birthday_month_august), context.getString(C0874R.string.birthday_month_september), context.getString(C0874R.string.birthday_month_october), context.getString(C0874R.string.birthday_month_november), context.getString(C0874R.string.birthday_month_december)).subList(0, 12));
        arrayList2.addAll(list);
        Collections.reverse(arrayList2);
        dateScrollSelectViewLayoutBindingInflate.wvDateMonth.setData(arrayList);
        dateScrollSelectViewLayoutBindingInflate.wvDateYear.setData(arrayList2);
        dateScrollSelectViewLayoutBindingInflate.wvDateMonth.setCyclicEnabled(true);
        dateScrollSelectViewLayoutBindingInflate.wvDateDay.setCyclicEnabled(true);
        dateScrollSelectViewLayoutBindingInflate.wvDateYear.setCyclicEnabled(true);
        dateScrollSelectViewLayoutBindingInflate.wvDateMonth.setOnWheelChangedListener(new OnWheelChangedListener() { // from class: com.aivox.common_ui.DateScrollSelectView.1
            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelLoopFinished(WheelView wheelView) {
            }

            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelScrollStateChanged(WheelView wheelView, int i2) {
            }

            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelScrolled(WheelView wheelView, int i2) {
            }

            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelSelected(WheelView wheelView, int i2) {
                DateScrollSelectView.this.updateDayRange();
            }
        });
        dateScrollSelectViewLayoutBindingInflate.wvDateDay.setOnWheelChangedListener(new OnWheelChangedListener() { // from class: com.aivox.common_ui.DateScrollSelectView.2
            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelLoopFinished(WheelView wheelView) {
            }

            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelScrollStateChanged(WheelView wheelView, int i2) {
            }

            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelScrolled(WheelView wheelView, int i2) {
            }

            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelSelected(WheelView wheelView, int i2) {
                DateScrollSelectView.this.mDayPosition = i2;
            }
        });
        dateScrollSelectViewLayoutBindingInflate.wvDateYear.setOnWheelChangedListener(new OnWheelChangedListener() { // from class: com.aivox.common_ui.DateScrollSelectView.3
            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelLoopFinished(WheelView wheelView) {
            }

            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelScrollStateChanged(WheelView wheelView, int i2) {
            }

            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelScrolled(WheelView wheelView, int i2) {
            }

            @Override // com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener
            public void onWheelSelected(WheelView wheelView, int i2) {
                DateScrollSelectView.this.updateDayRange();
            }
        });
        dateScrollSelectViewLayoutBindingInflate.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.DateScrollSelectView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2495lambda$new$0$comaivoxcommon_uiDateScrollSelectView(dateSelectListener, view2);
            }
        });
        dateScrollSelectViewLayoutBindingInflate.dtvTitle.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.DateScrollSelectView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2496lambda$new$1$comaivoxcommon_uiDateScrollSelectView(view2);
            }
        });
        updateDayRange();
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-DateScrollSelectView, reason: not valid java name */
    /* synthetic */ void m2495lambda$new$0$comaivoxcommon_uiDateScrollSelectView(DateSelectListener dateSelectListener, View view2) {
        int currentPosition = this.mBinding.wvDateMonth.getCurrentPosition() + 1;
        int iIntValue = this.mYearList.get(this.mBinding.wvDateYear.getCurrentPosition()).intValue();
        int iIntValue2 = this.mDayList.get(this.mBinding.wvDateDay.getCurrentPosition()).intValue();
        DecimalFormat decimalFormat = new DecimalFormat("00");
        dateSelectListener.onDateSelected(decimalFormat.format(currentPosition) + "-" + decimalFormat.format(iIntValue2) + "-" + iIntValue);
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-common_ui-DateScrollSelectView, reason: not valid java name */
    /* synthetic */ void m2496lambda$new$1$comaivoxcommon_uiDateScrollSelectView(View view2) {
        this.mDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDayRange() {
        int currentPosition = this.mBinding.wvDateMonth.getCurrentPosition();
        int iIntValue = this.mYearList.get(this.mBinding.wvDateYear.getCurrentPosition()).intValue();
        Calendar calendar = Calendar.getInstance();
        calendar.set(iIntValue, currentPosition, 1);
        List list = (List) IntStream.rangeClosed(1, calendar.getActualMaximum(5)).boxed().collect(Collectors.toList());
        this.mDayList.clear();
        this.mDayList.addAll(list);
        this.mBinding.wvDateDay.setData(this.mDayList, this.mDayPosition);
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
