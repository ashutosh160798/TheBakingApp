package com.example.android.thebakingapp.NetworkUtil;

import com.example.android.thebakingapp.Objects.RecipeListObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by ashu on 05-07-2018.
 */

public interface ApiInterface {

    @GET("baking.json")
    Call<ArrayList<RecipeListObject>> getRecipes();


}
