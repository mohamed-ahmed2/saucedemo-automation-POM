package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class CheckOutOverviewPage {
    public WebDriver driver;
    public CheckOutOverviewPage(WebDriver driver){this.driver = driver;}
    By subTotalLabel = By.cssSelector(".summary_subtotal_label");
    By taxLabel = By.className("summary_tax_label");
    By visibleTotalLabel = By.className("summary_total_label");
    By finishButton = By.id("finish");
    public double getTotal(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        double subTotal = Double
                .parseDouble(wait.until(ExpectedConditions.visibilityOfElementLocated(subTotalLabel))
                .getText()
                .replaceAll("[^0-9.]", ""));

       double tax = Double
               .parseDouble(wait.until(ExpectedConditions.visibilityOfElementLocated(taxLabel))
               .getText()
               .replaceAll("[^0-9.]", ""));

        double total = subTotal+ tax;

        System.out.println(total);
        return total;
        //return driver.findElement(total).getText();
    }

public double getVisibleTotal(){
    double visibleTotal = Double
            .parseDouble(driver.findElement(visibleTotalLabel)
                    .getText()
                    .replaceAll("[^0-9.]", ""));
            return visibleTotal;
    }
    public ConfirmationPage clickFinishButton(){
        driver.findElement(finishButton).click();
        return new ConfirmationPage(driver);
    }
}
