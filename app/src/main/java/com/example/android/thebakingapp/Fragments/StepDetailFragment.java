package com.example.android.thebakingapp.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.thebakingapp.Objects.StepObject;
import com.example.android.thebakingapp.R;
import com.example.android.thebakingapp.StepDetailActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.thebakingapp.RecipeDetailActivity.mTwoPane;


public class StepDetailFragment extends Fragment {

    @BindView(R.id.step_description)
    TextView descriptionTextView;

    @BindView(R.id.step_image)
    ImageView stepImage;

    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;

    @BindView(R.id.prev)
    TextView prevTextView;

    @BindView(R.id.next)
    TextView nextTextView;


    StepObject step;
    int position;
    ArrayList<StepObject> stepArrayList;
    SimpleExoPlayer exoPlayer;
    String vidURL;
    private long exo_current_position = 0;
    private long playerStopPosition;
    private boolean playerStopped = false;
    private static String EXO_CURRENT_POSITION = "current_position";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, mView);

        if (savedInstanceState != null) {
            exo_current_position = savedInstanceState.getLong(EXO_CURRENT_POSITION);
        }

        Bundle b = getActivity().getIntent().getBundleExtra("bundle");
        stepArrayList = b.getParcelableArrayList("steps");
        if (!mTwoPane) {
            position = b.getInt("position");
        } else {
            nextTextView.setVisibility(View.GONE);
            prevTextView.setVisibility(View.GONE);
        }
        step = stepArrayList.get(position);

        descriptionTextView.setText(step.getDescription());
        if(!step.getThumbnailURL().equals("")) {
            Picasso.get().load(step.getThumbnailURL()).into(stepImage);
        }
        if (position == 0) {
            prevTextView.setVisibility(View.INVISIBLE);
        } else if (position == (stepArrayList.size() - 1)) {
            nextTextView.setVisibility(View.INVISIBLE);
        }

        prevTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevClicked(position);
            }
        });

        nextTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClicked(position);
            }
        });


        vidURL = step.getVideoURL();
        if (!vidURL.equals("")) {
            initializePlayer(Uri.parse(vidURL));
            if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
                playerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                playerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        } else {
            playerView.setVisibility(View.GONE);
        }


        return mView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);
            String userAgent = Util.getUserAgent(getContext(), "TheBakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            if (exo_current_position != 0 && !playerStopped) {
                exoPlayer.seekTo(exo_current_position);
            } else {
                exoPlayer.seekTo(playerStopPosition);
            }
        }
    }

    /**
     * Method to release exoPlayer
     */
    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    /**
     * Method to check for current device orientation
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXO_CURRENT_POSITION, exoPlayer.getCurrentPosition());
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer(Uri.parse(vidURL));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            playerStopPosition = exoPlayer.getCurrentPosition();
            playerStopped =true;
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void prevClicked(int index) {
        if (index != 0) {
            Intent intent = new Intent(getContext(), StepDetailActivity.class);
            Bundle b = new Bundle();
            b.putParcelableArrayList("steps", stepArrayList);
            b.putInt("position", position - 1);
            intent.putExtra("bundle", b);
            startActivity(intent);
            getActivity().finish();
        }


    }

    private void nextClicked(int index) {
        if (index != (stepArrayList.size() - 1)) {
            Intent intent = new Intent(getContext(), StepDetailActivity.class);
            Bundle b = new Bundle();
            b.putParcelableArrayList("steps", stepArrayList);
            b.putInt("position", position + 1);
            intent.putExtra("bundle", b);
            startActivity(intent);
            getActivity().finish();

        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //getSupportActionBar().hide();
        } else {
            //getSupportActionBar().show();
        }
    }


    public void setPosition(int position) {
        this.position = position;
    }


}
