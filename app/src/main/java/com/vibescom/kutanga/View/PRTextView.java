package com.vibescom.kutanga.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class PRTextView extends TextView {
    public PRTextView(Context context) {
        super(context);
        createFont();
    }

    public PRTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }



    public PRTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppins_regular.otf");
        setTypeface(font);
    }
}
