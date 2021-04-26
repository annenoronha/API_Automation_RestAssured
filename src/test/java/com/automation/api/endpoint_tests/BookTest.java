package com.automation.api.endpoint_tests;

import com.automation.api.config.Configuration;
import com.automation.api.factory.BookingDataFactory;
import com.automation.api.factory.UserDataFactory;
import com.automation.api.pojo.Booking;
import com.automation.api.pojo.UserAuth;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import java.io.IOException;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(DataProviderRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookTest {

    private static  ResponseSpecification responseSuccessSpec;

    @Before
    public void setUp()  {
        Configuration config = ConfigFactory.create(Configuration.class);
        baseURI = config.baseURI();
    }

    @BeforeClass
    public static void bookSuccessResponseSpecification() {

        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectContentType(ContentType.JSON);
        responseSuccessSpec = builder.build();
    }

    @DataProvider
    public static Object[][] bookFirstNameAndLastName(){
        return new Object[][]{
                {"Juan","Marcel"},
                {"Lore","Flin"},
                {"Maria","Liz"}
        };
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

    @Test
    public void testSearchAllBooks() {

        when()
            .get("/booking")
        .then()
                .log()
                    .all()
                .assertThat()
                    .statusCode(200);
    }

    @Test
    public void testSearchBookById(){

        basePath = "/booking";


        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/1")
        .then()
                .spec(responseSuccessSpec)
        .and()
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

    @Test
    @UseDataProvider("bookFirstNameAndLastName")
    public void testBookUpdateWithDataCollection(String firstName, String lastName) {

        UserAuth useradmin = UserDataFactory.createUserAdmin();

        String token = given()
                .contentType(ContentType.JSON)
                .body(useradmin)
        .when()
                .post("/auth")
        .then()
                .extract()
                    .path("token");


        given()
                .contentType(ContentType.JSON)
                .header("Cookie","token="+token)
                .body("{\n" +
                        "    \"firstname\" : \""+firstName+"\",\n" +
                        "    \"lastname\" : \""+lastName+"\"\n" +
                        "}")
        .when()
                .patch("/booking/1")
        .then()
                .assertThat()
                    .statusCode(200)
                    .body("firstname", equalTo(firstName))
                    .body("lastname", equalTo(lastName))
                    .log()
                        .all();
    }
}
