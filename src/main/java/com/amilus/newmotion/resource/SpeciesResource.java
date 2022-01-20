package com.amilus.newmotion.resource;

import com.amilus.newmotion.client.HttpRestClient;
import com.amilus.newmotion.dto.SpeciesDto;
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
 * SpeciesResource contains generic methods for /species Endpoint. You can customize as you wish for Species.
 * Mapping JSON responses to Data Object using this class.
 */

@Component
public class SpeciesResource extends BaseResource
{
    private List<SpeciesDto> species = new ArrayList<>();
    private HttpRestClient httpRestClient;

    //Constructor for RestAssured httpClient
    @Autowired
    public SpeciesResource(HttpRestClient restClient){
        this.httpRestClient = restClient;
    }

    //Calling Species endpoint with parameters and without parameters
    public void callSpeciesEndpoint(Method method,String parameter){
        httpRestClient.initRestAPI();
        if(parameter == null){
            httpRestClient.sendHttpRequest(method,"/species/");
        }
        else {
            httpRestClient.sendHttpRequest(method,"/species/"+parameter);
        }
    }

    //Getting JSON response as String and parsing to single Species Data Object Class
    public SpeciesDto getSpeciesInfo() throws JsonProcessingException
    {
        getTest().info("Parsing JSON response to species Data object for assertions");
        return (mapFromJson(httpRestClient.getResponseBody().asString(), SpeciesDto.class));
    }

    //For Validation Rest Client Response Code
    public void verifyResponseCode(int expectedResponseCode){
        getTest().info("Expected Status Code: "+expectedResponseCode +" Actual Status Code: "+ httpRestClient.getStatusCode());
        Assertions.assertEquals(expectedResponseCode,httpRestClient.getStatusCode());
    }

    //Returning custom response and getting Json Objects and Json Array mapping to Species Data Object
    public List<SpeciesDto> getSearchedSpeciesInfo() throws JsonProcessingException
    {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        JsonArray results = jsonObject.getAsJsonArray("results");
        getTest().info("Getting <b>results</b> JsonObject to species Data for validations..");
        for(JsonElement object : results){
            species.add(mapFromJson(object.toString(),SpeciesDto.class));
        }
        return species;
    }

    //Getting Value of custom Json Object
    public String getJsonObjectValue(String objectName) {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        return jsonObject.get(objectName).toString();
    }
}
