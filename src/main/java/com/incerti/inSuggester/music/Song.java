package com.incerti.inSuggester.music;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Song {
  @JsonProperty
  private String name;
  @JsonProperty
  private String uri;
  @JsonProperty
  private String previewUrl;

  public Song(final String name, final String uri, final String previewUrl) {
    this.name = name;
    this.uri = uri;
    this.previewUrl = previewUrl;
  }

  public static Song getDummySong() {
    return new Song("Just a dummy song", "spoty:uri:dummy", "");
  }
}
