package com.amilus.newmotion.dto;

import lombok.Data;

/**
 * PeopleDto --> Pojo Of People Resource
 */
@Data
public class PeopleDto
{
    private String name;
    private String birth_year;
    private String eye_color;
    private String gender;
    private String hair_color;
    private String height;
    private String mass;
    private String skin_color;
    private String homeworld;
    private String[] films;
    private String[] species;
    private String[] vehicles;
    private String url;
    private String created;
    private String edited;
}
