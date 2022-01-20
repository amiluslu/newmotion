package com.amilus.newmotion.resource;

import com.amilus.newmotion.client.HttpRestClient;
import com.amilus.newmotion.dto.VehicleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.http.Method;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.amilus.newmotion.report.ExtentTestManager.getTest;

/**
 * VehiclesResource contains generic methods for /vehicles Endpoint. You can customize as you wish for vehicles.
 * Mapping JSON responses to Data Object using this class.
 */

@Component
public class VehiclesResource extends BaseResource
{
    private List<VehicleDto> vehicles;
    private HttpRestClient httpRestClient;

    //Constructor for RestAssured httpClient
    @Autowired
    public VehiclesResource(HttpRestClient restClient){
        this.httpRestClient = restClient;
    }

    //Calling vehicles endpoint with parameters and without parameters
    public void callVehiclesEndpoint(Method method,String parameter){
        httpRestClient.initRestAPI();
        if(parameter == null){
            httpRestClient.sendHttpRequest(method,"/vehicles/");
        }
        else {
            httpRestClient.sendHttpRequest(method,"/vehicles/"+parameter);
        }
    }

    //Getting JSON response as String and parsing to single vehicles Data Object Class
    public VehicleDto getVehicleInfo() throws JsonProcessingException
    {
        getTest().info("Parsing JSON response to vehicles Data object for assertions");
        return (mapFromJson(httpRestClient.getResponseBody().asString(), VehicleDto.class));
    }

    //For Validation Rest Client Response Code
    public void verifyResponseCode(int expectedResponseCode){
        getTest().info("Expected Status Code: "+expectedResponseCode +" Actual Status Code: "+ httpRestClient.getStatusCode());
        Assertions.assertEquals(expectedResponseCode,httpRestClient.getStatusCode());
    }

    //Returning custom response and getting Json Objects and Json Array mapping to vehicles Data Object
    public List<VehicleDto> getSearchedVehiclesInfo() throws JsonProcessingException
    {
        vehicles = new ArrayList<>();
        JsonObject jsonObject = getJsonObject(httpRestClient);
        JsonArray results = jsonObject.getAsJsonArray("results");
        getTest().info("Getting <b>results</b> JsonObject to vehicles Data for validations..");
        for(JsonElement object : results){
            vehicles.add(mapFromJson(object.toString(),VehicleDto.class));
        }
        return vehicles;
    }

    //Getting Value of custom Json Object
    public String getJsonObjectValue(String objectName) {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        return jsonObject.get(objectName).toString();
    }
}
