# inSuggester - The best search time reducer!

By now, this project provides an API for getting suggestions over musics based on weather conditions. 
It will be further improved with other streaming suggestion types. Ex: films, series, etc...

# Architecture 

## Language and framework
Java was chosen as the main language mainly because of:
 * My personal knowledge;
 * The framework Spring Boot
    * It makes easier to create stand-alone (no server configurations needed) applications 
    what lets us to rapidly start coding which really brings value: business rules. 
    * Easy to scale. Spring will internally manage threads to adapt to changes in volume. 
    Threads and database connections can be pooled to minimize overhead. 

See more: 
 * [Why Spring Boot?](https://dzone.com/articles/why-springboot)
 * [Spring Boot Autoscaler](https://piotrminkowski.wordpress.com/2018/09/18/spring-boot-autoscaler/)

## External services

#### Weather provider
After [Yahoo messed up its own weather service](https://www.igorkromin.net/index.php/2016/03/27/yahoo-effectively-shut-down-its-weather-api-by-forcing-oauth-10-and-crippling-it/),
Open Weather Map has become one of the best weather services. It has [a well documented](https://openweathermap.org/api) 
API that provides free weather data for thousands of cities across the globe 
and it also has [an easy to use Java library](https://bitbucket.org/aksinghnet/owm-japis/src/master/).

#### Music provider
Although this application is developed to easily add or change third party service integrations, 
[Spotify’s API](https://developer.spotify.com/documentation/web-api/) was chosen at first because 
it offers free access to public playlists, requiring only an Access Token. It means that, 
there is no need for users to login, and yet they will be able to listen a song preview.
It also has [an easy to use Java library](https://github.com/thelinmichael/spotify-web-api-java).

## Distributed cache with Redis
In order to optimize the response time and decrease the number of external services calls, 
this application relies on a cache mechanism. It uses Redis, a popular open-source, 
in-memory data structure store, that can be used as a distributed cache solution that could 
be accessed from all cloud services.

It uses different time-to-live (TTL) values as follow:
* 24h for playlist generations by genre.
* 10min for current temperature by city.

# How to run it
What you’ll need
- [Docker](https://docs.docker.com/docker-for-windows/install/)
- [JDK 1.8](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Maven 3.2+](https://maven.apache.org/download.cgi)

For the very first time, use following command to create a redis image:

`docker run -it --name redis -p 6379:6379 redis:5.0.3`

Obs: for future usages `docker start redis` should be enough.

Once you have cloned or download this project, you can use the command line to navigate to 
the project folder and run:

`mvn spring-boot:run`


# Using it
Once the application is up and running, the suggestion APIs will be available through
`/api/v1/suggestion` endpoint.

Following is an example of a curl request:

`curl "http://localhost:8080/api/v1/suggestion/music?city=Campinas"`

Response body:

```javascript
{
    "genre": "ROCK",
    "songs": [
        {
            "name": "The Lonesome Boatman",
            "uri": "spotify:track:1eX5SoCjIWCIS3TGoZ6upR",
            "previewUrl": "https://p.scdn.co/mp3-preview/a891eaa4735f8943f023ebc4932b18c1d98d9e02?cid=ee5c42e53c814c6ca2b5fa737b8b5cbd"
        },
        {
            "name": "World Away",
            "uri": "spotify:track:4hRz7rOTUet8jJpXgWvoXh",
            "previewUrl": "https://p.scdn.co/mp3-preview/9dbdf29a607e1f5a30b7db2e7abcbbfa161dd1d9?cid=ee5c42e53c814c6ca2b5fa737b8b5cbd"
        }
    ]
}
```

