package com.amilus.newmotion;


import com.amilus.newmotion.base.TestBase;
import com.amilus.newmotion.resource.FilmsResource;
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
public class FilmsTest
{
    @Autowired
    FilmsResource filmsResource;

    @SneakyThrows
    @ParameterizedTest
    @Description("Get Films Info by FilmId using Data-Driven Method")
    @ValueSource(strings = {"1","2"})
    void testGetFilmInfoById(String parameter)
    {
        getTest().getModel().setDescription("Get Films Informations by FilmId: <b>"+parameter+"</b> using Data-Driven Method");
        getTest().assignCategory("FilmsTest");
        getTest().info(MarkupHelper.createLabel("Now getting Film information", ExtentColor.INDIGO));
        filmsResource.callFilmEndpoint(Method.GET,parameter);
        filmsResource.verifyResponseCode(200);
        //Validating Film Title is Null or Not
        assertNotNull(filmsResource.getFilmInfo().getTitle(),"Validation of Film Title Failed!!");
    }

    @SneakyThrows
    @Test
    @Description("Get Total Count of Films and validate")
    void testGetTotalCountOfFilms()
    {
        getTest().getModel().setDescription("Get Total Count of Films and validate");
        getTest().assignCategory("FilmsTest");
        filmsResource.callFilmEndpoint(Method.GET,null);
        filmsResource.verifyResponseCode(200);
        //Validating Total Film Count
        assertEquals("7", filmsResource.getJsonObjectValue("count"),"Validation of Total Films Count Failed !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Films Resource Schema")
    void testFilmsResourceSchema()
    {
        getTest().getModel().setDescription("Getting FilmsResource Schema");
        getTest().assignCategory("FilmsTest");
        filmsResource.callFilmEndpoint(Method.GET,"schema/");
        getTest().info(MarkupHelper.createLabel("Getting Films Resource Schema", ExtentColor.BLUE));
        filmsResource.verifyResponseCode(200);
    }

    @SneakyThrows
    @Test
    @Description("Search Existing Film By Title in Films Resource")
    void testSearchExistingFilmByTitle(){
        getTest().getModel().setDescription("Search Existing Film By Title in Films Resource");
        getTest().assignCategory("FilmsTest");
        filmsResource.callFilmEndpoint(Method.GET,"?search=Attack of the Clones");
        filmsResource.verifyResponseCode(200);
        assertEquals(filmsResource.getSearchedFilmsInfo().get(0).getTitle(),"Attack of the Clones", "Searched Film NOT Found !!");
    }

    @SneakyThrows
    @Test
    @Description("Search Non-Existing Film By Title in Films Resource")
    void testSearchNonExistingFilmByTitle(){
        getTest().getModel().setDescription("Search Non-Existing Film By Title in Films Resource");
        getTest().assignCategory("FilmsTest");
        filmsResource.callFilmEndpoint(Method.GET,"?search=Attack of the Amil");
        getTest().info(MarkupHelper.createLabel("Searched Film NOT Found.. Response code must be 404 !!", ExtentColor.RED));
        filmsResource.verifyResponseCode(404);
    }
}
