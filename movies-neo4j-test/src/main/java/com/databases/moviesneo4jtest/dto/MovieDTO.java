package com.databases.moviesneo4jtest.dto;

import lombok.Data;

@Data
public class MovieDTO {
    private String title;
    private String tagline;

    public MovieDTO(String title, String tagline) {
        this.title = title;
        this.tagline = tagline;
    }
}
