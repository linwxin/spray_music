package com.xin.spray.ui.playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xin.spray.R;

public class PlaylistViewHolder extends RecyclerView.ViewHolder {

    private final TextView playlistSongName;

    private final TextView playlistArtist;

    public PlaylistViewHolder(@NonNull View itemView) {
        super(itemView);
        playlistSongName = itemView.findViewById(R.id.playlist_song_name);
        playlistArtist = itemView.findViewById(R.id.playlist_artist);

    }

    public void bind(String songName, String artist) {
        playlistSongName.setText(songName);
        playlistArtist.setText(artist);
    }

    static PlaylistViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview_music, parent, false);

        return new PlaylistViewHolder(view);
    }


}
