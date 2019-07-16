package com.incerti.inSuggester.infrastructure.external.spotify;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import com.incerti.inSuggester.InSuggesterApplicationRunner;
import com.incerti.inSuggester.domain.model.music.Genre;
import com.incerti.inSuggester.domain.model.music.MusicProvider;
import com.incerti.inSuggester.domain.model.music.Playlist;
import com.incerti.inSuggester.domain.model.music.Song;
import com.incerti.inSuggester.infrastructure.exceptions.MusicException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InSuggesterApplicationRunner.class)
public class SpotifyServiceTest {

  @Autowired
  private MusicProvider musicProvider;

  @Test
  public void shouldReturnAPlaylistWithSongs() throws MusicException {
    final Playlist actualPlaylist = musicProvider.getPlaylistByGenre(Genre.ROCK);
    final List<Song> actualPlaylistSongs = actualPlaylist.getSongs();
    assertThat(actualPlaylist.getGenre(), equalTo(Genre.ROCK));
    assertThat(actualPlaylistSongs, is(not(empty())));
    actualPlaylistSongs.forEach(song -> assertThat(song.getName(), not(isEmptyOrNullString())));
  }


}