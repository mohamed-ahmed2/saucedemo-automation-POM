package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckOutPage extends BasePage{
   // WebDriver driver;
    CheckOutPage(WebDriver driver){super(driver);}

    By firstNameBox = By.id("first-name");
    By lastNameBox = By.id("last-name");
    By postalCodeBox = By.id("postal-code");
    By continueButton = By.id("continue");
    By title = By.cssSelector("[data-test='title']");
    By errorMessage = By.cssSelector("[data-test='error']");

    //Actions
    public CheckOutOverviewPage fillOutCheckOutFields(String firstName,String lastName,String postalCode){
        type(firstNameBox,firstName);
        type(lastNameBox,lastName);
        type(postalCodeBox,postalCode);
        click(continueButton);
        return new CheckOutOverviewPage(driver);
    }

    public String getcurrentTitle(){

        return  getText(title);

    }

    public String getErrorMessage(){
        /*
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    */
        return getText(errorMessage);

    }

}
