package com.incerti.inSuggester.suggestion;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.incerti.inSuggester.music.Genre;
import com.incerti.inSuggester.music.Playlist;
import com.incerti.inSuggester.music.Song;
import com.incerti.inSuggester.suggestion.SuggestionController;
import com.incerti.inSuggester.suggestion.SuggestionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
  public void apiShouldRespondWithSuccess() {
    final Song expectedSong = Song.getDummySong();
    final Playlist expectedPlaylist = new Playlist(Genre.ROCK, expectedSong);

    when(suggestionService.getPlaylistSuggestion(LONDON)).thenReturn(expectedPlaylist);
    final ResponseEntity<Playlist> actualPlaylist = suggestionController.getMusicSuggestion(LONDON);

    assertThat(actualPlaylist.getStatusCode(), is(HttpStatus.OK));
    assertThat(actualPlaylist.getBody().getGenre(), equalTo(Genre.ROCK));
    assertThat(actualPlaylist.getBody().getSongs(), hasSize(1));
    assertThat(actualPlaylist.getBody().getSongs(), hasItem(samePropertyValuesAs(expectedSong)));
  }
}