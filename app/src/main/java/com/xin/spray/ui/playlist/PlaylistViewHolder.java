package com.xin.spray.ui.playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xin.spray.R;

import org.w3c.dom.Text;

public class PlaylistViewHolder extends RecyclerView.ViewHolder {

    private final TextView playlistSongName;

    private final TextView playlistAlbum;

    public PlaylistViewHolder(@NonNull View itemView) {
        super(itemView);
        playlistSongName = itemView.findViewById(R.id.playlist_song_name);
        playlistAlbum = itemView.findViewById(R.id.playlist_album);

    }

    public void bind(String songName, String album) {
        playlistSongName.setText(songName);
        playlistAlbum.setText(album);
    }

    static PlaylistViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview_music, parent, false);

        return new PlaylistViewHolder(view);
    }


}
