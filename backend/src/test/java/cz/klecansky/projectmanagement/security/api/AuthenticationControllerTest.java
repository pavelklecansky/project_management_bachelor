package cz.klecansky.projectmanagement.security.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

import cz.klecansky.projectmanagement.ContainersConfig;
import cz.klecansky.projectmanagement.user.ui.request.SignInRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainersConfig.class)
class AuthenticationControllerTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/api";
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public static String adminLoginToken() {
        SignInRequest signInRequest = new SignInRequest("admin", "admin@admin.com");

        return given().contentType(ContentType.JSON)
                .body(signInRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .get("accessToken");
    }

    @Test
    void shouldReturnAuthorizationErrorWhenNotValidCredentialsUsed() {
        SignInRequest signInRequest = new SignInRequest("nonExistingPassword", "notvalid@email.com");
        given().contentType(ContentType.JSON)
                .body(signInRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", equalTo("Authorization error"));
    }

    @Test
    void shouldAuthorized() {
        SignInRequest signInRequest = new SignInRequest("admin", "admin@admin.com");
        given().contentType(ContentType.JSON)
                .body(signInRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("tokenType", equalTo("Bearer"))
                .body("user", is(notNullValue()));
    }

    @Test
    void onCurrentUserShouldDeniedAccessWhenNotLogin() {
        given().when()
                .get("/users/currentUser")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("Access Denied"));
    }

    @Test
    void onCurrentUserShouldReturnCurrentUserWhenLogin() {
        String authorization = "Bearer " + adminLoginToken();
        given().header("Authorization", authorization)
                .when()
                .get("/users/currentUser")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("email", equalTo("admin@admin.com"));
    }
}
