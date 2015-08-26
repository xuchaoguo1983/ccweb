package com.pengyang.ccweb.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static List<List<Object>> readExcel(File file) throws IOException {
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = null;

		if (file.getName().endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			workbook = new XSSFWorkbook(inputStream);
		}

		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		List<List<Object>> result = new ArrayList<List<Object>>();
		DecimalFormat df = new DecimalFormat("0");

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			List<Object> list = new ArrayList<Object>();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					list.add(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					list.add(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					list.add(df.format(cell.getNumericCellValue()));
					break;
				case Cell.CELL_TYPE_BLANK:
					list.add("");
					break;
				}
			}

			result.add(list);
		}

		workbook.close();
		inputStream.close();

		return result;
	}
}
