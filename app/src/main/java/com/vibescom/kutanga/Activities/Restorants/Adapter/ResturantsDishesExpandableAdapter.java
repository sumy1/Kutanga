package com.vibescom.kutanga.Activities.Restorants.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductAtribute;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductDetails;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ProductPrice;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.SearchDishesModel;
import com.vibescom.kutanga.Models.SearchDishesModel.ProductImageModel;
import com.vibescom.kutanga.Models.SearchDishesModel.ResturantsDishesproductModel;
import com.vibescom.kutanga.Models.ViewCart.CartItemModel;
import com.vibescom.kutanga.R;
import com.vibescom.kutanga.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.vibescom.kutanga.Constants.Constants.kData;
import static com.vibescom.kutanga.Constants.Constants.kproductId;


public class ResturantsDishesExpandableAdapter extends BaseExpandableListAdapter {

	private CopyOnWriteArrayList<HashMap<String, Object>> childItems;
	CopyOnWriteArrayList<SearchDishesModel> parentItems;
	private LayoutInflater inflater;
	private Context context;
	private String imagepath,mainattribute,quantity;
	Dialog dialog;
	private AddItemClickInterfase addItemClickInterfase;
	private static final int[] EMPTY_STATE_SET = {};
	private static final int[] GROUP_EXPANDED_STATE_SET = { android.R.attr.state_expanded };
	private static final int[][] GROUP_STATE_SETS = { EMPTY_STATE_SET, // 0
			GROUP_EXPANDED_STATE_SET // 1
	};
	int productid,rawId,cartprice,cartquantity;
	CopyOnWriteArrayList<CartItemModel> cartItemModels;
	ArrayList<Integer>productidList;
	ArrayList<Integer>cartquantityist;


	public ResturantsDishesExpandableAdapter(CopyOnWriteArrayList<CartItemModel> cartItemModels,String imagepath, Context activity, CopyOnWriteArrayList<SearchDishesModel> parentList, CopyOnWriteArrayList<HashMap<String, Object>> childList, AddItemClickInterfase addItemClickInterfase) {
		this.parentItems = parentList;
		this.childItems = childList;
		this.context = activity;
		this.cartItemModels=cartItemModels;
		this.imagepath = imagepath;
		this.addItemClickInterfase=addItemClickInterfase;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d("Size","print"+parentItems.size()+"/"+childList.size()+"/"+cartItemModels.size());
	}



	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		CopyOnWriteArrayList<ResturantsDishesproductModel> list = (CopyOnWriteArrayList<ResturantsDishesproductModel>) childItems.get(groupPosition).get(kData);
		assert list != null;
		return list.size();
	}

	@Override
	public Object getGroup(int i) {
		return null;
	}

	@Override
	public Object getChild(int i, int i1) {
		return null;
	}

	@Override
	public long getGroupId(int i) {
		return 0;
	}

	@Override
	public long getChildId(int i, int i1) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getGroupView(final int groupPosition, final boolean b, View convertView, ViewGroup viewGroup) {
		final ViewHolderParent viewHolderParent;
		SearchDishesModel parent = parentItems.get(groupPosition);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_expandable_list_group_dishes, null);
			viewHolderParent = new ViewHolderParent();
			viewHolderParent.tvMainCategoryName = convertView.findViewById(R.id.tv_category_name);
			viewHolderParent.tv_delivery_time = convertView.findViewById(R.id.tv_delivery_time);
			viewHolderParent.tv_address = convertView.findViewById(R.id.tv_address);
			viewHolderParent.tv_rating = convertView.findViewById(R.id.tv_rating);
			viewHolderParent.tv_review = convertView.findViewById(R.id.tv_review);
			convertView.setTag(viewHolderParent);
		} else {
			viewHolderParent = (ViewHolderParent) convertView.getTag();
		}
		viewHolderParent.tvMainCategoryName.setText(parent.getCompanyName());
		viewHolderParent.tv_delivery_time.setText(parent.getDistanceTobeCovered()+" "+context.getResources().getString(R.string.min));
		viewHolderParent.tv_address.setText(parent.getAddress());
		viewHolderParent.tv_rating.setText(parent.getRating());
		viewHolderParent.tv_review.setText(parent.getReviewCount()+" "+context.getResources().getString(R.string.reviews));
		Utils.childItems = childItems;
		Utils.parentItemsm = parentItems;
		return convertView;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getChildView(final int groupPosition, final int childPosition, final boolean b, View convertView, ViewGroup viewGroup) {

		final ViewHolderChild viewHolderChild;
		CopyOnWriteArrayList<ResturantsDishesproductModel>data= (CopyOnWriteArrayList<ResturantsDishesproductModel>) childItems.get(groupPosition).get(kData);
		assert data != null;
		ResturantsDishesproductModel child = data.get(childPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_view_restorants_home_inthesportlight_details, null);
			viewHolderChild = new ViewHolderChild();

			viewHolderChild.tv_item_name = convertView.findViewById(R.id.tv_item_name);
			viewHolderChild.txt_add_item = convertView.findViewById(R.id.txt_add_item);
			viewHolderChild.txt_des = convertView.findViewById(R.id.txt_des);
            viewHolderChild.txt_popularity=convertView.findViewById(R.id.txt_popularity);
			viewHolderChild.txt_max_order=convertView.findViewById(R.id.txt_max_order);
			viewHolderChild.txt_prepration_time=convertView.findViewById(R.id.txt_prepration_time);
			viewHolderChild.ivBanner=convertView.findViewById(R.id.iv_banner);
			viewHolderChild.img_veg=convertView.findViewById(R.id.img_veg);
			viewHolderChild.rl_closed=convertView.findViewById(R.id.rl_closed);
			viewHolderChild.top=convertView.findViewById(R.id.top);
			viewHolderChild.img_cart_minus=convertView.findViewById(R.id.img_cart_minus);
			viewHolderChild.img_cart_plus=convertView.findViewById(R.id.img_cart_plus);
			viewHolderChild.txt_count=convertView.findViewById(R.id.txt_count);
			viewHolderChild.txt_remove=convertView.findViewById(R.id.txt_remove);
			viewHolderChild.ll_increse=convertView.findViewById(R.id.ll_increse);
			viewHolderChild.ll_customize=convertView.findViewById(R.id.ll_customize);
			convertView.setTag(viewHolderChild);
		} else {
			viewHolderChild = (ViewHolderChild) convertView.getTag();
		}

		viewHolderChild.tv_item_name.setText(child.getProductName());
		viewHolderChild.txt_prepration_time.setText(child.getPreprationTime()+" "+"Mins");
			CopyOnWriteArrayList<ProductDetails>productDetails=child.getProductDetails();
			CopyOnWriteArrayList<ProductImageModel>productImageModels=child.getProductImage();

			if(productDetails.size()==0){

			}else{
				mainattribute=productDetails.get(0).getMinAttribute();
				CopyOnWriteArrayList<ProductPrice>productPrices=productDetails.get(0).getProductPrices();
				CopyOnWriteArrayList<ProductAtribute>productAtributes=productDetails.get(0).getProductAtributes();

				for(int i=0;i<productPrices.size();i++){
					if(Integer.parseInt(productPrices.get(i).getProductprice())<=100){

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


				Log.d("p",data.get(childPosition).getProductId()+"");

				productidList=new ArrayList<>();
				cartquantityist=new ArrayList<>();
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
					if(productidList.get(i)==data.get(childPosition).getProductId()){
						viewHolderChild.ll_increse.setVisibility(View.VISIBLE);
						viewHolderChild.txt_add_item.setVisibility(View.GONE);
						AtomicInteger count = new AtomicInteger();
						count.set(cartquantityist.get(i));
						viewHolderChild.txt_count.setText(String.valueOf(cartquantityist.get(i)));

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
							addItemClickInterfase.itemcuatomize(productPrices,child.getProductName(),mainattribute,productid);

						});

						break;
					}else{
						viewHolderChild.ll_increse.setVisibility(View.GONE);
						viewHolderChild.txt_add_item.setVisibility(View.VISIBLE);

					}

				}
				viewHolderChild.txt_add_item.setOnClickListener(v -> {
					viewHolderChild.ll_increse.setVisibility(View.VISIBLE);
					viewHolderChild.txt_add_item.setVisibility(View.GONE);
					addItemClickInterfase.addItemclick(productPrices,child.getProductName(),mainattribute,child.getProductId());
					viewHolderChild.txt_count.setText("1");
					Utils.childItems = childItems;
					Utils.parentItemsm = parentItems;
				});


				viewHolderChild.txt_remove.setOnClickListener(v -> {
					viewHolderChild.ll_increse.setVisibility(View.GONE);
					viewHolderChild.txt_add_item.setVisibility(View.VISIBLE);
					addItemClickInterfase.onRemovecartItem(childPosition,rawId);
				});
				notifyDataSetChanged();
			}

			/*String closed="Close";

			if(childPosition==0){
				viewHolderChild.rl_closed.setVisibility(View.VISIBLE);
				//viewHolderChild.top.setVisibility(View.GONE);
			}else{
				viewHolderChild.rl_closed.setVisibility(View.GONE);
				//viewHolderChild.top.setVisibility(View.VISIBLE);
			}*/



		return convertView;
	}

	@Override
	public boolean isChildSelectable(int i, int i1) {
		return false;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	private class ViewHolderParent {
		TextView tvMainCategoryName,tv_delivery_time,tv_address,tv_rating,tv_review;

	}

	private class ViewHolderChild {
		ImageView ivBanner,img_veg,img_cart_minus,img_cart_plus;
		RelativeLayout rl_closed,top;
		LinearLayout ll_customize,ll_increse;
		TextView tv_item_name,txt_count,txt_add_item,txt_des,txt_prepration_time,txt_popularity,txt_max_order,txt_remove;
	}

	public interface AddItemClickInterfase{
		void itemcuatomize(CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId);
		void onRemovecartItem(int position,int rowId);
		void updatecrat(int rowId,int productPrice,int quantity);
		void addItemclick(CopyOnWriteArrayList<ProductPrice> productPrices, String productName, String mainAttribute, int productId);
	}


	private void menuDialog(){
		// dialog
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setContentView(R.layout.dialog_recommended_menu);
		dialog.findViewById(R.id.btn_close).setOnClickListener(view->{
			dialog.dismiss();
		});

		dialog.show();
	}

}
