package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductAtribute;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductDetails;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductImage;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductPrice;
import com.vibescom.kutanga.Models.ViewCart.CartItemModel;
import com.vibescom.kutanga.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.vibescom.kutanga.Constants.Constants.kItempriceSum;
import static com.vibescom.kutanga.Constants.Constants.kVendor;
import static com.vibescom.kutanga.Constants.Constants.kpreparationTime;
import static com.vibescom.kutanga.Constants.Constants.kpriceQuantity;
import static com.vibescom.kutanga.Constants.Constants.kproductDetails;
import static com.vibescom.kutanga.Constants.Constants.kproductId;
import static com.vibescom.kutanga.Constants.Constants.kproductImage;
import static com.vibescom.kutanga.Constants.Constants.kproductName;

public class RestautansCartAdapter extends RecyclerView.Adapter<RestautansCartAdapter.myViewHolder> {
    private Context context;
    CopyOnWriteArrayList<CartItemModel> cartItemModels;
    private onItemClickListener listener;
    String productImagePath,mainattribute;
    JSONObject jsonObject;
    private CopyOnWriteArrayList<ProductDetails> productDetails=new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<ProductAtribute> productAtributes=new CopyOnWriteArrayList<>();
    String  priceQuantity,selcetedCartprice,itemPrice;

    private CopyOnWriteArrayList<ProductImage> productImages=new CopyOnWriteArrayList<>();


    public RestautansCartAdapter(Context context, CopyOnWriteArrayList<CartItemModel> cartItemModels, String productImagePath, onItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.cartItemModels = cartItemModels;
        this.productImagePath = productImagePath;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.raw_cart_item, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        CartItemModel cartItemModel = cartItemModels.get(position);

        itemPrice=String.valueOf(cartItemModel.getCartPrice());
        JSONObject cartAssociateModel = cartItemModel.getAssocialteModel();

        JSONObject jsonObjectAttribute=cartItemModel.getCartattribute();

        try {
            selcetedCartprice=jsonObjectAttribute.getString(kItempriceSum);
            priceQuantity=jsonObjectAttribute.getString(kpriceQuantity);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObject = cartAssociateModel.getJSONObject(kVendor);

            JSONArray array = cartAssociateModel.getJSONArray(kproductDetails);
            JSONArray array1 = cartAssociateModel.getJSONArray(kproductImage);



            productDetails = handleProductDetails(array);
            productImages=handleProductImage(array1);


            productAtributes=productDetails.get(0).getProductAtributes();

            myViewHolder.txt_cart_food_details.setText(productAtributes.get(0).getAttibutes());

            myViewHolder.txt_count.setText(String.valueOf(cartItemModel.getCartQuantity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(productImages.contains("[]")){
                Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.img_cart_food);
            }else{
                if (productImages==null) {
                    Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.img_cart_food);
                } else {

                    String imgPath = productImagePath + "/" + productImages.get(0).getProduct_image();

                    Log.d("Image",imgPath);
                    Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(myViewHolder.img_cart_food);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CopyOnWriteArrayList<ProductPrice>productPrices=productDetails.get(0).getProductPrices();
        CopyOnWriteArrayList<ProductAtribute>productAtributes=productDetails.get(0).getProductAtributes();
        mainattribute=productDetails.get(0).getMinAttribute();
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<productPrices.size();i++){
            list.add(Integer.parseInt(productPrices.get(i).getProductprice()));
        }


        int min= Collections.min(list);

        Log.d("Min",min+"");
        for(int i=0;i<list.size();i++){
           for(int j=0;j<productPrices.size();j++){
               if(min== Integer.parseInt(productPrices.get(j).getProductprice())){
                   myViewHolder.txt_cart_food_weight.setText(min+" "+context.getResources().getString(R.string.kg)+" ("+productPrices.get(j).getQuantity()+")");
                   break;
               }

           }

        }

        AtomicInteger count = new AtomicInteger();
        count.set(cartItemModel.getCartQuantity());
        Log.d("Cunt", count.get() + "");

        myViewHolder.img_cart_plus.setOnClickListener(v -> {
            count.set(count.get() + 1);
            myViewHolder.img_cart_minus.setClickable(true);
            myViewHolder.txt_count.setText(String.valueOf(count.get()));

            listener.updatecrat(cartItemModel.getCartid(), cartItemModel.getCartPrice(), 1);
        });
        myViewHolder.img_cart_minus.setOnClickListener(v -> {
            if (count.get() == 0) {
                myViewHolder.img_cart_minus.setClickable(false);
                myViewHolder.txt_count.setText(String.valueOf(count.get()));
            } else {

                count.set(count.get() - 1);
                listener.updatecrat(cartItemModel.getCartid(), cartItemModel.getCartPrice(), -1);

                myViewHolder.img_cart_minus.setClickable(true);
                myViewHolder.txt_count.setText(String.valueOf(count.get()));
            }

        });
        myViewHolder.txt_cart_food_name.setText(cartItemModel.getCartName()+"("+priceQuantity+")");
        try {
            myViewHolder.txt_preparation_time.setText(cartAssociateModel.getString(kpreparationTime) + " " + "Min");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        myViewHolder.txt_remove.setOnClickListener(v -> {
            listener.onRemovecartItem(position, cartItemModel.getCartid());

        });

        myViewHolder.ll_customize.setOnClickListener(v -> {
            try {
                listener.customizeCart(productPrices,priceQuantity,String.valueOf(cartItemModel.getCartPrice()), cartAssociateModel.getString(kproductName), mainattribute, cartAssociateModel.getInt(kproductId));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }


    private CopyOnWriteArrayList<ProductDetails> handleProductDetails(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ProductDetails> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new ProductDetails(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }

    private CopyOnWriteArrayList<ProductImage> handleProductImage(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<ProductImage> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new ProductImage(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }

    public void removeItem(int position) {
        cartItemModels.remove(position);
        notifyItemRemoved(position);
    }



    @Override
    public int getItemCount() {
        return (cartItemModels.size() > 0) ? cartItemModels.size() : 0;
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_cart_food;
        ImageView img_cart_minus, img_cart_plus;
        LinearLayout ll_customize;
        TextView txt_customize, txt_remove, txt_preparation_time, txt_cart_food_name, txt_cart_food_details, txt_cart_food_weight, txt_count;

        private myViewHolder(View itemView) {
            super(itemView);
            img_cart_food = itemView.findViewById(R.id.img_cart_food);
            img_cart_minus = itemView.findViewById(R.id.img_cart_minus);
            img_cart_plus = itemView.findViewById(R.id.img_cart_plus);

            txt_cart_food_name = itemView.findViewById(R.id.txt_cart_food_name);
            txt_cart_food_details = itemView.findViewById(R.id.txt_cart_food_details);
            txt_cart_food_weight = itemView.findViewById(R.id.txt_cart_food_weight);
            txt_count = itemView.findViewById(R.id.txt_count);

            txt_customize = itemView.findViewById(R.id.txt_customize);
            txt_remove = itemView.findViewById(R.id.txt_remove);
            txt_preparation_time = itemView.findViewById(R.id.txt_preparation_time);

            ll_customize = itemView.findViewById(R.id.ll_customize);

        }
    }

    public interface onItemClickListener {
        void onRemovecartItem(int pos, int rowId);

        void updatecrat(int rowId, int productPrice, int quantity);

        void customizeCart(CopyOnWriteArrayList<ProductPrice> productPrices,String priceQuantity,String selcetedItemPrice, String productName, String mainAttribute, int productId);
    }
}
