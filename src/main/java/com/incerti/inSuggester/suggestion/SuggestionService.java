package com.incerti.inSuggester.suggestion;

import com.incerti.inSuggester.music.Genre;
import com.incerti.inSuggester.music.MusicService;
import com.incerti.inSuggester.music.Playlist;
import com.incerti.inSuggester.weather.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {
  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionService.class);

  private final WeatherService weatherService;
  private MusicService musicService;

  public SuggestionService(final WeatherService weatherService, final MusicService musicService) {
    this.weatherService = weatherService;
    this.musicService = musicService;
  }

  public Playlist getPlaylistSuggestion(final String city) {
    LOGGER.info("A playlist will be suggested based on current temperature of {}", city);
    final Double currentTemperature = weatherService.getCurrentTemperatureOf(city);
    Genre genre = Genre.ROCK;

    if (currentTemperature >= 25) {
      genre = Genre.POP;
    }

    if (currentTemperature < 10) {
      genre = Genre.CLASSICAL;
    }

    return musicService.getPlaylistByGenre(genre);
  }
}
