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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ali.topcoderandroid.Api.TopcoderClient;
import com.example.ali.topcoderandroid.Helpers.DateTimeParser;
import com.example.ali.topcoderandroid.Helpers.LogHelper;
import com.example.ali.topcoderandroid.Models.ChallengeDetailModel;
import com.example.ali.topcoderandroid.Models.ChallengeModel;
import com.example.ali.topcoderandroid.Models.DataInfoModel;
import com.example.ali.topcoderandroid.R;

import org.json.JSONObject;

import java.util.Arrays;

public class ChallengeDetailActivity extends BaseActivity {

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

                    if (model != null) {
                        bindChallenge(model);
                    }

                } catch (Exception e) {
                    LogHelper.Log(e);
                }


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


    private void showProgressBar() {

        try {

            findViewById(R.id.content).setVisibility(View.GONE);

            findViewById(R.id.progressBarContainer).setVisibility(View.VISIBLE);

        } catch (Exception e) {
            LogHelper.Log(e);
        }
    }


    private void hideProgressBar() {
        try {
            findViewById(R.id.content).setVisibility(View.VISIBLE);

            findViewById(R.id.progressBarContainer).setVisibility(View.GONE);

        } catch (Exception e) {
            LogHelper.Log(e);
        }
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

    private void bindChallenge(ChallengeDetailModel challengeDetail) {

        try {

            Spanned detailHtmlContent = Html.fromHtml(challengeDetail.getDetailedRequirements());
            TextView tvDetailedRequirements = (TextView) findViewById(R.id.tvDetailedRequirements);
            tvDetailedRequirements.setText(detailHtmlContent);

            String challengeName = String.valueOf(challengeDetail.getChallengeName());

            String[] techNameArr = challengeDetail.getTechnology();
            String technologies = Arrays.toString(techNameArr).replace("[", "").replace("]", "");

            String challengeType = challengeDetail.getChallengeType();
            String currentPhaseName = challengeDetail.getCurrentPhaseName();

            String registrationStartDate = DateTimeParser.ParseDateTime(challengeDetail.getPostingDate());
            String registrationEndDate = DateTimeParser.ParseDateTime(challengeDetail.getRegistrationEndDate());
            String submissionEndDate = DateTimeParser.ParseDateTime(challengeDetail.getSubmissionEndDate());

            long[] prizes = challengeDetail.getPrize();
            long reliabilityBonus = challengeDetail.getReliabilityBonus();
            String submissionsCount = challenge.getNumSubmissions();
            String registrantsCount = challenge.getNumRegistrants();

            TextView tvChallengeName = (TextView) findViewById(R.id.tvChallengeName);
            TextView tvTechnologies = (TextView) findViewById(R.id.tvTechnologies);
            TextView tvChallengeType = (TextView) findViewById(R.id.tvChallengeType);
            TextView tvCurrentPhaseRemainingTime = (TextView) findViewById(R.id.tvCurrentPhaseRemainingTime);
            TextView tvCurrentPhaseName = (TextView) findViewById(R.id.tvCurrentPhaseName);
            TextView tvRegistrationStartDate = (TextView) findViewById(R.id.tvPostingDate);
            TextView tvRegistrationEndDate = (TextView) findViewById(R.id.tvRegistrationEndDate);
            TextView tvSubmissionEndDate = (TextView) findViewById(R.id.tvSubmissionEndDate);

            TextView tvFirstPrize = (TextView) findViewById(R.id.tvFirstPrize);
            TextView tvSecondPrize = (TextView) findViewById(R.id.tvSecondPrize);
            TextView tvThirdPrize = (TextView) findViewById(R.id.tvThirdPrize);
            TextView tvReliabilityBonus = (TextView) findViewById(R.id.tvReliabilityBonus);
            TextView tvSubmissionsCount = (TextView) findViewById(R.id.tvSubmissionsCount);
            TextView tvRegistrantsCount = (TextView) findViewById(R.id.tvRegistrantsCount);

            tvSubmissionsCount.setText(submissionsCount);
            tvRegistrantsCount.setText(registrantsCount);

            String bonus = getString(R.string.reliability_bonus);
            tvReliabilityBonus.setText(bonus + reliabilityBonus + "$");


            if (prizes != null && prizes.length > 0) {

                try {

                    tvSecondPrize.setVisibility(View.GONE);
                    tvThirdPrize.setVisibility(View.GONE);

                    Spanned first = Html.fromHtml("<b>1st:</b> " + String.valueOf(prizes[0]) + "$");
                    tvFirstPrize.setText(first);

                    if (prizes.length >= 2) {
                        Spanned second = Html.fromHtml("<b>2nd:</b> " + String.valueOf(prizes[1]) + "$");
                        tvSecondPrize.setText(second);
                        tvSecondPrize.setVisibility(View.VISIBLE);
                    }

                    if (prizes.length == 3) {
                        Spanned third = Html.fromHtml("<b>3rd:</b> " + String.valueOf(prizes[2]) + "$");
                        tvThirdPrize.setText(third);
                        tvThirdPrize.setVisibility(View.VISIBLE);
                    }


                } catch (Exception e) {
                }
            }
            //String totalPrize = "$" + challenge.getTotalPrize();
            //TextView tvTotalPrize = (TextView) findViewById(R.id.tvTotalPrize);
            //tvTotalPrize.setText(totalPrize);


            tvTechnologies.setText(technologies);
            tvChallengeType.setText(challengeType);

            //take positive
            Long currentPhaseRemainingTime = Math.abs(challenge.getCurrentPhaseRemainingTime());
            String currentPhaseRemainingTimeString = DateTimeParser.ParseSecondToDayAndHour(currentPhaseRemainingTime);
            tvCurrentPhaseRemainingTime.setText(currentPhaseRemainingTimeString);
            if (currentPhaseRemainingTime < 7200) {
                try {
                    int color = ContextCompat.getColor(context, R.color.redColor);

                    tvCurrentPhaseRemainingTime.setTextColor(color); //todo error prone?
                } catch (Exception e) {
                    LogHelper.Log(e);
                }
            }

            tvCurrentPhaseName.setText(currentPhaseName);
            tvRegistrationStartDate.setText(registrationStartDate);
            tvRegistrationEndDate.setText(registrationEndDate);
            tvSubmissionEndDate.setText(submissionEndDate);
            tvChallengeName.setText(challengeName);

        } catch (Exception e) {
            LogHelper.Log(e);
        }

    }
}
