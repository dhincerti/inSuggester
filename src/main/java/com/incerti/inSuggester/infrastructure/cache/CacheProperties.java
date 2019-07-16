package com.incerti.inSuggester.infrastructure.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {
  private boolean enabled;
  private long timeout;
  private String redisHost;
  private int redisPort;
  private Map<String, Long> expiration = new HashMap<>();

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  public long getTimeout() {
    return timeout;
  }

  public void setTimeout(final long timeout) {
    this.timeout = timeout;
  }

  public String getRedisHost() {
    return redisHost;
  }

  public void setRedisHost(final String redisHost) {
    this.redisHost = redisHost;
  }

  public int getRedisPort() {
    return redisPort;
  }

  public void setRedisPort(final int redisPort) {
    this.redisPort = redisPort;
  }

  public Map<String, Long> getExpiration() {
    return expiration;
  }

  public void setExpiration(final Map<String, Long> expiration) {
    this.expiration = expiration;
  }
}
