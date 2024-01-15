package com.xin.spray.db.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.xin.spray.db.dao.SongDao;
import com.xin.spray.db.entity.Song;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Song.class}, version = 1, exportSchema = false)
abstract public class AppDatabase extends RoomDatabase {

    public abstract SongDao songDao();

    private static volatile AppDatabase appDatabase;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExcutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getAppDatabase(final Context context) {
        if (appDatabase == null) {
            synchronized (AppDatabase.class) {
                if (appDatabase == null) {
                    appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "spray.db")
                            .build();
                }
            }
        }

        return appDatabase;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExcutor.execute(() -> {
                SongDao songDao = appDatabase.songDao();
            });
        }
    };

}
