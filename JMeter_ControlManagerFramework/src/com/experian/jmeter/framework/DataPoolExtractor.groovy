package com.experian.jmeter.framework

import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook

class DataPoolExtractor {
    private String dataPoolFilePath_
    private HSSFWorkbook workbook_
    private HSSFSheet sheet_
    private int customerColumnIndex_, controlsListIndex_, sheetSize_

    DataPoolExtractor(String dataPoolFilePath){
        this.dataPoolFilePath_ = dataPoolFilePath
        this.workbook_ = new HSSFWorkbook(new FileInputStream(dataPoolFilePath_))
    }

    def sheetLoader = { sheetName ->
        this.sheet_ = workbook_.getSheet(sheetName)
        this.sheetSize_ = sheet_.lastRowNum
    }

    def controlsListLoader = { controlsListIndex ->
        HSSFRow topRow = sheet_.getRow(0)
        topRow.each { cell ->
            if (cell.stringCellValue.equals(controlsListIndex)) {
                this.controlsListIndex_ = cell.getColumnIndex()
            }
        }
    }

    def customerLoader = { customerCellID ->
        HSSFRow topRow = sheet_.getRow(0)
        topRow.each { cell ->
            if (cell.stringCellValue.equals(customerCellID)) {
                this.customerColumnIndex_ = cell.getColumnIndex()
            }
        }
    }

    String getStringDataRecord(String controlID) {
        for (int i = 0; i < sheetSize_; i++) {
            try {
                HSSFRow row = sheet_.getRow(i)
                if (row.getCell(controlsListIndex_).stringCellValue == controlID) {
                    return row.getCell(customerColumnIndex_).stringCellValue
                }
            }catch (Exception ex) {
                print ex.getMessage()
            }
        }
        return ""
    }
}
