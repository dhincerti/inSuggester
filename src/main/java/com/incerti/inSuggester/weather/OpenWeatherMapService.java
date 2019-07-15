package com.incerti.inSuggester.weather;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OpenWeatherMapService implements WeatherService {
  private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherMapService.class);

  @Value("${services.weather.apikey}")
  private String apiKey;

  @Override
  @Cacheable(cacheNames = "temperature", key = "#city")
  public Double getCurrentTemperatureOf(final String city) {
    LOGGER.info("Retrieving current temperature of '{}' city", city);

    try {
      final Double temp = getOwnCurrentWeatherByCityName(city);
      LOGGER.info("Current temperature of '{}' city is {}", city, temp);

      return temp;
    } catch (final APIException e) {
      final String message = "Unable to retrieve current weather for city " + city;
      LOGGER.debug(message, e);
      throw new WeatherException(message);
    }
  }

  /**
   * Only for test proposal!!
   *
   * This method can't be tested without spring context.
   * OWM can't be injected, so we cant mock it.
   *
   * This whole method will be stubbed.
   */
  public Double getOwnCurrentWeatherByCityName(final String city) throws APIException {
    final OWM owm = new OWM(apiKey);
    owm.setUnit(OWM.Unit.METRIC);
    return Objects.requireNonNull(owm.currentWeatherByCityName(city).getMainData()).getTemp();
  }
}
