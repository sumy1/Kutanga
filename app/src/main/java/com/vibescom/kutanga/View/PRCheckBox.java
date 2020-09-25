package com.vibescom.kutanga.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class PRCheckBox extends CheckBox {
    public PRCheckBox(Context context) {
        super(context);
        createFont();
    }

    public PRCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }



    public PRCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppins_regular.otf");
        setTypeface(font);
    }
}
