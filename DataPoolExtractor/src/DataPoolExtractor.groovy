import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row

class DataPoolExtractor {
    private String dataPoolFilePath_
    private HSSFWorkbook workbook_
    HSSFSheet sheet_

    DataPoolExtractor(String dataPoolFilePath){
        this.dataPoolFilePath_ = dataPoolFilePath
        this.workbook_ = new HSSFWorkbook(new FileInputStream(dataPoolFilePath_))
    }

    void sheetLoader(String sheetName){
        this.sheet_ = workbook_.getSheet(sheetName)
    }

    void customerLoader(String customerCellID){
        int customerColumnIndex
        HSSFRow topRow = sheet_.getRow(0)
        Iterator<Cell> cells = topRow.cellIterator()
        for (Cell cell : cells){
            if (cell.stringCellValue.equals(customerCellID)) {
                customerColumnIndex = cell.getColumnIndex()
            }
        }
        Iterator<Row> rows = sheet_.rowIterator()
        for (Row row : rows){
            row.getCell(customerColumnIndex)
        }
    }

    static void main(String[] args) {
        DataPoolExtractor xls1 = new DataPoolExtractor("path1")
        xls1.sheetLoader("Inputs")
        xls1.customerLoader("TC1")
    }

}
