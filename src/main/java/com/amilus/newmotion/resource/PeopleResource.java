package com.amilus.newmotion.resource;

import com.amilus.newmotion.client.HttpRestClient;
import com.amilus.newmotion.dto.PeopleDto;
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
 * PeopleResource contains generic methods for /people Endpoint. You can customize as you wish for People.
 * Mapping JSON responses to Data Object using this class.
 */

@Component
public class PeopleResource extends BaseResource
{
    private List<PeopleDto> people = new ArrayList<>();
    private HttpRestClient httpRestClient;

    //Constructor for RestAssured httpClient
    @Autowired
    public PeopleResource(HttpRestClient restClient){
        this.httpRestClient = restClient;
    }

    //Calling people endpoint with parameters and without parameters
    public void callPeopleEndpoint(Method method,String parameter){
        httpRestClient.initRestAPI();
        if(parameter == null){
            httpRestClient.sendHttpRequest(method,"/people/");
        }
        else {
            httpRestClient.sendHttpRequest(method,"/people/"+parameter);
        }
    }

    //Getting JSON response as String and parsing to People Data Object Class
    public PeopleDto getPeopleInfo() throws JsonProcessingException
    {
        getTest().info("Parsing JSON response to People Data object for assertions");
        return (mapFromJson(httpRestClient.getResponseBody().asString(), PeopleDto.class));
    }

    //For Validation Rest Client Response Code
    public void verifyResponseCode(int expectedResponseCode){
        getTest().info("Expected Status Code: "+expectedResponseCode +" Actual Status Code: "+ httpRestClient.getStatusCode());
        Assertions.assertEquals(expectedResponseCode,httpRestClient.getStatusCode());
    }

    //Returning custom response and getting Json Objects and Json Array mapping to People Data Object
    public List<PeopleDto> getSearchedPeopleInfo() throws JsonProcessingException
    {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        JsonArray results = jsonObject.getAsJsonArray("results");
        getTest().info("Getting <b>results</b> JsonObject to People Data for validations..");
        for(JsonElement object : results){
            people.add(mapFromJson(object.toString(),PeopleDto.class));
        }
        return people;
    }

    //Getting Value of custom Json Object
    public String getJsonObjectValue(String objectName) {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        return jsonObject.get(objectName).toString();
    }
}
