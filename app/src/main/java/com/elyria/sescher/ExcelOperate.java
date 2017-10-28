package com.elyria.sescher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    public static List<ExcelInfo> importExcel(File file, int sheetIndex) throws IOException {
        FileInputStream in = null;
        List<ExcelInfo> result = null;
        try {
            in = new FileInputStream(file);
            result = new ArrayList<>();
            Workbook wb = new HSSFWorkbook(in);
            Sheet sheet = wb.getSheetAt(sheetIndex);
            for (Row row : sheet) {
                if (row.getRowNum() < 1) {
                    continue;
                }
                ExcelInfo eInfo = new ExcelInfo();
                eInfo.setIndex(row.getRowNum());
                eInfo.setIp(row.getCell(0).getStringCellValue());
                eInfo.setCommunity(row.getCell(1).getStringCellValue());
                result.add(eInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return result;
    }

    public static List<ExcelInfo> importExcel(File file) throws IOException {
        return importExcel(file, 0);
    }
}