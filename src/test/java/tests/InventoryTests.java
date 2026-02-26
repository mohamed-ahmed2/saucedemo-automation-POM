package tests;

import base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

@Slf4j
public class InventoryTests extends BaseTest {

    @Test
    public  void verifySortingByPrice(){
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.validlogin("standard_user","secret_sauce");
        inventoryPage.sort("lohi");
        String actualSortingMethod = inventoryPage.getCurrentSortingMethod();
        Assert.assertEquals(actualSortingMethod,"Price (low to high)");
    }

    @Test
    public void checkSortingByPriceHighToLow(){
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.validlogin("standard_user","secret_sauce");
        inventoryPage.sort("hilo");
        boolean sortingHighToLow = inventoryPage.compare("hilo");
        Assert.assertTrue(sortingHighToLow);
    }

    @Test
    public void checkSortingByPriceLowToHigh(){
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.validlogin("standard_user","secret_sauce");
        inventoryPage.sort("lohi");
        boolean sortingLowToHigh = inventoryPage.compare("lohi");
        Assert.assertTrue(sortingLowToHigh);
    }
    @Test
    public void verifyAddSingleProductToCart(){
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.validlogin("standard_user","secret_sauce");
        String productName = "Sauce Labs Backpack";
        inventoryPage.clickAddToCart(productName);
        String itemsIntheCart = inventoryPage.getcurrentCartNumber();
        Assert.assertEquals(itemsIntheCart,"1");
        CartPage cartPage = inventoryPage.clickOnShoppingCart();
        String actualItemName = cartPage.getItems();
        Assert.assertEquals(actualItemName,productName);
    }
}
