import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.util.concurrent.TimeUnit

/**
 * @System.setProperty for browser
 * @WebDriver object for browser management
 */

System.setProperty("webdriver.chrome.driver", "path\\to\\browser-driver\\chromedriver.exe")

WebDriver driver = new ChromeDriver()

driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

driver.get("http://duckduckgo.com")
driver.findElement(By.id("search_form_input_homepage")).click()
driver.findElement(By.id("search_form_input_homepage")).clear()
driver.findElement(By.id("search_form_input_homepage")).sendKeys("blazemeter")
driver.findElement(By.id("search_button_homepage")).click()