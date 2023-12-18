package com.xin.spray.ui.playlist;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xin.spray.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment {

    private PlaylistViewModel mViewModel;

    private RecyclerView playlistRecyclerView;

    private PlaylistAdapter playlistAdapter;

    public static PlaylistFragment newInstance() {
        return new PlaylistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        playlistRecyclerView = view.findViewById(R.id.playlist_recycler_view);
        List<String> songNameList = new ArrayList<>();
        List<String> albumList = new ArrayList<>();
        songNameList.add("song 1");
        songNameList.add("song 2");
        songNameList.add("song 3");
        albumList.add("album 1");
        albumList.add("album 2");
        albumList.add("album 3");
        PlaylistAdapter adapter = new PlaylistAdapter(getContext(), songNameList, albumList);
        playlistRecyclerView.setAdapter(adapter);
        playlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.submitList()
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PlaylistViewModel.class);
        // TODO: Use the ViewModel


    }

}