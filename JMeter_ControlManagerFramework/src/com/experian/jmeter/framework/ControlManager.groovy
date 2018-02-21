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
    private final int noAttempts_ = 15
    private def log_

    ControlManager(WebDriver driver, WebDriverWait driverWait, DataPoolExtractor xls, def log){
        this.driverWait_ = driverWait
        this.driver_ = driver
        this.xls_ = xls
        this.log_ = log
    }

    void waitActive(){
        driver_.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
        driverWait_.until(ExpectedConditions.invisibilityOfElementLocated(By.className('ajax_loader')))
        driver_.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
    }

    void moveTo(def field, boolean scroll){
        driver_.executeScript("arguments[0].scrollIntoView($scroll);", field)
    }

    def reGetControl(String controlID){
        driverWait_.until(ExpectedConditions.visibilityOfElementLocated(By.id(controlID)))
        def field = driver_.findElement(By.id(controlID))
        moveTo(field, true)
        return field
    }

    def reGetControlXPath(String controlID, String controlXPath){
        driverWait_.until(ExpectedConditions.visibilityOfElementLocated(By.id(controlID)))
        def field = driver_.findElement(By.xpath(controlXPath))
        moveTo(field, true)
        return field
    }

    def preCtrl(String controlID){
        driver_.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)
        driverWait_.until(ExpectedConditions.visibilityOfElementLocated(By.id(controlID)))
        def field = driver_.findElement(By.id(controlID))
        moveTo(field, false)
        /* Subsample time */
        waitActive()
        return field
    }

    def preCtrlXPath(String controlID, String controlXPath){
        driver_.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)
        driverWait_.until(ExpectedConditions.visibilityOfElementLocated(By.id(controlID)))
        def field = driver_.findElement(By.xpath(controlXPath))
        moveTo(field, false)
        /* Subsample time */
        waitActive()
        return field
    }

    void selectCtrl(String controlID){
        log_.info("=== SELECT $controlID: ${xls_.getStringDataRecord(controlID)} ===")
        def field = preCtrl(controlID)
        def select = new Select(field)
        def attempt = 0
        def exceptionFlag = false

        driver_.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
        while (attempt < noAttempts_) {
            try {
                exceptionFlag = false
                select.selectByVisibleText(xls_.getStringDataRecord(controlID))
            } catch (Exception err) {
                exceptionFlag = true
                attempt++
                log_.info("=== SELECT ATTEMPTS: $attempt ===")
                field = reGetControl(controlID)
                select = new Select(field)
            } finally {
                if (!exceptionFlag) attempt = noAttempts_
            }
        }
    }

    void textCtrl(String controlID){
        log_.info("=== TEXT $controlID: ${xls_.getStringDataRecord(controlID)} ===")
        def field = preCtrl(controlID)
        def attempt = 0
        def exceptionFlag = false

        driver_.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
        while (attempt < noAttempts_) {
            try {
                exceptionFlag = false
                field.click()
                field.sendKeys(xls_.getStringDataRecord(controlID))
            } catch (Exception err) {
                exceptionFlag = true
                attempt++
                log_.info("=== TEXT ATTEMPTS: $attempt ===")
                field = reGetControl(controlID)
            } finally {
                if (!exceptionFlag) attempt = noAttempts_
            }
        }
    }

    void dateCtrl(String controlID){
        log_.info("=== DATE $controlID: ${xls_.getStringDataRecord(controlID)} ===")
        def field = preCtrl(controlID)
        def attempt = 0
        def exceptionFlag = false

        driver_.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
        while (attempt < noAttempts_) {
            try {
                exceptionFlag = false
                field.click()
                field.sendKeys(xls_.getStringDataRecord(controlID))
                field.sendKeys(Keys.TAB)
            } catch (Exception err) {
                exceptionFlag = true
                attempt++
                log_.info("=== DATE ATTEMPTS: $attempt ===")
                field = reGetControl(controlID)
            } finally {
                if (!exceptionFlag) attempt = noAttempts_
            }
        }
    }

    void radioButtonCtrl(String controlID){
        log_.info("=== RADIO $controlID: ${xls_.getStringDataRecord(controlID)} ===")
        if(xls_.getStringDataRecord(controlID) == 'Yes'){
            def field = preCtrlXPath(controlID, "//div[@id='"+controlID+"']/*/label[text()='Yes']")
            def attempt = 0
            def exceptionFlag = false

            driver_.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
            while (attempt < noAttempts_) {
                try {
                    exceptionFlag = false
                    field.click()
                } catch (Exception err) {
                    exceptionFlag = true
                    attempt++
                    log_.info("=== RADIO ATTEMPTS: $attempt ===")
                    field = reGetControlXPath(controlID, "//div[@id='"+controlID+"']/*/label[text()='Yes']")
                } finally {
                    if (!exceptionFlag) attempt = noAttempts_
                }
            }
        }
    }

    void checkBoxCtrl(){}

    void buttonCtrl(String controlID){
        log_.info("=== BUTTON $controlID ===")
        def field = preCtrl(controlID)
        def attempt = 0
        def exceptionFlag = false

        driver_.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS)
        while (attempt < noAttempts_) {
            try {
                exceptionFlag = false
                field.click()
            } catch (Exception err) {
                exceptionFlag = true
                attempt++
                log_.info("=== BUTTON ATTEMPTS: $attempt ===")
                field = reGetControl(controlID)
            } finally {
                if (!exceptionFlag) attempt = noAttempts_
            }
        }
    }
}