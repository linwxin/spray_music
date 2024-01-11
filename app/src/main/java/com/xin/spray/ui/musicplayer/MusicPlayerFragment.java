package com.xin.spray.ui.musicplayer;

import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xin.spray.R;
import com.xin.spray.db.entity.Song;
import com.xin.spray.service.PlayerService;

import org.w3c.dom.Text;

public class MusicPlayerFragment extends Fragment {

    private MusicPlayerViewModel mViewModel;

    private ImageView playOrPauseBtn;

    private ImageView nextBtn;

    private ImageView preBtn;

    private TextView playerSongTitle;

    private ImageView playSongImg;

    private PlayerService.MusicServiceBinder serviceBinder;

    public static MusicPlayerFragment newInstance() {
        return new MusicPlayerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_player, container, false);


        initFragment(view);
        return view;
    }

    private void initFragment(View view) {
        Log.d("wanxin", "initFragment musicPlayer");
        // 绑定播放服务
        Intent i = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
        playOrPauseBtn = view.findViewById(R.id.play_or_pause);
        nextBtn = view.findViewById(R.id.play_next);
        preBtn = view.findViewById(R.id.play_pre);
        playerSongTitle = view.findViewById(R.id.play_song_name);
        playSongImg = view.findViewById(R.id.play_song_img);

        playOrPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceBinder.isPlaying()) {
                    serviceBinder.pauseSong();

                } else {
                    serviceBinder.playSong();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceBinder.nextSong();
            }
        });

        preBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                serviceBinder.preSong();
            }
        });



    }


    // 定义与服务的连接匿名类
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d("wanxin", "service connected in MusicPlayFragment");
            serviceBinder = (PlayerService.MusicServiceBinder) service;
            serviceBinder.addListener(onStateChangeListener);

            Song curSong = serviceBinder.getCurrentSong();


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private PlayerService.OnStateChangeListener onStateChangeListener = new PlayerService.OnStateChangeListener() {

        @Override
        public void onPlayProgressChange(long played, long duration) {
            Song song = serviceBinder.getCurrentSong();
            String curSongTitle = song.getTitle();

        }

        @Override
        public void onPlay(Song songItem) {
            playOrPauseBtn.setImageResource(R.drawable.ic_pause_24);
            Song curSong = serviceBinder.getCurrentSong();
            String curSongTitle = curSong.getTitle();
            playerSongTitle.setText(curSongTitle);
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(curSong.getPath());
            byte[] picture = mediaMetadataRetriever.getEmbeddedPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
            playSongImg.setImageBitmap(bitmap);

        }

        @Override
        public void onPause() {
            playOrPauseBtn.setImageResource(R.drawable.ic_play);
        }
    };



}