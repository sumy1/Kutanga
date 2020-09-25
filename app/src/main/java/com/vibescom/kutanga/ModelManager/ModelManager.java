package com.vibescom.kutanga.ModelManager;

import android.util.Log;

import com.vibescom.kutanga.APIManager.ApiInterface;
import com.vibescom.kutanga.APIManager.ApiManager;
import com.vibescom.kutanga.ApplicationManager;
import com.vibescom.kutanga.BaseManager.BaseManager;
import com.vibescom.kutanga.Blocks.Block;
import com.vibescom.kutanga.Blocks.GenericResponse;
import com.vibescom.kutanga.Broadcast.ReachabilityManager;
import com.vibescom.kutanga.Constants.Constants;
import com.vibescom.kutanga.DispatchQueue.DispatchQueue;
import com.vibescom.kutanga.Models.AddfavModel.AddFavrouteModel;
import com.vibescom.kutanga.Models.BaseModel;
import com.vibescom.kutanga.Models.CurrentUser;
import com.vibescom.kutanga.Models.FavrouteModel.FavrouteModel;
import com.vibescom.kutanga.Models.ManageAddressModel.AddressModel;
import com.vibescom.kutanga.Models.PastOrderModle.PastOrderModel;
import com.vibescom.kutanga.Models.RestaurantsFoodCommonLanding;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.FoodLandingPageModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.OfferListModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ResturantsDishesSearchModel;
import com.vibescom.kutanga.Models.RestaurantsFoodModel.ReturantsDetailsModel;
import com.vibescom.kutanga.Models.ReviewModel.ReviewModel;
import com.vibescom.kutanga.Models.TakeYourPicDetailsModel.TakeYourPicModel;
import com.vibescom.kutanga.Models.UpdatedcartModel.Updatedcartdata;
import com.vibescom.kutanga.Models.ViewCart.ViewcartModel;
import com.vibescom.kutanga.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.vibescom.kutanga.APIManager.ApiInterface.kFPVeOTP;
import static com.vibescom.kutanga.APIManager.ApiInterface.kuserOtpVerified;

/**
 * Singleton class to manage all models in projects. this is basically provide data to view in the
 * form of models
 */

public class ModelManager extends BaseManager implements Constants {

    private final static String TAG = ModelManager.class.getSimpleName();
    //Static Properties
    private static ModelManager _ModelManager;

    //Instance variables
    private static CurrentUser mCurrentUser = null;
    private DispatchQueue dispatchQueue =
            new DispatchQueue("com.queue.serial.modelmanager", DispatchQueue.QoS.userInitiated);

    /**
     * private constructor make it to be Singleton class
     */
    private ModelManager() {
    }

    /**
     * method to create a threadsafe singleton class instance
     *
     * @return a thread safe singleton object of model manager
     */
    public static synchronized ModelManager modelManager() {
        if (_ModelManager == null) {
            _ModelManager = new ModelManager();
            mCurrentUser = getDataFromPreferences(kCurrentUser, CurrentUser.class);
            Log.e(TAG, mCurrentUser + " ");
        }
        return _ModelManager;
    }

    public DispatchQueue getDispatchQueue() {
        return dispatchQueue;
    }

    /**
     * to initialize the singleton object
     */
    public void initializeModelManager() {
        System.out.println("ModelManager object initialized.");
    }

    /**
     * getter and setter method for current user
     *
     * @return {@link CurrentUser} if user already logged in, null otherwise
     */
    public synchronized CurrentUser getCurrentUser() {
        return mCurrentUser;
    }

    public synchronized void setCurrentUser(CurrentUser o) {
        mCurrentUser = o;
        archiveCurrentUser();
    }

    /**
     * set response to @User
     *
     * @param genricResponse contains JSONObject with user information in it
     */
    private void setupCurrentUser(GenericResponse<JSONObject> genricResponse, int status) {
        try {
            mCurrentUser = new CurrentUser(genricResponse.getObject().getJSONObject(kResponse));
           /* if(status==news_head)
                mCurrentUser.setmFirstLogin(true);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        archiveCurrentUser();
    }


    /**
     * Stores {@link CurrentUser} to the share preferences and synchronize sharedpreferece
     */
    private synchronized void archiveCurrentUser() {
        saveDataIntoPreferences(mCurrentUser, BaseModel.kCurrentUser);
    }

    //User API

    /**
     * method will be called when user login through system eg. with email and password
     *
     * @param parameters include user info provided by user
     * @param success    Block passed as callback for success condition
     * @param failure    Block passed as callback for failure condition11
     */
    public void userLoginRequest(HashMap<String, Object> parameters, Block.Success<CurrentUser> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kUserLogin, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        try {
                            setupCurrentUser(genricResponse, 0);
                            GenericResponse<CurrentUser> generic = new GenericResponse<>(mCurrentUser);
                            DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                        } catch (Exception e) {
                            e.printStackTrace();
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, e.getMessage()));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!ReachabilityManager.getNetworkStatus())
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                        else
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Login", message);
                    DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }


    public void userProfileUpdate(HashMap<String, Object> parameters, Block.Success<CurrentUser> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kuserprofileUpdate, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        try {
                            setupCurrentUser(genricResponse, 0);
                            GenericResponse<CurrentUser> generic = new GenericResponse<>(mCurrentUser);
                            DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                        } catch (Exception e) {
                            e.printStackTrace();
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, e.getMessage()));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!ReachabilityManager.getNetworkStatus())
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                        else
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Login", message);
                    DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }


    /**
     * method will be called when user register through system
     *
     * @param parameters include user info provided by user
     * @param success    Block passed as callback for success condition
     * @param failure    Block passed as callback for failure condition
     */
    public void userRegisterRequest(HashMap<String, Object> parameters, Block.Success<CurrentUser> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kUserReg, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        setupCurrentUser(genricResponse, 1);
                        GenericResponse<CurrentUser> generic = new GenericResponse<>(mCurrentUser);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!ReachabilityManager.getNetworkStatus())
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                        else
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("SignUp", message);
                    DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }

    public void userUpdateRequest(HashMap<String, Object> parameters, Block.Success<CurrentUser> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kUserInfo, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        try {
                            setupCurrentUser(genricResponse, 0);
                            GenericResponse<CurrentUser> generic = new GenericResponse<>(mCurrentUser);
                            DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                        } catch (Exception e) {
                            e.printStackTrace();
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, e.getMessage()));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!ReachabilityManager.getNetworkStatus())
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                        else
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Login", message);
                    DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }

    /**
     * method will be called when user register through system
     *
     * @param parameters include user info provided by user
     * @param success    Block passed as callback for success condition
     * @param failure    Block passed as callback for failure condition
     */
    public void userResendOTP(HashMap<String, Object> parameters, Block.Success<String> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kuserResenOtp, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        String otp = genricResponse.getObject().getString(kMessage);
                        GenericResponse<String> generic = new GenericResponse<>(otp);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!ReachabilityManager.getNetworkStatus())
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                        else
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("SignUp", message);
                    DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }

    public void userResetResendOTP(HashMap<String, Object> parameters, Block.Success<String> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kResendOtp, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject jsonObject = genricResponse.getObject();
                        String msg = jsonObject.getString(kMessage);
                        GenericResponse<String> generic = new GenericResponse<>(msg);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!ReachabilityManager.getNetworkStatus())
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                        else
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("SignUp", message);
                    DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }


    /**
     * method will be called when user mobile Verification through system
     *
     * @param parameters include user info provided by user
     * @param success    Block passed as callback for success condition
     * @param failure    Block passed as callback for failure condition
     */
    public void userEmailVerification(HashMap<String, Object> parameters, Block.Success<CurrentUser> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(kuserOtpVerified, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        setupCurrentUser(genricResponse, 1);
                        GenericResponse<CurrentUser> generic = new GenericResponse<>(mCurrentUser);
                        mCurrentUser.setIsEmailVerified("1");
                        archiveCurrentUser();
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!ReachabilityManager.getNetworkStatus())
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                        else
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Email Verification", message);
                    DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }

    public void userForgotPasswordVerification(HashMap<String, Object> parameters, Block.Success<String> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(kFPVeOTP, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject jsonObject = genricResponse.getObject();
                        String msg = jsonObject.getString(kMessage);
                        GenericResponse<String> generic = new GenericResponse<>(msg);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!ReachabilityManager.getNetworkStatus())
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                        else
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Email Verification", message);
                    DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }

    /**
     * method will be called when user change password through system
     *
     * @param parameters include user info provided by user
     * @param success    Block passed as callback for success condition
     * @param failure    Block passed as callback for failure condition
     */
    public void userChangePassword(HashMap<String, Object> parameters, Block.Success<String> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kChangePassword, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        String new_password = genricResponse.getObject().getString(kResponse);
                        mCurrentUser.setPassword(new_password);
                        archiveCurrentUser();
                        GenericResponse<String> generic = new GenericResponse<>(new_password);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Change Password", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }

    /**
     * method will be called when user forgot Password through system
     *
     * @param parameters include user info provided by user
     * @param success    Block passed as callback for success condition
     * @param failure    Block passed as callback for failure condition
     */
    public void userForgotPassword(HashMap<String, Object> parameters, Block.Success<String> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kForgotPasswordd, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        String msg = genricResponse.getObject().getString(kResponse);
                        GenericResponse<String> generic = new GenericResponse<>(msg);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Forgot Password", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }


    public void userResetPassword(HashMap<String, Object> parameters, Block.Success<String> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kResetPassword, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        String msg = genricResponse.getObject().getString(kMessage);
                        GenericResponse<String> generic = new GenericResponse<>(msg);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Forgot Password", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }

    /**
     * method will be to logout user from application
     * <p>
     * * @param status  Block passed as callback for success condition
     *
     * @param failure Block passed as callback for failure condition
     */
    public void getLogout(Block.Status status, Block.Failure failure) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(kUserEmail, ModelManager.modelManager().getCurrentUser().getUserEmail());
        Log.e(TAG, parameters.toString());

        dispatchQueue.async(() ->
                ApiManager.ApiLogout().processFormRequest(ApiInterface.kUserLogout, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        DispatchQueue.main(() -> status.iStatus(iStatus));
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!ReachabilityManager.getNetworkStatus())
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                        else
                            DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Logout", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));


    }

    public void userSocialLoginRequest(HashMap<String, Object> parameters, Block.Success<CurrentUser> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kSocialLogin, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        setupCurrentUser(genricResponse, 0);
                        GenericResponse<CurrentUser> generic = new GenericResponse<>(mCurrentUser);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Login", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }


    public void getLandingPageFood(HashMap<String, Object> parameters, Block.Success<RestaurantsFoodCommonLanding> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.klandingPageFood, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        RestaurantsFoodCommonLanding restaurantsFoodCommonLanding = new RestaurantsFoodCommonLanding(genricResponse.getObject().getJSONObject(kResponse));
                        GenericResponse<RestaurantsFoodCommonLanding> generic = new GenericResponse<>(restaurantsFoodCommonLanding);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Login", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }


    public void getResturantsDetails(HashMap<String, Object> parameters, Block.Success<ReturantsDetailsModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kResturantsDetails, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        ReturantsDetailsModel returantsDetailsModel = new ReturantsDetailsModel(genricResponse.getObject().getJSONObject(kResponse));

                        GenericResponse<ReturantsDetailsModel> generic = new GenericResponse<>(returantsDetailsModel);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("Login", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));
                }));
    }


    public void getFoodlandingpage(HashMap<String, Object> parameters, Block.Success<FoodLandingPageModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.KFoodLandingPage, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject().getJSONObject(kResponse);
                        FoodLandingPageModel type = new FoodLandingPageModel(obj);
                        GenericResponse<FoodLandingPageModel> generic = new GenericResponse<>(type);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }

    public void getViewcart(Block.Success<ViewcartModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientAdd().processSubscriptionRequest((Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        //Log.e("NewResponse",genricResponse.getObject().toString());

                        JSONObject obj = genricResponse.getObject().getJSONObject(kResponse);

                        ViewcartModel type = new ViewcartModel(obj);
                        GenericResponse<ViewcartModel> generic = new GenericResponse<>(type);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }


    public void getFavroute(HashMap<String, Object> parameters, Block.Success<FavrouteModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kFavroroute, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        //Log.e("NewResponse",genricResponse.getObject().toString());

                        JSONObject obj = genricResponse.getObject().getJSONObject(kResponse);

                        FavrouteModel type = new FavrouteModel(obj);
                        GenericResponse<FavrouteModel> generic = new GenericResponse<>(type);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }


    public void getpastOrder(HashMap<String, Object> parameters, Block.Success<PastOrderModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kPastOrder, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        Log.e("NewResponse", genricResponse.getObject().toString());

                        JSONObject obj = genricResponse.getObject().getJSONObject(kResponse);

                        PastOrderModel type = new PastOrderModel(obj);
                        GenericResponse<PastOrderModel> generic = new GenericResponse<>(type);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }


    public void getNotification(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kNotification, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        //Log.e("NewResponse",genricResponse.getObject().toString());

                        JSONObject obj = genricResponse.getObject().getJSONObject(kResponse);

                        //PastOrderModel type = new PastOrderModel(obj);
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }

    public void getAddressmanage(HashMap<String, Object> parameters, Block.Success<AddressModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kmanageaddress, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {

                        JSONObject obj = genricResponse.getObject().getJSONObject(kResponse);

                        AddressModel type = new AddressModel(obj);
                        GenericResponse<AddressModel> generic = new GenericResponse<>(type);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }


    public void getReview(HashMap<String, Object> parameters, Block.Success<ReviewModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kReviewList, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {

                        JSONObject obj = genricResponse.getObject().getJSONObject(kResponse);

                        ReviewModel type = new ReviewModel(obj);
                        GenericResponse<ReviewModel> generic = new GenericResponse<>(type);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }

    public void getDeleteAddress(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kdeleteAddress, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {

                        JSONObject obj = genricResponse.getObject();

                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }


    public void getreviewPost(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kPostReview, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {

                        JSONObject obj = genricResponse.getObject();

                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }

    public void getAddFavroute(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kAddFavroute, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        //Log.e("NewResponse",genricResponse.getObject().toString());

                        JSONObject obj = genricResponse.getObject();

                        AddFavrouteModel type = new AddFavrouteModel(obj);
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }


    public void addPlaceorderAddress(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kAddressPlaceOrder, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        //Log.e("NewResponse",genricResponse.getObject().toString());

                        JSONObject obj = genricResponse.getObject();

                        AddFavrouteModel type = new AddFavrouteModel(obj);
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }


    public void getOfferList(HashMap<String, Object> parameters, Block.Success<OfferListModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kOfferList, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        OfferListModel obj = new OfferListModel(genricResponse.getObject().getJSONObject(kResponse));
                        GenericResponse<OfferListModel> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }

    public void getAddAddress(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClient().processFormRequest(ApiInterface.kAddAddress, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject();
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }


    public void getSearchList(HashMap<String, Object> parameters, Block.Success<ResturantsDishesSearchModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.ksearchResturantsDishes, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        ResturantsDishesSearchModel obj = new ResturantsDishesSearchModel(genricResponse.getObject().getJSONObject(kResponse));
                        GenericResponse<ResturantsDishesSearchModel> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }


    public void getTakeYourPicDetails(HashMap<String, Object> parameters, Block.Success<TakeYourPicModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientt().processFormRequest(ApiInterface.kTakeYourPicDetails, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        TakeYourPicModel obj = new TakeYourPicModel(genricResponse.getObject().getJSONObject(kResponse));
                        GenericResponse<TakeYourPicModel> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_internal_inconsistency)));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, ApplicationManager.getContext().getResources().getString(R.string.message_network_error)));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, ApplicationManager.getContext().getResources().getString(R.string.message_server_not_responding_error)));

                }));
    }

    public void getAddtocart(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientAdd().processFormRequest(ApiInterface.kaddtocart, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject();
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageNetworkError));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }

    public void getCheckedItem(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientAdd().processFormRequest(ApiInterface.kCheckcartItem, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject();
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageNetworkError));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }


    public void getRemovecartItem(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientAdd().processFormRequest(ApiInterface.kRemovecartItem, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject();
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageNetworkError));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }


    public void getCancelOrder(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientPlaceOrder().processFormRequest(ApiInterface.kCnacelOrder, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject();
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageNetworkError));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }

    public void getUpdateCart(HashMap<String, Object> parameters, Block.Success<Updatedcartdata> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientAdd().processFormRequest(ApiInterface.kUpdateCart, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject().getJSONObject(kResponse);

                        Updatedcartdata type = new Updatedcartdata(obj);

                        GenericResponse<Updatedcartdata> generic = new GenericResponse<>(type);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageNetworkError));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }

    public void getUpdateCartt(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientAdd().processFormRequest(ApiInterface.kUpdateCart, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject();

                        //Updatedcartdata type = new Updatedcartdata(obj);

                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageNetworkError));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }

    public void getClearAllcart(HashMap<String, Object> parameters, Block.Success<ViewcartModel> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientAdd().processFormRequest(ApiInterface.kClearAllCart, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject().getJSONObject(kResponse);

                        ViewcartModel type = new ViewcartModel(obj);
                        GenericResponse<ViewcartModel> generic = new GenericResponse<>(type);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageNetworkError));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }

    public void getApplayCoupon(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientAdd().processFormRequest(ApiInterface.kApplayCoupon, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject();
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageNetworkError));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }

    public void getPlaceOrder(HashMap<String, Object> parameters, Block.Success<JSONObject> success, Block.Failure failure) {
        dispatchQueue.async(() ->
                ApiManager.ApiClientPlaceOrder().processFormRequest(ApiInterface.kPlaceOrder, parameters, (Status iStatus, GenericResponse<JSONObject> genricResponse) -> {
                    try {
                        JSONObject obj = genricResponse.getObject();
                        GenericResponse<JSONObject> generic = new GenericResponse<>(obj);
                        DispatchQueue.main(() -> success.iSuccess(iStatus, generic));
                    } catch (Exception e) {
                        e.printStackTrace();
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageInternalInconsistency));
                    }
                }, (Status statusFail, String message) -> {
                    Log.e("News List", message);
                    if (!ReachabilityManager.getNetworkStatus())
                        DispatchQueue.main(() -> failure.iFailure(Status.fail, kMessageNetworkError));
                    else
                        DispatchQueue.main(() -> failure.iFailure(statusFail, message));

                }));
    }

}
