package com.incerti.inSuggester.weather;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.incerti.inSuggester.weather.OpenWeatherMapService;
import com.incerti.inSuggester.weather.WeatherException;
import net.aksingh.owmjapis.api.APIException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class OpenWeatherMapServiceTest {
  private static final String LONDON = "London";

  @Spy
  private OpenWeatherMapService openWeatherMapService;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldReturnRightTemperature() throws APIException {
    final Double expectedTemperature = 1.0;
    doReturn(expectedTemperature).when(openWeatherMapService)
        .getOwnCurrentWeatherByCityName(LONDON);
    assertThat(openWeatherMapService.getCurrentTemperatureOf(LONDON), is(expectedTemperature));
  }

  @Test(expected = WeatherException.class)
  public void shouldThrowBusinessException() throws APIException {
    doThrow(APIException.class).when(openWeatherMapService).getOwnCurrentWeatherByCityName(LONDON);
    openWeatherMapService.getCurrentTemperatureOf(LONDON);
  }

}