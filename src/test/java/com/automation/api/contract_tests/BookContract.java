package com.automation.api.contract_tests;
import com.automation.api.config.Configuration;
import com.automation.api.factory.BookingDataFactory;
import com.automation.api.pojo.Booking;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class BookContract {

    @Test
    public void testCreateBookSuccessContract() throws IOException {

        Configuration config = ConfigFactory.create(Configuration.class);
        baseURI = config.baseURI();
        Booking bookdescription = BookingDataFactory.createNewBook();

        given()
                .contentType(ContentType.JSON)
                .body(bookdescription)
        .when()
                .post("/booking")
        .then()
                .assertThat()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("schemas/booking/postCreateBookingSuccess.json"));
    }

    @Test
    public void testSearchBookByIdSuccessContract() throws IOException {
        Configuration config = ConfigFactory.create(Configuration.class);
        baseURI = config.baseURI();
        basePath = config.basePath();


        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/1")
        .then()
                .assertThat()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("schemas/booking/getBookByIdValid.json"));
    }
}
