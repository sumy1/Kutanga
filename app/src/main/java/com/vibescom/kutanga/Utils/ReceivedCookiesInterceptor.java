package com.vibescom.kutanga.Utils;

import com.vibescom.kutanga.ApplicationManager;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor  implements Interceptor {



    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            Method.setCookies(ApplicationManager.getContext(), cookies);
        }
        return originalResponse;
    }





   /* SharedPreferences sharedPreferences = ApplicationManager.getContext().getSharedPreferences(BaseModel.kAppPreferences, Context.MODE_PRIVATE);

    public ReceivedCookiesInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
                Log.d("Cookies",cookies.toString());
            }


            // Save the cookies (I saved in SharedPrefrence), you save whereever you want to save
            SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("COOKIES",cookies);
                editor.commit();
        }
        return originalResponse;
    }*/
}