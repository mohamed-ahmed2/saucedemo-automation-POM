package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
        //private WebDriver driver;

        // 1. Locators
        private By usernameField = By.id("user-name");
        private By passwordField = By.id("password");
        private By loginButton = By.id("login-button");
        private By errorMessage = By.cssSelector("h3[data-test='error']");
        // 2. Constructor
        public LoginPage(WebDriver driver) {
            super(driver);
            //super(driver);
            //this.driver = driver;
        }

        // 3. Methods (Actions)
        public void enterUsername(String user) {
            type(usernameField,user);
            //driver.findElement(usernameField).sendKeys(user);
        }

        public void enterPassword(String pass)
        {
          type(passwordField,pass);
            //  driver.findElement(passwordField).sendKeys(pass);
        }

        public void clickLogin() {
            click(loginButton);
            //driver.findElement(loginButton).click();
        }


        public void login(String user,String password){
           type(usernameField,user);
           type(passwordField,password);
           click(loginButton);
            /*
            driver.findElement(usernameField).sendKeys(user);
            driver.findElement(passwordField).sendKeys(password);
            driver.findElement(loginButton).click();
        */
        }

        public InventoryPage validlogin(String user,String password){
            login(user,password);
            return  new InventoryPage(driver);
        }

        public String geterror_message(){
            return getText(errorMessage);
            /*WebElement error_message = driver.findElement(errorMessage);
            return error_message.getText();
        */
        }

    }

