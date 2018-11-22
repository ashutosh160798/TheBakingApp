package com.example.android.thebakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.android.thebakingapp.Adapters.RecipeListAdapter;
import com.example.android.thebakingapp.IdlingResource.SimpleIdlingResource;
import com.example.android.thebakingapp.NetworkUtil.ApiInterface;
import com.example.android.thebakingapp.Objects.RecipeListObject;

import org.junit.Before;

import java.net.IDN;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String BASE_URL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    Retrofit mRetrofit;
    ArrayList<RecipeListObject>recipeList;
    @BindView(R.id.recipe_list_rv)RecyclerView recipeRecyclerView;
    RecipeListAdapter recipeListAdapter;

   CountingIdlingResource idlingResource= new CountingIdlingResource("DATA_LOADER");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        recipeList=new ArrayList<>();

        recipeListAdapter=new RecipeListAdapter(this, recipeList, new RecipeListAdapter.RecipeItemClickListener() {
            @Override
            public void onRecipeClick(RecipeListObject recipeListObject) {
                Intent intent =new Intent(MainActivity.this,RecipeDetailActivity.class);
                Bundle b= new Bundle();
                b.putString("name",recipeListObject.getName());
                b.putParcelableArrayList("ingredients",recipeListObject.getIngredientObjectArrayList());
                b.putParcelableArrayList("steps",recipeListObject.getStepsObjectArrayList());
                intent.putExtra("bundle",b);
                startActivity(intent);
            }
        });
        recipeRecyclerView.setAdapter(recipeListAdapter);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        idlingResource.increment();
        ApiInterface mApiInterface= mRetrofit.create(ApiInterface.class);
        final Call<ArrayList<RecipeListObject>>mCallResponse=mApiInterface.getRecipes();
        mCallResponse.enqueue(new Callback<ArrayList<RecipeListObject>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeListObject>> call, Response<ArrayList<RecipeListObject>> response) {
                ArrayList<RecipeListObject>callResponse=response.body();
                onDownloadComplete(callResponse);
                idlingResource.decrement();
                Log.d("Response:",response.body().toString());
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeListObject>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void onDownloadComplete(ArrayList<RecipeListObject> callResponse) {
        recipeList.addAll(callResponse);
        recipeListAdapter.notifyDataSetChanged();
    }

}
