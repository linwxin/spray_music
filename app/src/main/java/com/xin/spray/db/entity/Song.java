package com.xin.spray.db.entity;


import androidx.room.Entity;

@Entity(tableName = "song")
public class Song {
    private int id;
    private String title;

    private String artist;

    private String path;

    private String belong;

    private int duration;

    private Long size;

    public Song() {

    }

    public Song(String title, String artist, String path, int duration, Long size) {
        this.title = title;
        this.artist = artist;
        this.path = path;
        this.belong = "";
        this.duration = duration;
        this.size = size;
    }

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }
}
