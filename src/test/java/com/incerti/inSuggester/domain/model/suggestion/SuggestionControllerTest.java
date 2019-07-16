package com.incerti.inSuggester.domain.model.suggestion;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.incerti.inSuggester.domain.model.music.Genre;
import com.incerti.inSuggester.domain.model.music.Playlist;
import com.incerti.inSuggester.domain.model.music.Song;
import com.incerti.inSuggester.infrastructure.exceptions.MusicException;
import com.incerti.inSuggester.infrastructure.exceptions.WeatherException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SuggestionControllerTest {
  private static final String LONDON = "London";

  @InjectMocks
  private SuggestionController suggestionController;

  @Mock
  private SuggestionService suggestionService;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void apiShouldRespondWithSuccess() throws MusicException, WeatherException {
    final Song expectedSong = Song.getDummySong();
    final Playlist expectedPlaylist = new Playlist(Genre.ROCK, expectedSong);

    when(suggestionService.getPlaylistSuggestion(LONDON)).thenReturn(expectedPlaylist);
    final Playlist actualPlaylist = suggestionController.getMusicSuggestion(LONDON);

    assertThat(actualPlaylist.getGenre(), equalTo(Genre.ROCK));
    assertThat(actualPlaylist.getSongs(), hasSize(1));
    assertThat(actualPlaylist.getSongs(), hasItem(samePropertyValuesAs(expectedSong)));
  }
}