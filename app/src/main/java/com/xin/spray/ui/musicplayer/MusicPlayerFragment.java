package com.xin.spray.ui.musicplayer;

import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xin.spray.R;
import com.xin.spray.db.entity.Song;
import com.xin.spray.service.PlayerService;

public class MusicPlayerFragment extends Fragment {

    private MusicPlayerViewModel mViewModel;

    private PlayerService.MusicServiceBinder serviceBinder;

    public static MusicPlayerFragment newInstance() {
        return new MusicPlayerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_player, container, false);


        initFragment();
        return view;
    }

    private void initFragment() {
        // 绑定播放服务
        Intent i = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(i, mServiceConnection, Context.BIND_AUTO_CREATE);

    }


    // 定义与服务的连接匿名类
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d("wanxin", "service connected in MusicPlayFragment");
            serviceBinder = (PlayerService.MusicServiceBinder) service;


            Song curSong = serviceBinder.getCurrentSong();


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };



}