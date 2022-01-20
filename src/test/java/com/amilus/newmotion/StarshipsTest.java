package com.amilus.newmotion;


import com.amilus.newmotion.base.TestBase;
import com.amilus.newmotion.resource.StarshipsResource;
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
public class StarshipsTest
{
    @Autowired
    StarshipsResource starshipsResource;

    @SneakyThrows
    @ParameterizedTest
    @Description("Get Starships Info by StarshipId using Data-Driven Method")
    @ValueSource(strings = {"1","2"})
    void testGetStarshipInfoById(String parameter)
    {
        getTest().getModel().setDescription("Get Starships Informations by StarshipId: <b>"+parameter+"</b> using Data-Driven Method");
        getTest().assignCategory("StarshipsTest");
        getTest().info(MarkupHelper.createLabel("Now getting Starships information", ExtentColor.INDIGO));
        starshipsResource.callStarshipsEndpoint(Method.GET,parameter);
        starshipsResource.verifyResponseCode(200);
        //Validating Planets Name is Null or Not
        assertNotNull(starshipsResource.getStarshipInfo().getName(),"Starship Name cannot be NULL !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Total Count of Starships and validate")
    void testGetTotalCountOfStarships()
    {
        getTest().getModel().setDescription("Get Total Count of Starships and validate");
        getTest().assignCategory("StarshipsTest");
        starshipsResource.callStarshipsEndpoint(Method.GET,null);
        starshipsResource.verifyResponseCode(200);
        //Validating Total Planets Count
        String totalCount = starshipsResource.getJsonObjectValue("count");
        getTest().info("Total Count: <b>"+totalCount+"</b>");
        assertEquals("36", totalCount,"Validation of Total Starships Count Failed !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Starships Resource Schema")
    void testStarshipsResourceSchema()
    {
        getTest().getModel().setDescription("Getting Starships Resource Schema");
        getTest().assignCategory("StarshipsTest");
        starshipsResource.callStarshipsEndpoint(Method.GET,"schema");
        getTest().info(MarkupHelper.createLabel("Getting Starships Resource Schema", ExtentColor.BLUE));
        starshipsResource.verifyResponseCode(200);
    }

    @SneakyThrows
    @Test
    @Description("Search Existing Starship By Name in Starships Resource")
    void testSearchExistingStarshipByName(){
        getTest().getModel().setDescription("Search Existing Starship By Name in Starships Resource");
        getTest().assignCategory("StarshipsTest");
        starshipsResource.callStarshipsEndpoint(Method.GET,"?search=Death Star");
        starshipsResource.verifyResponseCode(200);
        assertEquals(starshipsResource.getSearchedStarshipsInfo().get(0).getName(),"Death Star");
    }

    @SneakyThrows
    @Test
    @Description("Search Existing Starship By Model in Starships Resource")
    void testSearchExistingStarshipByModel(){
        getTest().getModel().setDescription("Search Existing Starship By Model in Starships Resource");
        getTest().assignCategory("StarshipsTest");
        starshipsResource.callStarshipsEndpoint(Method.GET,"?search=YT-1300");
        starshipsResource.verifyResponseCode(200);
        assertEquals(starshipsResource.getSearchedStarshipsInfo().get(0).getModel(),"YT-1300 light freighter","Searched Starship NOT Found !!");
    }
}
