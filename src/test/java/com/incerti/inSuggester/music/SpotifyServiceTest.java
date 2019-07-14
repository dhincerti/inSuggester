package com.incerti.inSuggester.music;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;

import com.incerti.inSuggester.InSuggesterApplicationRunner;
import com.incerti.inSuggester.music.Genre;
import com.incerti.inSuggester.music.MusicException;
import com.incerti.inSuggester.music.Playlist;
import com.incerti.inSuggester.music.SpotifyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InSuggesterApplicationRunner.class)
public class SpotifyServiceTest {

  @Autowired
  private SpotifyService spotifyService;

  @Test
  public void shouldReturnAPlaylistWithSongs() {
    final Playlist actualPlaylist = spotifyService.getPlaylistByGenre(Genre.ROCK);

    assertThat(actualPlaylist.getGenre(), equalTo(Genre.ROCK));
    assertThat(actualPlaylist.getSongs(), is(not(empty())));
  }

  @Test(expected = MusicException.class)
  public void shouldThrowExceptionIfCredentialsAreInvalid() {
    final SpotifyService spotifyService = new SpotifyService("", "");
    spotifyService.generateAccessToken();
  }

  @Test(expected = MusicException.class)
  public void shouldThrowExceptionIfRecommendationsFails() {
    final SpotifyService spotifyService = spy(this.spotifyService);
    doNothing().when(spotifyService).generateAccessToken();

    spotifyService.getPlaylistByGenre(Genre.ROCK);
  }
}