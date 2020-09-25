package com.vibescom.kutanga.Models.RestaurantsFoodModel;

import org.json.JSONObject;

import static com.vibescom.kutanga.Constants.Constants.kCreatedBy;
import static com.vibescom.kutanga.Constants.Constants.kCreatedOn;
import static com.vibescom.kutanga.Constants.Constants.kDescription;
import static com.vibescom.kutanga.Constants.Constants.kDiscountType;
import static com.vibescom.kutanga.Constants.Constants.kDiscountUpto;
import static com.vibescom.kutanga.Constants.Constants.kEndDate;
import static com.vibescom.kutanga.Constants.Constants.kImage;
import static com.vibescom.kutanga.Constants.Constants.kIndustriesId;
import static com.vibescom.kutanga.Constants.Constants.kMinOffer;
import static com.vibescom.kutanga.Constants.Constants.kOfferDiscaunt;
import static com.vibescom.kutanga.Constants.Constants.kOfferId;
import static com.vibescom.kutanga.Constants.Constants.kOfferName;
import static com.vibescom.kutanga.Constants.Constants.kOfferType;
import static com.vibescom.kutanga.Constants.Constants.kPromoCode;
import static com.vibescom.kutanga.Constants.Constants.kPromocodeUse;
import static com.vibescom.kutanga.Constants.Constants.kStartDate;
import static com.vibescom.kutanga.Constants.Constants.kStatus;
import static com.vibescom.kutanga.Models.BaseModel.getValue;

public class OfferData {
    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getIndustriesId() {
        return industriesId;
    }

    public void setIndustriesId(String industriesId) {
        this.industriesId = industriesId;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getOffername() {
        return offername;
    }

    public void setOffername(String offername) {
        this.offername = offername;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPromocodeUse() {
        return promocodeUse;
    }

    public void setPromocodeUse(String promocodeUse) {
        this.promocodeUse = promocodeUse;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getOffferDiscount() {
        return offferDiscount;
    }

    public void setOffferDiscount(String offferDiscount) {
        this.offferDiscount = offferDiscount;
    }

    public String getMinOffer() {
        return minOffer;
    }

    public void setMinOffer(String minOffer) {
        this.minOffer = minOffer;
    }

    public String getDiscountUpto() {
        return discountUpto;
    }

    public void setDiscountUpto(String discountUpto) {
        this.discountUpto = discountUpto;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSattus() {
        return sattus;
    }

    public void setSattus(String sattus) {
        this.sattus = sattus;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    private int offerId;
    private String industriesId;
    private String offerType;
    private String offername;
    private String promoCode;
    private String promocodeUse;
    private String discountType;
    private String offferDiscount;
    private  String minOffer;
    private String discountUpto;
    private String StartDate;
    private String EndDate;
    private String Description;
    private String image;
    private String sattus;
    private String createdOn;
    private String createdBy;

    public OfferData(JSONObject jsonObject){
        this.offerId = getValue(jsonObject,kOfferId,Integer.class);
        this.createdBy=getValue(jsonObject,kCreatedOn,String.class);
        this.createdBy=getValue(jsonObject,kCreatedBy,String.class);
        this.Description=getValue(jsonObject,kDescription,String.class);
        this.discountType=getValue(jsonObject,kDiscountType,String.class);
        this.discountUpto=getValue(jsonObject,kDiscountUpto,String.class);
        this.EndDate=getValue(jsonObject,kEndDate,String.class);
        this.image=getValue(jsonObject,kImage,String.class);
        this.industriesId=getValue(jsonObject,kIndustriesId,String.class);
        this.minOffer=getValue(jsonObject,kMinOffer,String.class);
        this.offername=getValue(jsonObject,kOfferName,String.class);
        this.offerId=getValue(jsonObject,kOfferId,String.class);
        this.offerType=getValue(jsonObject,kOfferType,String.class);
        this.offferDiscount=getValue(jsonObject,kOfferDiscaunt,String.class);
        this.promoCode=getValue(jsonObject,kPromoCode,String.class);
        this.promocodeUse=getValue(jsonObject,kPromocodeUse,String.class);
        this.sattus=getValue(jsonObject,kStatus,String.class);
        this.StartDate=getValue(jsonObject,kStartDate,String.class);
    }
}
