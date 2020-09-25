package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansHomeFeturedDetailsAdapter;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class FearuedBrandDetailsActivity extends AppCompatActivity {

    private RecyclerView rv_take_your_pic_details;
    private RestautansHomeFeturedDetailsAdapter restautansHomeInTheSportLightDetailsAdapter;
    Context context;
    ImageView img_back;
    TextView txt_menu;
    Dialog dialog;
    LinearLayout lv_reviews;
    int businessId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetureddetails);
        context=this;
        inItView();
    }

    private void inItView() {
        rv_take_your_pic_details=findViewById(R.id.rv_inthe_sport_light_details);
        rv_take_your_pic_details.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL ));
        rv_take_your_pic_details.addItemDecoration(new VerticalSpaceItemDecoration(10));
        rv_take_your_pic_details.setHasFixedSize(true);
        restautansHomeInTheSportLightDetailsAdapter=new RestautansHomeFeturedDetailsAdapter(context);
        rv_take_your_pic_details.setAdapter(restautansHomeInTheSportLightDetailsAdapter);

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(v->{
            finish();
        });

        lv_reviews=findViewById(R.id.lv_reviews);
        lv_reviews.setOnClickListener(v -> {
            Intent intent=new Intent(context,ReviewActivity.class);
            startActivity(intent);
        });

        /*txt_menu=findViewById(R.id.txt_menu);
        txt_menu.setOnClickListener(v->{
            menuDialog();
        });*/
    }


    private void menuDialog(){
        // dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_recommended_menu);

        dialog.findViewById(R.id.btn_close).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
