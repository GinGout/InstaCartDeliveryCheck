import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.awt.*;
import java.util.Scanner;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {
    private static WebDriver browser;
    // Static inputs
    private static String driverPath = "c:/devtools/chromedriver.exe";
    private static String user = "xxxxx";
    private static String pwd = "yyyy";
    // locators
    private static By loginButtonLandingPage = By.xpath("//*[@id=\"root\"]/div/div/header/div/div[2]/div/button");
    private static By userNameTextBox = By.id("nextgen-authenticate.all.log_in_email");
    private static By passwordTextBox = By.id("nextgen-authenticate.all.log_in_password");
    private static By loginButtonSignInPage = By.xpath("//*[@id='main-content']/div[2]/form/div[3]/button");
    private static By cartButton = By.xpath("//*[@id='header']/div/div/div[2]/div[3]/button");
    private static By gotoCHeckOutButton = By.xpath("//*[@id=\"react-views-container\"]/div/div/div/div[3]/div/div/div[3]/div/div/div/div/a/div[1]");

    // initialize chrome driver
    static {
        System.setProperty("webdriver.chrome.driver", driverPath);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
    }
    //rmq-a26a37a6 - try classname

    public static void main(String[] args) throws InterruptedException {


        // Get necessary inputs from the console
        Scanner scanner = new Scanner(System.in);
/*        System.out.println("Enter the chromedriver path");
        String driverPath = scanner.nextLine();
        System.out.println("Enter user name: ");
        String user = scanner.nextLine();
        System.out.println("Enter password: ");
        String pwd = scanner.nextLine();
*/

        // setup chrome driver

        // Navigate to instacart  home page
        browser.get("https://www.instacart.com/store/costco/storefront");

        // Click on Login Button
        fluentWait(loginButtonLandingPage);
        browser.findElement(loginButtonLandingPage).click();

        // Login
        fluentWait(userNameTextBox);
        browser.findElement(userNameTextBox).sendKeys(user);
        browser.findElement(passwordTextBox).sendKeys(pwd);
        browser.findElement(loginButtonSignInPage).click();

        // wait until the next page is loaded and then click on Cart button
        fluentWait(cartButton);
        browser.findElement(cartButton).click();

        // wait until checkout button is visible and the click on it
        fluentWait(gotoCHeckOutButton);
        browser.findElement(gotoCHeckOutButton).click();

        // refresh and check for slots every 30 seconds for 7 days and start beeping as soon as a slot is available
        for (int i = 0; i < 1440 * 7 * 2; i++) {
            try {
                String noSlotsMessage = browser.findElements(By.cssSelector("div.rmq-28f9c13a.rmq-8e4e203")).get(0).findElement(By.tagName("img")).getAttribute("alt");
                // check if the site says 'No delivery times available. If present, refresh the page after 30 seconds and check again
                if (noSlotsMessage.contains("No delivery times available")) {
                    Thread.sleep(30000);
                    browser.navigate().refresh();
                    continue;
                } else {
                    beeps(40, 1000);
                    scanner.nextLine();
                }
            } catch (Exception e) {
                beeps(40, 1000);
                scanner.nextLine();
            }
        }
        browser.quit();
    }


    private static void beeps(int count, int interval) throws InterruptedException {
        while (count-- > 0) {
            Toolkit.getDefaultToolkit().beep();
            Thread.sleep(interval);
        }
    }

    private static boolean fluentWait(By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(browser)
                .withTimeout(30, SECONDS)
                .pollingEvery(200, MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
        return false;
    }
}
