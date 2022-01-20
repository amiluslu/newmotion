package com.amilus.newmotion.dto;

import lombok.Data;

/**
 * VehicleDto --> Pojo Of Vehicles Resource
 */
@Data
public class VehicleDto
{
    private String name;
    private String model;
    private String vehicle_class;
    private String manufacturer;
    private String length;
    private String cost_in_credits;
    private String crew;
    private String passengers;
    private String max_atmosphering_speed;
    private String cargo_capacity;
    private String consumables;
    private String[] films;
    private String[] pilots;
    private String url;
    private String created;
    private String edited;
}
