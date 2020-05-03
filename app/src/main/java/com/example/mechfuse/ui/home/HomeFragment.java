package com.example.mechfuse.ui.home;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mechfuse.Adapters.NowPlayingMoviesAdapter;
import com.example.mechfuse.MainActivity;
import com.example.mechfuse.Models.NowPlayingMovies;
import com.example.mechfuse.Models.Result;
import com.example.mechfuse.Models.UpcomingMovies;
import com.example.mechfuse.Network.ApiClient;
import com.example.mechfuse.R;
import com.example.mechfuse.Room.MyappDatabse;
import com.example.mechfuse.Room.RoomResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    int currentPage = 0;
    int nowPlayingResultSize=0;

    int currentPageUp = 0;
    int upComingResultSize=0;

    Timer timer;
    Timer timerUp;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    private HomeViewModel homeViewModel;

    private ViewPager2 nowPlayingViewPager;
    private ViewPager2 upcomingPlayingViewPager;

    public static MyappDatabse myappDatabse;

    NowPlayingMoviesAdapter nowPlayingMoviesAdapter;
    List<Integer> idList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        idList = new ArrayList<>();
        nowPlayingViewPager = root.findViewById(R.id.nowPlayigViewpager);
        upcomingPlayingViewPager = root.findViewById(R.id.upcomingViewPager);



        myappDatabse = Room.databaseBuilder(getActivity(), MyappDatabse.class, "favmoviedb").allowMainThreadQueries().build();
        List<RoomResult> roomResultsList = myappDatabse.mydao().getFavMovies();

        for(RoomResult roomResult : roomResultsList)
        {
            idList.add(roomResult.getId());
        }
        Call<NowPlayingMovies> nowPlayingMoviesCall = ApiClient.getInstance().getApi().getNowPlayingMovies();
        Call<UpcomingMovies> upcomingMoviesCall = ApiClient.getInstance().getApi().getUpcomingMovies();

        nowPlayingMoviesCall.enqueue(new Callback<NowPlayingMovies>() {
            @Override
            public void onResponse(Call<NowPlayingMovies> call, Response<NowPlayingMovies> response) {
                NowPlayingMovies nowPlayingMovies = response.body();
                List<Result> resultList = nowPlayingMovies.getResults();
                nowPlayingResultSize = resultList.size();
                nowPlayingMoviesAdapter = new NowPlayingMoviesAdapter(resultList,upcomingPlayingViewPager,getContext(),idList);
                nowPlayingViewPager.setAdapter(nowPlayingMoviesAdapter);
            }

            @Override
            public void onFailure(Call<NowPlayingMovies> call, Throwable t) {

            }
        });

        upcomingMoviesCall.enqueue(new Callback<UpcomingMovies>() {
            @Override
            public void onResponse(Call<UpcomingMovies> call, Response<UpcomingMovies> response) {
                UpcomingMovies upcomingMovies = response.body();
                List<Result> resultList = upcomingMovies.getResults();
                upComingResultSize = resultList.size();
                nowPlayingMoviesAdapter = new NowPlayingMoviesAdapter(resultList,upcomingPlayingViewPager,getContext(),idList);
                upcomingPlayingViewPager.setAdapter(nowPlayingMoviesAdapter);
            }

            @Override
            public void onFailure(Call<UpcomingMovies> call, Throwable t) {

            }
        });


        /*final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == nowPlayingResultSize-1) {
                    currentPage = 0;
                }
                nowPlayingViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        final Handler handlerUp = new Handler();
        final Runnable UpdateUp = new Runnable() {
            public void run() {
                if (currentPageUp == upComingResultSize-1) {
                    currentPageUp = 0;
                }
                upcomingPlayingViewPager.setCurrentItem(currentPageUp++, true);
            }
        };

        timerUp = new Timer(); // This will create a new Thread
        timerUp.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handlerUp.post(UpdateUp);
            }
        }, DELAY_MS, PERIOD_MS);

         */
        return root;

    }


    @Override
    public void onDetach() {
        super.onDetach();
        //Intent intent = new Intent(getContext(), MainActivity.class);
        //startActivity(intent);
    }
}
