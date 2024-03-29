package com.incerti.inSuggester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableConfigurationProperties
@SpringBootApplication
public class InSuggesterApplicationRunner {

  public static void main(final String[] args) {
    SpringApplication.run(InSuggesterApplicationRunner.class, args);
  }
}
