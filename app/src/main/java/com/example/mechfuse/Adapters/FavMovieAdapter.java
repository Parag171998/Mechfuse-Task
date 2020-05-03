package com.example.mechfuse.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mechfuse.Movie_DetailsActivity;
import com.example.mechfuse.R;
import com.example.mechfuse.Room.RoomResult;
import com.example.mechfuse.ui.home.HomeFragment;

import java.util.List;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.FavMovieAdapterViewHolder> {

    List<RoomResult> roomResultList;
    Context context;

    public FavMovieAdapter(List<RoomResult> roomResultList, Context context) {
        this.roomResultList = roomResultList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavMovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavMovieAdapterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.custom_recycler_layout
                        ,parent
                        ,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieAdapterViewHolder holder, int position) {

        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+roomResultList.get(position).getPosterPath()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return roomResultList.size();
    }

    class FavMovieAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        Button button;
        public FavMovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.roomImage);
            button = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Movie_DetailsActivity.class);
                    intent.putExtra("id",String.valueOf(roomResultList.get(getAdapterPosition()).getId()));
                    intent.putExtra("url",roomResultList.get(getAdapterPosition()).getPosterPath());
                    intent.putExtra("title",roomResultList.get(getAdapterPosition()).getTitle());
                    intent.putExtra("vote",String.valueOf(roomResultList.get(getAdapterPosition()).getVoteAverage()));
                    intent.putExtra("overview",roomResultList.get(getAdapterPosition()).getOverview());
                    intent.putExtra("tag","1");
                    context.startActivity(intent);
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeFragment.myappDatabse.mydao().deleteResult(roomResultList.get(getAdapterPosition()));
                    roomResultList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
