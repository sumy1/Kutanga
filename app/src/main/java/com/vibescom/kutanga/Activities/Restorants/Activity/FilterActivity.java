package com.vibescom.kutanga.Activities.Restorants.Activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.vibescom.kutanga.Adapter.CousineAdapter;
import com.vibescom.kutanga.Models.CousineModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.EqualSpacingItemDecoration;

import java.util.concurrent.CopyOnWriteArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FilterActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView rec_rest;
    private CousineAdapter cousineAdapter;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        context = this;
        rec_rest=findViewById(R.id.rv_coisines);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        rec_rest.setLayoutManager(mLayoutManager);
        rec_rest.addItemDecoration(new EqualSpacingItemDecoration(10, EqualSpacingItemDecoration.HORIZONTAL));
        getMarketdata();
        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(v->{
            finish();
        });
    }

    private void getMarketdata() {
        CopyOnWriteArrayList<CousineModel> res_data=new CopyOnWriteArrayList<>();
        res_data.add(new CousineModel(1,"All"));
        res_data.add(new CousineModel(1,"Fast food"));
        res_data.add(new CousineModel(1,"Angolan"));
        res_data.add(new CousineModel(1,"Pizza"));
        res_data.add(new CousineModel(1,"Greek"));
        res_data.add(new CousineModel(1,"Asian"));
        res_data.add(new CousineModel(1,"Mexican"));
        res_data.add(new CousineModel(1,"Thai"));
        res_data.add(new CousineModel(1,"American"));
        res_data.add(new CousineModel(1,"Dessert"));
        setAdapter(res_data);


    }

    public void setAdapter(CopyOnWriteArrayList<CousineModel>res_data){
        cousineAdapter=new CousineAdapter(context,res_data);
        rec_rest.setAdapter(cousineAdapter);

    }
}
