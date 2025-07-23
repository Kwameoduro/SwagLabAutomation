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

import static org.junit.jupiter.api.Assertions.*;

@Epic("Swag Labs")
@Feature("Shopping Cart Functionality")
@Severity(SeverityLevel.NORMAL)
public class CartTest extends BaseTest {

    @Test
    @Story("User adds a product to cart")
    @DisplayName("Add product to cart and verify cart count")
    @Description("Ensure that adding a product updates the cart and the product is listed in the cart page")
    public void addProductToCartTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        HomePage homePage = new HomePage(driver);
        homePage.addFirstProductToCart();
        homePage.goToCart();

        CartPage cartPage = new CartPage(driver);
        assertTrue(cartPage.isCartNotEmpty(), "Cart should contain at least one item.");
    }

    @Test
    @Story("User removes a product from cart")
    @DisplayName("Remove product from cart and verify it disappears")
    @Description("Ensure that removing a product updates the cart immediately")
    public void removeProductFromCartTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        HomePage homePage = new HomePage(driver);
        homePage.addFirstProductToCart();
        homePage.goToCart();

        CartPage cartPage = new CartPage(driver);
        assertTrue(cartPage.isCartNotEmpty(), "Cart should initially contain the product.");

        // Use product name that appears first — adjust if needed for specific product logic
        cartPage.removeProduct("Sauce Labs Backpack");

        // Optional: assert the item is gone
        assertFalse(cartPage.isProductInCart("Sauce Labs Backpack"),
                "Product should be removed from cart.");
    }

    @Test
    @Story("User attempts to checkout with an empty cart")
    @DisplayName("Verify empty cart leads to empty checkout summary")
    @Description("Even if checkout is allowed with an empty cart, the summary page should show no items")
    public void checkoutWithEmptyCartShowsNoItems() {
        // Login and go to cart directly
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        HomePage homePage = new HomePage(driver);
        homePage.goToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        // Validate that no items are shown in the overview
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        boolean isAnyItemPresent = checkoutPage.isAnyItemInCheckoutOverview();

        assertFalse(isAnyItemPresent, "No items should be listed on checkout overview for an empty cart.");
    }


}
