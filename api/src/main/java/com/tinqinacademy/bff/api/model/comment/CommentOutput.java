package com.tinqinacademy.bff.api.model.comment;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class CommentOutput {
    private String id;
    private String firstName;
    private String lastName;
    private String content;
    private LocalDate publishDate;
    private LocalDate lastEditedDate;
    private String lastEditedBy;
}
