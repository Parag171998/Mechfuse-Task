package com.example.mechfuse.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mechfuse.Models.Result;

import java.util.List;

@Dao
public interface Mydao {

    @Insert
    public void addFavMovie(RoomResult roomResult);

    @Query("select * from RoomResult")
    public List<RoomResult> getFavMovies();

    @Query("DELETE FROM RoomResult")
    public void deleteAll();

    @Delete
    public void deleteResult(RoomResult roomResult);
}
