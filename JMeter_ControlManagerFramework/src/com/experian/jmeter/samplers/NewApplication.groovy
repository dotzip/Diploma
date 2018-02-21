package com.experian.jmeter.samplers

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.ExpectedConditions

import java.util.concurrent.TimeUnit

def ctrlManager = vars.getObject("CTRL_MANAGER")
def driver = vars.getObject("DRIVER")

//New application
driver.findElement(By.xpath("//ul[@id='menu']/li[2]")).click()
driver.findElement(By.xpath("//ul[@id='menu']/li[2]/ul/li[1]")).click()

/*Fill data*/

//SELECT
ctrlManager.selectCtrl("ApplicationTypeRbg")
//TEXT FIELD
ctrlManager.textCtrl("SurnameTxt")
ctrlManager.textCtrl("ForenameTxt")
//DATE
ctrlManager.dateCtrl("id2b")
//TEXT
ctrlManager.textCtrl("identificationCardNo")
//RADIO
ctrlManager.radioButtonCtrl("ProofOfAddressRbg")
ctrlManager.radioButtonCtrl("ProofOfIncomeRbg")
ctrlManager.radioButtonCtrl("PaymentProtectionRbg")
//BUTTON
ctrlManager.buttonCtrl("nav1Btn")
