package com.example.android.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmovies.utilities.MovieInformation;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {

    private static final String CINEMA_SHARE_HASHTAG = " #PopmoviesApp";

    private String sBackdropPath;
    private ImageView mBackdropPath;
    private String sMovieTitle;
    private TextView mFilmTitle;
    private String sPosterPath;
    private ImageView mPosterPath;
    private String sMovieOverview;
    private TextView mFilmOverview;
    private Double dMovieVote;
    private TextView mFilmVote;
    private String sMovieDate;
    private TextView mFilmDate;

    private MovieInformation mMovieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBackdropPath   = (ImageView) findViewById(R.id.tv_display_movie_backdrop);
        mFilmTitle    = (TextView)  findViewById(R.id.tv_display_movie_title);
        mPosterPath   = (ImageView) findViewById(R.id.tv_display_movie_poster);
        mFilmOverview = (TextView)  findViewById(R.id.tv_display_movie_overview);
        mFilmDate     = (TextView) findViewById(R.id.tv_display_movie_date);
        mFilmVote     = (TextView) findViewById(R.id.tv_display_movie_vote);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

            if (intentThatStartedThisActivity.hasExtra("backdrop_path")) {
                sBackdropPath = intentThatStartedThisActivity.getStringExtra("backdrop_path");
                Picasso.with(DetailActivity.this).load(sBackdropPath).into(mBackdropPath);
            }

            if (intentThatStartedThisActivity.hasExtra("title")) {
                sMovieTitle = intentThatStartedThisActivity.getStringExtra("title");
                mFilmTitle.setText(sMovieTitle);
            }

            if (intentThatStartedThisActivity.hasExtra("poster_path")) {
                sPosterPath = intentThatStartedThisActivity.getStringExtra("poster_path");
                Picasso.with(DetailActivity.this).load(sPosterPath).into(mPosterPath);
            }

            if (intentThatStartedThisActivity.hasExtra("overview")) {
                sMovieOverview = intentThatStartedThisActivity.getStringExtra("overview");
                mFilmOverview.setText(sMovieOverview);
            }

            if (intentThatStartedThisActivity.hasExtra("vote_average")) {
                dMovieVote = intentThatStartedThisActivity.getDoubleExtra("vote_average", 0);
                mFilmVote.setText(dMovieVote.toString());
            }

            if (intentThatStartedThisActivity.hasExtra("release_date")) {
                sMovieDate = intentThatStartedThisActivity.getStringExtra("release_date");
                mFilmDate.setText(sMovieDate);
            }

        }
    }

     private Intent createShareCinemaIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(sMovieTitle + CINEMA_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareCinemaIntent());
        return true;
    }
}