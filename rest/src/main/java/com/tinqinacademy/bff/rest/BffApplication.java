package com.tinqinacademy.bff.rest;

import com.tinqinacademy.bff.domain.HotelFeignClient;
import com.tinqinacademy.hotel.api.model.enums.BathroomType;
import com.tinqinacademy.hotel.api.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.system.addroom.AddRoomInput;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;


@ComponentScan(basePackages = "com.tinqinacademy.bff")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = "com.tinqinacademy.bff.domain")
public class BffApplication {

    @Autowired
    private HotelFeignClient hotelFeignClient;

    @PostConstruct
    private void testFeign(){
        System.out.println(hotelFeignClient.getRoom("2586e647-8b53-43cb-8916-c93915787979"));
        //AddRoomInput addRoomInput = AddRoomInput.builder()
        //        .bedSize(BedSize.SINGLE)
        //        .bedCount(2)
        //        .price(BigDecimal.valueOf(1230.12))
        //        .bathroomType(BathroomType.SHARED)
        //        .floor(3)
        //        .roomNumber("20A")
        //        .build();
        //System.out.println(hotelFeignClient.addRoom(addRoomInput));
    }

    public static void main(String[] args) {
        SpringApplication.run(BffApplication.class, args);
    }

}
