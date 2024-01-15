package com.xin.spray.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.xin.spray.db.entity.Song;

import java.util.ArrayList;
import java.util.List;

public class MusicUtils {

    public static List<Song> getMusicData(Context context) {
        // 媒体库查询
        List<Song> songList = new ArrayList<>();

        String[] projection = {
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE
        };

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                Integer duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                songList.add(new Song(name, artist, path, duration, size));

            }

            cursor.close();
        }


        return songList;
    }

    public static String formatTime(int duration) {
        if (duration / 1000 % 60 < 10) {
            return duration / 1000 / 60 + ":0" + duration / 1000 % 60;
        } else {
            return duration / 1000 / 60 + ":" + duration / 1000 % 60;
        }
    }
}
