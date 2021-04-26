package com.automation.api.endpoint_tests;

import com.automation.api.config.Configuration;
import com.automation.api.factory.UserDataFactory;
import com.automation.api.pojo.UserAuth;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserTest {

    @Before
    public void setUp()  {
        Configuration config = ConfigFactory.create(Configuration.class);
        baseURI = config.baseURI();
    }

    @Test
    public void testAuthFailed () {

        UserAuth useradminfailed = UserDataFactory.authUserAdminIncorrectCredentials();

        given()
                .contentType(ContentType.JSON)
                .body(useradminfailed)
        .when()
                .post("/auth")
        .then()
                .assertThat()
                    .body("reason",
                containsString("Bad credentials"))
                .log()
                    .all();
    }

}
