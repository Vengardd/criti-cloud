package com.krajust.criti_cloud_back.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krajust.criti_cloud_back.CritiCloudBackApplicationIntegrationTest;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieControllerSpec extends CritiCloudBackApplicationIntegrationTest {

    static UUID movieId;
    static String title = "randomTitle";
    static int year = 2137;
    static int runtime = 420;
    static String director = "randomDirector";
    static String plot = "Plot about some random movie when random things happen";
    static String imbdId = "tt0111161";
    static String posterUrl = "https://m.media-amazon.com/images/M/MV5BNjRjNmU1M2ItNDU4Ni00ZGY2LTlmNzItY2MxYmY3OTllZjMwXkEyXkFqcGc@._V1_SX300.jpg";

    @Test
    @Order(1)
    public void add_movie() throws JSONException, JsonProcessingException {
        // given
        var movieJson = new JSONObject();
        movieJson.put("title", title);
        movieJson.put("year", year);
        movieJson.put("runtime", runtime);
        movieJson.put("director", director);
        movieJson.put("plot", plot);
        movieJson.put("imbdId", imbdId);
        movieJson.put("posterUrl", posterUrl);
        HttpEntity<String> request = new HttpEntity<>(movieJson.toString(), headers);

        // when
        var addMovieResponse = restTemplate.postForEntity("/movies", request, String.class);

        // then
        assertThat(addMovieResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        var root = objectMapper.readTree(addMovieResponse.getBody());
        assertThat(root).isNotNull();
        movieId = UUID.fromString(root.get("id").asText());
    }

    @Test
    @Order(2)
    public void fetch_movie() {
        // given
        var given = given()
                .contentType(ContentType.JSON);

        // when
        var when = given.when()
                .get("/movies/" + movieId);

        // then
        var result = when.then()
                .statusCode(200)
                .extract()
                .as(MovieDTO.class);
        assertThat(result).isEqualTo(new MovieDTO(movieId, title, year, runtime, director, plot, imbdId, posterUrl));
    }

}
