package com.incerti.inSuggester.music;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;

public class Playlist {
  @JsonIgnoreProperties
  private Genre genre;

  @JsonIgnoreProperties
  private List<Song> songs;

  public Playlist(final Genre genre, final List<Song> songs) {
    this.genre = genre;
    this.songs = songs;
  }

  public Playlist(final Genre genre, final Song... songs) {
    this.genre = genre;
    this.songs = Arrays.asList(songs);
  }

  public Genre getGenre() {
    return genre;
  }

  public List<Song> getSongs() {
    return songs;
  }
}
