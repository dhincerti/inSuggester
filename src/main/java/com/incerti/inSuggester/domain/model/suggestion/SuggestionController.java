package com.incerti.inSuggester.domain.model.suggestion;

import com.incerti.inSuggester.domain.model.music.Playlist;
import com.incerti.inSuggester.infrastructure.exceptions.MusicException;
import com.incerti.inSuggester.infrastructure.exceptions.WeatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/suggestion")
public class SuggestionController {

  private final SuggestionService suggestionService;

  @Autowired
  public SuggestionController(final SuggestionService suggestionService) {
    this.suggestionService = suggestionService;
  }

  @GetMapping(path = "/music", produces = MediaType.APPLICATION_JSON_VALUE)
  public Playlist getMusicSuggestion(@RequestParam("city") final String city)
      throws MusicException, WeatherException {
    return suggestionService.getPlaylistSuggestion(city);
  }
}
