package com.swaglabs.pages;

import com.swaglabs.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    // Locators for Step 1: Checkout Information
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");

    // Locators for Step 2: Overview
    private final By finishButton = By.id("finish");

    // Locators for Confirmation
    private final By confirmationHeader = By.className("complete-header");
    private final By totalAmount = By.className("summary_total_label");

    // Error Message (shown when required fields are blank or invalid)
    private final By errorMessage = By.cssSelector("h3[data-test='error']");
    private final By cartItemsInOverview = By.className("cart_item");


    // Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // Step 1: Enter Checkout Info
    public void enterCheckoutInformation(String firstName, String lastName, String zipCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, zipCode);
    }

    public void clickContinue() {
        click(continueButton);
    }

    // Step 2: Overview
    public void clickFinish() {
        click(finishButton);
    }

    // Confirmation Page
    public boolean isOrderConfirmed() {
        return isElementDisplayed(confirmationHeader);
    }

    public String getConfirmationMessage() {
        return getText(confirmationHeader);
    }

    public String getTotalAmountWithTax() {
        return getText(totalAmount);
    }

    // Error Handling (for negative tests)
    public boolean isErrorDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return isElementDisplayed(errorMessage) ? getText(errorMessage) : "";
    }

    public boolean isAnyItemInCheckoutOverview() {
        return driver.findElements(cartItemsInOverview).size() > 0;
    }

}
