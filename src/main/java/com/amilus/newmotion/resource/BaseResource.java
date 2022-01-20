package com.amilus.newmotion.resource;

import com.amilus.newmotion.client.HttpRestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * The BaseResource is an abstract class holds the common object mapper methods that has been used by other resource classes
 */

public abstract class BaseResource
{
    /**
     * This is generic method of parsing JSON string into single JAVA dto objects
     */
    protected final <T> T mapFromJson(String json, Class<T> className) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructType(className));
    }

    /**
     * This is generic method of parsing JsonObjects from HttpRestClient
     */
    @SneakyThrows
    public JsonObject getJsonObject(HttpRestClient httpRestClient){
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(httpRestClient.getResponseBody().asInputStream()));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        JsonObject jsonObject = deserialize(stringBuilder.toString());
        return jsonObject;
    }

    /**
     * This is generic method of deserializing of JsonObject
     */
    public JsonObject deserialize(String json) {
        Gson gson = new Gson();
        JsonObject jsonClass = gson.fromJson(json, JsonObject.class);
        return jsonClass;
    }
}
