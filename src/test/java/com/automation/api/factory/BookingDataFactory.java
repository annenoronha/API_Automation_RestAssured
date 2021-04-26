package com.automation.api.factory;
import com.automation.api.pojo.Booking;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import com.tngtech.java.junit.dataprovider.*;
import org.junit.runner.RunWith;
import java.io.IOException;

@RunWith(DataProviderRunner.class)
public class BookingDataFactory {


    public static Booking createNewBook() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Booking book = objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postBooking.json"),Booking.class);
        return book;
    }

    public static Booking updateFirstAndLastNameBook() throws IOException {
        Booking updatebookinfo = createNewBook();
        updatebookinfo.setFirstname("Carol");
        updatebookinfo.setLastname("Caroline");
        return updatebookinfo;

    }

}
