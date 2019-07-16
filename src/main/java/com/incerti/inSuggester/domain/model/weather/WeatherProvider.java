package com.incerti.inSuggester.domain.model.weather;

import com.incerti.inSuggester.infrastructure.exceptions.WeatherException;

public interface WeatherProvider {

  /**
   * Retrieves current temperature for a given city
   * @param city String representation a city
   * @return Double value of the current temperature
   */
  Double getCurrentTemperatureOf(String city) throws WeatherException;
}
