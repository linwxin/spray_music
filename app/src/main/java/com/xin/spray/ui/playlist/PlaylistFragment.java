package com.xin.spray.ui.playlist;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xin.spray.R;
import com.xin.spray.db.entity.Song;
import com.xin.spray.utils.MusicUtils;

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
        requestPermission();
        playlistRecyclerView = view.findViewById(R.id.playlist_recycler_view);
        List<Song> songList = MusicUtils.getMusicData(getContext());
//        List<Song> songList = new ArrayList<>();
        PlaylistAdapter adapter = new PlaylistAdapter(new PlaylistAdapter.SongDiff());
        playlistRecyclerView.setAdapter(adapter);
        playlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.submitList(songList);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1001:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }  else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
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
}




