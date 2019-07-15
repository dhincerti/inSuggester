package com.incerti.inSuggester.suggestion;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.incerti.inSuggester.music.Genre;
import com.incerti.inSuggester.music.Playlist;
import com.incerti.inSuggester.music.Song;
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
  public void apiShouldRespondWithSuccess() {
    final Song expectedSong = Song.getDummySong();
    final Playlist expectedPlaylist = new Playlist(Genre.ROCK, expectedSong);

    when(suggestionService.getPlaylistSuggestion(LONDON)).thenReturn(expectedPlaylist);
    final Playlist actualPlaylist = suggestionController.getMusicSuggestion(LONDON);

    assertThat(actualPlaylist.getGenre(), equalTo(Genre.ROCK));
    assertThat(actualPlaylist.getSongs(), hasSize(1));
    assertThat(actualPlaylist.getSongs(), hasItem(samePropertyValuesAs(expectedSong)));
  }
}