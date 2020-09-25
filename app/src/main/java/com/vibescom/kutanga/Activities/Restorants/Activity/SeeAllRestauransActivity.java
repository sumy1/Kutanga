package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibescom.kutanga.Activities.Restorants.Adapter.RestautansHomeSeeAllAdapter;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.VerticalSpaceItemDecoration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class SeeAllRestauransActivity extends AppCompatActivity {

    private RecyclerView rv_tack_your_pic;
    private RestautansHomeSeeAllAdapter restautansHomeSeeAllAdapter;
    Context context;
    ImageView img_back;
    TextView txt_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_restaurans);
        context=this;
        inItView();
    }

    private void inItView() {
        rv_tack_your_pic=findViewById(R.id.rv_seeall);

        rv_tack_your_pic.setLayoutManager(new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL ));
        rv_tack_your_pic.addItemDecoration(new VerticalSpaceItemDecoration(10));
        rv_tack_your_pic.setHasFixedSize(true);
        restautansHomeSeeAllAdapter=new RestautansHomeSeeAllAdapter(context);
        rv_tack_your_pic.setAdapter(restautansHomeSeeAllAdapter);

        txt_filter=findViewById(R.id.txt_filter);
        txt_filter.setOnClickListener(v->{
            Intent intent=new Intent(context,FilterActivity.class);
            startActivity(intent);
        });

        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(v->{
            finish();
        });
    }
}
