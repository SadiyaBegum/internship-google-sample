package com.google;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private ArrayList<String> arr = new ArrayList<>(0);
  private Boolean paused;
  private ArrayList<VideoPlaylist> playlists = new ArrayList<>();

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> sortedVideos = videoLibrary.getVideos().stream().sorted(Comparator.comparing(Video::getTitle)).collect(Collectors.toList());
    for (int i = 0; i < sortedVideos.size(); i++) {
      String tags = "";
      int j = 0;
      for (j = 0; j < sortedVideos.get(i).getTags().size(); j++) {
        tags += sortedVideos.get(i).getTags().get(j);
        if (j != sortedVideos.get(i).getTags().size() - 1) {
          tags += " ";
        }
      }
      System.out.println(sortedVideos.get(i).getTitle() + " (" + sortedVideos.get(i).getVideoId() + ") [" + tags + "]");
    }
  }

  public void playVideo(String videoId) {
    paused = false;
    Video video = videoLibrary.getVideo(videoId);
    if (video == null) {
      System.out.println("Cannot play video: Video does not exist");
    } else {
      if (arr.size() >= 1) {
        System.out.println("Stopping video: " + arr.get(0));
        arr.remove(0);
      }
      arr.add(videoLibrary.getVideo(videoId).getTitle());
      System.out.println("Playing video: " + arr.get(0));
    }
  }

  public void stopVideo() {
    if (arr.size() >= 1) {
      System.out.println("Stopping video: " + arr.get(0));
      arr.remove(0);
    } else {
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    paused = false;
    if (arr.size() >= 1) {
      System.out.println("Stopping video: " + arr.get(0));
      arr.remove(0);
    }
    Random random = new Random();
    int rand = random.nextInt(4);
    arr.add(videoLibrary.getVideos().get(rand).getTitle());
    System.out.println("Playing video: " + arr.get(0));
  }

  public void pauseVideo() {
    if (arr.size() >= 1) {
      if (paused) {
        System.out.println("Video already paused: " + arr.get(0));
      } else {
        paused = true;
        System.out.println("Pausing video: " + arr.get(0));
      }
    } else {
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }

  public void continueVideo() {
    if (arr.size() >= 1) {
      if (paused) {
        System.out.println("Continuing video: " + arr.get(0));
        paused = false;
      } else {
        System.out.println("Cannot continue video: Video is not paused");
      }
    } else {
      System.out.println("Cannot continue video: No video is currently playing");
    }
  }

  public void showPlaying() {
    if (arr.size() >= 1) {
      String isPaused = "";
      if (paused) {
        isPaused = " - PAUSED";
      }
      List<Video> videos = videoLibrary.getVideos();
      for (Video video : videos) {
        if (video.getTitle() == arr.get(0)) {
          String tags = "";
          for (int j = 0; j < video.getTags().size(); j++) {
            tags += video.getTags().get(j);
            if (j != video.getTags().size() - 1) {
              tags += " ";
            }
          }
          System.out.println("Currently playing: " + video.getTitle() + " (" + video.getVideoId() + ") [" + tags + "]" + isPaused);
        }
      }
    } else {
      System.out.println("No video is currently playing");
    }
  }

  public void createPlaylist(String playlistName) {
    boolean created = false;
    for (int i = 0; i < playlists.size(); i++) {
      String title = playlists.get(i).getName();
      if (title.equalsIgnoreCase(playlistName)) {
        created = true;
      }
    }

    if (!created && !playlistName.isEmpty()) {
      System.out.println("Successfully created new playlist: " + playlistName);
      playlists.add(new VideoPlaylist(playlistName));
    } else {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    }
  }

  private VideoPlaylist checkPlaylistExists(String playlistName) {
    return playlists.stream().filter(a -> a.getName().equalsIgnoreCase(playlistName)).findFirst().orElse(null);
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    VideoPlaylist videoPlaylist = checkPlaylistExists(playlistName);
    Video vid = videoLibrary.getVideo(videoId);
    if (videoPlaylist == null) {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    } else if (vid == null) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
    } else if (videoPlaylist.addVid(vid)) {
      System.out.println("Added video to " + playlistName + ": " + vid.getTitle());
    } else {
      System.out.println("Cannot add video to " + playlistName + ": Video already added");
    }
  }

  public void showAllPlaylists () {
    if (playlists.size() <= 1){
      System.out.println("No playlists exist yet");
    } else{
      System.out.println("Showing all playlists:");
      List<VideoPlaylist> sortedPlaylists = playlists.stream().sorted(Comparator.comparing(VideoPlaylist::getName)).collect(Collectors.toList());
      for (int i = 0; i < playlists.size(); i++){
        System.out.println(sortedPlaylists.get(i).getName());
      }
    }
  }

  public void showPlaylist (String playlistName){
    VideoPlaylist videoPlaylist = checkPlaylistExists(playlistName);
    if (videoPlaylist == null){
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    } else{
      System.out.println("Showing playlist: " + playlistName);
      if (videoPlaylist.getVideos() == null){
        System.out.println("No videos here yet");
      } else{
        List<Video> vid = videoPlaylist.getVideos();
        for (int i = 0; i < vid.size(); i++){
          String tags = "";
          for (int j = 0; j < vid.get(i).getTags().size(); j++) {
            tags += vid.get(i).getTags().get(j);
            if (j != vid.get(i).getTags().size() - 1) {
              tags += " ";
            }
          }
          System.out.println(vid.get(i).getTitle() + " (" + vid.get(i).getVideoId() + ") [" + tags + "]");
        }
      }
    }
  }

  public void removeFromPlaylist (String playlistName, String videoId){
    VideoPlaylist videoPlaylist = checkPlaylistExists(playlistName);
    Video vid = videoLibrary.getVideo(videoId);
    if (videoPlaylist == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    } else if (vid == null) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
    } else if (videoPlaylist.addVid(vid)) {
      System.out.println("Removed video from " + playlistName + ": " + vid.getTitle());
    } else {
      System.out.println("Cannot remove video from " + playlistName + ": Video is not in the playlist");
    }
  }

  public void clearPlaylist (String playlistName){
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist (String playlistName){
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos (String searchTerm){
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag (String videoTag){
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo (String videoId){
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo (String videoId, String reason){
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo (String videoId){
    System.out.println("allowVideo needs implementation");
  }
}