/*
* Copyright (C) 2015 Hasan Ali Karaca - http://www.hasanalikaraca.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.ali.topcoderandroid.Models;

import com.example.ali.topcoderandroid.Helpers.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
                if (item != null && item.getCurrentPhaseRemainingTime() > 0) {
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
