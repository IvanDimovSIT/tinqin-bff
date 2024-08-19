package com.tinqinacademy.bff.domain.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authentication.restexport.AuthenticationRestExport;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import feign.Contract;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.ParameterizedType;

@Configuration
@RequiredArgsConstructor
public class FeignConfiguration {
    private final ObjectMapper objectMapper;

    @Value("${hotel.url}")
    private String hotelUrl;
    @Value("${comments.url}")
    private String commentsUrl;
    @Value("${authentication.url}")
    private String authenticationUrl;

    @Bean
    public Contract useFeignAnnotations() {return new Contract.Default();}


    @Bean
    public HotelRestExport hotelRestExport() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(HotelRestExport.class, hotelUrl);

    }

    @Bean
    public CommentsRestExport commentsRestExport() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(CommentsRestExport.class, commentsUrl);

    }

    @Bean
    public AuthenticationRestExport authenticationRestExport() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(AuthenticationRestExport.class, authenticationUrl);

    }
}
