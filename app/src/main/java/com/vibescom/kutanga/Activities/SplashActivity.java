package com.vibescom.kutanga.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vibescom.kutanga.ApplicationManager;
import com.vibescom.kutanga.ModelManager.ModelManager;
import com.vibescom.kutanga.R;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import static com.vibescom.kutanga.Constants.Constants.kAppPreferencesLanguage;
import static com.vibescom.kutanga.Constants.Constants.kLangPref;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Context context;
    String selectedlanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;
        sharedPreferences = getSharedPreferences(kAppPreferencesLanguage, Context.MODE_PRIVATE);
        ApplicationManager.setLanguageForApp(context, sharedPreferences.getString(kLangPref, ""));
        selectedlanguage = sharedPreferences.getString(kLangPref, "");
        ImageView logoView = findViewById(R.id.imgLogo);

        // menuDialog();

        Glide.with(this).asGif().load(R.drawable.splash_new).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);
                resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        //do whatever after specified number of loops complete
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {


                            //Do something after 1000ms
                            if (ModelManager.modelManager().getCurrentUser() != null) {
                                Intent intent = new Intent(SplashActivity.this, MainScreen.class);
                                startActivity(intent);
                                finish();

                                /*if (ModelManager.modelManager().getCurrentUser().getOTPVerified().equals("1")) {
                                    if (ModelManager.modelManager().getCurrentUser().getSubscription() == 0) {
                                        Intent mainIntent = new Intent(SplashActivity.this, SetDeliveryLocationActivity.class);
                                        mainIntent.putExtra(kType, 1);
                                        SplashActivity.this.startActivity(mainIntent);
                                        SplashActivity.this.finish();
                                    } else {
                                        Intent mainIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                                        mainIntent.putExtra(kData, "nothing");
                                        SplashActivity.this.startActivity(mainIntent);
                                        SplashActivity.this.finish();
                                    }
                                } else {
                                    Intent mainIntent = new Intent(SplashActivity.this, IntroScreenActivity.class);
                                    SplashActivity.this.startActivity(mainIntent);
                                    SplashActivity.this.finish();
                                }*/
                            } else {
                                menuDialog();
                            }
                        }, 500);
                    }
                });
                return false;
            }
        }).into(logoView);


    }


    private void menuDialog() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        // dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_select_language);
        RadioButton rb_french = dialog.findViewById(R.id.rb_french);
        RadioButton rb_english = dialog.findViewById(R.id.rb_english);
        RadioButton rb_portgal = dialog.findViewById(R.id.rb_portgal);
        TextView tv_confirm = dialog.findViewById(R.id.tv_confirm);



        if (selectedlanguage.equalsIgnoreCase("en")) {
            rb_english.setChecked(true);
            rb_portgal.setChecked(false);
            rb_french.setChecked(false);
        } else if (selectedlanguage.equalsIgnoreCase("pt")) {
            rb_english.setChecked(false);
            rb_portgal.setChecked(true);
            rb_french.setChecked(false);

        } else if (selectedlanguage.equalsIgnoreCase("fr")) {
            rb_english.setChecked(false);
            rb_portgal.setChecked(false);
            rb_french.setChecked(true);
        } else {

        }

        rb_french.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb_french.setChecked(true);
                    rb_english.setChecked(false);
                    rb_portgal.setChecked(false);

                    editor.putString(kLangPref, "fr");


                }
            }
        });
        rb_english.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb_english.setChecked(true);
                    rb_french.setChecked(false);
                    rb_portgal.setChecked(false);
                    editor.putString(kLangPref, "en");

                }
            }
        });

        rb_portgal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rb_portgal.setChecked(true);
                    rb_french.setChecked(false);
                    rb_english.setChecked(false);
                    editor.putString(kLangPref, "pt");

                }
            }
        });


        RelativeLayout rl_english = dialog.findViewById(R.id.rl_english);
        rl_english.setOnClickListener(v -> {
            rb_english.setChecked(true);
            rb_french.setChecked(false);
            rb_portgal.setChecked(false);
            editor.putString(kLangPref, "en");

        });
        RelativeLayout rl_french = dialog.findViewById(R.id.rl_french);
        rl_french.setOnClickListener(v -> {
            rb_english.setChecked(false);
            rb_french.setChecked(true);
            rb_portgal.setChecked(false);
            editor.putString(kLangPref, "fr");

        });
        RelativeLayout rl_purtgal = dialog.findViewById(R.id.rl_purtgal);
        rl_purtgal.setOnClickListener(v -> {
            rb_english.setChecked(false);
            rb_french.setChecked(false);
            rb_portgal.setChecked(true);
            editor.putString(kLangPref, "pt");

        });


        tv_confirm.setOnClickListener(v -> {
            editor.commit();

            //Log.e("Click","hi"+sharedPreferences.getString(kLangPref, ""));
            ApplicationManager.setLanguageForApp(context, sharedPreferences.getString(kLangPref, ""));
            dialog.dismiss();

            Intent mainIntent = new Intent(SplashActivity.this, IntroScreenActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();

        });

        dialog.show();
    }
}
