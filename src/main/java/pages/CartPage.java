package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    //WebDriver driver;
   public CartPage(WebDriver driver){super(driver);}
    By itemsInTheCart = By.xpath("//div[@class='inventory_item_name']");
    By checkOutButton = By.id("checkout");

   public String getItems(){
       return getText(itemsInTheCart);
   }
   public CheckOutPage clickOnCheckOut(){
       click(checkOutButton);
       return new CheckOutPage(driver);
   }

}
