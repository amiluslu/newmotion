package com.amilus.newmotion.client;

import com.amilus.newmotion.report.ExtentTestManager;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.amilus.newmotion.report.ExtentTestManager.getTest;

/**
 * HttpRestClient --> RestAssured base configurations and related methods with HttpClient
 */
@Component
public class HttpRestClient
{
    @Value("${api.url}")
    private String apiUrl;

    private RequestSpecification requestSpecification;
    private ResponseBody responseBody;
    private Integer statusCode;
    private Response response;

    //Initialize RestAssured
    public void initRestAPI() {
        RestAssured.baseURI = apiUrl;
        requestSpecification = RestAssured.given();
        getTest().info("Api URL: "+apiUrl);
    }

    //Sending HTTP Requests
    public void sendHttpRequest(Method method, String endpoint) {
        getTest().info("Sending Http Request.. Method: <b>"+method+" </b> EndPoint: <b>"+endpoint+"</b>");
        requestSpecification.header("Content-Type","application/json");
        response = requestSpecification.request(method, endpoint);
        setResponseBody(response.getBody());
        setStatusCode(response.getStatusCode());
    }

    //For post a json object
    public void setBody(Object body) {
        requestSpecification.body(body);
    }

    //Getter for response body
    public ResponseBody getResponseBody() {
        return responseBody;
    }

    //Setter for response body
    private void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    //Getter for response status code
    public Integer getStatusCode() {
        return statusCode;
    }

    //Setter for response status code
    private void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
