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

package com.example.ali.topcoderandroid.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ali.topcoderandroid.Api.ChallengeType;
import com.example.ali.topcoderandroid.Api.TopcoderClient;
import com.example.ali.topcoderandroid.Helpers.LogHelper;
import com.example.ali.topcoderandroid.Models.ChallengeModel;
import com.example.ali.topcoderandroid.Models.DataInfoModel;
import com.example.ali.topcoderandroid.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;
    protected ChallengeRecyclerView mRecyclerView;
    protected ChallengeRecyclerViewAdapter challengeRecyclerViewAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChallengeType preferredChallengeType;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        preferredChallengeType = ChallengeType.getPreferred(context);


    }

    public void fetchChallenges(ChallengeType challengeType) {

        TopcoderClient client = new TopcoderClient();

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    ArrayList<ChallengeModel> list = DataInfoModel.mapJsonToChallengeModelArray(response);

                    UpdateChallengeListOnUI(list);

                } catch (Exception e) {
                    LogHelper.Log(e);
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    if (error.networkResponse != null) {
                        String networkResponseStatusCode = String.valueOf(error.networkResponse.statusCode);
                        String errorMessage = "Network error: " + networkResponseStatusCode + ". Try again in a few sec";
                        Snackbar.make(mRecyclerView, errorMessage, Snackbar.LENGTH_LONG).show();

                    }
                } catch (Exception e) {
                    LogHelper.Log(e);
                }

                LogHelper.Log(error);

                UpdateChallengeListOnUI(new ArrayList<ChallengeModel>());

                swipeRefreshLayout.setRefreshing(false);
            }
        };

        client.getChallenges(context, challengeType, responseListener, errorListener);

    }

    private void UpdateChallengeListOnUI(ArrayList<ChallengeModel> list) {
        challengeRecyclerViewAdapter = new ChallengeRecyclerViewAdapter(mRecyclerView.getContext(), list);

        mRecyclerView.setAdapter(challengeRecyclerViewAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container, false);
        rootView.setTag(TAG);

        /*init recyclerview */
        mRecyclerView = (ChallengeRecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setEmptyView(rootView.findViewById(android.R.id.empty));
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(context);

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType)
                    savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        /*init adapter*/
        ArrayList<ChallengeModel> emptyChallengeList = new ArrayList<>();

        challengeRecyclerViewAdapter = new ChallengeRecyclerViewAdapter(context, emptyChallengeList);
        mRecyclerView.setAdapter(challengeRecyclerViewAdapter);

        /*init pull to refresh*/
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);


     /*   mLinearLayoutRadioButton = (RadioButton) rootView.findViewById(R.id.linear_layout_rb);
        mLinearLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
            }
        });

        mGridLayoutRadioButton = (RadioButton) rootView.findViewById(R.id.grid_layout_rb);
        mGridLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
            }
        });*/


        fetchChallenges(preferredChallengeType);


        return rootView;

    }

    private void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);

        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public void onRefresh() {
        fetchChallenges(preferredChallengeType);
    }


    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

}
