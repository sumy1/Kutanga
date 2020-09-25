package com.vibescom.kutanga.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class PRRadioButton extends RadioButton {
    public PRRadioButton(Context context) {
        super(context);
        createFont();
    }

    public PRRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }



    public PRRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppins_regular.otf");
        setTypeface(font);
    }
}
