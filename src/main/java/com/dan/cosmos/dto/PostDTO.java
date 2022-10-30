package com.dan.cosmos.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
public class PostDTO {
    private Long Id;
    private String text;
    private Date created;
    private String filename;
}
