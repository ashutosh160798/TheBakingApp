package com.example.android.thebakingapp.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ashu on 05-07-2018.
 */

public class RecipeListObject implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private ArrayList<IngredientObject>ingredientObjectArrayList;

    @SerializedName("steps")
    private ArrayList<StepObject>stepsObjectArrayList;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String image;

    protected RecipeListObject(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<RecipeListObject> CREATOR = new Creator<RecipeListObject>() {
        @Override
        public RecipeListObject createFromParcel(Parcel in) {
            return new RecipeListObject(in);
        }

        @Override
        public RecipeListObject[] newArray(int size) {
            return new RecipeListObject[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<IngredientObject> getIngredientObjectArrayList() {
        return ingredientObjectArrayList;
    }

    public void setIngredientObjectArrayList(ArrayList<IngredientObject> ingredientObjectArrayList) {
        this.ingredientObjectArrayList = ingredientObjectArrayList;
    }

    public ArrayList<StepObject> getStepsObjectArrayList() {
        return stepsObjectArrayList;
    }

    public void setStepsObjectArrayList(ArrayList<StepObject> stepsObjectArrayList) {
        this.stepsObjectArrayList = stepsObjectArrayList;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }
}
