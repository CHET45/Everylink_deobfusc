package com.lxj.xpopupext.popup;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.contrarywind.view.WheelView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopupext.C2296R;
import com.lxj.xpopupext.listener.ISelectTimeCallback;
import com.lxj.xpopupext.listener.TimePickerListener;
import com.lxj.xpopupext.view.WheelTime;
import java.text.ParseException;
import java.util.Calendar;

/* JADX INFO: loaded from: classes4.dex */
public class TimePickerPopup extends BottomPopupView {
    TextView btnCancel;
    TextView btnConfirm;
    private Calendar date;
    public int dividerColor;
    private Calendar endDate;
    private int endYear;
    private boolean isLunar;
    private int itemTextSize;
    private int itemsVisibleCount;
    public float lineSpace;
    private Mode mode;
    boolean showLabel;
    private Calendar startDate;
    private int startYear;
    public int textColorCenter;
    public int textColorOut;
    public TimePickerListener timePickerListener;
    private WheelTime wheelTime;

    public enum Mode {
        YMDHMS,
        YMDHM,
        YMDH,
        YMD,
        YM,
        Y
    }

    public TimePickerPopup(Context context) {
        super(context);
        this.mode = Mode.YMD;
        this.isLunar = false;
        this.startYear = 0;
        this.endYear = 0;
        this.itemsVisibleCount = 7;
        this.itemTextSize = 18;
        this.date = Calendar.getInstance();
        this.dividerColor = -2763307;
        this.lineSpace = 2.4f;
        this.textColorOut = -5723992;
        this.textColorCenter = -14013910;
        this.showLabel = true;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return C2296R.layout._xpopup_ext_time_picker;
    }

    /* JADX INFO: renamed from: com.lxj.xpopupext.popup.TimePickerPopup$4 */
    static /* synthetic */ class C23064 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopupext$popup$TimePickerPopup$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$lxj$xpopupext$popup$TimePickerPopup$Mode = iArr;
            try {
                iArr[Mode.Y.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopupext$popup$TimePickerPopup$Mode[Mode.YM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopupext$popup$TimePickerPopup$Mode[Mode.YMD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopupext$popup$TimePickerPopup$Mode[Mode.YMDH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$lxj$xpopupext$popup$TimePickerPopup$Mode[Mode.YMDHM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public boolean[] mode2type() {
        int i = C23064.$SwitchMap$com$lxj$xpopupext$popup$TimePickerPopup$Mode[this.mode.ordinal()];
        if (i == 1) {
            return new boolean[]{true, false, false, false, false, false};
        }
        if (i == 2) {
            return new boolean[]{true, true, false, false, false, false};
        }
        if (i == 3) {
            return new boolean[]{true, true, true, false, false, false};
        }
        if (i == 4) {
            return new boolean[]{true, true, true, true, false, false};
        }
        if (i == 5) {
            return new boolean[]{true, true, true, true, true, false};
        }
        return new boolean[]{true, true, true, true, true, true};
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        this.btnCancel = (TextView) findViewById(C2296R.id.btnCancel);
        this.btnConfirm = (TextView) findViewById(C2296R.id.btnConfirm);
        this.btnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopupext.popup.TimePickerPopup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                TimePickerPopup.this.dismiss();
            }
        });
        this.btnConfirm.setTextColor(XPopup.getPrimaryColor());
        this.btnConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopupext.popup.TimePickerPopup.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (TimePickerPopup.this.timePickerListener != null) {
                    try {
                        TimePickerPopup.this.timePickerListener.onTimeConfirm(WheelTime.dateFormat.parse(TimePickerPopup.this.wheelTime.getTime()), view2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                TimePickerPopup.this.dismiss();
            }
        });
        initWheelTime((LinearLayout) findViewById(C2296R.id.timepicker));
        if (this.popupInfo.isDarkTheme) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        this.btnCancel.setTextColor(Color.parseColor("#999999"));
        this.btnConfirm.setTextColor(Color.parseColor("#ffffff"));
        getPopupImplView().setBackground(XPopupUtils.createDrawable(getResources().getColor(C2296R.color._xpopup_dark_color), this.popupInfo.borderRadius, this.popupInfo.borderRadius, 0.0f, 0.0f));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        this.btnCancel.setTextColor(Color.parseColor("#666666"));
        this.btnConfirm.setTextColor(Color.parseColor("#222222"));
        getPopupImplView().setBackground(XPopupUtils.createDrawable(getResources().getColor(C2296R.color._xpopup_light_color), this.popupInfo.borderRadius, this.popupInfo.borderRadius, 0.0f, 0.0f));
    }

    private void initWheelTime(LinearLayout linearLayout) {
        int i;
        WheelTime wheelTime = new WheelTime(linearLayout, mode2type(), 17, this.itemTextSize);
        this.wheelTime = wheelTime;
        if (this.timePickerListener != null) {
            wheelTime.setSelectChangeCallback(new ISelectTimeCallback() { // from class: com.lxj.xpopupext.popup.TimePickerPopup.3
                @Override // com.lxj.xpopupext.listener.ISelectTimeCallback
                public void onTimeSelectChanged() {
                    try {
                        TimePickerPopup.this.timePickerListener.onTimeChanged(WheelTime.dateFormat.parse(TimePickerPopup.this.wheelTime.getTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        this.wheelTime.setLunarMode(this.isLunar);
        int i2 = this.startYear;
        if (i2 != 0 && (i = this.endYear) != 0 && i2 <= i) {
            applyYear();
        }
        Calendar calendar = this.startDate;
        if (calendar == null || this.endDate == null) {
            if (calendar != null) {
                if (calendar.get(1) < 1900) {
                    throw new IllegalArgumentException("The startDate can not as early as 1900");
                }
                applyDateRange();
            } else {
                Calendar calendar2 = this.endDate;
                if (calendar2 != null && calendar2.get(1) > 2100) {
                    throw new IllegalArgumentException("The endDate should not be later than 2100");
                }
                applyDateRange();
            }
        } else {
            if (calendar.getTimeInMillis() > this.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("startDate can't be later than endDate");
            }
            applyDateRange();
        }
        setTime();
        if (this.showLabel) {
            this.wheelTime.setLabels(getResources().getString(C2296R.string._xpopup_ext_year), getResources().getString(C2296R.string._xpopup_ext_month), getResources().getString(C2296R.string._xpopup_ext_day), getResources().getString(C2296R.string._xpopup_ext_hours), getResources().getString(C2296R.string._xpopup_ext_minutes), getResources().getString(C2296R.string._xpopup_ext_seconds));
        }
        this.wheelTime.setItemsVisible(this.itemsVisibleCount);
        this.wheelTime.setAlphaGradient(true);
        this.wheelTime.setCyclic(true);
        this.wheelTime.setDividerColor(this.popupInfo.isDarkTheme ? Color.parseColor("#444444") : this.dividerColor);
        this.wheelTime.setDividerType(WheelView.DividerType.FILL);
        this.wheelTime.setLineSpacingMultiplier(this.lineSpace);
        this.wheelTime.setTextColorOut(this.textColorOut);
        this.wheelTime.setTextColorCenter(this.popupInfo.isDarkTheme ? Color.parseColor("#CCCCCC") : this.textColorCenter);
        this.wheelTime.isCenterLabel(false);
    }

    public TimePickerPopup setShowLabel(boolean z) {
        this.showLabel = z;
        return this;
    }

    public TimePickerPopup setTimePickerListener(TimePickerListener timePickerListener) {
        this.timePickerListener = timePickerListener;
        return this;
    }

    public TimePickerPopup setItemTextSize(int i) {
        this.itemTextSize = i;
        return this;
    }

    public TimePickerPopup setMode(Mode mode) {
        this.mode = mode;
        return this;
    }

    public TimePickerPopup setLunar(boolean z) {
        this.isLunar = z;
        return this;
    }

    public TimePickerPopup setItemsVisibleCount(int i) {
        this.itemsVisibleCount = i;
        return this;
    }

    public TimePickerPopup setLineSpace(float f) {
        this.lineSpace = f;
        return this;
    }

    public TimePickerPopup setDefaultDate(Calendar calendar) {
        this.date = calendar;
        return this;
    }

    public TimePickerPopup setYearRange(int i, int i2) {
        this.startYear = i;
        this.endYear = i2;
        return this;
    }

    public TimePickerPopup setDateRange(Calendar calendar, Calendar calendar2) {
        this.startDate = calendar;
        this.endDate = calendar2;
        return this;
    }

    private void applyYear() {
        this.wheelTime.setStartYear(this.startYear);
        this.wheelTime.setEndYear(this.endYear);
    }

    private void applyDateRange() {
        this.wheelTime.setRangDate(this.startDate, this.endDate);
        initDefaultSelectedDate();
    }

    private void initDefaultSelectedDate() {
        Calendar calendar = this.startDate;
        if (calendar != null && this.endDate != null) {
            Calendar calendar2 = this.date;
            if (calendar2 == null || calendar2.getTimeInMillis() < this.startDate.getTimeInMillis() || this.date.getTimeInMillis() > this.endDate.getTimeInMillis()) {
                this.date = this.startDate;
                return;
            }
            return;
        }
        if (calendar != null) {
            this.date = calendar;
            return;
        }
        Calendar calendar3 = this.endDate;
        if (calendar3 != null) {
            this.date = calendar3;
        }
    }

    private void setTime() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = this.date;
        if (calendar2 == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            i = calendar.get(1);
            i2 = calendar.get(2);
            i3 = calendar.get(5);
            i4 = calendar.get(11);
            i5 = calendar.get(12);
            i6 = calendar.get(13);
        } else {
            i = calendar2.get(1);
            i2 = this.date.get(2);
            i3 = this.date.get(5);
            i4 = this.date.get(11);
            i5 = this.date.get(12);
            i6 = this.date.get(13);
        }
        int i7 = i4;
        int i8 = i3;
        int i9 = i2;
        WheelTime wheelTime = this.wheelTime;
        wheelTime.setPicker(i, i9, i8, i7, i5, i6);
    }
}
