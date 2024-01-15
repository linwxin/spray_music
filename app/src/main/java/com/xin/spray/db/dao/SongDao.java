package com.xin.spray.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.xin.spray.db.entity.Song;

import java.util.List;

@Dao
public interface SongDao {

    @Query("SELECT * FROM song")
    LiveData<List<Song>> queryAllSong();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);


}
