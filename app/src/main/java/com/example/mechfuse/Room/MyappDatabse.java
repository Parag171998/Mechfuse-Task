package com.example.mechfuse.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {RoomResult.class},version = 1)
public abstract class MyappDatabse extends RoomDatabase {

    public abstract Mydao mydao();

}
