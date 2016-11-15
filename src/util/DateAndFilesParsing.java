package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateAndFilesParsing
{
	/**
	 * Retrieves substring of exchange type from CSV file path string.
	 * 
	 * @param stringContainingExchange
	 *            string containing the file path of individual CSV file.
	 * @return String representing stock exchange.
	 */
	public String getExchange(String stringContainingExchange)
	{

		return stringContainingExchange.substring(stringContainingExchange.lastIndexOf('\\') + 1,
				stringContainingExchange.lastIndexOf('_'));

	}

	/**
	 * Get array of all files from the folder containing stock data CSV files.
	 * 
	 * @return File[] containing all CSV files.
	 */
	public File[] getAllFiles()
	{

		File file = new File("C:/Users/seb/Downloads/stocks");
		return file.listFiles();
	}

	/**
	 * Method to take string representation of date in format dd-MMM-yy and
	 * convert to date of java.sql.Date type.
	 * 
	 * @param date
	 *            string representation of date in format dd-MMM-yy.
	 * @return java.sql.Date.
	 */
	public Date parseDate(String date, String format)
	{

		java.util.Date parsed = null;
		try
		{

			SimpleDateFormat f = new SimpleDateFormat(format);
			parsed = f.parse(date);

		} catch (ParseException pe)
		{

			pe.printStackTrace();
		}
		return new java.sql.Date(parsed.getTime());
	}
	/**
	 * Move file to new directory after writing to database.
	 * @param originalFile
	 * @throws IOException
	 */
	public void moveFile(File originalFile) throws IOException
	{
		//substring starting at 30 to get CSV file name from original file passed in.
		//string then appends to path of where the file is to be moved to.
		File file = new File("C:/Users/seb/Downloads/movedStocks/" + originalFile.toString().substring(30));
		Files.move(originalFile.toPath(), Paths.get(file.getPath()), StandardCopyOption.REPLACE_EXISTING);
	}
}
