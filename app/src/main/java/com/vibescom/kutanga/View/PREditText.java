package com.vibescom.kutanga.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class PREditText extends EditText {
    public PREditText(Context context) {
        super(context);
        createFont();
    }

    public PREditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }



    public PREditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppins_regular.otf");
        setTypeface(font);
    }
}
