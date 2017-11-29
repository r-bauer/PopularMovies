package com.example.android.popmovies.utilities;


public final class MovieInformation
{
    public int voteCount = 0;
    public int movieId = 0;
    public boolean video = false;
    public double voteAverage;
    public String title;
    public double popularity = 0;
    public String posterPath;
    public String originalLanguage = null;
    public String originalTitle = null;
    public String backdropPath;
    public boolean adult = false;
    public String overview;
    public String releaseData = null;



    public MovieInformation(    int voteCount,
                                int movieId,
                                boolean video,
                                double voteAverage,
                                String title,
                                double popularity,
                                String posterPath,
                                String originalLanguage,
                                String originalTitle,
                                String backdropPath,
                                boolean adult,
                                String overview,
                                String releaseData  )
    {
        this.voteCount        = voteCount;
        this.movieId          = movieId;
        this.video            = video;
        this.voteAverage      = voteAverage;
        this.title            = title;
        this.popularity       = popularity;
        this.posterPath       = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle    = originalTitle;
        this.backdropPath     = backdropPath;
        this.adult            = adult;
        this.overview         = overview;
        this.releaseData      = releaseData;
    }

}
