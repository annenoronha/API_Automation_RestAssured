package com.automation.api.scenarios_tests;

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
public class BookTestScenarios {

    private String token;

    @Before
    public void setUp()  {
        Configuration config = ConfigFactory.create(Configuration.class);
        baseURI = config.baseURI();
        UserAuth useradmin = UserDataFactory.createUserAdmin();

        // User Authentication - Get Token

        this.token =  given()
                .contentType(ContentType.JSON)
                .body(useradmin)
        .when()
                .post("/auth")
        .then()
                .extract()
                    .path("token");

    }

    @Test
    public void testCreateAndUpdateNewBook() throws IOException {

        Booking bookdescription = BookingDataFactory.createNewBook();
        Booking updateinfobook = BookingDataFactory.updateFirstAndLastNameBook();


        // Create a new Book

        Integer bookId = given()
                .contentType(ContentType.JSON)
                .body(bookdescription)
        .when()
                .post("/booking")
        .then()
                .log()
                    .all()
                    .extract()
                        .path("bookingid");

        // Update the New Book

        given()
                .contentType(ContentType.JSON)
                .body(updateinfobook)
                .header("Cookie","token="+token)
        .when()
                .patch("/booking/"+ bookId)

        .then()
                .assertThat()
                    .statusCode(200)
                    .body("firstname", equalToIgnoringCase("Carol"))
                    .body("lastname", equalToIgnoringCase("Caroline"))
                    .log()
                        .all();
        System.out.println("Book Id Updated: " + bookId);

       }

    @Test
    public void testCreateAndDeleteNewBook() throws IOException {

        Booking bookdescription = BookingDataFactory.createNewBook();

        // Create a new Book

        Integer bookId = given()
                .contentType(ContentType.JSON)
                .body(bookdescription)
        .when()
                .post("/booking")
        .then()
                .log()
                    .all()
                .extract()
                    .path("bookingid");

        // Delete the New Book

        given()
                .contentType(ContentType.JSON)
                .header("Cookie","token="+token)
        .when()
                .delete("/booking/"+ bookId)
        .then()
                .assertThat()
                    .statusCode(201)
                    .statusLine(containsString("Created"))
                .log()
                    .all();
        System.out.println("Book Id Deleted: " + bookId);

        // Search for the book with the deleted ID

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/"+bookId)
        .then()
                .assertThat()
                    .statusCode(404)
                    .statusLine(containsString("Not Found"))
                .log()
                    .all();;
    }
}
