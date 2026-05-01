package com.aivox.common.view;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.base.language.MultiLanguageUtil;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common.databinding.IncludeCountryCodePickerBinding;
import com.sahooz.library.countrypicker.Adapter;
import com.sahooz.library.countrypicker.Country;
import com.sahooz.library.countrypicker.Language;
import com.sahooz.library.countrypicker.PickCountryCallback;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class CountryCodePickerView extends BaseDialogViewWrapper {
    private final ArrayList<Country> allCountries;
    private Context context;
    private Language language;
    private final ArrayList<Country> selectedCountries;

    public CountryCodePickerView(Context context) {
        super(context);
        this.allCountries = new ArrayList<>();
        this.selectedCountries = new ArrayList<>();
    }

    public CountryCodePickerView(Context context, final PickCountryCallback pickCountryCallback) {
        super(context);
        ArrayList<Country> arrayList = new ArrayList<>();
        this.allCountries = arrayList;
        this.selectedCountries = new ArrayList<>();
        this.context = context;
        IncludeCountryCodePickerBinding includeCountryCodePickerBindingInflate = IncludeCountryCodePickerBinding.inflate(LayoutInflater.from(context), this, true);
        loadCountry();
        arrayList.clear();
        arrayList.addAll(Country.getAll());
        for (Country country : arrayList) {
            country.flag = 0;
            if (this.language == Language.ENGLISH) {
                if (country.code == 886) {
                    country.name = "Taiwan";
                } else if (country.code == 852) {
                    country.name = "Hong Kong";
                } else if (country.code == 853) {
                    country.name = "Macao";
                }
            } else if (country.code == 886) {
                country.name = "台湾";
            } else if (country.code == 852) {
                country.name = "香港";
            } else if (country.code == 853) {
                country.name = "澳门";
            }
        }
        this.selectedCountries.clear();
        this.selectedCountries.addAll(this.allCountries);
        final Adapter adapter = new Adapter(getContext());
        adapter.setCallback(new PickCountryCallback() { // from class: com.aivox.common.view.CountryCodePickerView$$ExternalSyntheticLambda0
            @Override // com.sahooz.library.countrypicker.PickCountryCallback
            public final void onPick(Country country2) {
                this.f$0.m2475lambda$new$0$comaivoxcommonviewCountryCodePickerView(pickCountryCallback, country2);
            }
        });
        adapter.setSelectedCountries(this.selectedCountries);
        includeCountryCodePickerBindingInflate.rvCountry.setAdapter(adapter);
        includeCountryCodePickerBindingInflate.rvCountry.setLayoutManager(new LinearLayoutManager(getContext()));
        includeCountryCodePickerBindingInflate.etSearch.addTextChangedListener(new TextWatcher() { // from class: com.aivox.common.view.CountryCodePickerView.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                String string = editable.toString();
                CountryCodePickerView.this.selectedCountries.clear();
                for (Country country2 : CountryCodePickerView.this.allCountries) {
                    if (country2.name.toLowerCase().contains(string.toLowerCase())) {
                        CountryCodePickerView.this.selectedCountries.add(country2);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        includeCountryCodePickerBindingInflate.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.CountryCodePickerView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2476lambda$new$1$comaivoxcommonviewCountryCodePickerView(pickCountryCallback, view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common-view-CountryCodePickerView, reason: not valid java name */
    /* synthetic */ void m2475lambda$new$0$comaivoxcommonviewCountryCodePickerView(PickCountryCallback pickCountryCallback, Country country) {
        if (pickCountryCallback != null) {
            pickCountryCallback.onPick(country);
        }
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-common-view-CountryCodePickerView, reason: not valid java name */
    /* synthetic */ void m2476lambda$new$1$comaivoxcommonviewCountryCodePickerView(PickCountryCallback pickCountryCallback, View view2) {
        if (pickCountryCallback != null) {
            pickCountryCallback.onPick(null);
        }
        this.mDialog.dismiss();
    }

    private void loadCountry() {
        try {
            int languageType = MultiLanguageUtil.getInstance().getLanguageType();
            if (languageType == 2) {
                this.language = Language.SIMPLIFIED_CHINESE;
            } else if (languageType == 3) {
                this.language = Language.TRADITIONAL_CHINESE;
            } else {
                this.language = Language.ENGLISH;
            }
            Country.load(this.context, this.language);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }
}
