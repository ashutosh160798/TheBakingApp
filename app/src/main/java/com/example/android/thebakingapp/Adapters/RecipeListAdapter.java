package com.example.android.thebakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.thebakingapp.Objects.RecipeListObject;
import com.example.android.thebakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ashu on 06-07-2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private RecipeItemClickListener mListener;
    private ArrayList<RecipeListObject> mDataList;
    private Context mContext;

    public RecipeListAdapter(Context mContext, ArrayList<RecipeListObject>mDataList,RecipeItemClickListener mListener){
        this.mListener=mListener;
        this.mContext=mContext;
        this.mDataList=mDataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecipeListObject recipe = mDataList.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.servingsTextView.setText(String.valueOf(recipe.getServings()));
        if(!recipe.getImage().equals("")) {
            Picasso.get().load(recipe.getImage()).into(holder.recipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.servings)TextView servingsTextView;
        @BindView(R.id.recipe_name)TextView recipeName;
        @BindView(R.id.recipe_image)ImageView recipeImage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onRecipeClick(mDataList.get(getAdapterPosition()));
        }
    }

    public interface RecipeItemClickListener {
        void onRecipeClick(RecipeListObject recipeListObject);
    }

}
