package com.swaglabs.pages;

import com.swaglabs.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    // Locators
    private final By cartItem = By.className("cart_item");
    private final By removeButton = By.xpath("//button[contains(text(),'Remove')]");
    private final By checkoutButton = By.id("checkout");

    // Constructor
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Validations
    public boolean isProductInCart(String productName) {
        By productLocator = By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");
        return isElementDisplayed(productLocator);
    }

    public boolean isCartNotEmpty() {
        return isElementDisplayed(cartItem);
    }

    public boolean isCheckoutButtonEnabled() {
        return driver.findElement(checkoutButton).isEnabled();
    }

    // Actions
    public void removeProduct(String productName) {
        By removeButtonForProduct = By.xpath(
                "//div[@class='cart_item'][.//div[@class='inventory_item_name' and text()='" + productName + "']]//button[contains(text(),'Remove')]");
        click(removeButtonForProduct);
    }

    public void clickCheckout() {
        click(checkoutButton);
    }
}
