package com.krajust.criti_cloud_back.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krajust.criti_cloud_back.CritiCloudBackApplicationIntegrationTest;
import com.krajust.criti_cloud_back.media.MediaDTO;
import com.krajust.criti_cloud_back.media.MediaService;
import com.krajust.criti_cloud_back.media.MediaTestData;
import com.krajust.criti_cloud_back.rating.RatingDTO;
import com.krajust.criti_cloud_back.rating.RatingService;
import com.krajust.criti_cloud_back.rating.RatingTestData;
import com.krajust.criti_cloud_back.user.UserDTO;
import com.krajust.criti_cloud_back.user.UserService;
import com.krajust.criti_cloud_back.user.UserTestData;
import io.restassured.http.Header;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatingControllerSpec extends CritiCloudBackApplicationIntegrationTest
        implements UserTestData, MediaTestData, RatingTestData {

    static UUID ratingId;

    static MediaDTO media;
    static UserDTO user;

    @Autowired
    MediaService mediaService;
    @Autowired
    UserService userService;
    @Autowired
    RatingService ratingService;

    @Test
    @Order(1)
    public void create_user_and_media() {
        // given
        user = userService.save(toCreateUser().build());
        media = mediaService.save(toCreateMedia().build());
    }

    @Test
    @Order(2)
    public void adds_rating() throws JsonProcessingException {
        // given
        var rating = toCreateRating(user.id, media.id).build();
        var ratingJson = objectMapper.writeValueAsString(rating);

        // when
        var result = given()
                .body(ratingJson)
                .header(new Header("Content-Type", "application/json"))
                .when()
                .post("/ratings")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(RatingDTO.class);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id).isNotNull();
        ratingId = result.id;
    }

    @Test
    @Order(3)
    public void finds_by_id() {
        // when
        var result = given()
                .header(new Header("Content-Type", "application/json"))
                .when()
                .get("/ratings/" + ratingId)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(RatingDTO.class);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id).isEqualTo(ratingId);
    }

    @Test
    @Order(4)
    public void finds_by_user_id() {
        // given
        var differentUser = userService.save(toCreateUser().build());
        var differentMedia = mediaService.save(toCreateMedia().detailsId(randomUUID()).build());
        var differentRating = ratingService.save(toCreateRating(differentUser.id, differentMedia.id).build());

        // when
        var result = given()
                .header(new Header("Content-Type", "application/json"))
                .when()
                .get("/ratings?userId=" + user.id)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(RatingDTO[].class);

        // then
        assertThat(result).hasSize(1);
        assertThat(result[0].id).isEqualTo(ratingId);
    }

    @Test
    @Order(4)
    public void finds_by_media_id() {
        // given
        var differentUser = userService.save(toCreateUser().build());
        var differentMedia = mediaService.save(toCreateMedia().detailsId(randomUUID()).build());
        var differentRating = ratingService.save(toCreateRating(differentUser.id, differentMedia.id).build());

        // when
        var result = given()
                .header(new Header("Content-Type", "application/json"))
                .when()
                .get("/ratings?mediaId=" + media.id)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(RatingDTO[].class);

        // then
        assertThat(result).hasSize(1);
        assertThat(result[0].id).isEqualTo(ratingId);
    }

}
