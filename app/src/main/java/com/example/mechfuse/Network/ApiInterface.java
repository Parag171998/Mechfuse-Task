package com.example.mechfuse.Network;

import com.example.mechfuse.Models.NowPlayingMovies;
import com.example.mechfuse.Models.UpcomingMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("now_playing?api_key=e16f9ec421f01f05db45a6d069d84d56")
    Call<NowPlayingMovies> getNowPlayingMovies();

    @GET("upcoming?api_key=e16f9ec421f01f05db45a6d069d84d56")
    Call<UpcomingMovies> getUpcomingMovies();

}
