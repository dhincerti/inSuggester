package com.incerti.inSuggester.music;

import static java.util.stream.Collectors.toList;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class SpotifyService implements MusicService {
  private static final Logger LOGGER = LoggerFactory.getLogger(MusicService.class);
  private SpotifyApi spotifyApi;

  public SpotifyService(@Value("${services.music.clientId}") final String clientId,
      @Value("${services.music.clientSecret}") final String clientSecret) {
    spotifyApi = new SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .build();
  }

  public Playlist getPlaylistByGenre(final Genre genre) {
    LOGGER.info("Retrieving a {} playlist", genre);
    generateAccessToken();

    try {
      final Recommendations recommendations = spotifyApi.getRecommendations()
          .seed_genres(genre.toString().toLowerCase())
          .build()
          .execute();

      final List<Song> songs = Arrays.stream(recommendations.getTracks())
          .map(track -> new Song(track.getName(), track.getUri(), track.getPreviewUrl()))
          .collect(toList());

      LOGGER.info("Successfully retrieved a {} playlist with {} songs", genre, songs.size());
      return new Playlist(genre, songs);
    } catch (final IOException | SpotifyWebApiException | AssertionError e) {
      final String message = "Unable to retrieve spotify playlist for genre " + genre;
      LOGGER.debug(message, e);
      throw new MusicException(message);
    }
  }

  public void generateAccessToken() {
    try {
      final ClientCredentials clientCredentials = spotifyApi.clientCredentials().build().execute();
      spotifyApi.setAccessToken(clientCredentials.getAccessToken());
      LOGGER.info("Spotify token expires in: {}", clientCredentials.getExpiresIn());
    } catch (final IOException | SpotifyWebApiException | AssertionError e) {
      final String message = "Unable to generate spotify credentials";
      LOGGER.debug(message, e);
      throw new MusicException(message);
    }
  }
}
