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

package com.example.ali.topcoderandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ali.topcoderandroid.Api.TopcoderClient;
import com.example.ali.topcoderandroid.Helpers.LogHelper;
import com.example.ali.topcoderandroid.Models.ChallengeDetailModel;
import com.example.ali.topcoderandroid.Models.ChallengeModel;
import com.example.ali.topcoderandroid.Models.DataInfoModel;
import com.example.ali.topcoderandroid.R;

import org.json.JSONObject;

public class ChallengeDetailActivity extends AppCompatActivity {

    public static String CHALLENGE_ID_TAG = "CHALLENGE_ID";
    private ChallengeModel challenge;
    private CoordinatorLayout detail_content_container;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);

        Intent i = getIntent();
        //challengeId =  i.getStringExtra(CHALLENGE_ID_TAG);
        challenge = (ChallengeModel) i.getSerializableExtra(CHALLENGE_ID_TAG);

        if (challenge != null) {


            Toolbar detail_toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
            if (detail_toolbar != null) {
                setSupportActionBar(detail_toolbar);
                getSupportActionBar().setTitle(challenge.getChallengeName());
            }

            fetchChallengeDetail(challenge);

        }

        //init for snackbar
        detail_content_container = (CoordinatorLayout) findViewById(R.id.detail_content_container);


    }

    public void fetchChallengeDetail(ChallengeModel challenge) {

        String challengeId = challenge.getChallengeId();
        if (challengeId == null || challengeId.isEmpty()) return;

        TopcoderClient client = new TopcoderClient();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressBar();

                try {

                    ChallengeDetailModel model = DataInfoModel.mapJsonToChallengeDetailModel(response);

                    if (model != null)
                        UpdateUI(model);

                } catch (Exception e) {
                    LogHelper.Log(e);
                }

                String t = "";

                //swipeRefreshLayout.setRefreshing(false);

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressBar();

                try {

                    if (error.networkResponse != null) {
                        String networkResponseStatusCode = String.valueOf(error.networkResponse.statusCode);
                        String errorMessage = "Network error: " + networkResponseStatusCode + ". Try again in a few sec";

                        Snackbar.make(detail_content_container, errorMessage, Snackbar.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    LogHelper.Log(e);
                }

                LogHelper.Log(error);

                //UpdateChallengeListOnUI(new ArrayList<ChallengeModel>());

                //swipeRefreshLayout.setRefreshing(false);
            }
        };

        client.getChallengeDetails(context, challengeId, responseListener, errorListener);
        showProgressBar();

    }

    private void UpdateUI(ChallengeDetailModel model) {


        TextView tvChallengeDetailContent = (TextView) findViewById(R.id.tvChallengeDetailContent);

        Spanned detailHtmlContent = Html.fromHtml(model.getDetailedRequirements());

        tvChallengeDetailContent.setText(detailHtmlContent);


    }


    private void showProgressBar() {
        //LinearLayout content = (LinearLayout) findViewById(R.id.content);
        CardView content = (CardView) findViewById(R.id.content);
        content.setVisibility(View.GONE);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        //LinearLayout content = (LinearLayout) findViewById(R.id.content);
        CardView content = (CardView) findViewById(R.id.content);
        content.setVisibility(View.VISIBLE);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenge_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
