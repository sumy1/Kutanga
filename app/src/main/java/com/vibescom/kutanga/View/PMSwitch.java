package com.vibescom.kutanga.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.SwitchCompat;

public class PMSwitch extends SwitchCompat {
    public PMSwitch(Context context) {
        super(context);
        createFont();
    }

    public PMSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }



    public PMSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppins_medium.otf");
        setTypeface(font);
    }
}
