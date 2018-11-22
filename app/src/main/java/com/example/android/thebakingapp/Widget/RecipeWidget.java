package com.example.android.thebakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.android.thebakingapp.R;

import static com.example.android.thebakingapp.Fragments.RecipeDetailFragment.SHARED_PREF_RECIPE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences recipeSP = context.getSharedPreferences(SHARED_PREF_RECIPE,0);


        CharSequence recipeName = recipeSP.getString("recipe_name","Nutella Pie");

        CharSequence ingredients=recipeSP.getString("ingredients","► Graham Cracker crumbs (2.0 CUP)\n" +
                "► unsalted butter, melted (6.0 TBLSP)\n" +
                "► granulated sugar (0.5 CUP)\n" +
                "► salt (1.5 TSP)\n" +
                "► vanilla (5.0 TBLSP)\n" +
                "► Nutella or other chocolate-hazelnut spread (1.0 K)\n" +
                "► Mascapone Cheese(room temperature) (500.0 G)\n" +
                "► heavy cream(cold) (1.0 CUP)\n" +
                "► cream cheese(softened) (4.0 OZ)");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.recipe_name, recipeName);
        views.setTextViewText(R.id.ingredients,ingredients);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

