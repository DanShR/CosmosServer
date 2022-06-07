package com.dan.cosmos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor

public class PostDTO {
    private Long Id;
    private String text;
    private Date created;
}
