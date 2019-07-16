package com.incerti.inSuggester.domain.model.suggestion;

import com.incerti.inSuggester.domain.model.music.Genre;
import com.incerti.inSuggester.domain.model.music.MusicProvider;
import com.incerti.inSuggester.domain.model.music.Playlist;
import com.incerti.inSuggester.domain.model.weather.WeatherProvider;
import com.incerti.inSuggester.infrastructure.exceptions.MusicException;
import com.incerti.inSuggester.infrastructure.exceptions.WeatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {
  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionService.class);

  private final WeatherProvider weatherProvider;
  private MusicProvider musicProvider;

  public SuggestionService(final WeatherProvider weatherProvider,
      final MusicProvider musicProvider) {
    this.weatherProvider = weatherProvider;
    this.musicProvider = musicProvider;
  }

  public Playlist getPlaylistSuggestion(final String city) throws MusicException, WeatherException {
    LOGGER.info("A playlist will be suggested based on current temperature of {}", city);
    final Double currentTemperature = weatherProvider.getCurrentTemperatureOf(city);
    Genre genre = Genre.ROCK;

    if (currentTemperature >= 25) {
      genre = Genre.POP;
    }

    if (currentTemperature < 10) {
      genre = Genre.CLASSICAL;
    }

    return musicProvider.getPlaylistByGenre(genre);
  }
}
