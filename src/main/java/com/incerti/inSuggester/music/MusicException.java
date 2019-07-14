package com.incerti.inSuggester.music;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class MusicException extends RuntimeException {
  public MusicException(final String message) {
    super(message);
  }
}
