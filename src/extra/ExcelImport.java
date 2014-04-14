package extra;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import engine.MongoDBConnection;





import jxl.*;
import jxl.read.biff.BiffException;
/**
 * This helps you import Excel data into MongoDB
 * @author Weiwei Chen
 * @since Jan 1, 2014
 *
 */
public class ExcelImport {

	public static void main(String[] args) {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(
					"/Users/chenweiwei/Work/Gift/database_2-15-14-2.xls"));
			

			MongoDBConnection connection = new MongoDBConnection();
			
			Sheet[] sheets = workbook.getSheets();
			for(Sheet sheet: sheets){
				int columns = sheet.getColumns();
				String[] keys = new String[columns];
				for (int i = 0; i < columns; i++) {
					keys[i] = sheet.getCell(i, 0).getContents();
					System.out.println(keys[i]);
				}
				int rows = sheet.getRows();

				for (int i = 1; i < rows; i++) {
					Map<String, String> map = new HashMap<String, String>();
					for (int j = 0; j < columns; j++) {
						String value = sheet.getCell(j, i).getContents();
						if (!keys[j].equals("")) {
							map.put(keys[j], value);
						}
					}
					connection.addCollection(map, sheet.getName());
				}
			}
			
			

		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
