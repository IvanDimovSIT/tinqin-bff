package com.tinqinacademy.bff.domain;

import com.tinqinacademy.bff.domain.config.ClientConfiguration;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "hotel", url = "${hotel.url}", configuration = ClientConfiguration.class)
public interface HotelFeignClient extends HotelRestExport {
}
