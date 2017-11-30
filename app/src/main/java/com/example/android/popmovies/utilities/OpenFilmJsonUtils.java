package com.example.android.popmovies.utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import static android.content.ContentValues.TAG;

public final class OpenFilmJsonUtils {


    public static MovieInformation[] getSimpleDataFromJson(Context context, String cinemaJsonStr)
            throws JSONException {

        final String OWM_LIST = "results";
        final String OWM_MESSAGE_CODE = "cod";

        MovieInformation[] parsedInfo = null;

        JSONObject cinemaJson = new JSONObject(cinemaJsonStr);

        if (cinemaJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = cinemaJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray movieArray = cinemaJson.getJSONArray(OWM_LIST);

        parsedInfo = new MovieInformation[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject movieInformation = movieArray.getJSONObject(i);

            MovieInformation mi = new MovieInformation(
                    movieInformation.getInt("vote_count"),
                    movieInformation.getInt("id"),
                    movieInformation.getBoolean("video"),
                    movieInformation.getDouble("vote_average"),
                    movieInformation.getString("title"),
                    movieInformation.getDouble("popularity"),
                    "http://image.tmdb.org/t/p/w342" + movieInformation.getString("poster_path"),
                    movieInformation.getString("original_language"),
                    movieInformation.getString("original_title"),
                    "http://image.tmdb.org/t/p/w500" + movieInformation.getString("backdrop_path"),
                    movieInformation.getBoolean("adult"),
                    movieInformation.getString("overview"),
                    movieInformation.getString("release_date"));

            parsedInfo[i] = mi;

        }

        return parsedInfo;
    }

}