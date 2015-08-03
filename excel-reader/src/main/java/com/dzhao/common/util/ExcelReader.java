package com.dzhao.common.util;

import com.dzhao.common.util.annotation.ExcelCell;
import com.dzhao.common.util.annotation.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dzhao on 27/07/2015.
 */
public class ExcelReader {

    private final String filePath;
    private static Logger logger = LoggerFactory.getLogger(ExcelReader.class);
    private Workbook workbook;
    private static final String DEFAULT_FIRST_COLUMN = "A";

    public ExcelReader(final String filePath) throws IOException {
        this.filePath = filePath;
        this.workbook = load();
    }

    private Workbook load() throws IOException {
        return new XSSFWorkbook(readContent());
    }

    private InputStream readContent(){
        InputStream is = this.getClass().getResourceAsStream(filePath);
        if(is==null){
            StringBuilder pathBuilder = new StringBuilder();
            if(!filePath.startsWith("/")){
                pathBuilder.append("/");
            }
            pathBuilder.append(filePath);
            is = this.getClass().getResourceAsStream(pathBuilder.toString());
        }

        if(is==null) {
            logger.error("File [" + filePath + "] not found");
            throw new RuntimeException("File [" + filePath + "] not found");
        }
        return is;
    }

    public <T> List<T> convert(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        Excel excelSheet = clazz.getAnnotation(Excel.class);
        if(excelSheet==null){
            logger.error("Unsupported object!");
            throw new RuntimeException("Unsupported object");
        }

        String sheetTabName = excelSheet.tabName();
        if(sheetTabName==null || sheetTabName.isEmpty()){
            logger.error("sheet tabe name isn't provided!");
            throw new RuntimeException("sheet tabe name isn't provided!");
        }

        Field[] fields = clazz.getDeclaredFields();

        int startRow = excelSheet.startRow();

        List<Object[]> results = convert(sheetTabName, startRow);

        List<T> list = new ArrayList<T>();

        for (Object[] objs : results){
            T object;
            object = clazz.newInstance();
            for (Field field : fields){
                ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
                int columnIndex = excelCell.column().toUpperCase().compareTo(DEFAULT_FIRST_COLUMN);
                field.setAccessible(true);
                Object value = PojoConvertor.convertTo(field, objs[columnIndex]);
                field.set(object, value);
                field.setAccessible(false);
            }
            list.add(object);
        }

        return list;
    }

    private List<Object[]> convert(String sheetTabName, int startRow){
        List<Object[]> results = new ArrayList<Object[]>();
        Sheet sheet = workbook.getSheet(sheetTabName);
        int rowCount = 0;
        for (Row row : sheet){
            if(rowCount < startRow - 1){
                rowCount ++ ;
                continue;
            }
            List<Object> objects = new ArrayList<Object>();
            for(Cell cell : row){
                objects.add(getCellValue(cell));
            }
            results.add(objects.toArray());
        }
        return results;
    }

    private static Object getCellValue(Cell cell){
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
/*            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                throw new AssertionError("CELL_TYPE_FORMULA");*/
            default:
                throw new AssertionError("unsupported type");
        }
    }
}
