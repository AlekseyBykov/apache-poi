package alekseybykov.portfolio.apache.poi;

import alekseybykov.portfolio.apache.poi.officeopen.helpers.CustomXSSFHelper;
import alekseybykov.portfolio.apache.poi.officeopen.model.Book;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Aleksey Bykov
 * @since 28.05.2020
 */
public class ApachePOITestBase {

	protected static final Logger logger = Logger.getLogger(ApachePOITestBase.class.getPackage().getName());

	protected final List<String> datesFixture = Arrays.asList(
		"28/05/20", "27/05/20", "26/05/20", "25/05/20",
		"24/05/20", "23/05/20", "22/05/20", "21/05/20",
		"20/05/20", "19/05/20", "18/05/20", "17/05/20",
		"16/05/20", "15/05/20", "14/05/20", "13/05/20",
		"12/05/20", "11/05/20", "10/05/20", "09/05/20",
		"08/05/20", "07/05/20", "06/05/20", "05/05/20",
		"04/05/20", "03/05/20", "02/05/20", "01/05/20"
	);

	protected final List<String> eursFixture = Arrays.asList(
		"0.9078", "0.9098", "0.9112", "0.9166", "0.9171",
		"0.9171", "0.9171", "0.9091", "0.9126", "0.9132",
		"0.9232", "0.9261", "0.9261", "0.9261", "0.9266",
		"0.9195", "0.921", "0.9239", "0.9223", "0.9223",
		"0.9223", "0.9274", "0.9253", "0.9223", "0.9139",
		"0.9195", "0.9195", "0.9195"
	);

	protected final List<String> gbpsFixture = Arrays.asList(
			"0.8145", "0.8152", "0.8098", "0.8205", "0.8214",
			"0.8214", "0.8214", "0.8177", "0.8155", "0.8177",
			"0.8231", "0.8218", "0.8218", "0.8218", "0.82",
			"0.8114", "0.8084", "0.8119", "0.8073", "0.8073",
			"0.8073", "0.8113", "0.8074", "0.8029", "0.8033",
			"0.7991", "0.7991", "0.7991"
	);

	@BeforeClass
	public static void init() throws IOException {
		System.setProperty("java.util.logging.config.file", ClassLoader.getSystemResource("logging.properties").getPath());
		FileUtils.cleanDirectory(new File("xls"));
		FileUtils.cleanDirectory(new File("xlsx"));
	}

	protected void createAndPopulateXlsxWithData(String path, List<Book> books) {
		try (Workbook workbook = createXlsxWorkbook(books);
		     FileOutputStream outputStream = new FileOutputStream(path)) {
			workbook.write(outputStream);
		} catch (IOException ex) {
			logger.severe("Error while reading the file:" + ex.getMessage());
		}
	}

	private Workbook createXlsxWorkbook(List<Book> books) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("TAB NAME");
		CustomXSSFHelper.createHeaderAreaForSheet(sheet);
		CustomXSSFHelper.createTableAreaForSheet(sheet, books);
		CustomXSSFHelper.setAutoFilterForSheet(sheet);
		CustomXSSFHelper.setZoomForSheet(sheet);
		return workbook;
	}
}
