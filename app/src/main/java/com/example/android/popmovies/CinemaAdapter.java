package com.example.android.popmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popmovies.utilities.MovieInformation;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaAdapterViewHolder> {

    private MovieInformation[] mMovieInfo;

    private final CinemaAdapterOnClickHandler mClickHandler;

    public interface CinemaAdapterOnClickHandler {

        void onClick(MovieInformation mi);
    }

    public CinemaAdapter(CinemaAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class CinemaAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public final ImageView mMoviePosterView;
        public final Context mContext;

        public CinemaAdapterViewHolder(View view, Context mContext) {
            super(view);
            mMoviePosterView = (ImageView) view.findViewById(R.id.movie_poster_data);
            this.mContext = mContext;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            MovieInformation mi = mMovieInfo[adapterPosition];
            mClickHandler.onClick(mi);
        }
    }

    @Override
    public CinemaAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.cinema_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new CinemaAdapterViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(CinemaAdapterViewHolder cinemaAdapterViewHolder, int position) {

        String selectedMovie = mMovieInfo[position].posterPath;
        Log.d(TAG, "selected Movie = " + selectedMovie);
        Picasso.with(cinemaAdapterViewHolder.mContext).load(selectedMovie).into(cinemaAdapterViewHolder.mMoviePosterView);

    }

    @Override
    public int getItemCount() {
        if (null == mMovieInfo) {
            return 0;
        }
        return mMovieInfo.length;
    }

    public void setMovieInfo(MovieInformation[] movieInfo) {
        mMovieInfo = movieInfo;
        notifyDataSetChanged();
    }
}