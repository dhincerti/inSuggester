package com.incerti.inSuggester.suggestion;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.incerti.inSuggester.music.Genre;
import com.incerti.inSuggester.music.MusicService;
import com.incerti.inSuggester.music.Playlist;
import com.incerti.inSuggester.music.Song;
import com.incerti.inSuggester.suggestion.SuggestionService;
import com.incerti.inSuggester.weather.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SuggestionServiceTest {

  @InjectMocks
  private SuggestionService suggestionService;

  @Mock
  private WeatherService weatherService;

  @Mock
  private MusicService musicService;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldSuggestPopMusic() {
    final Song song = Song.getDummySong();
    final Playlist expectedPlaylist = new Playlist(Genre.POP, song);

    when(weatherService.getCurrentTemperatureOf(anyString())).thenReturn(Double.valueOf(30));
    when(musicService.getPlaylistByGenre(Genre.POP)).thenReturn(expectedPlaylist);

    final Playlist actualPlaylist = suggestionService.getPlaylistSuggestion(anyString());

    assertThat(actualPlaylist.getGenre(), equalTo(Genre.POP));
    assertThat(actualPlaylist.getSongs(), hasSize(1));
    assertThat(actualPlaylist.getSongs(), hasItem(samePropertyValuesAs(song)));
  }

  @Test
  public void shouldSuggestRockMusic() {
    final Song expectedSong = Song.getDummySong();
    final Playlist expectedPlaylist = new Playlist(Genre.ROCK, expectedSong);

    when(weatherService.getCurrentTemperatureOf(anyString())).thenReturn(Double.valueOf(15));
    when(musicService.getPlaylistByGenre(Genre.ROCK)).thenReturn(expectedPlaylist);

    final Playlist actualPlaylist = suggestionService.getPlaylistSuggestion(anyString());

    assertThat(actualPlaylist.getGenre(), equalTo(Genre.ROCK));
    assertThat(actualPlaylist.getSongs(), hasSize(1));
    assertThat(actualPlaylist.getSongs(), hasItem(samePropertyValuesAs(expectedSong)));
  }

  @Test
  public void shouldSuggestClassicalMusic() {
    final Song expectedSong = Song.getDummySong();
    final Playlist expectedPlaylist = new Playlist(Genre.CLASSICAL, expectedSong);

    when(weatherService.getCurrentTemperatureOf(anyString())).thenReturn(Double.valueOf(0));
    when(musicService.getPlaylistByGenre(Genre.CLASSICAL)).thenReturn(expectedPlaylist);

    final Playlist actualPlaylist = suggestionService.getPlaylistSuggestion(anyString());

    assertThat(actualPlaylist.getGenre(), equalTo(Genre.CLASSICAL));
    assertThat(actualPlaylist.getSongs(), hasSize(1));
    assertThat(actualPlaylist.getSongs(), hasItem(samePropertyValuesAs(expectedSong)));
  }
}