package com.example.mechfuse.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.mechfuse.Models.Result;
import com.example.mechfuse.Movie_DetailsActivity;
import com.example.mechfuse.R;
import com.example.mechfuse.Room.RoomResult;
import com.example.mechfuse.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingMoviesAdapter extends RecyclerView.Adapter<NowPlayingMoviesAdapter.UpcomingMoviesAdapterViewHolder> {
    public static List<Result> resultList;
    ViewPager2 viewPager2;
    Context context;
    List<Integer> idList;

    public NowPlayingMoviesAdapter(List<Result> resultList, ViewPager2 viewPager2, Context context, List<Integer> idList) {
        this.resultList = resultList;
        this.viewPager2 = viewPager2;
        this.context = context;
        this.idList = idList;
    }

    @NonNull
    @Override
    public UpcomingMoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UpcomingMoviesAdapterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingMoviesAdapterViewHolder holder, int position) {

        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+resultList.get(position).getPosterPath()).into(holder.imageView);
        for(Integer integer : idList)
        {
            if(integer.intValue() == resultList.get(position).getId().intValue())
                {
                    holder.favButton.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                    holder.favButton.setTag(String.valueOf(1));
                }
        }

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class UpcomingMoviesAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        Button favButton;

        public UpcomingMoviesAdapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
            favButton = itemView.findViewById(R.id.imageSlideButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Movie_DetailsActivity.class);
                    intent.putExtra("id",String.valueOf(resultList.get(getAdapterPosition()).getId()));
                    intent.putExtra("url",resultList.get(getAdapterPosition()).getPosterPath());
                    intent.putExtra("title",resultList.get(getAdapterPosition()).getTitle());
                    intent.putExtra("vote",String.valueOf(resultList.get(getAdapterPosition()).getVoteAverage()));
                    intent.putExtra("overview",resultList.get(getAdapterPosition()).getOverview());
                    intent.putExtra("tag",String.valueOf(favButton.getTag()));
                    context.startActivity(intent);
                }
            });

            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(Integer.parseInt((String) favButton.getTag()) == 0) {
                        favButton.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                        favButton.setTag(String.valueOf(1));

                        Result result = resultList.get(getAdapterPosition());
                        RoomResult roomResult = new RoomResult(result.getId(),result.getPosterPath(),result.getTitle(),result.getOverview(),
                                result.getVoteAverage());
                        HomeFragment.myappDatabse.mydao().addFavMovie(roomResult);
                        Toast.makeText(context, "Movie added", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        favButton.setBackgroundResource(R.drawable.ic_favorite_grey_24dp);
                        favButton.setTag(String.valueOf(0));

                        List<RoomResult> roomResults = new ArrayList<>();
                        roomResults = HomeFragment.myappDatabse.mydao().getFavMovies();

                        for(RoomResult roomResult : roomResults)
                        {
                            if(roomResult.getId().intValue() == resultList.get(getAdapterPosition()).getId().intValue())
                                HomeFragment.myappDatabse.mydao().deleteResult(roomResult);
                        }
                    }


                }
            });
        }
    }

}
