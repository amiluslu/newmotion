package com.amilus.newmotion.dto;

import lombok.Data;

import java.util.Date;

/**
 * FilmDto --> Pojo Of Films Resource
 */
@Data
public class FilmDto
{
    private String title;
    private String episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private Date release_date;
    private String[] species;
    private String[] starships;
    private String[] vehicles;
    private String[] characters;
    private String[] planets;
    private String url;
    private String created;
    private String edited;
}
