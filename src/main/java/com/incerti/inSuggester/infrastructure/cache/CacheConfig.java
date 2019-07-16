package com.incerti.inSuggester.infrastructure.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class CacheConfig extends CachingConfigurerSupport {
  private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

  private static RedisCacheConfiguration createCacheConfiguration(final long timeoutInSeconds) {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(timeoutInSeconds));
  }

  @Bean
  public LettuceConnectionFactory redisConnectionFactory(
      final CacheProperties properties) {
    LOGGER.info("Redis (/Lettuce) configuration enabled. With cache timeout {} seconds.",
        properties.getTimeout());

    final RedisStandaloneConfiguration redisStandaloneConfiguration =
        new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(properties.getRedisHost());
    redisStandaloneConfiguration.setPort(properties.getRedisPort());
    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(final RedisConnectionFactory cf) {
    final RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(cf);
    return redisTemplate;
  }

  @Bean
  public RedisCacheConfiguration cacheConfiguration(final CacheProperties properties) {
    return createCacheConfiguration(properties.getTimeout());
  }

  @Bean
  public CacheManager cacheManager(final RedisConnectionFactory redisConnectionFactory,
      final CacheProperties properties) {
    final Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

    for (final Entry<String, Long> cacheNameAndTimeout : properties.getExpiration()
        .entrySet()) {
      cacheConfigurations.put(cacheNameAndTimeout.getKey(),
          createCacheConfiguration(cacheNameAndTimeout.getValue()));
    }


    if (properties.isEnabled()) {
      return RedisCacheManager
          .builder(redisConnectionFactory)
          .cacheDefaults(cacheConfiguration(properties))
          .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    return new NoOpCacheManager();
  }
}
