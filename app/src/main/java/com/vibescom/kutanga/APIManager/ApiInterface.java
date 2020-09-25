package com.vibescom.kutanga.APIManager;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {

    //..Kutanga...Api...
    String kUserLogin = "login";
    String kSocialLogin="social-login";
    String kuserprofileUpdate="profile/update-profile";
    String kUserReg = "register";
    String kuserResenOtp="resend-otp-registration";
    String kuserOtpVerified="verify";
    String kUserLogout="logout";
    String kForgotPasswordd="forgot-password";
    String kFPVeOTP="forgot-otp-verify";
    String kResetPassword="reset-password";
    String kResendOtp="resend-otp";
    String klandingPageFood="landing-page";
    String KFoodLandingPage="food-landing-page";
    String kOfferList="kutanga-food/offers";
    String kAddAddress="kutanga-food/address-update";
    String ksearchResturantsDishes="kutanga-food/search-food";
    String kResturantsDetails="kutanga-food/restaurent-detail";
    String kaddtocart="kutanga-food/add-to-cart";
    String kViewcart="kutanga-food/view-cart";
    String kFavroroute="profile/food-favorites";
    String kAddFavroute="kutanga-food/favorite";
    String kmanageaddress="profile/manage-address";
    String kPastOrder="profile/food-orders";
    String kApplayCoupon="kutanga-food/apply-coupon";
    String kRemovecartItem="kutanga-food/remove-item-cart";
    String kCnacelOrder="profile/cancel-order";
    String kUpdateCart="kutanga-food/update-cart";
    String kClearAllCart="kutanga-food/clear-cart";
    String kTakeYourPicDetails="kutanga-food/search-by-type";
    String kPlaceOrder="kutanga-food/place-order";
    String kNotification="profile/food-notifications";
    String kCheckcartItem="kutanga-food/check-cart-item";
    String kAddresslist="profile/manage-address";
    String kdeleteAddress="kutanga-food/address-delete";
    String kReviewList="kutanga-food/restaurant-review-list";
    String kPostReview="kutanga-food/post-review";
    String kAddressPlaceOrder="kutanga-food/address-set";




    //..end..hera..



    //Base page for all the api's
    String kBasePage     = "index.php";

    //User will login through userLogin API.


    String kOtpVerify="otpverify";


    //User will get logout from app.
    String kLogout = "facility/logout";

    String kNews ="News";

    String kOrder = "order";

    String kOrderPlace = "orderplace";

    String kUpdateProfile = "updateprofile";

    String kForgotPassword = "forgotpassword";

    String kChangePassword = "changepassword";

    String kSubscriptionPlan = "plan_listing_new";

    String kPublicationList = "publicationlist";

    String kLikeCount = "likeCount";

    String kComment = "comment";

    String kCommentList = "commentlist";

    String kReleaseDates = "releaseDates";


    String kPushNotification = "notificationtag";

    String kNotificationUpdate = "notisreadstatusupdate";

    String kNotificationCount = "notisunreadcount";

    String kAdvertisement = "advertismentlist";

    String kInactiveUser = "isactiveuser";

    String kStaticPages = "staticpages";

    String kContactUs = "contactus";

    String kContactForm = "contactusform";

    String kQueryForm = "addquery";

    String kQueryList = "query";

    String kUserSubscription = "usersubscription";

    String kResendOTP = "resendotp";

    String kSubscription = "titles";

    String kUserInfo = "userinfo";

    String kSubscriptionLog = "subscriptionhistory";

    String kDownloadLogData = "downloadHistory";



    /**
     * set api request with api key and corresponding parameters
     * @param APIKey  key of the url
     * @param details details contains request body parameters
     * @param files   if include file will be sent in multipart
     * @return JsonObject ie. response
     */
    @Multipart
    @POST()
    Call<JsonObject> APIRequestWithFile(
            @Url String APIKey,
            @PartMap Map<String, RequestBody> details,
            @Part List<MultipartBody.Part> files
    );

    /**
     * set api request with api key and corresponding parameters
     * @param APIKey  key of the url
     * @param details details contains request body parameters
     * @param files   if include file will be sent in multipart
     * @return JsonObject ie. response
     */
    @Streaming
    @Multipart
    @POST()
    Call<ResponseBody> APIRequestGetFile(
            @Url String APIKey,
            @PartMap Map<String, RequestBody> details,
            @Part List<MultipartBody.Part> files
    );

    @POST()
    Call<JsonObject> APIRequestRaw(
            @Url String APIKey,
            @Body RequestBody params
    );

    @GET()
    Call<JsonObject> APIGetRequestRaw(
            @Url String APIKey,
            @Body RequestBody params
    );

    @GET("maps/api/geocode/json")
    Call<JsonObject> getLocation(
            @Query("latlng") String latlng,
            @Query("key") String key
    );

    @GET("maps/api/place/autocomplete/json")
    Call<JsonObject> getPlaces(
            @Query("input") String input,
            @Query("key") String key,
            @Query("components") String value
    );

    @GET("maps/api/place/details/json")
    Call<JsonObject> getPlacesByID(
            @Query("placeid") String placeID,
            @Query("key") String key
    );

    @GET("maps/api/geocode/json")
    Call<JsonObject> getCordinates(
            @Query("address") String address,
            @Query("key") String key
    );

    @GET("publicationlist")
    Call<JsonObject> getPublications();

    @POST("kutanga-food/view-cart")
    Call<JsonObject> getPlanListing();


    @POST("kutanga-food/add-to-cart")
    public void insertaddCart(
            @Field("product_price") String name,
            @Field("product_id") String username,
            Callback<JSONObject> callback);
}




