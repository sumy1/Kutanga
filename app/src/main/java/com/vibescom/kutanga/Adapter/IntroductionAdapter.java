package com.vibescom.kutanga.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibescom.kutanga.Models.IntroModel;
import com.vibescom.kutanga.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class IntroductionAdapter extends PagerAdapter {
    private ArrayList<IntroModel> IntoModel;
    private LayoutInflater inflater;
    private Context context;

    public IntroductionAdapter(Context context, ArrayList<IntroModel> IMAGES) {
        this.context = context;
        this.IntoModel =IMAGES;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater.from(context)
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IntoModel.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.layout_intro_sliding_image, view, false);
        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.image);
        final TextView tvDesc = imageLayout.findViewById(R.id.tv_desc);

        final IntroModel model= IntoModel.get(position);

        imageView.setImageResource(model.getImage());
        tvDesc.setText(model.getDesc());
        view.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

}
