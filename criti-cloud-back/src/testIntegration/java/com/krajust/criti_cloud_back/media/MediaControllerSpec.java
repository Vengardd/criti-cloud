package com.krajust.criti_cloud_back.media;

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
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MediaControllerSpec extends CritiCloudBackApplicationIntegrationTest {

    static UUID mediaId;
    static String mediaName = "randomName";
    static DetailsType detailsType = DetailsType.MOVIE;
    static UUID detailsId = randomUUID();

    @Test
    @Order(1)
    public void add_media() throws JSONException, JsonProcessingException {
        // given
        var mediaJson = new JSONObject();
        mediaJson.put("name", mediaName);
        mediaJson.put("detailsType", detailsType);
        mediaJson.put("detailsId", detailsId);
        HttpEntity<String> request = new HttpEntity<>(mediaJson.toString(), headers);

        // when
        var addMediaResponse = restTemplate.postForEntity("/media", request, String.class);

        // then
        assertThat(addMediaResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        var root = objectMapper.readTree(addMediaResponse.getBody());
        assertThat(root).isNotNull();
        mediaId = UUID.fromString(root.get("id").asText());
    }

    @Test
    @Order(2)
    public void fetch_all_media() {
        // given
        var given = given()
                .contentType(ContentType.JSON);

        // when
        var when = given.when()
                .get("/media");

        // then
        MediaDTO[] result = when.then()
                .statusCode(200)
                .extract()
                .as(MediaDTO[].class);
        assertThat(result).hasSize(1);
        assertThat(result[0]).isEqualTo(new MediaDTO(mediaId, mediaName, detailsType, detailsId));
    }

    @Test
    @Order(3)
    public void fetch_newly_added_media() {
        // given
        var given = given()
                .contentType(ContentType.JSON);

        // when
        var when = given.when()
                .get("/media/" + mediaId);

        // then
        MediaDTO result = when.then()
                .statusCode(200)
                .extract()
                .as(MediaDTO.class);
        assertThat(result).isEqualTo(new MediaDTO(mediaId, mediaName, detailsType, detailsId));
    }

}
