
package com.example.android.popmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popmovies.CinemaAdapter.CinemaAdapterOnClickHandler;
import com.example.android.popmovies.utilities.MovieInformation;
import com.example.android.popmovies.utilities.NetworkUtils;
import com.example.android.popmovies.utilities.OpenFilmJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements CinemaAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private CinemaAdapter mCinemaAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private static boolean bMovieType = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_cinema);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mCinemaAdapter = new CinemaAdapter(this);

        mRecyclerView.setAdapter(mCinemaAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadFilmData();
    }

    private void loadFilmData() {
        showFilmDataView();

        String sType;

        if (bMovieType == true)
        {
            sType = "popular";
        }
        else
        {
            sType = "top_rated";
        }

        new FetchFilmTask().execute(sType);
    }

    @Override
    public void onClick(MovieInformation mMovieInfo) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        intentToStartDetailActivity.putExtra("vote_count",mMovieInfo.voteCount);
        intentToStartDetailActivity.putExtra("id",mMovieInfo.movieId);
        intentToStartDetailActivity.putExtra("video",mMovieInfo.video);
        intentToStartDetailActivity.putExtra("vote_average",mMovieInfo.voteAverage);
        intentToStartDetailActivity.putExtra("title",mMovieInfo.title);
        intentToStartDetailActivity.putExtra("popularity",mMovieInfo.popularity);
        intentToStartDetailActivity.putExtra("poster_path",mMovieInfo.posterPath);
        intentToStartDetailActivity.putExtra("original_language",mMovieInfo.originalLanguage);
        intentToStartDetailActivity.putExtra("original_title",mMovieInfo.originalTitle);
        intentToStartDetailActivity.putExtra("backdrop_path",mMovieInfo.backdropPath);
        intentToStartDetailActivity.putExtra("adult",mMovieInfo.adult);
        intentToStartDetailActivity.putExtra("overview",mMovieInfo.overview);
        intentToStartDetailActivity.putExtra("release_date",mMovieInfo.releaseData);

        startActivity(intentToStartDetailActivity);

    }

    private void showFilmDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchFilmTask extends AsyncTask<String, Void, MovieInformation[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieInformation[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String type = params[0];
            URL filmRequestUrl = NetworkUtils.buildUrl(type);

            try {
                String jsonFilmResponse = NetworkUtils
                        .getResponseFromHttpUrl(filmRequestUrl);

                MovieInformation[] simpleJsonMovieInfo = OpenFilmJsonUtils
                            .getSimpleDataFromJson(MainActivity.this, jsonFilmResponse);

                return simpleJsonMovieInfo;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieInformation[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showFilmDataView();
                mCinemaAdapter.setMovieInfo(movieData);
            } else {
                showErrorMessage();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cinema, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mCinemaAdapter.setMovieInfo(null);
            loadFilmData();
            return true;
        }

        if (id == R.id.type_popular) {
            bMovieType = true;
            loadFilmData();
            return true;
        }

        if (id == R.id.type_toprated) {
            bMovieType = false;
            loadFilmData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}