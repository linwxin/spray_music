package com.xin.spray.ui.playlist;

import android.content.Context;
import android.provider.MediaStore;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {
    private Context context;

    private List<String> songNameList;

    private List<String> albumList;
    public PlaylistAdapter(Context context, List<String> songNameList, List<String> albumList) {
        this.context = context;
        this.songNameList = songNameList;
        this.albumList = albumList;

    }
    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PlaylistViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.bind(songNameList.get(position), albumList.get(position));
    }

    @Override
    public int getItemCount() {
        return songNameList.size();
    }
}
