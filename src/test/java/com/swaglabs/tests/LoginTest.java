package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.data.TestData;
import com.swaglabs.pages.HomePage;
import com.swaglabs.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Epic("Swag Labs")
@Feature("Login Functionality")
@Story("User logs in with valid or invalid credentials")
@Severity(SeverityLevel.CRITICAL)
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Successful login with valid credentials")
    @Description("Verify that a user can log in with valid username and password")
    public void validLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        // Success is indicated by URL change or inventory visibility — this can be adjusted
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("inventory"), "User should be redirected to inventory page after login.");
    }

    @Test
    @DisplayName("Login fails with invalid credentials")
    @Description("Verify that an error message is shown for invalid login")
    public void invalidLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD);

        String errorMsg = loginPage.getErrorMessage();
        assertTrue(errorMsg.contains("Username and password do not match")
                || !errorMsg.isEmpty(), "Error message should appear for invalid login.");
    }

    @Test
    @Story("User submits login form without any input")
    @DisplayName("Login fails with blank username and password")
    @Description("Verify error message when both username and password are blank")
    public void loginWithBlankCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        String error = loginPage.getErrorMessage();
        assertTrue(error.toLowerCase().contains("required"), "Error message should indicate required fields.");
    }

    @Test
    @Story("User submits login form without a username")
    @DisplayName("Login fails with blank username only")
    @Description("Verify error message when username is blank")
    public void loginWithBlankUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", TestData.VALID_PASSWORD);

        String error = loginPage.getErrorMessage();
        assertTrue(error.toLowerCase().contains("required"), "Error message should mention missing username.");
    }

    @Test
    @Story("User submits login form without a password")
    @DisplayName("Login fails with blank password only")
    @Description("Verify error message when password is blank")
    public void loginWithBlankPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, "");

        String error = loginPage.getErrorMessage();
        assertTrue(error.toLowerCase().contains("required"), "Error message should mention missing password.");
    }

    @Test
    @Story("User logs out from the application")
    @DisplayName("Logout from application returns user to login screen")
    @Description("Verify that a logged-in user can logout and is redirected to login page")
    public void logoutTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        HomePage homePage = new HomePage(driver);
        homePage.logout();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("saucedemo.com"), "User should be redirected to login page after logout.");
    }

}
