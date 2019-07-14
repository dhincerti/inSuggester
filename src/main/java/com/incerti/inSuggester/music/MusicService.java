package com.incerti.inSuggester.music;

public interface MusicService {

  /**
   * Retrieves a playlist filled with songs of given genre
   * @param genre String representation of a music genre
   * @return Playlist with given genre and a list of songs
   */
  Playlist getPlaylistByGenre(final Genre genre);
}
