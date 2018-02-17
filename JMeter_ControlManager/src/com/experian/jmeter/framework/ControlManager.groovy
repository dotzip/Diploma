package com.experian.jmeter.framework

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit


class ControlManager {
    private WebDriverWait driverWait_
    private WebDriver driver_
    private DataPoolExtractor xls_

    ControlManager(WebDriver driver, WebDriverWait driverWait, DataPoolExtractor xls){
        this.driverWait_ = driverWait
        this.driver_ = driver
        this.xls_ = xls
    }

    void waitActive(){
        driver_.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
        driverWait_.until(ExpectedConditions.invisibilityOfElementLocated(By.className('ajax_loader')))
        driver_.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
    }

    void moveTo(def field){
        driver_.executeScript("arguments[0].scrollIntoView(false);", field)
    }

    def reGetControl(String controlID){
        driverWait_.until(ExpectedConditions.visibilityOfElementLocated(By.id(controlID)))
        def field = driver_.findElement(By.id(controlID))
        moveTo(field)
        return field
    }

    def preCtrl(String controlID){
        driver_.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)
        driverWait_.until(ExpectedConditions.visibilityOfElementLocated(By.id(controlID)))
        def field = driver_.findElement(By.id(controlID))
        moveTo(field)
        /* Subsample time */
        waitActive()
        return field
    }


    void selectCtrl(String controlID){
        def field = preCtrl(controlID)
        def select = new Select(field)
        select.selectByVisibleText(xls_.getStringDataRecord(controlID))
    }

    void textCtrl(String controlID){
        def field = preCtrl(controlID)
        field.click()
        field.sendKeys(xls_.getStringDataRecord(controlID))
    }

    void dateCtrl(String controlID){
        def field = preCtrl(controlID)
        field.click()
        field.sendKeys(xls_.getStringDataRecord(controlID))
        field.sendKeys(Keys.TAB)
    }

    void radioButtonCtrl(){}

    void checkBoxCtrl(){}

    void buttonCtrl(String controlID){
        def field = preCtrl(controlID)

        def attempt = 0
        def noAttempts = 15
        def exceptionFlag = false

        driver_.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
        while (attempt < noAttempts) {
            try {
                exceptionFlag = false
                field.click()
            } catch (Exception err) {
                exceptionFlag = true
                attempt++
                field = reGetControl(controlID)
            } finally {
                if (!exceptionFlag) attempt = noAttempts
            }
        }
    }

}