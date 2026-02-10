package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    BasePage(WebDriver driver){
        this.driver = driver;
        String timeoutStr = ConfigReader.getProperty("timeout");
        int timeout = (timeoutStr != null) ? Integer.parseInt(timeoutStr) : 10;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public String getText(By locator){
        return driver.findElement(locator).getText();
    }

    public void click(By locator){
       wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();

    }
    public void type(By locator,String text){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }
    public String getUrl(){
        return driver.getCurrentUrl();
    }

    public String getTitle(){
        return driver.getTitle();
    }





}
