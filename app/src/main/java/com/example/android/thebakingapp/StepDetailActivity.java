package com.example.android.thebakingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.example.android.thebakingapp.Fragments.StepDetailFragment;

public class StepDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if(savedInstanceState==null) {
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_container, stepDetailFragment)
                    .commit();
        }

    }
}
