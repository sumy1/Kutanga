package com.vibescom.kutanga;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.vibescom.kutanga.Broadcast.ReachabilityManager;
import com.vibescom.kutanga.ModelManager.ModelManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import androidx.multidex.MultiDexApplication;

import static com.vibescom.kutanga.Constants.Constants.kAppPreferencesLanguage;
import static com.vibescom.kutanga.Constants.Constants.kLangPref;

public class ApplicationManager extends MultiDexApplication {

    //Static Properties
    private static Context _Context;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public static final String TAG = ApplicationManager.class.getSimpleName();


    public static Context getContext() {
        return _Context;
    }
    public static ApplicationManager getInstance() {
        return (ApplicationManager) _Context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        _Context = getApplicationContext();

        //to initial Leak canary **not be removed
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            //this process is dedicated to LeakCanary for heap analysis.
            //you should not init your app in this process
            return;
        }*/
        //LeakCanary.install(this);

        //enableStrictMode();
        printKeyHash(getContext());

        setLanguageForApp(getContext(),kLangPref);

        // Initialize the SDK
        //Places.initialize(getApplicationContext(), getString(R.string.google_key));
        // Create a new Places client instance
        //Places.createClient(_Context);

        //We must initialize the ModelManager singleton object at the time of application launch.
        ModelManager.modelManager().initializeModelManager();
    }

    public static void setLanguageForApp(Context context,String languageToLoad){
        /*Locale locale;
        if(languageToLoad.equals("not-set")){ //use any value for default
            locale = Locale.getDefault();
        } else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());*/

        Locale myLocale = new Locale(languageToLoad);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }


    public static String getLanguageForApp(){
        SharedPreferences sharedPreferences = _Context.getSharedPreferences(kAppPreferencesLanguage, Context.MODE_PRIVATE);
        String selectedlanguage =sharedPreferences.getString(kLangPref, "en");

        return selectedlanguage;
    }

    public static void setConnectivityListener(ReachabilityManager.ConnectivityReceiverListener listener) {
        ReachabilityManager._ConnectivityReceiverListener = listener;
    }

    /**
     * detects things you might be doing by accident and brings them to your attention so you can fix them
     * @link https://developer.android.com/reference/android/os/StrictMode.html
     */
    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
       /* StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());*/
    }

    public void printKeyHash(Context context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            // getting application package name, as defined in manifest
            String packageName = context.getApplicationContext()
                    .getPackageName();

            // Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext()
                    .getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Hash Key ==> ", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }



    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new ImageClass());
        }
        return this.mImageLoader;
    }

    public  void addToRequestQueue(Request req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public  void addToRequestQueue(Request req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
