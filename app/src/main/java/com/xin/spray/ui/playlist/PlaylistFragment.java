package com.xin.spray.ui.playlist;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xin.spray.R;
import com.xin.spray.db.entity.Song;
import com.xin.spray.service.PlayerService;
import com.xin.spray.utils.MusicUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment {

    private PlaylistViewModel mViewModel;

    private RecyclerView playlistRecyclerView;

    private List<Song> playlist;

    private PlayerService.MusicServiceBinder serviceBinder;

    private PlaylistAdapter playlistAdapter;

    public static PlaylistFragment newInstance() {
        return new PlaylistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        requestPermission();
        playlistRecyclerView = view.findViewById(R.id.playlist_recycler_view);
        playlist = MusicUtils.getMusicData(getContext());
        PlaylistAdapter adapter = new PlaylistAdapter(new PlaylistAdapter.SongDiff());
        playlistRecyclerView.setAdapter(adapter);
        playlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.submitList(playlist);

        Intent i = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);
        adapter.setOnItemClickListener(new PlaylistAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                // todo
                Toast.makeText(getContext(), "click item " + position, Toast.LENGTH_SHORT).show();
                Song song = playlist.get(position);
                Log.e("wanxin", "song's url: " + song.getPath());
                if (serviceBinder != null) {
                    serviceBinder.addSongToPlaylist(song);
                } else {
                    Log.d("wanxin", "serviceBinder null");
                }

            }

            @Override
            public void onAddBtnClick(View view, int position) {
                // todo
                Toast.makeText(getContext(), "click add button " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMoreBtnClick(View view, int position) {
                // todo
                Toast.makeText(getContext(), "Click more button " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 最后的请求码是对应回调方法的请求码
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        } else {
            Toast.makeText(getContext(), "你已经有写权限", Toast.LENGTH_SHORT).show();
        }
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d("wanxin", "service connected in PlaylistFragment");
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

        }

        @Override
        public void onPlay(Song songItem) {


            int position = playlist.indexOf(songItem);
            RecyclerView.LayoutManager layoutManager = playlistRecyclerView.getLayoutManager();
            // 清除颜色
            int itemCount = layoutManager.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                if (layoutManager instanceof LinearLayoutManager) {
                    View view = ((LinearLayoutManager) layoutManager).findViewByPosition(i);
                    if (view != null) {
                        TextView songName = view.findViewById(R.id.playlist_song_name);
                        TextView songArtist = view.findViewById(R.id.playlist_artist);
                        songName.setTextColor(Color.BLACK);
                        songArtist.setTextColor(Color.BLACK);
                    }
                }
            }
            if (layoutManager instanceof LinearLayoutManager) {
                View view = ((LinearLayoutManager) layoutManager).findViewByPosition(position);
                if (view != null) {
                    TextView songName = view.findViewById(R.id.playlist_song_name);
                    TextView songArtist = view.findViewById(R.id.playlist_artist);
                    songName.setTextColor(Color.RED);
                    songArtist.setTextColor(Color.RED);
                }
            }


        }

        @Override
        public void onPause() {

        }
    };
}




