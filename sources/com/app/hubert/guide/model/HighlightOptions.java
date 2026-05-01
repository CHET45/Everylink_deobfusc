package com.app.hubert.guide.model;

import android.view.View;
import com.app.hubert.guide.listener.OnHighlightDrewListener;

/* JADX INFO: loaded from: classes.dex */
public class HighlightOptions {
    public View.OnClickListener onClickListener;
    public OnHighlightDrewListener onHighlightDrewListener;
    public RelativeGuide relativeGuide;

    public static class Builder {
        private HighlightOptions options = new HighlightOptions();

        public Builder setOnClickListener(View.OnClickListener onClickListener) {
            this.options.onClickListener = onClickListener;
            return this;
        }

        public Builder setRelativeGuide(RelativeGuide relativeGuide) {
            this.options.relativeGuide = relativeGuide;
            return this;
        }

        public Builder setOnHighlightDrewListener(OnHighlightDrewListener onHighlightDrewListener) {
            this.options.onHighlightDrewListener = onHighlightDrewListener;
            return this;
        }

        public HighlightOptions build() {
            return this.options;
        }
    }
}
