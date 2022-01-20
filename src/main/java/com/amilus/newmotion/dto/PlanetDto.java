package com.amilus.newmotion.dto;

import lombok.Data;

/**
 * PlanetDto --> Pojo Of Planet Resource
 */
@Data
public class PlanetDto
{
    private String name;
    private String diameter;
    private String rotation_period;
    private String orbital_period;
    private String gravity;
    private String population;
    private String climate;
    private String terrain;
    private String surface_water;
    private String[] residents;
    private String[] films;
    private String url;
    private String created;
    private String edited;
}
