package com.vibescom.kutanga.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class PBTextView extends TextView {
    public PBTextView(Context context) {
        super(context);
        createFont();
    }

    public PBTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }



    public PBTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppins_semibold.otf");
        setTypeface(font);
    }
}
