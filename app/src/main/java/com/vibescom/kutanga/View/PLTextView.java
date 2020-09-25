package com.vibescom.kutanga.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class PLTextView extends TextView {
    public PLTextView(Context context) {
        super(context);
        createFont();
    }

    public PLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createFont();
    }



    public PLTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createFont();
    }

    public void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppins_light.otf");
        setTypeface(font);
    }
}
