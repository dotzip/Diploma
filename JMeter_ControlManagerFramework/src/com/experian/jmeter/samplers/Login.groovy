import org.apache.jmeter.threads.JMeterContextService
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

def vars = JMeterContextService.getContext().getVariables()
// Waiting for driver initialize
WebDriverWait driverWait = new WebDriverWait(WDS.browser, 60)
vars.putObject("DRIVER_WAIT", driverWait)
//System properties for browser driver
System.setProperty("webdriver.chrome.driver", "C:\\Users\\c22466a\\Documents\\Diploma\\apache-jmeter-3.3\\browser_drivers\\chromedriver.exe")

WDS.log.info('Login')
WDS.sampleResult.subSampleStart('>>> Get page')
WDS.browser.get("http://sso.experian.local:8080/sso/UI/Login?realm=TENANT1")
WDS.sampleResult.subSampleEnd(true)

WDS.browser.findElement(By.id("IDToken1")).click()
WDS.browser.findElement(By.id("IDToken1")).sendKeys("john")
WDS.browser.findElement(By.id("IDToken2")).click()
WDS.browser.findElement(By.id("IDToken2")).sendKeys("Admin123!")

WDS.sampleResult.subSampleStart('>>> Login')
WDS.browser.findElement(By.name("Login.Submit")).click()
driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='menu']/li[2]"))) // waiting when homepage will be load
WDS.sampleResult.subSampleEnd(true)

WDS.sampleResult.subSampleStart('>>> New App')
WDS.browser.findElement(By.xpath("//ul[@id='menu']/li[2]")).click()
WDS.browser.findElement(By.xpath("//ul[@id='menu']/li[2]/ul/li[1]")).click()
WDS.sampleResult.subSampleEnd(true)
