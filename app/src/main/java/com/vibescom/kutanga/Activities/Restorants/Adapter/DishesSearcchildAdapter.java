package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductAtribute;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductDetails;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductPrice;
import com.vibescom.kutanga.Models.SearchDishesModel.ProductImageModel;
import com.vibescom.kutanga.Models.SearchDishesModel.ResturantsDishesproductModel;
import com.vibescom.kutanga.Models.ViewCart.CartItemModel;
import com.vibescom.kutanga.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.vibescom.kutanga.Constants.Constants.kpriceQuantity;
import static com.vibescom.kutanga.Constants.Constants.kproductId;

public class DishesSearcchildAdapter extends RecyclerView.Adapter<DishesSearcchildAdapter.myViewHolder> {
    private Context context;
    private CopyOnWriteArrayList<ResturantsDishesproductModel>data;
    private String imagepath,booking_order_status;
    private String mainattribute,quantity,priceQuantity;
    Dialog dialog;
    RecyclerView child_reclerView;
    private LinearLayoutManager mLayoutManager;
    int productid,rawId,cartprice,cartquantity;
    CopyOnWriteArrayList<CartItemModel> cartItemModels;
    ArrayList<Integer>productidList;
    ArrayList<Integer>cartquantityist;
    private AddItemClickInterfase addItemClickInterfase;

    public DishesSearcchildAdapter(Context context,int cartPrice,String imagepath, CopyOnWriteArrayList<ResturantsDishesproductModel>data,CopyOnWriteArrayList<CartItemModel> cartItemModels,AddItemClickInterfase addItemClickInterfase) {
        this.context = context;
        this.data=data;
        this.cartItemModels=cartItemModels;
        this.imagepath=imagepath;
        this.cartprice=cartPrice;
        this.addItemClickInterfase=addItemClickInterfase;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.row_view_restorants_home_inthesportlight_details, parent, false);
        return new myViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolderChild, int position) {
        ResturantsDishesproductModel child = data.get(position);
        productidList=new ArrayList<>();
        cartquantityist=new ArrayList<>();
        viewHolderChild.tv_item_name.setText(child.getProductName());
        viewHolderChild.txt_prepration_time.setText(child.getPreprationTime()+" "+"Mins");


        CopyOnWriteArrayList<ProductDetails>productDetails=child.getProductDetails();
        CopyOnWriteArrayList<ProductImageModel>productImageModels=child.getProductImage();

        if(productDetails.size()==0){

        }else{
            mainattribute=productDetails.get(0).getMinAttribute();
            CopyOnWriteArrayList<ProductPrice>productPrices=productDetails.get(0).getProductPrices();
            CopyOnWriteArrayList<ProductAtribute>productAtributes=productDetails.get(0).getProductAtributes();


            List<Integer> list=new ArrayList<>();
            for(int i=0;i<productPrices.size();i++){
                list.add(Integer.parseInt(productPrices.get(i).getProductprice()));
            }


            int min= Collections.min(list);

            for(int i=0;i<productPrices.size();i++){
                if(Integer.parseInt(productPrices.get(i).getProductprice())==min){

                    quantity=productPrices.get(i).getQuantity();
                    viewHolderChild.txt_max_order.setText(productPrices.get(i).getProductprice()+" Kz"+"("+productPrices.get(i).getQuantity()+")");
                }else{
                    //viewHolderChild.txt_max_order.setText("40"+" Kz"+"("+"Quarter"+")");
                }

            }

            if(productImageModels.size()==0){

            }else{
                if(!productImageModels.get(0).getProductImage().isEmpty()){
                    String imgPath = imagepath+"/"+productImageModels.get(0).getProductImage();
                    Picasso.with(context).load(imgPath).placeholder(R.drawable.ic_image_black_24dp).fit().into(viewHolderChild.ivBanner);
                }else{
                    Picasso.with(context).load(R.drawable.ic_image_black_24dp).placeholder(R.drawable.ic_image_black_24dp).fit().into(viewHolderChild.ivBanner);
                }
            }


            if(mainattribute.equalsIgnoreCase("Veg")){
                viewHolderChild.img_veg.setColorFilter(context.getResources().getColor(R.color.green));
            }else{
                viewHolderChild.img_veg.setColorFilter(context.getResources().getColor(R.color.user_theme_color));
            }

            viewHolderChild.txt_des.setText(productAtributes.get(0).getAttibutes());




            for(int i=0;i<cartItemModels.size();i++){
                JSONObject jsonObject=cartItemModels.get(i).getAssocialteModel();
                try {
                    productid=jsonObject.getInt(kproductId);
                    productidList.add(productid);
                    cartquantityist.add(cartItemModels.get(i).getCartQuantity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            for(int i=0;i<productidList.size();i++){

                Log.d("ProductId",productidList.get(i)+"/"+child.getProductId());
                if(productidList.get(i)==child.getProductId()){
                    viewHolderChild.ll_increse.setVisibility(View.VISIBLE);
                    viewHolderChild.txt_add_item.setVisibility(View.GONE);
                    rawId=cartItemModels.get(i).getCartid();
                    cartprice=cartItemModels.get(i).getCartPrice();
                    cartquantity=cartItemModels.get(i).getCartQuantity();
                    break;
                }else{
                    viewHolderChild.ll_increse.setVisibility(View.GONE);
                    viewHolderChild.txt_add_item.setVisibility(View.VISIBLE);

                }

            }




            AtomicInteger count = new AtomicInteger();
            count.set(cartquantity);
            viewHolderChild.txt_count.setText(String.valueOf(cartquantity));
            viewHolderChild.img_cart_plus.setOnClickListener(v->{

                Log.d("Viewcart","1");
                count.set(count.get() + 1);
                viewHolderChild.img_cart_minus.setClickable(true);
                viewHolderChild.txt_count.setText(String.valueOf(count.get()));

                addItemClickInterfase.updatecrat(rawId,cartprice,1);
            });
            viewHolderChild.img_cart_minus.setOnClickListener(v->{
                Log.d("Viewcart","-1");
                if(count.get() ==0){
                    viewHolderChild.img_cart_minus.setClickable(false);
                    viewHolderChild.txt_count.setText(String.valueOf(count.get()));
                }else{

                    count.set(count.get() - 1);

                    addItemClickInterfase.updatecrat(rawId,cartprice,-1);
                    viewHolderChild.img_cart_minus.setClickable(true);
                    viewHolderChild.txt_count.setText(String.valueOf(count.get()));
                }

            });



            viewHolderChild.ll_customize.setOnClickListener(v -> {

                for(int i=0;i<productidList.size();i++){
                    if(productidList.get(i)==child.getProductId()){
                        rawId=cartItemModels.get(i).getCartid();
                        cartprice=cartItemModels.get(i).getCartPrice();
                        cartquantity=cartItemModels.get(i).getCartQuantity();

                        JSONObject cartAssociateModel = cartItemModels.get(i).getAssocialteModel();

                        JSONObject jsonObjectAttribute=cartItemModels.get(i).getCartattribute();

                        try {
                            //cartprice=Integer.parseInt(jsonObjectAttribute.getString(kItemprice));
                            priceQuantity=jsonObjectAttribute.getString(kpriceQuantity);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("ProductIdD",productidList.get(i)+"/"+child.getProductId()+"/"+cartprice+"/"+rawId);
                        break;
                    }else{

                    }
                }
                addItemClickInterfase.itemcuatomize(rawId,cartprice,priceQuantity,productPrices,child.getProductName(),mainattribute,productid);

            });
            viewHolderChild.txt_add_item.setOnClickListener(v -> {
                viewHolderChild.ll_increse.setVisibility(View.VISIBLE);
                viewHolderChild.txt_add_item.setVisibility(View.GONE);
                addItemClickInterfase.addItemclick(productPrices,child.getProductName(),mainattribute,child.getProductId());
                viewHolderChild.txt_count.setText("1");

            });


            viewHolderChild.txt_remove.setOnClickListener(v -> {
                viewHolderChild.ll_increse.setVisibility(View.GONE);
                viewHolderChild.txt_add_item.setVisibility(View.VISIBLE);
                addItemClickInterfase.onRemovecartItem(position,rawId);
            });
           // notifyDataSetChanged();
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(CopyOnWriteArrayList<ResturantsDishesproductModel>data,String imagepath) {
        data.addAll(data);
        this.imagepath=imagepath;
        notifyDataSetChanged();
    }


    private void setRecyclerView(){

        //main_reclerView.addOnScrollListener(onScrollListener);
    }
    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
    public void newData( CopyOnWriteArrayList<ResturantsDishesproductModel>data, String imagepath) {
        data.clear();
        this.imagepath=imagepath;
        data.addAll(data);
        notifyDataSetChanged();
    }



    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBanner,img_veg,img_cart_minus,img_cart_plus;
        RelativeLayout rl_closed,top;
        LinearLayout ll_customize,ll_increse;
        TextView tv_item_name,txt_count,txt_add_item,txt_des,txt_prepration_time,txt_popularity,txt_max_order,txt_remove;
        private myViewHolder(View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_item_name);
           txt_add_item = itemView.findViewById(R.id.txt_add_item);
            txt_des = itemView.findViewById(R.id.txt_des);
            txt_popularity=itemView.findViewById(R.id.txt_popularity);
           txt_max_order=itemView.findViewById(R.id.txt_max_order);
            txt_prepration_time=itemView.findViewById(R.id.txt_prepration_time);
            ivBanner=itemView.findViewById(R.id.iv_banner);
            img_veg=itemView.findViewById(R.id.img_veg);
            rl_closed=itemView.findViewById(R.id.rl_closed);
            top=itemView.findViewById(R.id.top);
            img_cart_minus=itemView.findViewById(R.id.img_cart_minus);
            img_cart_plus=itemView.findViewById(R.id.img_cart_plus);
            txt_count=itemView.findViewById(R.id.txt_count);
            txt_remove=itemView.findViewById(R.id.txt_remove);
            ll_increse=itemView.findViewById(R.id.ll_increse);
            ll_customize=itemView.findViewById(R.id.ll_customize);

        }
    }

    public interface AddItemClickInterfase{
        void itemcuatomize(int quantity,int selectedPrice,String priceQuantity,CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId);
        void onRemovecartItem(int position,int rowId);
        void updatecrat(int rowId,int productPrice,int quantity);
        void addItemclick(CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId);
    }

}
