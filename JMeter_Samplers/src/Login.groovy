import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.apache.jmeter.samplers.SampleResult
import java.util.concurrent.TimeUnit

/**
 * @System.setProperty for browser
 * @WebDriver object for browser management
 * @SampleResult for timestamps
 */

System.setProperty("webdriver.chrome.driver", "path_to_browser_driver\\chromedriver.exe")

WebDriver driver = new ChromeDriver()

SampleResult result = new SampleResult()

driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

/*log.info("=== Login sampler ===")*/

driver.get("localhost")
driver.findElement(By.id("IDToken1")).click()
driver.findElement(By.id("IDToken1")).sendKeys("john")
driver.findElement(By.id("IDToken2")).click()
driver.findElement(By.id("IDToken2")).sendKeys("Admin123!")

result.sampleStart()
driver.findElement(By.name("Login.Submit")).click()
result.sampleEnd()

/*log.info("Login's time: ${(result.endTime - result.startTime) / 1000} seconds")

log.info("=== End ===")*/

/*vars.putObject("DRIVER", driver)
vars.putObject("RESULT", result)*/
