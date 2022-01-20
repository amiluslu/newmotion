package com.amilus.newmotion.resource;

import com.amilus.newmotion.client.HttpRestClient;
import com.amilus.newmotion.dto.StarshipDto;
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
 * StarshipsResource contains generic methods for /starships Endpoint. You can customize as you wish for starships.
 * Mapping JSON responses to Data Object using this class.
 */

@Component
public class StarshipsResource extends BaseResource
{
    private List<StarshipDto> starships;
    private HttpRestClient httpRestClient;

    //Constructor for RestAssured httpClient
    @Autowired
    public StarshipsResource(HttpRestClient restClient){
        this.httpRestClient = restClient;
    }

    //Calling starships endpoint with parameters and without parameters
    public void callStarshipsEndpoint(Method method,String parameter){
        httpRestClient.initRestAPI();
        if(parameter == null){
            httpRestClient.sendHttpRequest(method,"/starships/");
        }
        else {
            httpRestClient.sendHttpRequest(method,"/starships/"+parameter);
        }
    }

    //Getting JSON response as String and parsing to single starships Data Object Class
    public StarshipDto getStarshipInfo() throws JsonProcessingException
    {
        getTest().info("Parsing JSON response to starships Data object for assertions");
        return (mapFromJson(httpRestClient.getResponseBody().asString(), StarshipDto.class));
    }

    //For Validation Rest Client Response Code
    public void verifyResponseCode(int expectedResponseCode){
        getTest().info("Expected Status Code: "+expectedResponseCode +" Actual Status Code: "+ httpRestClient.getStatusCode());
        Assertions.assertEquals(expectedResponseCode,httpRestClient.getStatusCode());
    }

    //Returning custom response and getting Json Objects and Json Array mapping to starships Data Object
    public List<StarshipDto> getSearchedStarshipsInfo() throws JsonProcessingException
    {
        starships = new ArrayList<>();
        JsonObject jsonObject = getJsonObject(httpRestClient);
        JsonArray results = jsonObject.getAsJsonArray("results");
        getTest().info("Getting <b>results</b> JsonObject to starships Data for validations..");
        for(JsonElement object : results){
            starships.add(mapFromJson(object.toString(),StarshipDto.class));
        }
        return starships;
    }

    //Getting Value of custom Json Object
    public String getJsonObjectValue(String objectName) {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        return jsonObject.get(objectName).toString();
    }
}
