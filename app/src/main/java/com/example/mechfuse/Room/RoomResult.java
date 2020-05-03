package com.example.mechfuse.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomResult {
    @PrimaryKey
    private Integer id;

    @ColumnInfo
    private String posterPath;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String overview;

    @ColumnInfo
    private Double voteAverage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public RoomResult(Integer id, String posterPath, String title, String overview, Double voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
    }
}
