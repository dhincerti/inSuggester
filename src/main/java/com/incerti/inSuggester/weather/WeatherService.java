package com.incerti.inSuggester.weather;

public interface WeatherService {

  /**
   * Retrieves current temperature for a given city
   * @param city String representation a city
   * @return Double value of the current temperature
   */
  Double getCurrentTemperatureOf(String city);
}
