package com.experian.jmeter.framework

import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook

class DataPoolExtractor {
    private String dataPoolFilePath_
    private HSSFWorkbook workbook_
    private HSSFSheet sheet_
    private int customerColumnIndex_
    private int controlsListIndex_

    DataPoolExtractor(String dataPoolFilePath){
        this.dataPoolFilePath_ = dataPoolFilePath
        this.workbook_ = new HSSFWorkbook(new FileInputStream(dataPoolFilePath_))
    }

    void sheetLoader(String sheetName){
        this.sheet_ = workbook_.getSheet(sheetName)
    }

    void controlsListLoader(String controlsListIndex){
        HSSFRow topRow = sheet_.getRow(0)
        topRow.each { cell ->
            if (cell.stringCellValue.equals(controlsListIndex)) {
                this.controlsListIndex_ = cell.getColumnIndex()
            }
        }
    }

    void customerLoader(String customerCellID) {
        HSSFRow topRow = sheet_.getRow(0)
        topRow.each { cell ->
            if (cell.stringCellValue.equals(customerCellID)) {
                this.customerColumnIndex_ = cell.getColumnIndex()
            }
        }
    }

    String getStringDataRecord(String controlID) {
        try {
            def flag = true
            for (int i = 0; flag; i++) {
                HSSFRow row = sheet_.getRow(i)
                if (row.getCell(controlsListIndex_).stringCellValue == controlID) {
                    return row.getCell(customerColumnIndex_).stringCellValue
                    flag = false
                }
            }
        }catch (Exception ex){
            print ex.getMessage()
        }
    }

    int getNumberDataRecord(String controlID){
        try {
            def flag = true
            for (int i = 0; flag; i++) {
                HSSFRow row = sheet_.getRow(i)
                if (row.getCell(controlsListIndex_).stringCellValue == controlID) {
                    return (int)row.getCell(customerColumnIndex_).numericCellValue
                    flag = false
                }
            }
        }catch (Exception ex){
            print ex.getMessage()
        }
    }
}
