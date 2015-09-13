package com.example.ali.topcoderandroid.Api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ali.topcoderandroid.Helpers.VolleySingleton;

import org.json.JSONObject;

/**
 * Created by ali on 27.08.2015.
 */
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
