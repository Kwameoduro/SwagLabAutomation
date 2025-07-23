package com.swaglabs.pages;

import com.swaglabs.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    // Locators
    private final By inventoryContainer = By.id("inventory_container");
    private final By productTitle = By.className("inventory_item_name");
    private final By productPrice = By.className("inventory_item_price");
    private final By addToCartButton = By.xpath("//button[contains(text(),'Add to cart')]");
    private final By shoppingCartIcon = By.className("shopping_cart_link");

    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Validations
    public boolean isInventoryLoaded() {
        return isElementDisplayed(inventoryContainer);
    }

    public boolean isProductDisplayed(String productName) {
        By productNameLocator = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");
        return isElementDisplayed(productNameLocator);
    }

    // Actions
    public void addFirstProductToCart() {
        click(addToCartButton);
    }

    public void clickProductByName(String productName) {
        By productNameLocator = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");
        click(productNameLocator);
    }

    public void goToCart() {
        click(shoppingCartIcon);
    }

    public void logout() {
        click(menuButton);
        waitForVisibility(logoutLink);
        click(logoutLink);
    }
}
