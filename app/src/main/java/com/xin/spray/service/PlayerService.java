package com.xin.spray.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.xin.spray.db.entity.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerService extends Service {

    private MediaPlayer player;

    List<Song> playingMusicList;

    List<OnStateChangeListener> onStateChangeListeners;

    private Song currentMusic;


    private Boolean isNeedReload;

    private MusicServiceBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        initPlayer();
        playingMusicList = new ArrayList<>();
        player = new MediaPlayer();
        binder = new MusicServiceBinder();
        Log.d("wanxin", "PlayerSerice created");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
        playingMusicList.clear();

    }


    public void initPlayer() {
        onStateChangeListeners = new ArrayList<>();

    }

    private void addPlaylistInner(List<Song> playlist) {
        playingMusicList.clear();
        playingMusicList.addAll(playlist);
        currentMusic = playingMusicList.get(0);
        playInner();
    }

    private void addSongToPlaylistInner(Song song) {
        if (!playingMusicList.contains(song)) {
            playingMusicList.add(0, song);
        }

        currentMusic = song;
        isNeedReload = true;
        playInner();
    }

    private boolean isPlayingInner() {
        return player.isPlaying();
    }

    private void playInner() {
        if (currentMusic == null && playingMusicList.size() > 0) {
            currentMusic = playingMusicList.get(0);
            isNeedReload = true;
        }

        playSongItem(currentMusic, isNeedReload);
    }

    private void pauseInner() {
        player.pause();
        for (OnStateChangeListener listener : onStateChangeListeners) {
            listener.onPause();
        }
    }

    private void nextSongInner() {
        if (playingMusicList.size() > 0) {
            int curIndex = playingMusicList.indexOf(currentMusic);
            int nextIndex = curIndex + 1 < playingMusicList.size() ? curIndex + 1 : 0;
            Song nextSong = playingMusicList.get(nextIndex);
            currentMusic = nextSong;
            playSongItem(currentMusic, true);
        }
    }

    private void preSongInner() {
        if (playingMusicList.size() > 0) {
            int curIndex = playingMusicList.indexOf(currentMusic);
            int preIndex = curIndex - 1 > 0 ? curIndex - 1 : playingMusicList.size() - 1;
            Song preSong = playingMusicList.get(preIndex);
            currentMusic = preSong;
            playSongItem(currentMusic, true);

        }
    }

    private void playSongItem(Song song, Boolean reload) {
        if (song == null) {
            return;
        }

        for (OnStateChangeListener listener : onStateChangeListeners) {
            listener.onPlay(song);
        }
        if (reload) {
            prepareToPlay(song);
        }
        player.start();

        isNeedReload = true;
    }

    private void prepareToPlay(Song song) {
        try {
            player.reset();
            player.setDataSource(PlayerService.this, Uri.parse(song.getPath()));
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Song getCurrentMusicInner() {
        return currentMusic;
    }

    private void addListenerInner(OnStateChangeListener listener) {
        onStateChangeListeners.add(listener);
    }


    public interface OnStateChangeListener {
        void onPlayProgressChange(long played, long duration);  //播放进度变化
        void onPlay(Song songItem);    //播放状态变化
        void onPause();   //播放状态变化
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MusicServiceBinder extends Binder {

        public void addListener(OnStateChangeListener listener) {
            addListenerInner(listener);
        }


        public Song getCurrentSong() {
            return getCurrentMusicInner();
        }

        public void addSongToPlaylist(Song song) {
            addSongToPlaylistInner(song);
        }

        public boolean isPlaying() {
            return isPlayingInner();
        }

        public void pauseSong() {
            pauseInner();
        }

        public void playSong() {
            isNeedReload = false;
            playInner();
        }

        public void nextSong() {
            nextSongInner();
        }

        public void preSong() {
            preSongInner();
        }
    }

}
