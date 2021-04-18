package com.automation.api.endpoint_tests;

import com.automation.api.config.Configuration;
import com.automation.api.factory.BookingDataFactory;
import com.automation.api.factory.UserDataFactory;
import com.automation.api.pojo.Booking;
import com.automation.api.pojo.UserAuth;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookTest {

    @Before
    public void setUp()  {
        Configuration config = ConfigFactory.create(Configuration.class);
        baseURI = config.baseURI();

    }


    @Test
    public void testCreateToken () {

        UserAuth useradmin = UserDataFactory.createUserAdmin();

        String token =  given()
            .contentType(ContentType.JSON)
            .body(useradmin)
        .when()
            .post("/auth")
        .then()
            .extract()
                .path("token");

        System.out.println(token);


    }

    @Test
    public void testCreateBookValidValue () throws IOException {


        Booking bookdescription = BookingDataFactory.createNewBook();

        given()
            .contentType(ContentType.JSON)
            .body(bookdescription)
        .when()
                .post("/booking")
        .then()
                .assertThat()
                    .statusCode(200)
                    .body("booking.firstname", equalToIgnoringCase("jim"))
                    .body("booking.lastname", equalToIgnoringCase("Brown"))
                .log()
                    .all();


   }

   // validar o endpoint com o que está no contrato = teste de contrato
    @Test
    public void testSearchAllBooks() {

        when()
            .get("/booking")
        .then()
            .assertThat()
                .statusCode(200)
            .log()
                .all();

    }

    // validar o endpoint com o que está no contrato = teste de contrato
    @Test
    public void testSearchBookById(){

        basePath = "/booking";


        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/1")
        .then()
                .log()
                    .all();

    }

    @Test
    public void testSearchBookByFirstNameAndLastName(){
        Configuration config = ConfigFactory.create(Configuration.class);
        basePath = config.basePath();


        given()
                .contentType(ContentType.JSON)
        .when()
                .get("?firstname=Jim&lastname=Brown")
        .then()
                .log()
                    .all()
                 .assertThat()
                    .statusCode(200)
                    .root("bookingid")
                        .body("size",greaterThanOrEqualTo(1));
    }

    @Test
    public void testBookUpdate() throws IOException {

        UserAuth useradmin = UserDataFactory.createUserAdmin();
        Booking updateinfobook = BookingDataFactory.updateFirstAndLastNameBook();

        String token =  given()
                .contentType(ContentType.JSON)
                .body(useradmin)
        .when()
                .post("/auth")
        .then()
                .extract()
                    .path("token");


        given()
                .contentType(ContentType.JSON)
                .body(updateinfobook)
                .header("Cookie","token="+token)
        .when()
                .patch("/booking/1")
        .then()
                .assertThat()
                    .statusCode(200)
                    .body("firstname", equalToIgnoringCase("carol"))
                    .body("lastname", equalToIgnoringCase("caroline"))
                    .log()
                        .all();
    }
}
