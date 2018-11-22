package com.example.android.thebakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.thebakingapp.Objects.StepObject;
import com.example.android.thebakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ashu on 06-07-2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private StepClickListener mListener;
    private ArrayList<StepObject> mDataList;
    private Context mContext;

    public StepAdapter(Context mContext, ArrayList<StepObject> mDataList, StepClickListener mListener) {
        this.mListener = mListener;
        this.mDataList = mDataList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemp_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StepObject step = mDataList.get(position);
        String s=step.getStepId()+":";
        holder.stepNumberTextView.setText(s);
        holder.stepTextView.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step)TextView stepTextView;
        @BindView(R.id.step_number)TextView stepNumberTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onStepClick(mDataList,getAdapterPosition());
        }
    }

    public interface StepClickListener{
        void onStepClick(ArrayList<StepObject> stepObjectArrayList,int position);
    }
}
