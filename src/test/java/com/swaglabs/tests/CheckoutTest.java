package com.swaglabs.tests;

import com.swaglabs.base.BaseTest;
import com.swaglabs.data.TestData;
import com.swaglabs.pages.CartPage;
import com.swaglabs.pages.CheckoutPage;
import com.swaglabs.pages.HomePage;
import com.swaglabs.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Swag Labs")
@Feature("Checkout Functionality")
@Severity(SeverityLevel.CRITICAL)
public class CheckoutTest extends BaseTest {

    @Test
    @Story("User completes the checkout process")
    @DisplayName("Successful checkout flow with valid details")
    @Description("Verify that a user can add an item, fill out checkout info, and place the order")
    public void successfulCheckoutTest() {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        // Step 2: Add product to cart
        HomePage homePage = new HomePage(driver);
        homePage.addFirstProductToCart();
        homePage.goToCart();

        // Step 3: Go to checkout
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        // Step 4: Fill in checkout info
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutInformation(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                TestData.POSTAL_CODE
        );
        checkoutPage.clickContinue();

        // Step 5: Confirm and finish
        checkoutPage.clickFinish();

        // Step 6: Verify order confirmation
        assertTrue(checkoutPage.isOrderConfirmed(), "Order confirmation should be displayed.");
    }

    @Test
    @Story("User submits checkout form without first name")
    @DisplayName("Blank first name should trigger error")
    @Description("Verify form validation when first name is missing")
    public void checkoutWithoutFirstName() {
        loginAndStartCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutInformation(
                "", TestData.LAST_NAME, TestData.POSTAL_CODE);
        checkoutPage.clickContinue();

        String error = checkoutPage.getErrorMessage();
        assertTrue(error.toLowerCase().contains("first name"),
                "Error should indicate missing first name.");
    }

    @Test
    @Story("User submits empty checkout form")
    @DisplayName("All blank fields should trigger error")
    @Description("Verify validation when no data is entered in the form")
    public void checkoutWithAllFieldsBlank() {
        loginAndStartCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutInformation("", "", "");
        checkoutPage.clickContinue();

        String error = checkoutPage.getErrorMessage();
        assertFalse(error.isEmpty(), "Error message should appear when all fields are blank.");
    }

    @Test
    @Story("User submits checkout form without last name")
    @DisplayName("Blank last name should trigger error")
    @Description("Verify form validation when last name is missing")
    public void checkoutWithoutLastName() {
        loginAndStartCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutInformation(
                TestData.FIRST_NAME, "", TestData.POSTAL_CODE);
        checkoutPage.clickContinue();

        String error = checkoutPage.getErrorMessage();
        assertTrue(error.toLowerCase().contains("last name"),
                "Error should indicate missing last name.");
    }

    @Test
    @Story("User submits checkout form without postal code")
    @DisplayName("Blank postal code should trigger error")
    @Description("Verify form validation when postal code is missing")
    public void checkoutWithoutPostalCode() {
        loginAndStartCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutInformation(
                TestData.FIRST_NAME, TestData.LAST_NAME, "");
        checkoutPage.clickContinue();

        String error = checkoutPage.getErrorMessage();
        assertTrue(error.toLowerCase().contains("postal code"),
                "Error should indicate missing postal code.");
    }


    private void loginAndStartCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        HomePage homePage = new HomePage(driver);
        homePage.addFirstProductToCart();
        homePage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();
    }
}
