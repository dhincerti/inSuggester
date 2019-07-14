package com.incerti.inSuggester.weather;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WeatherException extends RuntimeException {
  public WeatherException(final String message) {
    super(message);
  }
}
