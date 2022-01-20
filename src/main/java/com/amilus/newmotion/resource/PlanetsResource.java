package com.amilus.newmotion.resource;

import com.amilus.newmotion.client.HttpRestClient;
import com.amilus.newmotion.dto.PlanetDto;
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
 * PlanetsResource contains generic methods for /planets Endpoint. You can customize as you wish for Planets.
 * Mapping JSON responses to Data Object using this class.
 */

@Component
public class PlanetsResource extends BaseResource
{
    private List<PlanetDto> planets = new ArrayList<>();
    private HttpRestClient httpRestClient;

    //Constructor for RestAssured httpClient
    @Autowired
    public PlanetsResource(HttpRestClient restClient){
        this.httpRestClient = restClient;
    }

    //Calling planets endpoint with parameters and without parameters
    public void callPlanetsEndpoint(Method method,String parameter){
        httpRestClient.initRestAPI();
        if(parameter == null){
            httpRestClient.sendHttpRequest(method,"/planets/");
        }
        else {
            httpRestClient.sendHttpRequest(method,"/planets/"+parameter);
        }
    }

    //Getting JSON response as String and parsing to single Planets Data Object Class
    public PlanetDto getPlanetInfo() throws JsonProcessingException
    {
        getTest().info("Parsing JSON response to Planets Data object for assertions");
        return (mapFromJson(httpRestClient.getResponseBody().asString(), PlanetDto.class));
    }

    //For Validation Rest Client Response Code
    public void verifyResponseCode(int expectedResponseCode){
        getTest().info("Expected Status Code: "+expectedResponseCode +" Actual Status Code: "+ httpRestClient.getStatusCode());
        Assertions.assertEquals(expectedResponseCode,httpRestClient.getStatusCode());
    }

    //Returning custom response and getting Json Objects and Json Array mapping to Planets Data Object
    public List<PlanetDto> getSearchedPlanetsInfo() throws JsonProcessingException
    {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        JsonArray results = jsonObject.getAsJsonArray("results");
        getTest().info("Getting <b>results</b> JsonObject to Planets Data for validations..");
        for(JsonElement object : results){
            planets.add(mapFromJson(object.toString(),PlanetDto.class));
        }
        return planets;
    }

    //Getting Value of custom Json Object
    public String getJsonObjectValue(String objectName) {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        return jsonObject.get(objectName).toString();
    }
}
