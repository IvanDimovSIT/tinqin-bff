package com.tinqinacademy.bff.kafka.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class WordMessage {
    private String word;
    private String id;
}

