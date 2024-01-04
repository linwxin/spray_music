package com.xin.spray.ui.playlist;

import android.content.Context;
import android.provider.MediaStore;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.xin.spray.db.entity.Song;

import java.util.List;

public class PlaylistAdapter extends ListAdapter<Song, PlaylistViewHolder> {

    protected PlaylistAdapter(@NonNull DiffUtil.ItemCallback<Song> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PlaylistViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Song current = getItem(position);
        holder.bind(current.getTitle(), current.getArtist());
    }

    static class SongDiff extends DiffUtil.ItemCallback<Song> {

        @Override
        public boolean areItemsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Song oldItem, @NonNull Song newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getArtist().equals(newItem.getArtist())
                    && oldItem.getSize().equals(newItem.getSize());
        }
    }
}
