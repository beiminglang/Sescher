package com.elyria.sescher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelOperate {
    /**
     * 导入excel文件，使用绝对路径 * * @param file * @param sheetIndex * @return * @throws IOException
     */
    public static List<ExcelInfo> importExcel(InputStream in, int sheetIndex) throws IOException {
        List<ExcelInfo> result = null;
        try {
            result = new ArrayList<ExcelInfo>();
            Workbook wb = new HSSFWorkbook(in);
            Sheet sheet = wb.getSheetAt(sheetIndex);
            for (Row row : sheet) {
                if (row.getRowNum() < 1) {
                    continue;
                }
                ExcelInfo eInfo = new ExcelInfo();
                eInfo.setIndex(row.getRowNum());
                Cell cell1 = row.getCell(1);
                if (cell1 != null) {
                    eInfo.setIp(cell1.getStringCellValue());
                }
                Cell cell = row.getCell(2);
                if (cell != null) {
                    eInfo.setCommunity(cell.getStringCellValue());
                }
                result.add(eInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return result;
    }

    public static List<ExcelInfo> importExcel(InputStream file) throws IOException {
        return importExcel(file, 0);
    }
}