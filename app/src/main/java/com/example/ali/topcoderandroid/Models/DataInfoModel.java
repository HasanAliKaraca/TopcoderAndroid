package com.example.ali.topcoderandroid.Models;

import com.example.ali.topcoderandroid.Helpers.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ali on 27.08.2015.
 */
public class DataInfoModel {
    private int total;

    private int pageSize;

    private ArrayList<ChallengeModel> data;

    private int pageIndex;

    public DataInfoModel() {
        data = new ArrayList<>();
    }

    public static ArrayList<ChallengeModel> mapJsonToChallengeModelArray(JSONObject json) {
        DataInfoModel d = new DataInfoModel();

        ArrayList<ChallengeModel> list = new ArrayList<>();

        try {
            JSONArray data = json.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);

                ChallengeModel item = ChallengeModel.mapJsonToChallengeModel(obj);
                if (item != null) {
                    list.add(item);
                }
            }


        } catch (JSONException e) {
            LogHelper.Log(e);
            return null;
        }

        return list;
    }


    public ArrayList<ChallengeModel> getData() {
        return data;
    }

    public void setData(ArrayList<ChallengeModel> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

}
