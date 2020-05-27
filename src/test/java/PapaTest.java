import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class PapaTest {
    WebDriver driver;
    Wait<WebDriver> wait;
    ChromeOptions options = new ChromeOptions();

    @BeforeEach
    void startUp() {
        String browser = System.getProperty("browser", "chrome");
        switch (browser) {
            case "chrome": {
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                driver = new ChromeDriver(options);
                break;
            }
            case "firefox": {
                System.getProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            }
            case "edge":{
                System.getProperty("webdriver.edge.driver", "drivers/msedgedriver.exe");
                driver = new EdgeDriver();
                break;
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    void click(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    void waitUntilVisible(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    void waitUntilClickable(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    boolean isDisplayed(String xpath) {
        return driver.findElement(By.xpath(xpath)).isDisplayed();
    }

    boolean isSelected(String xpath) {
        return driver.findElement(By.xpath(xpath)).isSelected();
    }

    String getAttribute(String xpath, String attribute) {
        return driver.findElement(By.xpath(xpath)).getAttribute(attribute);
    }

    void textInput(String xpath, String message) {
        WebElement input = driver.findElement(By.xpath(xpath));
        input.click();
        input.sendKeys(message);
    }

    void clearTextInput(String xpath) {
        driver.findElement(By.xpath(xpath)).clear();
    }

    void moveToElement(String xpath) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath(xpath))).perform();
    }
}
