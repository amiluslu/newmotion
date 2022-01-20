package com.amilus.newmotion.dto;

import lombok.Data;

/**
 * SpeciesDto --> Pojo Of Species Resource
 */
@Data
public class SpeciesDto
{
    private String name;
    private String classification;
    private String designation;
    private String average_height;
    private String average_lifespan;
    private String eye_colors;
    private String hair_colors;
    private String skin_colors;
    private String language;
    private String homeworld;
    private String consumables;
    private String[] people;
    private String[] films;
    private String url;
    private String created;
    private String edited;
}
