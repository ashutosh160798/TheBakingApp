package com.example.android.thebakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.thebakingapp.Adapters.StepAdapter;
import com.example.android.thebakingapp.Fragments.RecipeDetailFragment;
import com.example.android.thebakingapp.Fragments.StepDetailFragment;
import com.example.android.thebakingapp.Objects.IngredientObject;
import com.example.android.thebakingapp.Objects.RecipeListObject;
import com.example.android.thebakingapp.Objects.StepObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener {
    ArrayList<StepObject>stepObjectArrayList;
    ArrayList<IngredientObject>ingredientObjectArrayList;
    int stepClicked;
    public static boolean mTwoPane;
    String mRecipeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Bundle b=getIntent().getExtras().getBundle("bundle");
        stepObjectArrayList=b.getParcelableArrayList("steps");
        ingredientObjectArrayList=b.getParcelableArrayList("ingredients");
        mRecipeName=b.getString("name");
        getSupportActionBar().setTitle(mRecipeName);

        RecipeDetailFragment recipeDetailFragment =new RecipeDetailFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_container,recipeDetailFragment)
                .commit();

        if(findViewById(R.id.step_detail_container)!=null){
            mTwoPane=true;

            StepDetailFragment stepDetailFragment =new StepDetailFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.step_detail_container,stepDetailFragment)
                    .commit();
        }
        else {
            mTwoPane=false;
        }

    }

    @Override
    public void OnStepClicked(int position) {
        stepClicked=position;
        if(mTwoPane){
            StepDetailFragment newFragment=new StepDetailFragment();
            newFragment.setPosition(position);
            getSupportFragmentManager().beginTransaction().replace(R.id.step_detail_container,newFragment).commit();
        }
    }
}
