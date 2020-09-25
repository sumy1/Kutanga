package com.vibescom.kutanga.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.vibescom.kutanga.Adapter.IntroductionAdapter;
import com.vibescom.kutanga.Models.IntroModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.ZoomOutPageTransformer;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class IntroScreenActivity extends AppCompatActivity {

    private ViewPager mPager;
    private Integer[] IMAGES= {R.drawable.intro_img_1,R.drawable.intro_img_2,R.drawable.intro_img_3};
    private ArrayList<IntroModel> ImagesArray = new ArrayList<>();
    private CirclePageIndicator indicator;
    private TextView tvDesc;
    private TextView txt_login,txt_create_account;
    Context mContext;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 100;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);
        mContext=this;

        mPager = findViewById(R.id.pager);
        tvDesc=findViewById(R.id.tv_desc);
        indicator = findViewById(R.id.indicator);
        txt_login=findViewById(R.id.txt_login);


        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,SignInActivity.class);
                intent.putExtra("FROM","1");
                startActivity(intent);
                //finish();
            }
        });

        txt_create_account=findViewById(R.id.txt_create_account);
        txt_create_account.setOnClickListener(v->{
            Intent intent=new Intent(mContext,SignUpActivity.class);
            intent.putExtra("FROM","1");
            startActivity(intent);
            //finish();
        });
        init();

        //setStatusBarGradient(IntroScreenActivity.this);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // second argument is the default to use if the preference can't be found
       /* boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);
        if (!welcomeScreenShown) {
            // here you can launch another activity if you like
            // the code below will display a popup
            mPager = findViewById(R.id.pager);
            indicator = findViewById(R.id.indicator);
            init();

            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.apply(); // Very important to save the preference
        }else{
           *//* Intent intent = new Intent(IntroScreenActivity.this, WelcomeScreenActivity.class);
            startActivity(intent);
            finish();*//*
        }*/
    }


    @SuppressLint("ClickableViewAccessibility")
    private void init() {

        findViewById(R.id.btn_get_started).setOnClickListener(view -> {

                Intent intent = new Intent(IntroScreenActivity.this, MainScreen.class);
                startActivity(intent);
                finish();

        });
       /* findViewById(R.id.btn_skip).setOnClickListener(view -> {
            Intent intent = new Intent(IntroScreenActivity.this, WelcomeScreenActivity.class);
            startActivity(intent);
            finish();
        });*/

        ImagesArray.add(new IntroModel(IMAGES[0],getString(R.string.intro_text1),""));
        ImagesArray.add(new IntroModel(IMAGES[1],getString(R.string.intro_text2),""));
        ImagesArray.add(new IntroModel(IMAGES[2],getString(R.string.intro_text3),""));
        //ImagesArray.addAll(Arrays.asList(IMAGES));
        mPager.setAdapter(new IntroductionAdapter(IntroScreenActivity.this,ImagesArray));
        mPager.setPageTransformer(true,new ZoomOutPageTransformer());

        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        //Set circle indicator radius
        indicator.setRadius(5 * density);

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == ImagesArray.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                /*if(currentPage==1){

                    tvDesc.setText(R.string.intro_text2);
                }
                else if(currentPage==2){

                    tvDesc.setText(R.string.intro_text3);
                }
                else{

                    tvDesc.setText(R.string.intro_text1);
                }*/
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) { }

            @Override
            public void onPageScrollStateChanged(int pos) { }
        });
        //mPager.setOnTouchListener((arg0, arg1) -> true);
        mPager.setOnTouchListener(null);

    }
    private int getItem(int i) {
        return mPager.getCurrentItem() + i;
    }

    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.canvas_theme_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }*/
}
