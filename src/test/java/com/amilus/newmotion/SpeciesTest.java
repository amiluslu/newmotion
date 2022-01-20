package com.amilus.newmotion;


import com.amilus.newmotion.base.TestBase;
import com.amilus.newmotion.resource.SpeciesResource;
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
public class SpeciesTest
{
    @Autowired
    SpeciesResource speciesResource;

    @SneakyThrows
    @ParameterizedTest
    @Description("Get Species Info by SpeciesId using Data-Driven Method")
    @ValueSource(strings = {"1","3"})
    void testGetSpeciesInfoById(String parameter)
    {
        getTest().getModel().setDescription("Get Species Informations by SpeciesId: <b>"+parameter+"</b> using Data-Driven Method");
        getTest().assignCategory("SpeciesTest");
        getTest().info(MarkupHelper.createLabel("Now getting Species information", ExtentColor.INDIGO));
        speciesResource.callSpeciesEndpoint(Method.GET,parameter);
        speciesResource.verifyResponseCode(200);
        //Validating Planets Name is Null or Not
        assertNotNull(speciesResource.getSpeciesInfo().getName(),"Species Name cannot be NULL !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Total Count of Species and validate")
    void testGetTotalCountOfSpecies()
    {
        getTest().getModel().setDescription("Get Total Count of Species and validate");
        getTest().assignCategory("SpeciesTest");
        speciesResource.callSpeciesEndpoint(Method.GET,null);
        speciesResource.verifyResponseCode(200);
        //Validating Total Planets Count
        String totalCount = speciesResource.getJsonObjectValue("count");
        getTest().info("Total Count: <b>"+totalCount+"</b>");
        assertEquals("37", totalCount,"Validation of Total Species Count Failed !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Species Resource Schema")
    void testSpeciesResourceSchema()
    {
        getTest().getModel().setDescription("Getting Planets Resource Schema");
        getTest().assignCategory("SpeciesTest");
        speciesResource.callSpeciesEndpoint(Method.GET,"schema");
        getTest().info(MarkupHelper.createLabel("Getting Planets Resource Schema", ExtentColor.BLUE));
        speciesResource.verifyResponseCode(200);
    }

    @SneakyThrows
    @Test
    @Description("Search Existing Species By Name in Species Resource")
    void testSearchExistingSpeciesByName(){
        getTest().getModel().setDescription("Search Existing Species By Name in Species Resource");
        getTest().assignCategory("SpeciesTest");
        speciesResource.callSpeciesEndpoint(Method.GET,"?search=Ewok");
        speciesResource.verifyResponseCode(200);
        assertEquals(speciesResource.getSearchedSpeciesInfo().get(0).getDesignation(),"sentient","Searched Species NOT Found !!");
    }

    @SneakyThrows
    @Test
    @Description("Search Non-Existing Species By Name in Species Resource")
    void testSearchNonExistingSpeciesByName(){
        getTest().getModel().setDescription("Search Non-Existing Species By Name in Species Resource");
        getTest().assignCategory("SpeciesTest");
        speciesResource.callSpeciesEndpoint(Method.GET,"?search=amil");
        getTest().info(MarkupHelper.createLabel("Searched Species NOT Found.. Response code must be 404 !!", ExtentColor.RED));
        speciesResource.verifyResponseCode(404);
    }
}
