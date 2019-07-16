package com.incerti.inSuggester.domain.model.suggestion;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.incerti.inSuggester.domain.model.music.Genre;
import com.incerti.inSuggester.domain.model.music.MusicProvider;
import com.incerti.inSuggester.domain.model.music.Playlist;
import com.incerti.inSuggester.domain.model.music.Song;
import com.incerti.inSuggester.domain.model.weather.WeatherProvider;
import com.incerti.inSuggester.infrastructure.exceptions.MusicException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SuggestionServiceTest {

  @InjectMocks
  private SuggestionService suggestionService;

  @Mock
  private WeatherProvider weatherProvider;

  @Mock
  private MusicProvider musicProvider;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldSuggestPopMusic() throws Exception {
    final Song song = Song.getDummySong();
    final Playlist expectedPlaylist = new Playlist(Genre.POP, song);

    when(weatherProvider.getCurrentTemperatureOf(anyString())).thenReturn(Double.valueOf(30));
    when(musicProvider.getPlaylistByGenre(Genre.POP)).thenReturn(expectedPlaylist);

    final Playlist actualPlaylist = suggestionService.getPlaylistSuggestion(anyString());

    assertThat(actualPlaylist.getGenre(), equalTo(Genre.POP));
    assertThat(actualPlaylist.getSongs(), hasSize(1));
    assertThat(actualPlaylist.getSongs(), hasItem(samePropertyValuesAs(song)));
  }

  @Test
  public void shouldSuggestRockMusic() throws Exception {
    final Song expectedSong = Song.getDummySong();
    final Playlist expectedPlaylist = new Playlist(Genre.ROCK, expectedSong);

    when(weatherProvider.getCurrentTemperatureOf(anyString())).thenReturn(Double.valueOf(15));
    when(musicProvider.getPlaylistByGenre(Genre.ROCK)).thenReturn(expectedPlaylist);

    final Playlist actualPlaylist = suggestionService.getPlaylistSuggestion(anyString());

    assertThat(actualPlaylist.getGenre(), equalTo(Genre.ROCK));
    assertThat(actualPlaylist.getSongs(), hasSize(1));
    assertThat(actualPlaylist.getSongs(), hasItem(samePropertyValuesAs(expectedSong)));
  }

  @Test
  public void shouldSuggestClassicalMusic() throws Exception {
    final Song expectedSong = Song.getDummySong();
    final Playlist expectedPlaylist = new Playlist(Genre.CLASSICAL, expectedSong);

    when(weatherProvider.getCurrentTemperatureOf(anyString())).thenReturn(Double.valueOf(0));
    when(musicProvider.getPlaylistByGenre(Genre.CLASSICAL)).thenReturn(expectedPlaylist);

    final Playlist actualPlaylist = suggestionService.getPlaylistSuggestion(anyString());

    assertThat(actualPlaylist.getGenre(), equalTo(Genre.CLASSICAL));
    assertThat(actualPlaylist.getSongs(), hasSize(1));
    assertThat(actualPlaylist.getSongs(), hasItem(samePropertyValuesAs(expectedSong)));
  }

  @Test(expected = MusicException.class)
  public void shouldThrowBusinessException() throws Exception {
    doThrow(MusicException.class).when(musicProvider).getPlaylistByGenre(any(Genre.class));
    suggestionService.getPlaylistSuggestion(Genre.ROCK.toString());
  }
}