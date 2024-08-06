package com.tinqinacademy.bff.rest;

import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.api.operations.system.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;


@ComponentScan(basePackages = "com.tinqinacademy.bff")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = "com.tinqinacademy.bff.domain")
public class BffApplication {

    @Autowired
    private HotelRestExport hotelRestExport;
    @Autowired
    private CommentsRestExport commentsRestExport;

    @PostConstruct
    private void testComments() {
        try {
            System.out.println(commentsRestExport.getComments("2586e647-8b53-43cb-8916-c93915787979"));
        }catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @PostConstruct
    private void testFeign() {
        try {
            System.out.println(hotelRestExport.checkAvailableRooms(
                    LocalDate.of(2024, 1, 1),
                    LocalDate.of(2025, 1, 1),
                    null, null, null));


            System.out.println(hotelRestExport.getRoom("2586e647-8b53-43cb-8916-c93915787979"));
            System.out.println(hotelRestExport.partialUpdateRoom("2586e647-8b53-43cb-8916-c93915787979",
                    PartialUpdateRoomInput.builder().roomNumber("9990A").build()));

            System.out.println(hotelRestExport.getRoom("2586e647-8b53-43cb-8916-c93915787970"));
        }catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BffApplication.class, args);
    }

}
