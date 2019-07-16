package com.incerti.inSuggester.infrastructure.external.spotify;

import static java.util.stream.Collectors.toList;

import com.incerti.inSuggester.domain.model.music.Genre;
import com.incerti.inSuggester.domain.model.music.MusicProvider;
import com.incerti.inSuggester.domain.model.music.Playlist;
import com.incerti.inSuggester.domain.model.music.Song;
import com.incerti.inSuggester.infrastructure.exceptions.MusicException;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class SpotifyService implements MusicProvider {
  private static final Logger LOGGER = LoggerFactory.getLogger(SpotifyService.class);
  private SpotifyApiProperties spotifyApiProperties;
  private SpotifyApi spotifyApi;

  @Autowired
  public SpotifyService(final SpotifyApiProperties spotifyApiProperties) {
    this.spotifyApiProperties = spotifyApiProperties;
    this.spotifyApi = getSpotifyApi();
  }

  @Override
  @Cacheable(cacheNames = "playlist", key = "#genre")
  public Playlist getPlaylistByGenre(final Genre genre) throws MusicException {
    LOGGER.info("A '{}' playlist will be retrieved from spotify", genre);
    final List<Song> songs = getSongsByGenre(genre);
    LOGGER.info("Successfully retrieved a '{}' playlist with {} songs", genre, songs.size());
    return new Playlist(genre, songs);
  }

  @Override
  public List<Song> getSongsByGenre(final Genre genre) throws MusicException {
    try {
      doAuthorize();
      return Arrays.stream(spotifyApi.getRecommendations()
          .seed_genres(genre.toString().toLowerCase())
          .build()
          .execute()
          .getTracks()
      ).map(s -> new Song(s.getName(), s.getUri(), s.getPreviewUrl())).collect(toList());
    } catch (final IOException | SpotifyWebApiException e) {
      final String message = "Unable to generate a playlist for genre " + genre;
      LOGGER.debug(message, e);
      throw new MusicException(message);
    }
  }

  @Override
  public void doAuthorize() throws MusicException {
    LOGGER.info("Generating Spotify access token");
    try {
      final ClientCredentials clientCredentials = spotifyApi.clientCredentials().build().execute();
      spotifyApi.setAccessToken(clientCredentials.getAccessToken());
      LOGGER.info("Spotify token expires in: {}", clientCredentials.getExpiresIn());
    } catch (final IOException | SpotifyWebApiException e) {
      final String message = "Unable to retrieve access token for spotify apis";
      LOGGER.debug(message, e);
      throw new MusicException(message);
    }
  }

  public SpotifyApi getSpotifyApi() {
    if (spotifyApi != null) { return spotifyApi; }

    return new SpotifyApi.Builder()
        .setClientId(spotifyApiProperties.getClientId())
        .setClientSecret(spotifyApiProperties.getClientSecret())
        .build();
  }
}
