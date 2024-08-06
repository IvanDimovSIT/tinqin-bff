package com.tinqinacademy.bff.domain.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import feign.Contract;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.ParameterizedType;

@Configuration
@RequiredArgsConstructor
public class FeignConfiguration {
    private final ObjectMapper objectMapper;

    @Bean
    public Contract useFeignAnnotations() {return new Contract.Default();}


    @Bean
    public HotelRestExport hotelRestExport() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                //.decoder((response, type) -> {
                //    if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType().equals(ResponseEntity.class)) {
                //        JavaType innerType = objectMapper.getTypeFactory().constructType(((ParameterizedType) type).getActualTypeArguments()[0]);
                //        Object body = objectMapper.readValue(response.body().asInputStream(), innerType);
                //        return ResponseEntity.status(response.status()).body(body);
                //    }
                //    return new JacksonDecoder().decode(response, type);
                //})
                .target(HotelRestExport.class,"http://localhost:8080");

    }

    @Bean
    public CommentsRestExport commentsRestExport() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(CommentsRestExport.class,"http://localhost:8081");

    }
}
