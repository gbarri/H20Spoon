package H2OSpoon.service;

import hex.genmodel.easy.RowData;
import nonapi.io.github.classgraph.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.DateFormatConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ReadCsv {

    Logger logger = LoggerFactory.getLogger("ReadCsv");

    public Workbook getExcelFileAsWorkbook(String path) throws IOException {
        Workbook document;
        try {
            File inputFile = new File(path);
            FileUtils.checkCanReadAndIsFile(inputFile);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Files.readAllBytes(inputFile.toPath()));
            document = WorkbookFactory.create(byteArrayInputStream);
        }catch (IOException e) {
            //errore nella codifica del documento excel
            logger.error("Failure in downloading file {}",path);
            throw e;
        }
        return document;
    }

    public List<RowData> toRowData(Workbook workbook){
        List<RowData> rowDataList = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        String excelFormatPattern = DateFormatConverter.convert(Locale.ITALIAN, "dd/MM/yyyy");
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat poiFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(poiFormat.getFormat(excelFormatPattern));

        List<String> headerNames = new ArrayList<>();
        Row firstRow = sheet.getRow(0);
        for (int c = 0, cn = firstRow.getLastCellNum() ; c < cn ; c++) {
            Cell cell = firstRow.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if ( cell != null ) {
                headerNames.add(formatter.formatCellValue(cell));
            }
        }

        for (int r = 1, rn = sheet.getLastRowNum() ; r <= rn ; r++) {
            RowData newData = new RowData();
            Row row = sheet.getRow(r);
            if ( row == null ) {
                continue;
            }
            for (int c = 0, cn = row.getLastCellNum() ; c < cn ; c++) {
                Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (cell != null && StringUtils.isNotEmpty(cell.getStringCellValue())) {
                    double value = Double.parseDouble(cell.getStringCellValue());
                    newData.put(headerNames.get(c), value);
                }
            }
            rowDataList.add(newData);
        }

        return rowDataList;
    }
}
