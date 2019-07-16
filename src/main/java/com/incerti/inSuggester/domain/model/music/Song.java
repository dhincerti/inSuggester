package com.incerti.inSuggester.domain.model.music;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Song implements Serializable {
  private static final long serialVersionUID = 1L;

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

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(final String uri) {
    this.uri = uri;
  }

  public String getPreviewUrl() {
    return previewUrl;
  }

  public void setPreviewUrl(final String previewUrl) {
    this.previewUrl = previewUrl;
  }
}
