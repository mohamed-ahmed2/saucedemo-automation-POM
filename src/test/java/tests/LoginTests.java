package tests;
import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import lombok.Data;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.JsonReader;

import java.io.IOException;

public class LoginTests extends BaseTest{
    @Test
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);

        String user = JsonReader.getTestData("validUser", "username");
        String pass = JsonReader.getTestData("validUser", "password");

        loginPage.validlogin(user, pass);
        //loginPage.login("standard_user","secret_sauce");
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals(actualUrl, expectedUrl, "Login has failed");
    }

@Test
    public void testFailedLogin(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user","Test");
        String error_message = loginPage.geterror_message();
        String expected_error = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(error_message, expected_error);
    }

    @Test
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.validlogin("locked_out_user", "secret_sauce");

        String expectedError = "Epic sadface: Sorry, this user has been locked out.";
        Assert.assertEquals(loginPage.geterror_message(), expectedError);
    }

    @DataProvider(name = "validLoginData")
    public Object[][] getValidLoginData() {
        return new Object[][] {
                {"standard_user", "secret_sauce"},
                {"problem_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"}
        };
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        return new Object[][] {
                {"locked_out_user", "secret_sauce", "locked"},
                {"problem_user", "TEST", " Username and password do not match any user"},
                {"performance_glitch_user", "sauce", " Username and password do not match any user"}
        };
    }
    @Test(dataProvider = "validLoginData")
    public void testValidLoginScenarios(String user, String pass) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.validlogin(user, pass);

        Assert.assertTrue(loginPage.getUrl().contains("inventory.html"));


    }

    @Test(dataProvider = "invalidLoginData")
    public void verifyInvalidLoginErrorMessage(String user,String pass,String errorType){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.validlogin(user, pass);
        // check error type
        Assert.assertTrue(loginPage.geterror_message().contains(errorType));

    }

    @DataProvider(name="loginDataProvider")
    public Object[][] loginData() throws IOException, ParseException {
        return JsonReader.getJsonArrayData("testData.Json","usersList");
    }
    @Test(dataProvider = "loginDataProvider")
    public void loginWithMultipleUsers(JSONObject userData) {
        // Read data from json file
        String user = (String) userData.get("username");
        String pass = (String) userData.get("password");
        // optional line
        ExtentTest node = test.createNode("Login Test for User: " + user);
        try {
            InventoryPage inventoryPage = loginPage.validlogin(user, pass);


            Assert.assertTrue(inventoryPage.isProductsTitleDisplayed());
            node.pass("Login successful for user: " + user);
        }
        catch (AssertionError | Exception e) {
            node.fail("Login failed for user: " + user + " | Error: " + e.getMessage());
            throw e;
        }
    }
}
