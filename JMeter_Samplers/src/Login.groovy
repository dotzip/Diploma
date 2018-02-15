import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.apache.jmeter.samplers.SampleResult
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit

/**
 * @System.setProperty for browser
 * @WebDriver object for browser management
 * @SampleResult for timestamps
 */

System.setProperty("webdriver.chrome.driver", "path_to\\chromedriver.exe")

WebDriver driver = new ChromeDriver()
SampleResult result = new SampleResult()
WebDriverWait driverWait = new WebDriverWait(driver, 30)
def myXls = vars.getObject("XLS1")

driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

log.info("=== Login sampler ===")

driver.get("localhost")
driver.findElement(By.id("IDToken1")).click()
driver.findElement(By.id("IDToken1")).sendKeys(myXls.getStringDataRecord("IDToken1"))
driver.findElement(By.id("IDToken2")).click()
driver.findElement(By.id("IDToken2")).sendKeys(myXls.getStringDataRecord("IDToken2"))

result.sampleStart()
driver.findElement(By.name("Login.Submit")).click()
driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='menu']/li[2]"))) // waiting when homepage will be load
result.sampleEnd()

log.info("Login's time: ${(result.endTime - result.startTime) / 1000} seconds")

log.info("=== End ===")

vars.putObject("DRIVER", driver)
vars.putObject("RESULT", result)
vars.putObject("DRIVER_WAIT", driverWait)
