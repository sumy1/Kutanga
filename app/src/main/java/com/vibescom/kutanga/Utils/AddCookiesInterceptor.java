package com.vibescom.kutanga.Utils;

import android.util.Log;

import com.vibescom.kutanga.ApplicationManager;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {



    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = Method.getCookies(ApplicationManager.getContext());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
        return chain.proceed(builder.build());
    }



   /* SharedPreferences sharedPreferences = ApplicationManager.getContext().getSharedPreferences(BaseModel.kAppPreferences, Context.MODE_PRIVATE);
    // We're storing our stuff in a database made just for cookies called PREF_COOKIES.
    // I reccomend you do this, and don't change this default value.
    private Context context;

    public static String getcookies;

    public AddCookiesInterceptor() {
        //this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> cookies = (HashSet<String>) sharedPreferences.getStringSet("COOKIES",new HashSet<>());

        Log.e("getCookies",cookies.toString());
        if (cookies != null) {
            for (String cookie: cookies) {
                // Use this cookie whereever you wanna use.

                getcookies=cookie;
                Log.d("Cookie_new", cookie);
            }
        }

        return chain.proceed(builder.build());
    }*/



}