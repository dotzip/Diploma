import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.apache.jmeter.samplers.SampleResult
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit

def myXls = vars.getObject("XLS1")
def driver = vars.getObject("DRIVER")
def driverWait = vars.getObject("DRIVER_WAIT")
def result = vars.getObject("RESULT")

//New application
driver.findElement(By.xpath("//ul[@id='menu']/li[2]")).click()
driver.findElement(By.xpath("//ul[@id='menu']/li[2]/ul/li[1]")).click()

/*Fill data*/

//SELECT
Select productSelect = new Select(driver.findElement(By.id("ApplicationTypeRbg")))
switch(myXls.getStringDataRecord("ApplicationTypeRbg")) {
    case "Credit Card" :
        productSelect.selectByValue("C")
        break
    case "Mortgage" :
        productSelect.selectByValue("M")
        break
    case "Personal Loan" :
        productSelect.selectByValue("P")
        break
    case "Instant Loan" :
        productSelect.selectByValue("I")
        break
}
//TEXT FIELD
driver.findElement(By.id("SurnameTxt")).click()
driver.findElement(By.id("SurnameTxt")).sendKeys(myXls.getStringDataRecord("SurnameTxt"))
driver.findElement(By.id("ForenameTxt")).click()
driver.findElement(By.id("ForenameTxt")).sendKeys(myXls.getStringDataRecord("ForenameTxt"))
//DATE
driver.findElement(By.id("id2b")).click()
driver.findElement(By.id("id2b")).sendKeys(myXls.getStringDataRecord("id2b"))
driver.findElement(By.id("datepicker")).click()
//TEXT
driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className('ajax_loader')))
driverWait.until(ExpectedConditions.elementToBeClickable(By.id("identificationCardNo"))) // waiting when element will be clickable
driver.findElement(By.id("identificationCardNo")).click()
driver.findElement(By.id("identificationCardNo")).sendKeys(myXls.getStringDataRecord("identificationCardNo"))
driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)


//log.info("======= DEBUG: ${myXls.getStringDataRecord("SurnameTxt")} =======")
//log.info("======= DEBUG: ${myXls.getDateDataRecord("id-DOBDate hasDatepicker")} =======")
//log.info("======= DEBUG: ${myXls.getNumberDataRecord("HomePhoneNumberTxt")} =======")
//log.info("======= DEBUG: ${myXls.getStringDataRecord("CountryDDL")} =======")