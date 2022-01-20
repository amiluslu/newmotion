package com.amilus.newmotion;


import com.amilus.newmotion.base.TestBase;
import com.amilus.newmotion.resource.PlanetsResource;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.http.Method;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import static com.amilus.newmotion.report.ExtentTestManager.getTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(TestBase.class)
public class PlanetsTest
{
    @Autowired
    PlanetsResource planetsResource;

    @SneakyThrows
    @ParameterizedTest
    @Description("Get Planets Info by PlanetId using Data-Driven Method")
    @ValueSource(strings = {"58","34"})
    void testGetPlanetsInfoById(String parameter)
    {
        getTest().getModel().setDescription("Get Planets Informations by PlanetId: <b>"+parameter+"</b> using Data-Driven Method");
        getTest().assignCategory("PlanetsTest");
        getTest().info(MarkupHelper.createLabel("Now getting Planets information", ExtentColor.INDIGO));
        planetsResource.callPlanetsEndpoint(Method.GET,parameter);
        planetsResource.verifyResponseCode(200);
        //Validating Planets Name is Null or Not
        assertNotNull(planetsResource.getPlanetInfo().getName(),"Planets Name cannot be NULL !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Total Count of Planets and verify")
    void testGetTotalCountOfPlanets()
    {
        getTest().getModel().setDescription("Get Total Count of Planets and validate");
        getTest().assignCategory("PlanetsTest");
        planetsResource.callPlanetsEndpoint(Method.GET,null);
        planetsResource.verifyResponseCode(200);
        //Validating Total Planets Count
        String totalCount = planetsResource.getJsonObjectValue("count");
        getTest().info("Total Count: <b>"+totalCount+"</b>");
        assertEquals("60", totalCount,"Validation of Total Planets Count Failed !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Planets Resource Schema")
    void testPlanetsResourceSchema()
    {
        getTest().getModel().setDescription("Getting Planets Resource Schema");
        getTest().assignCategory("PlanetsTest");
        planetsResource.callPlanetsEndpoint(Method.GET,"schema");
        getTest().info(MarkupHelper.createLabel("Getting Planets Resource Schema", ExtentColor.BLUE));
        planetsResource.verifyResponseCode(200);
    }

    @SneakyThrows
    @Test
    @Description("Search Existing Planets By Name in Planets Resource")
    void testSearchExistingPlanetsByName(){
        getTest().getModel().setDescription("Search Existing Planets By Name in Planets Resource");
        getTest().assignCategory("PlanetsTest");
        planetsResource.callPlanetsEndpoint(Method.GET,"?search=tatooine");
        planetsResource.verifyResponseCode(200);
        assertEquals(planetsResource.getSearchedPlanetsInfo().get(0).getClimate(),"arid","Searched Planets NOT Found !!");
    }

    @SneakyThrows
    @Test
    @Description("Search Non-Existing Planets By Name in Planets Resource")
    void testSearchNonExistingPlanetsByName(){
        getTest().getModel().setDescription("Search Non-Existing Planets By Name in Planets Resource");
        getTest().assignCategory("PlanetsTest");
        planetsResource.callPlanetsEndpoint(Method.GET,"?search=amil");
        getTest().info(MarkupHelper.createLabel("Searched Planets NOT Found.. Response code must be 404 !!", ExtentColor.RED));
        planetsResource.verifyResponseCode(404);
    }
}
