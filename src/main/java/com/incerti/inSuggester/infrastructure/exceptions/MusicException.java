package com.incerti.inSuggester.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class MusicException extends Exception {
  public MusicException(final String message) {
    super(message);
  }
}
