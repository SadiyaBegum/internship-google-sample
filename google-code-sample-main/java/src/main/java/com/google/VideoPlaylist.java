package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private String name;
    private List<Video> videos;

    VideoPlaylist(String name){
        this.name = name;
        this.videos = new ArrayList<>();
    }

    String getName(){
        return this.name;
    }

    List<Video> getVideos(){
        return this.videos;
    }

    public boolean addVid(Video vid){
        if (videos.contains(vid)){
            return false;
        } else{
            videos.add(vid);
            return true;
        }
    }

    boolean removeVid(Video vid){
        if (videos.contains(vid)){
            videos.remove(vid);
            return true;
        } else{
            return false;
        }
    }
}
