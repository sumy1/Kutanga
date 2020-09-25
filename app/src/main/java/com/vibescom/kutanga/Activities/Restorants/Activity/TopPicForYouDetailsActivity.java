package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansTopPicForYouDetailsAdapter;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class TopPicForYouDetailsActivity extends AppCompatActivity {

    private RecyclerView rv_top_pic_for_you_details;
    private RestautansTopPicForYouDetailsAdapter restautansHomeInTheSportLightDetailsAdapter;
    Context context;
    ImageView img_back;
    LinearLayout lv_reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toppicforyoudetails);
        context=this;
        inItView();
    }

    private void inItView() {
        rv_top_pic_for_you_details=findViewById(R.id.rv_top_pic_for_you_details);
        rv_top_pic_for_you_details.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL ));
        rv_top_pic_for_you_details.addItemDecoration(new VerticalSpaceItemDecoration(10));
        rv_top_pic_for_you_details.setHasFixedSize(true);
        restautansHomeInTheSportLightDetailsAdapter=new RestautansTopPicForYouDetailsAdapter(context);
        rv_top_pic_for_you_details.setAdapter(restautansHomeInTheSportLightDetailsAdapter);

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(v->{
            finish();
        });

        lv_reviews=findViewById(R.id.lv_reviews);
        lv_reviews.setOnClickListener(v -> {
            Intent intent=new Intent(context,ReviewActivity.class);
            startActivity(intent);
        });
    }
}
