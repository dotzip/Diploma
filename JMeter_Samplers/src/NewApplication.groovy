import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.apache.jmeter.samplers.SampleResult
import java.util.concurrent.TimeUnit

/*def myXls = vars.getObject("XLS1")
def driver = vars.getObject("DRIVER")
def result = vars.getObject("RESULT")*/

//New application
driver.findElement(By.xpath("//ul[@id='menu']/li[2]")).click()
driver.findElement(By.xpath("//ul[@id='menu']/li[2]/ul/li[1]")).click()

