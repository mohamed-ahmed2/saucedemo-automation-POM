package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Objects;

public class InventoryPage extends BasePage{
    //WebDriver driver;
    public InventoryPage(WebDriver driver){
        super(driver);
    }
    private By sortingButton = By.cssSelector("select[data-test='product-sort-container']");
    private By first_item_price = By.xpath("//div[@class='inventory_list']//div[@class='inventory_item'][1]//div[@class='inventory_item_price']");
    private By product_prices = By.cssSelector("[data-test='inventory-item-price']");
           // By.xpath("//div[@class='inventory_list']//div[@class='inventory_item_price']");

    private By title = By.className("title");
    //Cart
    private By cartBadge = By.cssSelector("span[data-test='shopping-cart-badge']");
    private By cartLink = By.cssSelector("a[data-test=shopping-cart-link]");
    //Actions
    public void sort (String sortingmethod){
        WebElement sortingElement = driver.findElement(sortingButton);
        Select sortingDropDown = new Select(sortingElement);
        switch (sortingmethod) {
            case "az" -> sortingDropDown.selectByVisibleText("Name (A to Z)");
            case "za" -> sortingDropDown.selectByVisibleText("Name (Z to A)");
            case "hilo" -> sortingDropDown.selectByVisibleText("Price (high to low)");
            case "lohi" -> sortingDropDown.selectByVisibleText("Price (low to high)");
        }
            
        }
        public String getCurrentSortingMethod(){
            WebElement sortingElement = driver.findElement(sortingButton);
            Select sortingDropDown = new Select(sortingElement);
            return  sortingDropDown.getFirstSelectedOption().getText();
        }

    public boolean compare(String sorting_method){

        //String firstElementPrice = driver.findElement(first_item_price).getText().substring(1);
       // double firstElementPrice_double = Double.parseDouble(firstElementPrice);
        // for logs
        if (sorting_method.equals("lohi")) {
            System.out.println("Low to High");
        }
        if(sorting_method.equals("hilo")){
            System.out.println("High to Low");
        }

        List<WebElement> prices = driver.findElements(product_prices);
        for (int i=0;i< prices.size()-1;i++){
            // Removing '$' currency symbol before parsing
            double currentPrice_double = Double.parseDouble(prices.get(i).getText().substring(1));
            double nextPrice_double = Double.parseDouble(prices.get(i+1).getText().substring(1));
            if(Objects.equals(sorting_method, "lohi")){
               // System.out.println("Low to High");
                if (currentPrice_double > nextPrice_double) {
                    System.out.println("Looks like sorting isn't working correctly as the current price (" + currentPrice_double + ") is greater than " + nextPrice_double);
                    return false;
                }
                System.out.println(currentPrice_double+" is less than or equal "+nextPrice_double);
            }
            if(Objects.equals(sorting_method, "hilo")){
                //System.out.println("High to Low");
                if (currentPrice_double < nextPrice_double) {
                    System.out.println("Looks like sorting isn't working correctly as the current price (" + currentPrice_double + ") is less than " + nextPrice_double);
                    return false;
                }
                System.out.println(currentPrice_double+" is greater than or equal "+nextPrice_double);
            }

            }

        return true;
    }

    public void clickAddToCart(String productName){
        String addToCartButton_string = String.format("//div[text()='%s']" +
                "/ancestor::div[contains(@class,'inventory_item')]" +
                "//button[contains(text(),'Add to cart')]",productName);
        By addToCartButton = By.xpath(addToCartButton_string);
        click(addToCartButton);
        //driver.findElement(addToCartButton).click();
    }

    public String getcurrentCartNumber(){
        return getText(cartBadge);
    }

    public CartPage clickOnShoppingCart(){
        click(cartLink);
        return new CartPage(driver);
    }

    public boolean isProductsTitleDisplayed() {
        return driver.findElement(title).isDisplayed();
    }
}



