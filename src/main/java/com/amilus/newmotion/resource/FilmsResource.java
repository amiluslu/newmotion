package com.amilus.newmotion.resource;

import com.amilus.newmotion.client.HttpRestClient;
import com.amilus.newmotion.dto.FilmDto;
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
 * FilmsResource contains generic methods for /films Endpoint. You can customize as you wish for Films. Mapping JSON responses to Data Object using this class.
 */
@Component
public class FilmsResource extends BaseResource
{
    private List<FilmDto> films = new ArrayList<>();
    private HttpRestClient httpRestClient;

    //Constructor for RestAssured httpClient
    @Autowired
    public FilmsResource(HttpRestClient restClient){
        this.httpRestClient = restClient;
    }

    //Calling films endpoint with parameters and without parameters
    public void callFilmEndpoint(Method method,String parameter){
        httpRestClient.initRestAPI();
        if(parameter == null){
            httpRestClient.sendHttpRequest(method,"/films/");
        }
        else {
            httpRestClient.sendHttpRequest(method,"/films/"+parameter);
        }
    }

    //Getting JSON response as String and parsing to Film Data Object Class
    public FilmDto getFilmInfo() throws JsonProcessingException
    {
        getTest().info("Parsing JSON response to Film Data object for assertions");
        return (mapFromJson(httpRestClient.getResponseBody().asString(), FilmDto.class));
    }

    //For Validation Rest Client Response Code
    public void verifyResponseCode(int expectedResponseCode){
        getTest().info("Expected Status Code: "+expectedResponseCode +" Actual Status Code: "+ httpRestClient.getStatusCode());
        Assertions.assertEquals(expectedResponseCode,httpRestClient.getStatusCode());
    }

    //Returning custom response and getting Json Objects and Json Array mapping to Film Data Object
    public List<FilmDto> getSearchedFilmsInfo() throws JsonProcessingException
    {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        JsonArray results = jsonObject.getAsJsonArray("results");
        getTest().info("Getting <b>results</b> JsonObject to Film Data for validations..");
        for(JsonElement object : results){
            films.add(mapFromJson(object.toString(),FilmDto.class));
        }
        return films;
    }

    //Getting Value of custom Json Object
    public String getJsonObjectValue(String objectName) {
        JsonObject jsonObject = getJsonObject(httpRestClient);
        return jsonObject.get(objectName).toString();
    }
}
