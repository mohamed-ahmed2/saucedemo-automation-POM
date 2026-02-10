package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class CheckOutTests extends BaseTest {

    @Test
    public void verifyCheckOutSingleProduct() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.validlogin("standard_user", "secret_sauce");
        String productName = "Sauce Labs Backpack";
        inventoryPage.clickAddToCart(productName);
        String itemsIntheCart = inventoryPage.getcurrentCartNumber();
        Assert.assertEquals(itemsIntheCart, "1","number of items in the cart doesn't match the expected number");
        CartPage cartPage = inventoryPage.clickOnShoppingCart();
        String actualtItemName = cartPage.getItems();
        Assert.assertEquals(actualtItemName, productName,"product name can't be found in the cart page");
       CheckOutPage checkOutPage =  cartPage.clickOnCheckOut();
       CheckOutOverviewPage checkOutOverviewPage = checkOutPage.fillOutCheckOutFields("Testing user","Testing lastName","PP1");
       double expectedTotal = checkOutOverviewPage.getTotal();
       double visibleTotal =  checkOutOverviewPage.getVisibleTotal();
       Assert.assertEquals(visibleTotal,expectedTotal,"total is wrong");
       ConfirmationPage confirmationPage = checkOutOverviewPage.clickFinishButton();
       Assert.assertEquals(confirmationPage.getConfrimation(),"Thank you for your order!","Confirmation message not displayed");
    }

@Test
    public void verifyErrorMessage(){
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.validlogin("standard_user","secret_sauce");
         inventoryPage.clickAddToCart("Sauce Labs Backpack");
         CartPage cartPage = inventoryPage.clickOnShoppingCart();
         CheckOutPage checkOutPage = cartPage.clickOnCheckOut();
         checkOutPage.fillOutCheckOutFields("Testing first name","Testing last name","");
         Assert.assertEquals(checkOutPage.getErrorMessage(),
                 "Error: Postal Code is required",
                 "Error message not displayed");
    }

}
