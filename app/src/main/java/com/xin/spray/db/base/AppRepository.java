package com.xin.spray.db.base;

import com.xin.spray.db.dao.SongDao;
import com.xin.spray.db.entity.Song;

import java.util.List;

public class AppRepository {

    private SongDao mSongDao;

    private List<Song> mAllSong;
}
