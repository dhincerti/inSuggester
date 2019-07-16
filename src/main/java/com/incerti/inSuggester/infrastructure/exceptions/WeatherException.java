package com.incerti.inSuggester.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WeatherException extends Exception {
  public WeatherException(final String message) {
    super(message);
  }
}
