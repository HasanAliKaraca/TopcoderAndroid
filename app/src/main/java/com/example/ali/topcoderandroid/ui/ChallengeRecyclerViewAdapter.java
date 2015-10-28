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
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ali.topcoderandroid.Activities.ChallengeDetailActivity;
import com.example.ali.topcoderandroid.Interfaces.ItemClickListener;
import com.example.ali.topcoderandroid.Models.ChallengeModel;
import com.example.ali.topcoderandroid.R;

import java.util.ArrayList;

/**
 * Simple RecyclerView subclass that supports providing an empty view (which
 * is displayed when the adapter has no data and hidden otherwise).
 */
public class ChallengeRecyclerViewAdapter extends
        RecyclerView.Adapter<ChallengeViewHolder> implements ItemClickListener {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private ArrayList<ChallengeModel> mChallengeList;
    private Context mContext;
    private int selectedPos = 0;

    public ChallengeRecyclerViewAdapter(Context context, ArrayList<ChallengeModel> list) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mContext = context;
        mChallengeList = list;
    }

    @Override
    public ChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(mContext);

        View mView = LayoutInflater.from(mContext)
                .inflate(R.layout.challenges_list_item, parent, false);

        //mView.setBackgroundResource(mBackground);

        ChallengeViewHolder vh = new ChallengeViewHolder(mView, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ChallengeViewHolder holder, int position) {
        ChallengeModel challenge = mChallengeList.get(position);
        holder.bindChallenge(challenge);
    }

    @Override
    public int getItemCount() {
        return mChallengeList != null ? mChallengeList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onItemClick(View view, int position) {

        ChallengeModel challenge = mChallengeList.get(position);

        Context context = view.getContext();

        if (context != null) {
            Intent intent = new Intent(context, ChallengeDetailActivity.class);
            intent.putExtra(ChallengeDetailActivity.CHALLENGE_ID_TAG, challenge);

            context.startActivity(intent);
        }


    }
}
