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

package com.example.ali.topcoderandroid.Api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ali.topcoderandroid.Helpers.VolleySingleton;

import org.json.JSONObject;

public class TopcoderClient {

    private final String API_BASE_URL = "https://api.topcoder.com/v2/";
    private final String API_CHALLENGE_DETAIL = "challenges/";
    private final String API_CHALLENGES = "challenges/active?type=";
    private final ChallengeType challengeType;

    public TopcoderClient() {
        this.challengeType = ChallengeType.Develop;
    }

    public String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }


    public void getChallenges(Context context, ChallengeType challengeType, Response.Listener<JSONObject> responseListener,
                              Response.ErrorListener errorListener) {

        String challengeTypeString = challengeType.toString().isEmpty() ? "develop" : challengeType.toString();

        if (challengeType == ChallengeType.Data) {
            challengeTypeString = "develop&technologies=Data+Science&challengeType=Code";
        }

        String url = getApiUrl(API_CHALLENGES + challengeTypeString);


        JsonObjectRequest jsObjRequest =
                new JsonObjectRequest(Request.Method.GET, url, (String) null, responseListener, errorListener);

        setRetryPolicy(jsObjRequest);

        VolleySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }


    public void getChallengeDetails(Context context, Long challengeId, Response.Listener<JSONObject> responseListener,
                                    Response.ErrorListener errorListener) {

        String url = getApiUrl(API_CHALLENGE_DETAIL + challengeId);


        JsonObjectRequest jsObjRequest =
                new JsonObjectRequest(Request.Method.GET, url, (String) null, responseListener, errorListener);

        setRetryPolicy(jsObjRequest);

        VolleySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    private void setRetryPolicy(JsonObjectRequest jsonObjectRequest) {
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


}
