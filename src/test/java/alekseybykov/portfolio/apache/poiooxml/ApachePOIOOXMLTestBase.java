package alekseybykov.portfolio.apache.poiooxml;

import alekseybykov.portfolio.apache.poiooxml.model.Book;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ApachePOIOOXMLTestBase {

	@BeforeClass
	public static void init() throws IOException {
		System.setProperty("java.util.logging.config.file", ClassLoader.getSystemResource("logging.properties").getPath());
		FileUtils.cleanDirectory(new File("xlsx"));
	}

	protected File getFile(String fileName) {
		return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
	}

	protected void createAndPopulateXlsxWithData(String path, List<Book> books) {
		try (Workbook workbook = createXlsxWorkbook(books);
		     FileOutputStream outputStream = new FileOutputStream(path)) {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Workbook createXlsxWorkbook(List<Book> books) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("TAB NAME");
		createHeaderArea(sheet);
		createTableArea(sheet, books);
		setAutoFilter(sheet);
		setZoom(sheet);
		return workbook;
	}

	private void createHeaderArea(Sheet sheet) {
		Row row = sheet.createRow(0);
		CellStyle cellStyle = createStyleForHeaderCell(sheet);
		for (int i = 0; i < 5; i++) {
			Cell cell = row.createCell(i);
			sheet.autoSizeColumn(i);

			if (i == 0) {
				cell.setCellValue("ISBN");
				cell.setCellStyle(cellStyle);
			} else if (i == 1) {
				cell.setCellValue("BOOK TITLE");
				cell.setCellStyle(cellStyle);
			} else if (i == 2) {
				cell.setCellValue("AUTHOR");
				cell.setCellStyle(cellStyle);
			} else if (i == 3) {
				cell.setCellValue("PUBLISHER");
				cell.setCellStyle(cellStyle);
			} else {
				cell.setCellValue("PRICE");
				cell.setCellStyle(cellStyle);
			}
		}
	}

	private void createTableArea(Sheet sheet, List<Book> books) {
		CellStyle cellStyle = createStyleForTableCell(sheet);
		for (int i = 0; i < books.size(); i++) {
			Row row = sheet.createRow(i + 1);
			for (int j = 0; j < 5; j++) {
				Cell cell = row.createCell(j);
				sheet.autoSizeColumn(j);

				if (j == 0) {
					cell.setCellValue(books.get(i).getIsbn());
					cell.setCellStyle(cellStyle);
				} else if (j == 1) {
					cell.setCellValue(books.get(i).getTitle());
					cell.setCellStyle(cellStyle);
				} else if (j == 2) {
					cell.setCellValue(books.get(i).getAuthor());
					cell.setCellStyle(cellStyle);
				} else if (j == 3) {
					cell.setCellValue(books.get(i).getPublisher());
					cell.setCellStyle(cellStyle);
				} else {
					cell.setCellValue(books.get(i).getPrice());
					cell.setCellStyle(cellStyle);
				}
			}
		}
	}

	private CellStyle createStyleForHeaderCell(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontName("Arial");
		font.setFontHeightInPoints((short)21);
		font.setColor(IndexedColors.WHITE.getIndex());

		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);

		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);

		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);

		cellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	private CellStyle createStyleForTableCell(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setItalic(true);
		font.setFontName("Arial");
		font.setFontHeightInPoints((short)10);

		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);

		cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		cellStyle.setBorderTop(BorderStyle.THIN);

		cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		cellStyle.setBorderRight(BorderStyle.THIN);

		cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		cellStyle.setBorderBottom(BorderStyle.THIN);

		cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		cellStyle.setBorderLeft(BorderStyle.THIN);

		return cellStyle;
	}

	private void setAutoFilter(Sheet sheet) {
		sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum(),
				sheet.getRow(0).getFirstCellNum(), sheet.getRow(0).getLastCellNum() - 1));
	}

	private void setZoom(Sheet sheet) {
		sheet.setZoom(150);
	}
}
