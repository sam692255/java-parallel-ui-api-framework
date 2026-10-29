package com.framework.tests;

import com.framework.driver.DriverFactory;
import com.framework.listeners.RetryAnalyzer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ParallelSmokeTest {

    @BeforeMethod
    public void setUp() {
        boolean isRemote = Boolean.parseBoolean(System.getProperty("remote", "false"));
        String gridUrl = System.getProperty("gridUrl", "http://localhost:4444/wd/hub");
        DriverFactory.initDriver(isRemote, gridUrl);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify thread-safe parallel UI execution")
    public void verifyWebPageTitleInParallel() {
        DriverFactory.getDriver().get("https://example.com");
        String title = DriverFactory.getDriver().getTitle();
        Assert.assertEquals(title, "Example Domain");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class, description = "Verify REST API status and payload contract")
    public void verifyHealthCheckEndpoint() {
        Response response = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        Assert.assertEquals(response.jsonPath().getInt("id"), 1);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}