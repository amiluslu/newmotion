package com.amilus.newmotion;


import com.amilus.newmotion.base.TestBase;
import com.amilus.newmotion.resource.PeopleResource;
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
public class PeopleTest
{
    @Autowired
    PeopleResource peopleResource;

    @SneakyThrows
    @ParameterizedTest
    @Description("Get People Info by PeopleId using Data-Driven Method")
    @ValueSource(strings = {"1","2"})
    void testGetPeopleInfoById(String parameter)
    {
        getTest().getModel().setDescription("Get People Informations by PeopleId: <b>"+parameter+"</b> using Data-Driven Method");
        getTest().assignCategory("PeopleTest");
        getTest().info(MarkupHelper.createLabel("Now getting People information", ExtentColor.INDIGO));
        peopleResource.callPeopleEndpoint(Method.GET,parameter);
        peopleResource.verifyResponseCode(200);
        //Validating People Name is Null or Not
        assertNotNull(peopleResource.getPeopleInfo().getName(),"People Name cannot be NULL !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Total Count of People and validate")
    void testGetTotalCountOfPeople()
    {
        getTest().getModel().setDescription("Get Total Count of People and validate");
        getTest().assignCategory("PeopleTest");
        peopleResource.callPeopleEndpoint(Method.GET,null);
        peopleResource.verifyResponseCode(200);
        //Validating Total People Count
        String totalCount = peopleResource.getJsonObjectValue("count");
        getTest().info("Total Count: <b>"+totalCount+"</b>");
        assertEquals("82", totalCount,"Validation of Total People Count Failed !!");
    }

    @SneakyThrows
    @Test
    @Description("Get People Resource Schema")
    void testPeopleResourceSchema()
    {
        getTest().getModel().setDescription("Getting PeopleResource Schema");
        getTest().assignCategory("PeopleTest");
        peopleResource.callPeopleEndpoint(Method.GET,"schema");
        getTest().info(MarkupHelper.createLabel("Getting People Resource Schema", ExtentColor.BLUE));
        peopleResource.verifyResponseCode(200);
    }

    @SneakyThrows
    @Test
    @Description("Search Existing Person By Name in People Resource")
    void testSearchExistingPersonByName(){
        getTest().getModel().setDescription("Search Existing Person By Name in People Resource");
        getTest().assignCategory("PeopleTest");
        peopleResource.callPeopleEndpoint(Method.GET,"?search=tion");
        peopleResource.verifyResponseCode(200);
        assertEquals(peopleResource.getSearchedPeopleInfo().get(0).getName(),"Tion Medon", "Searched Person NOT Found !!");
    }

    @SneakyThrows
    @Test
    @Description("Search Non-Existing Person By Name in People Resource")
    void testSearchNonExistingPersonByName(){
        getTest().getModel().setDescription("Search Non-Existing Person By Name in People Resource");
        getTest().assignCategory("PeopleTest");
        peopleResource.callPeopleEndpoint(Method.GET,"?search=Amil");
        getTest().info(MarkupHelper.createLabel("Searched Person NOT Found.. Response code must be 404 !!", ExtentColor.RED));
        peopleResource.verifyResponseCode(404);
    }
}
