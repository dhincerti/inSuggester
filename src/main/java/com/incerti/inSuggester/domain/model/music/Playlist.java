package com.incerti.inSuggester.domain.model.music;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Playlist implements Serializable {
  private static final long serialVersionUID = 1L;

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
