package com.incerti.inSuggester.domain.model.music;

import com.incerti.inSuggester.infrastructure.exceptions.MusicException;

import java.util.List;

public interface MusicProvider {

  /**
   * Retrieves a playlist filled with songs of given genre
   * @param genre A music genre
   * @return Playlist with given genre and a list of songs
   * @throws MusicException if any third-party integration error occurs
   */
  Playlist getPlaylistByGenre(final Genre genre) throws MusicException;

  /**
   * Retrieves list of given genre songs
   * @param genre A music genre
   * @return A list of songs
   * @throws MusicException if any third-party integration error occurs
   */
  List<Song> getSongsByGenre(Genre genre) throws MusicException;

  /**
   * Authorize with the third-party service if needed
   * @throws MusicException if any third-party integration error occurs
   */
  void doAuthorize() throws MusicException;
}
