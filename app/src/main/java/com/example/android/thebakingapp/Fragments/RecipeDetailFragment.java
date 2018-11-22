package com.example.android.thebakingapp.Fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.thebakingapp.Adapters.StepAdapter;
import com.example.android.thebakingapp.Objects.IngredientObject;
import com.example.android.thebakingapp.Objects.StepObject;
import com.example.android.thebakingapp.R;
import com.example.android.thebakingapp.Widget.RecipeWidget;
import com.example.android.thebakingapp.StepDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.example.android.thebakingapp.RecipeDetailActivity.mTwoPane;

public class RecipeDetailFragment extends Fragment {
    ArrayList<IngredientObject> ingredients;
    ArrayList<StepObject> steps;
    StepAdapter stepAdapter;
    String recipeName;
    @BindView(R.id.ingredient_list)
    TextView ingredientsListTextView;
    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;
    OnStepClickListener mCallback;
    public final static String SHARED_PREF_RECIPE = "WidgetRecipe";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        ButterKnife.bind(this, mView);

        SharedPreferences.Editor editor = mView.getContext().getSharedPreferences(SHARED_PREF_RECIPE, MODE_PRIVATE).edit();


        Bundle b = getActivity().getIntent().getBundleExtra("bundle");
        ingredients = b.getParcelableArrayList("ingredients");
        steps = b.getParcelableArrayList("steps");
        recipeName = b.getString("name");
        for (int i = 0; i < ingredients.size(); i++) {
            IngredientObject ingredient = ingredients.get(i);
            ingredientsListTextView.append("\u25BA " + ingredient.getIngredient() + " (" + ingredient.getQuantity() + " " + ingredient.getMeasure() + ")" + "\n");
        }
        Log.d("ing", ingredientsListTextView.getText().toString());
        editor.putString("recipe_name", recipeName);
        editor.putString("ingredients", ingredientsListTextView.getText().toString());
        editor.apply();

        Intent intent = new Intent(getContext(), RecipeWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
// since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getContext()).
                getAppWidgetIds(new ComponentName(getContext(), RecipeWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getActivity().sendBroadcast(intent);

        stepAdapter = new StepAdapter(getActivity(), steps, new StepAdapter.StepClickListener() {
            @Override
            public void onStepClick(ArrayList<StepObject> stepObjectArrayList, int position) {
                mCallback.OnStepClicked(position);
                if (!mTwoPane) {
                    Intent intent = new Intent(getActivity(), StepDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelableArrayList("steps", stepObjectArrayList);
                    b.putInt("position", position);
                    intent.putExtra("bundle", b);
                    startActivity(intent);
                }
            }
        });
        stepsRecyclerView.setNestedScrollingEnabled(false);
        stepsRecyclerView.setAdapter(stepAdapter);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnStepClickListener) context;
    }

    public interface OnStepClickListener {
        void OnStepClicked(int position);
    }

}
