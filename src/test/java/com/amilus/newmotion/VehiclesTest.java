package com.amilus.newmotion;


import com.amilus.newmotion.base.TestBase;
import com.amilus.newmotion.resource.VehiclesResource;
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
public class VehiclesTest
{
    @Autowired
    VehiclesResource vehiclesResource;

    @SneakyThrows
    @ParameterizedTest
    @Description("Get Vehicle Info by VehicleId using Data-Driven Method")
    @ValueSource(strings = {"1","4"})
    void testGetVehicleInfoById(String parameter)
    {
        getTest().getModel().setDescription("Get Vehicle Informations by VehicleId: <b>"+parameter+"</b> using Data-Driven Method");
        getTest().assignCategory("VehiclesTest");
        getTest().info(MarkupHelper.createLabel("Now getting Vehicle information", ExtentColor.INDIGO));
        vehiclesResource.callVehiclesEndpoint(Method.GET,parameter);
        vehiclesResource.verifyResponseCode(200);
        //Validating Planets Name is Null or Not
        assertNotNull(vehiclesResource.getVehicleInfo().getName(),"Vehicle Name cannot be NULL !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Total Count of Vehicles and validate")
    void testGetTotalCountOfVehicles()
    {
        getTest().getModel().setDescription("Get Total Count of Vehicles and validate");
        getTest().assignCategory("VehiclesTest");
        vehiclesResource.callVehiclesEndpoint(Method.GET,null);
        vehiclesResource.verifyResponseCode(200);
        //Validating Total Vehicles Count
        String totalCount = vehiclesResource.getJsonObjectValue("count");
        getTest().info("Total Count: <b>"+totalCount+"</b>");
        assertEquals("39", totalCount,"Validation of Total Vehicles Count Failed !!");
    }

    @SneakyThrows
    @Test
    @Description("Get Vehicles Resource Schema")
    void testVehiclesResourceSchema()
    {
        getTest().getModel().setDescription("Getting Vehicles Resource Schema");
        getTest().assignCategory("VehiclesTest");
        vehiclesResource.callVehiclesEndpoint(Method.GET,"schema");
        getTest().info(MarkupHelper.createLabel("Getting Vehicles Resource Schema", ExtentColor.BLUE));
        vehiclesResource.verifyResponseCode(200);
    }

    @SneakyThrows
    @Test
    @Description("Search Existing Vehicles By Name in Vehicles Resource")
    void testSearchExistingVehiclesByName(){
        getTest().getModel().setDescription("Search Existing Vehicles By Name in Vehicles Resource");
        getTest().assignCategory("VehiclesTest");
        getTest().info("Search Existing Vehicles By Name: AT-RT");
        vehiclesResource.callVehiclesEndpoint(Method.GET,"?search=AT-RT");
        vehiclesResource.verifyResponseCode(200);
        assertEquals(vehiclesResource.getSearchedVehiclesInfo().get(0).getName(),"AT-RT","Searched Vehicles NOT Found !!");
    }

    @SneakyThrows
    @Test
    @Description("Search Existing Vehicles By Model in Vehicles Resource")
    void testSearchExistingVehiclesByModel(){
        getTest().getModel().setDescription("Search Existing Vehicles By Model in Vehicles Resource");
        getTest().assignCategory("VehiclesTest");
        getTest().info("Search Existing Vehicles By Model: Storm IV Twin-Pod");
        vehiclesResource.callVehiclesEndpoint(Method.GET,"?search=Storm IV Twin-Pod");
        vehiclesResource.verifyResponseCode(200);
        assertEquals(vehiclesResource.getSearchedVehiclesInfo().get(0).getModel(),"Storm IV Twin-Pod","Searched Vehicles NOT Found !!");
    }
}
