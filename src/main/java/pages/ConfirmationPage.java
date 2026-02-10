package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage extends BasePage{
    //WebDriver driver;
     public ConfirmationPage(WebDriver driver){
        super(driver);
         //this.driver = driver;
    }
    private By confirmationMessage = By.className("complete-header");
    public String getConfrimation(){
        return  getText(confirmationMessage);
    }
}
