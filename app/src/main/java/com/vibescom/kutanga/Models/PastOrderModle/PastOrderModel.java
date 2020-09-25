package com.vibescom.kutanga.Models.PastOrderModle;

import com.vibescom.kutanga.Models.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class PastOrderModel extends BaseModel implements Serializable {

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public String getFirstpageUrl() {
        return firstpageUrl;
    }

    public void setFirstpageUrl(String firstpageUrl) {
        this.firstpageUrl = firstpageUrl;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLastpage() {
        return lastpage;
    }

    public void setLastpage(int lastpage) {
        this.lastpage = lastpage;
    }

    public String getLastpageUrl() {
        return lastpageUrl;
    }

    public void setLastpageUrl(String lastpageUrl) {
        this.lastpageUrl = lastpageUrl;
    }

    public String getNextpageurl() {
        return nextpageurl;
    }

    public void setNextpageurl(String nextpageurl) {
        this.nextpageurl = nextpageurl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public CopyOnWriteArrayList<PastOrderData> getPastDataModels() {
        return pastOrderData;
    }

    public void setPastDataModels(CopyOnWriteArrayList<PastOrderData> pastOrderData) {
        this.pastOrderData = pastOrderData;
    }

    private int currentpage;
    private String firstpageUrl;
    private int from;
    private int lastpage;
    private String lastpageUrl;
    private String nextpageurl;
    private String path;
    private int perpage;
    private String prevPageUrl;
    private int to;
    private int total;

    public String getRes_img_path() {
        return res_img_path;
    }

    public void setRes_img_path(String res_img_path) {
        this.res_img_path = res_img_path;
    }

    private String res_img_path;

    private CopyOnWriteArrayList<PastOrderData>pastOrderData;





    public PastOrderModel(JSONObject jsonObject){


        this.res_img_path=getValue(jsonObject,kresturantsImagePath,String.class);

        if(jsonObject.has(kCurrentPage)){
            try {
                this.currentpage=jsonObject.getInt(kCurrentPage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(jsonObject.has(kTo)){
            try {
                this.to=jsonObject.getInt(kTo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has(kTotal)){
            try {
                this.total=jsonObject.getInt(kTotal);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.firstpageUrl=getValue(jsonObject,kFirstpage,String.class);
        this.lastpage=getValue(jsonObject,kLastpage,String.class);

        if(jsonObject.has(kLastpage)){
            try {
                this.lastpage=jsonObject.getInt(kLastpage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.nextpageurl=getValue(jsonObject,knextpageUrl,String.class);
        this.lastpageUrl=getValue(jsonObject,klastpageUrl,String.class);


        if(jsonObject.has(kPrevpage)){
            try {
                this.perpage=jsonObject.getInt(kPrevpage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.path=getValue(jsonObject,kPath,String.class);

        if(jsonObject.has(kFrom)){
            try {
                this.from=jsonObject.getInt(kFrom);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.prevPageUrl=getValue(jsonObject,kPrevpageUrl,String.class);

        if(jsonObject.has(kdata)){
            try {
                this.pastOrderData = handleProductPrice(jsonObject.getJSONArray(kdata));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private CopyOnWriteArrayList<PastOrderData> handleProductPrice(JSONArray jsonArray) throws JSONException {
        CopyOnWriteArrayList<PastOrderData> marketPlaces = new CopyOnWriteArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            marketPlaces.add(new PastOrderData(jsonArray.getJSONObject(i)));
        }
        return marketPlaces;
    }


}
