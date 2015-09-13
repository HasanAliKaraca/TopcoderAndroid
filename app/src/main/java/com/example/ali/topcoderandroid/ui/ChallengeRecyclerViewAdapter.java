package com.example.ali.topcoderandroid.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        /*if (mCrime != null) {
            Intent i = CrimeActivity.getIntent(v.getContext(), mCrime);
            startActivity(i);
        }*/


       /*   Intent intent = new Intent(context, CheeseDetailActivity.class);
        intent.putExtra(CheeseDetailActivity.EXTRA_NAME, holder.mBoundString);

        context.startActivity(intent);*/

    }
}
