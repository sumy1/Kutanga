package com.vibescom.kutanga.Constants;

public interface Constants {

    /*****************************Public Static Constant and Keys**********************************/

    String kDefaultAppName = "Kutanga";
    String kIsFirstTime = "isFirstTime";
    String kApiVersion = "apiVersion";
    String kAppPreferences = "KutangaPreferences";
    String kAppPreferencesLanguage = "KutangaPreferenceslanguage";
    String kMessageInternalInconsistency = "Some internal inconsistency occurred. Please try again.";
    String kMessageNetworkError = "Device does not connect to internet.";
    String kSocketTimeOut = "Kutanga Server not responding..";
    String kMessageServerNotRespondingError = "Kutanga server not responding!";
    String kImageBaseUrl = "https://vibestest.com/wip_projects/development/socialsportz/api/assets/public/images/";

    /**********************API RequestParameters And ResponseParameters****************************/

    int PERMISSIONS_REQUEST = 22;
    int REQUEST_LOCATION = 101;
    int REQUEST_PERMISSION_LOCATION = 102;
    int REQUEST_PERMISSIONS_STORAGE = 103;

    //API Base Key
    String kId = "Id";
    String kPageId = "page_id";
    String kStatus = "status";
    String kTag = "tag";
    String kMessage = "message";
    String kResponse = "response";
    String kResponseMessage = "response_message";
    String kSignUp = "Please signup";
    String kAction = "action";
    String kSuccess = "success";
    String kResult = "result";
    String kRecords = "records";
    String kRecord = "record";
    String kLanguage = "lang";
    String kData = "data";
    String kType = "type";
    String kPage = "page";
    String kCurrentPage = "current_page";
    String kFlag = "flag";
    String kTitle = "title";
    String kResults = "results";
    String kTotalRecords = "totalRecords";
    String kFolderName = "folder_name";
    String kTotal="total";
    String kLangPref = "en";// should be change here en to pt before build thr app

    String welcomeScreenShownPref = "welcomeScreenShown";

    String kSeperator = "__";
    String kEmptyString = "";
    String kWhitespace = " ";
    Number kEmptyNumber = 0;

    // Error Messages
    String kDataEmptyMessage = "Record empty please insert data";
    String kDataAlreadySubmitted = "Record already save";
    String kRecordSucsessfull = "Record insert successfully";
    String kAlreadyLogin = "Already login";
    String kAlreadyLogout = "Already logout";
    String kMessageConnecting = "Connecting...";
    String kError = "Error";

    String kCurrentTimeStamp = "CurrentTimeStamp";
    String kTimestamp = "timeStamp";

    // Types
    String kCurrentUser = "currentUser";
    String kAuthToken = "social_media_id";
    String kGenericAuthToken = "genericAuthToken";
    String kDefaultAuthToken = "defaultAuthToken";

    // New parm add for

    String kProviderId = "provider_id";
    String kProvider = "provider";
    String kUserStatus = "user_status";
    String kRememberToken = "remember_token";
    String kCretedAt = "created_at";


    //....Kutanga SignUp...Param...here..

    String kUserEmail = "user_email";
    String kCoupon = "coupon";
    String ksearchFoodRes = "food_or_restaurant";
    String kUserName = "user_name";
    String kUserMobile = "user_mobile";
    String kUserPassword = "password";
    String kUserConfirmPassword = "password_confirmation";
    String kisMobileVerified = "is_mobile_verified";
    String kisEmailVerified = "is_email_verified";
    String kuserStatus = "user_status";
    String kuserCreatedAt = "created_at";
    String kUserId = "user_id";
    String kPassword = "password";
    String kToken = "token";
    String kfoodTypeId="food_type_id";
    String kLocalization="X-localization";


    //marketPlace...

    String kCateId = "cate_id";
    String kParentcateId = "parent_cate_id";
    String kIndustriesId = "industries_id";
    String kcateName = "cate_name";
    String kcateSlug = "cate_slug";
    String kcateImage = "cate_image";
    String kMetaTitle = "meta_title";
    String kMetaKeyword = "meta_keyword";
    String kMetaDes = "meta_description";
    String kCateStatus = "cate_status";
    String kCreatedOn = "created_on";
    String kCreatedBy = "created_by";
    String kUpdatedOn = "updated_on";
    String kUpdatedBy = "updated_by";

    //.End..marketPlace


    //...Restaurants & Food..
    String kRestuarantsTypeId = "resturant_type_id";
    String kTypeName = "type_name";
    String kOfferId = "offer_id";
    String kOfferType = "offer_type";
    String kOfferName = "offer_name";
    String kPromoCode = "promo_code";
    String kPromocodeUse = "promo_code_use";
    String kDiscountType = "dicount_type";
    String kOfferDiscaunt = "offer_discount";
    String kMinOffer = "min_offer";
    String kDiscountUpto = "discount_upto";
    String kStartDate = "start_date";
    String kEndDate = "end_date";
    String kDescription = "description";
    String kImage = "image";
    String ktitle = "title";
    String ksubTitle = "sub_title";
    String kViewAlllabel = "view_all_label";
    String kviewAllValue = "view_all_value";
    String kOrder = "order";
    String kimagepath = "img_path";
    String kImageOfferPath = "image_path";
    String kOfferdata = "data";
    String kproductAttribute = "product_attribute";


    //Resturants Details..

    String kResturant = "restaurant";
    //..End Resturants Details


    //...Search Resturans and Dishes..
    String kproductImagePath = "product_img_path";
    String kresturantsImagePath = "restaurent_img_path";
    String kkeyword = "keyword";
    String kshortkey = "sort_key";
    String kshortOrder = "sort_order";
    String kcurrentShorting = "current_sorting";
    String kdishes = "dishes";
    String kResturants = "restaurants";
    String kproduct = "product";
    String kproductImage="product_image";

    //...End .Search Resturans and Dishes..


    //...inTheSportsLight..

    String kvVendorBusinessId = "vendor_business_id";
    String kBusinessId = "business_id";
    String kVendorId = "vendor_id";
    String kDesignation = "designation";
    String kCompanyName = "company_name";
    String kCoverImage = "cover_image";
    String kLogoImage = "logo_image";
    String kFoodIndustriesType = "food_industry_type";
    String kBusinessType = "business_type";
    String kAddress = "address";
    String kAddress1 = "address1";
    String klatitude = "latitude";
    String kLongitude = "longitude";
    String kPaymentAcceptMode = "payment_accept_mode";
    String kEstimateDeliveryTime = "estimate_delivery_time";
    String kRegistrationNumber = "registration_number";
    String kMinimumOrder = "minimum_order";
    String kcontactEmailId = "contact_email_id";
    String kResturantsCharge = "restaurant_charges";
    String kdeliveryCharges = "delivery_charges";
    String kPackagingTime = "packaging_time";
    String kDisToBeCovered = "distance_to_be_cover";
    String kvendorBusinessStatus = "vendor_business_status";
    String kCreatedAt = "created_at";
    String kCreatedate="created_date";
    String ktemplateSubject="template_subject";
    String ktemplateDetails="template_detail";
    String kUpdatedAt = "updated_at";
    String krating = "rating";
    String kReviewCount = "review_count";
    String kcategories = "categories";
    String kDistance="distance";
    String kVendorProduct="vendorproducts";
    String kOrderBookingStatus="order_booking_status";
    String kdeliveryTime="delivery_time";


    //...TopPic...

    // product attibutes..

    String kProductattibuteId = "product_attribute_id";
    String kProductDetaildId = "product_detail_id";
    String kattibutes = "attibutes";
    String kproductDetails = "product_detail";
    String kProductImage="product_image";

    //..end product attibutes.

    //..product price

    String kPriceId = "price_id";
    String kquantity = "quantity";
    String kqty = "qty";
    String kproductPrice = "product_price";
    String kDiscount = "discount";
    String kdiscountprice = "discounted_price";

    //..end product price

    //..Resturantsproduct;
    String kproductId = "product_id";
    String kRowId="row_id";
    String kOrderCode="order_code";
    String kcateid = "cate_id";
    String ksubcateid = "subcate_id";
    String kproductName = "product_name";
    String kproductSlug = "product_slug";
    String kpreparationTime = "preparation_time";
    String kPopularityTag = "popularity_tag";
    String kproductStatus = "product_status";
    String kproducttag = "product_tag";
    String kProductAvailale = "product_available";
    String kproductdelStatus = "product_del_status";
    String kapprovalMessage = "approval_message";
    String kapprovedBy = "approved_by";
    String krequestdate = "request_date";
    //..end product.

    //Product Details..
    String kmainattribute = "main_attribute";
    String kmainattributeslug = "mail_attribute_slug";


    //..end product details


    //..ViewCart..
    String kTotalItems = "total_items";
    String kTotalPayablePrice = "total_payable_price";
    String kSubTotal = "sub_total";
    String kTotalPrice = "total_price";
    String kCouponDis = "coupon_discount";
    String kCart = "cart";
    String kpriceQuantity = "priceQuantity";
    String kItempriceSum = "item_price_sum";
    String kattribute = "attributes";
    String kcartid = "id";
    String kCartName = "name";
    String kcartData="cartData";
    String kPrice = "price";
    String kQuantity = "quantity";
    String kProductShow = "product_show";
    String kassociateModel = "associatedModel";
    String kVendor = "vendor";
    String kVendorMobileNo="vendor_mobile";
    //..End ViewCart

    //Favroute.

    String kCurrentpage = "current_page";
    String kFirstpage = "first_page_url";
    String kFrom = "from";
    String kLastpage = "last_page";
    String klastpageUrl = "last_page_url";
    String knextpageUrl = "next_page_url";
    String kPath = "path";
    String kPrevpage = "per_page";
    String kPrevpageUrl = "prev_page_url";
    String kTo = "to";
    String kuserfavId = "user_favorite_id";
    String kIsfav = "is_favorite";
    String kBusiness = "business";
    String kdata = "data";
    String kpage = "page";
    String kAddressId="address_id";
    //End Favroute..

    //Addfav


    //end fav

    // Manage Address
    String kuserAddressid = "user_address_id";
    String kShipName = "ship_name";
    String kShipMobile = "ship_mobile";
    String kZip = "zip";
    String kLandMark = "landmark";
    String kLat = "lat";
    String klng = "lng";
    String kLon="lon";
    String kFullAddress = "full_address";
    String kaddressType = "address_type";
    String kUserAddressStatus = "user_address_status";
    String kisDefault = "is_default";
    String kArea = "area";


    //End manage Address

    //PastOrder..

    String getkIndustryId = "industry_id";
    String kOrderConFirmCode = "order_confirmation_code";
    String kOrderPrice = "order_price";
    String kCouponCode = "coupon_code";
    String kCouponCodePrice = "coupon_code_price";
    String kFoodItemTax = "food_item_tax";
    String kDeliveryCharge = "delivery_charges";
    String kFoodDeliveryTax = "food_delivery_tax";
    String kRestaurantsCharge = "restaurant_charges";
    String kRestaurantsChargetax = "restaurant_charges_tax";
    String kdiscaountPrice = "discounted_price";
    String kcanelReson = "cancel_reason";
    String kuserCancelStatus = "user_cancel_status";
    String kuserCancelReson = "user_cancel_reason";
    String kUserCancelReq = "user_cancel_req_on";
    String kPaymentMode = "paymet_mode";
    String kTransitionId = "transction_id";
    String kTaransitionStatus = "transction_status";
    String kTransitionOtherInfo = "transction_orther_info";
    String kTransitionOn = "transction_on";
    String kuserPincode = "user_pincode";
    String kuserAddress = "user_address";
    String kuserCity = "user_city";
    String kuserState = "user_state";
    String knoticetemaplate="notistemplate";
    String kuserCountry = "user_country";
    String kShippingname = "shipping_name";
    String kShippingMobile = "shipping_mobile";
    String kShippingEmail = "shipping_email";
    String kShippingPincode = "shipping_pincode";
    String kShippingAddress = "shipping_address";
    String kShippingCity = "shipping_city";
    String kShippingState = "shipping_state";
    String kShippingCountry = "shipping_country";
    String kOrderDetails="order_detail";

    //End PastOrder

    //AddAddress..

    String kuserAddressId="user_address_id";
    String kFormatedaddress="formatted_address";

    //End Address..

    //Review.
    String kratingId="rating_id";
    String kReview="review";
    String khasObjection="has_objection";
    String kratingStatus="rating_status";


    //End Review..


    //..en Restaurants & Food.

    //..Common landing page.
    String kmarketplaceImagePath = "market_place_img_path";
    String kFoodImagePath = "food_img_path";
    String kmarketPlace = "marketPlace";
    String kFood = "food";
    String kOffers = "offers";
    String kResturantsType = "restaurantTypes";
    String kIntheSportLight = "inTheSportLight";
    String kTopPics = "topPics";
    String kfeturedBrand = "featuredBrand";

    //..end of Common landing page


    //....End SignUp param hre..


    String kDBEmail = "user_email";
    String kConfirmPassword = "password_confirmation";
    String kMobile = "mobile";
    String kPhone = "phone";
    String kTelephone = "telephone";
    String kFax = "fax";
    //...my constans

    String kCity = "city";
    String kState = "state";
    String kCountry = "country";
    String kPincode = "pincode";
    String ksubArea="subArea";


    String kLocationPermissionMsg = "You have denied the access permission permanently, allow the permission from setting.";

    //..end
    String kDBImage = "user_image";
    String kDeviceId = "device_id";
    String kOtp = "otp";
    String kLoginType = "login_type";
    String kprovider="provider";
    String kUserType = "key";
    String kIsOTPVerified = "is_otp_verified";
    String kIsEmailVerified = "is_email_verified";
    String kProfile = "user_profile_image";
    String kEmailVerify = "email_verification";
    String kRegisteredEmail = "register_email";
    String kHashId = "hash_id";
    String ksocialId="social_id";
    String kDate = "date";
    String kReleaseId = "Release_id";
    String kDbRelease = "release_id";
    String kReleaseDate = "Release_date";
    String kDBReleaseDate = "release_date";
    String kContactDetail = "contactdetail";
    String kIsUserActive = "isActive";
    String kReferId = "refer_id";

    // Subscription Plan
    String kSubscription = "Subscription";
    String kSubscriptionId = "subscription_plan_id";
    String kSubscriptionTitle = "subscription_plan_title";
    String kSubscriptionPrice = "subscription_plan_price_kwanzaa";
    String kSubscriptionStartDate = "subscription_start_date";
    String kSubscriptionEndDate = "subscription_end_date";
    String kSubscribed = "subscribed";
    String kTitleExist = "title_exist";
    String kExpiryStatus = "expiryStatus";
    String kExpiryText = "expiryText";

    String kTitleId = "title_id";
    String kTitleDesc = "title_description";
    String kTitleName = "title_name";
    String kTitleImage = "title_image";
    String kTitleStatus = "title_status";
    String kTitleFrequency = "title_frequency";
    String kTitlePrice = "price_per_title";
    String kTempId = "title_release_frequency_id";
    String kImagePath = "background_img_path";
    String kPrices = "prices";

    String kSubscription_id = "subscription_id";
    String kUserSubscriptionId = "user_subscription_id";
    String kTitlePriceId = "title_price_id";
    String kTitlePlanId = "title_plan_id";
    String kTitlePlanName = "title_plan_name";
    String kPriceCost = "price";
    String kPlanIconPath = "plan_icon_path";
    String kPlanIcon = "plan_icon";


    String kPackages = "packages";
    String kPackage = "package";
    String kPackageId = "package_id";
    String kPackageName = "package_name";
    String kPackageDesc = "package_description";
    String kPackageImage = "package_image";
    String kPackageStatus = "package_status";
    String kPackagePrice = "package_price";
    String kPackageTitles = "titles";

    String kTitles = "titles";
    String kSingle = "single";
    String kPayType = "payType";
    String kPlanType = "plan_type";
    String kTotalAmount = "total_amount";
    String kFinalAmount = "total_paid_amount";
    String kPaymentMethod = "payment_method";
    String kOrderStatus = "order_status";

    String kParentId = "parent_id";
    String kOrderId = "order_id";
    String kSubscriptionDate = "subscription_date";
    String kNoRelease = "number_of_release";

    String kFileUrl = "file_url";
    String kFileName = "file_name";
    String kOrderDetailsId="order_detail_id";
    String kProductColor="product_color";
    String kVendorName="vendor_name";
    String kvendorAddress="vendor_address";
    String kItemName="item_name";
    String kItemQuantityType="item_quantity_type";
    String kItemQuantity="item_quantity";
    String kItemPrice="item_price";
    String kItemImage="item_image";
    String kIsDispached="is_dispatched";
    String kuserItemCancelStatus="user_item_cancel_status";
    String kuserCancelReason="user_item_cancel_reason";
    String kuserCancelReqOn="user_item_cancel_req_on";



    //Publication list
    String kPublication = "publication";
    String kPublicationId = "publication_id";
    String kPublicationName = "publication_name";
    String kPublicationImage = "publication_image";
    String kReleaseAvail = "release_available";

    //News
    String kNewsPageId = "news_page_id";
    String kPageNumber = "page_number";
    String kPageImage = "page_image";
    String kPageImageWidth = "page_image_width";
    String kPageImageHeight = "page_image_hight";
    String kPageName = "page_name";

    //Section
    String kSection = "sections";
    String kSectionId = "section_id";
    String kSectionDetailId = "section_detail_id";
    //String kSecDetailTitle ="sec_detail_title";
    String kSectionTitle = "section_title";
    String kSecDetailImage = "sec_detail_image";
    String kSecDetailDescription = "sec_detail_description";
    String kSectionCoords = "section_coords";

    String kLikeStatus = "like_status";
    String kLikeCount = "total_likes";
    String kCommentCount = "total_comments";
    String kComments = "comments";
    String kComment = "comment";
    String kLikeNews = "news_like_dislike";
    String kNewsCommentId = "news_comment_id";
    String kNewsComment = "news_comment";

    //Notification
    String kNotifications = "notifications";
    String kNotification = "notification_detail";
    String kNotificationId = "notis_id";
    String kNotificationTag = "notis_tag";
    String kNotificationTitle = "notis_title";
    String kNotificationDesc = "notis_description";
    String kNotificationDetail = "notis_detail";
    String kExternalLink = "external_link";
    String kIsRead = "is_read";
    String kReadCount="readCount";
    String kUnReadCount="unreadCount";



    //Query
    String kQueries = "queries";
    String kQueryId = "query_id";
    String kQuery = "query";
    String kQueryTitle = "title";
    String kQueryResponse = "response";

    // advertisement
    String kAdvertisement = "advertisement";
    String kAdvertisementId = "advertisement_id";
    String kAdvertisementImage = "advertisement_image";
    String kAdvertisementLink = "advertisement_link";

    //Static Pages
    String kCmsId = "cms_id";
    String kCmsPage = "cms_page";
    String kCmsTitle = "cms_title";
    String kCmsImage = "cms_image";
    String kCmsContent = "cms_content";

    // Facebook Constants
    String kFacebookFields = "fields";
    String kFacebookAllFields = "id,name,link,email,picture,first_name,last_name,gender";

    String kFacebookId = "id";
    String kFacebookEmail = "email";
    String kFacebookFirstName = "first_name";
    String kFacebookLastName = "last_name";
    String kFacebookGender = "gender";

    //Google map api constants
    String kPremise = "premise";
    String kStreetNumber = "street_number";
    String kRoute = "route";
    String kLocality = "locality";
    String kAdministrativeAreaLevel2 = "administrative_area_level_2";
    String kAdministrativeAreaLevel1 = "administrative_area_level_1";
    String kPostalCode = "postal_code";
    String kGCountry = "country";
    String kimage = "image";

    /**
     * Http Status for API Response
     */
    enum HTTPStatus {
        success(1),
        badRequest(400),
        unauthorized(401),
        notFound(404),
        methodNotAllowed(405),
        notAcceptable(406),
        proxyAuthenticationRequired(407),
        requestTimeout(408),
        error(-100);         //No option found.

        //Definition
        private int httpStatus;

        HTTPStatus(int httpStatus) {
            this.httpStatus = httpStatus;
        }

        public static HTTPStatus getStatus(int status) {
            for (HTTPStatus httpStatus : HTTPStatus.values()) {
                if (httpStatus.httpStatus == status) {
                    return httpStatus;
                }
            }
            return error;
        }

        public Integer getValue() {
            return this.httpStatus;
        }
    }

    /**
     * Status Enumeration for Task Status
     */
    enum Status {
        success(1),
        fail(0),
        reachLimit(2),
        noChange(3),
        history(4),            //If xmpp message is history
        normal(5),            //If Normal xmpp message
        discard(6);

        //Definition
        private int value;

        Status(int status) {
            this.value = status;
        }

        public static Status getStatus(int value) {
            for (Status status : Status.values()) {
                if (status.value == value) {
                    return status;
                }
            }
            return fail;
        }

        /**
         * To get Integer value of corresponding emum
         */
        public Integer getValue() {
            return this.value;
        }

    }


    /**
     * PlanType Enumeration for facility/Academy types
     */
    enum PlanType {
        Supreme(3),
        Ultimate(2),
        Starter(1);

        //Definition
        private int value;

        PlanType(int type) {
            this.value = type;
        }

        public static PlanType getPlanType(int value) {
            for (PlanType status : PlanType.values()) {
                if (status.value == value) {
                    return status;
                }
            }
            return Starter;
        }

        /**
         * To get Integer value of corresponding emum
         */
        public Integer getValue() {
            return this.value;
        }

    }

    /**
     * LoginType Enumeration for user login scenario
     */
    enum LoginType {
        Normal(1),
        Facebook(2),
        Google(3);

        //Definition
        private int value;

        LoginType(int status) {
            this.value = status;
        }

        public static Constants.LoginType getLoginType(int value) {
            for (Constants.LoginType status : Constants.LoginType.values()) {
                if (status.value == value) {
                    return status;
                }
            }
            return Normal;
        }

        /**
         * To get Integer value of corresponding emum
         */
        public Integer getValue() {
            return this.value;
        }

    }

    /**
     * PaymentStatus Enumeration for any type of status
     */
    enum PaymentStatus {
        PaymentDone(0),
        PaymentPending(1);

        //Definition
        private int value;

        PaymentStatus(int status) {
            this.value = status;
        }

        public static Constants.PaymentStatus getPaymentStatus(int value) {
            for (Constants.PaymentStatus status : Constants.PaymentStatus.values()) {
                if (status.value == value) {
                    return status;
                }
            }
            return PaymentDone;
        }

        /**
         * To get Integer value of corresponding emum
         */
        public Integer getValue() {
            return this.value;
        }

    }

}
