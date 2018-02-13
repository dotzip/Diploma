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

    String getStringDataRecord(String controlsName){
        try {
            def flag = true
            for (int i = 0; flag; i++) {
                HSSFRow row = sheet_.getRow(i)
                if (row.getCell(controlsListIndex_).stringCellValue == controlsName) {
                    return row.getCell(customerColumnIndex_).stringCellValue
                    flag = false
                }
            }
        }catch (Exception ex){
            print ex.getMessage()
        }
    }

    String getDateDataRecord(String controlsName){
        try {
            def flag = true
            for (int i = 0; flag; i++) {
                HSSFRow row = sheet_.getRow(i)
                if (row.getCell(controlsListIndex_).stringCellValue == controlsName) {
                    return row.getCell(customerColumnIndex_).stringCellValue
                    flag = false
                }
            }
        }catch (Exception ex){
            print ex.getMessage()
        }
    }

    int getNumberDataRecord(String controlsName){
        try {
            def flag = true
            for (int i = 0; flag; i++) {
                HSSFRow row = sheet_.getRow(i)
                if (row.getCell(controlsListIndex_).stringCellValue == controlsName) {
                    return (int)row.getCell(customerColumnIndex_).numericCellValue
                    flag = false
                }
            }
        }catch (Exception ex){
            print ex.getMessage()
        }
    }

    static void main(String[] args) {
        DataPoolExtractor xls1 = new DataPoolExtractor("C:\\Users\\c22466a\\Documents\\Diploma\\tc-pco-create-app.xls")
        xls1.sheetLoader("Inputs")
        xls1.controlsListLoader("Field")
        xls1.customerLoader("TC1")

        println "======= DEBUG: ${xls1.getStringDataRecord("SurnameTxt")} ======="
        println "======= DEBUG: ${xls1.getDateDataRecord("id-DOBDate hasDatepicker")} ======="
        println "======= DEBUG: ${xls1.getNumberDataRecord("HomePhoneNumberTxt")} ======="
        println "======= DEBUG: ${xls1.getStringDataRecord("CountryDDL")} ======="


    }

}
