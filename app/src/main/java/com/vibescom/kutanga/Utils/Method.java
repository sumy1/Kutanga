package com.vibescom.kutanga.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.vibescom.kutanga.Models.BaseModel;

import java.util.HashSet;

public class Method {

    public static HashSet<String> getCookies(Context context) {
        SharedPreferences mcpPreferences = context.getSharedPreferences(BaseModel.kAppPreferences, Context.MODE_PRIVATE);
        return (HashSet<String>) mcpPreferences.getStringSet("cookies", new HashSet<String>());
    }

    public static boolean setCookies(Context context, HashSet<String> cookies) {
        SharedPreferences mcpPreferences = context.getSharedPreferences(BaseModel.kAppPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putStringSet("cookies", cookies).commit();
    }
}
