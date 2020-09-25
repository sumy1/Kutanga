package com.vibescom.kutanga.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

public class PRMaterialEditText extends TextInputEditText {
    public PRMaterialEditText(Context context) {
        super(context);
        createFont();
    }

    public PRMaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }

    public PRMaterialEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppins_regular.otf");
        setTypeface(font);
    }
}
