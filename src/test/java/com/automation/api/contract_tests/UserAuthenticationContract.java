package com.automation.api.contract_tests;
import com.automation.api.config.Configuration;
import com.automation.api.factory.UserDataFactory;
import com.automation.api.pojo.UserAuth;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class UserAuthenticationContract {

    @Test
    public void testAuthenticationSuccessContract(){

        Configuration config = ConfigFactory.create(Configuration.class);
        baseURI = config.baseURI();
        UserAuth useradmin = UserDataFactory.createUserAdmin();

        given()
                .contentType(ContentType.JSON)
                .body(useradmin)
        .when()
                .post("/auth")
        .then()
                .assertThat()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("schemas/user/postAuthenticationSuccess.json"));

    }


}
