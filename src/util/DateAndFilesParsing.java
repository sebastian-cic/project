package util;

import java.io.File;
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
	public String getExchange(String stringContainingExchange) {

		return stringContainingExchange.substring(stringContainingExchange.lastIndexOf('\\') + 1,
				stringContainingExchange.lastIndexOf('_'));

	}

	/**
	 * Get array of all files from the folder containing stock data CSV files.
	 * 
	 * @return File[] containing all CSV files.
	 */
	public File[] getAllFiles() {

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
	public Date parseDate(String date, String format) {

		java.util.Date parsed = null;
		try {

			SimpleDateFormat f = new SimpleDateFormat(format);
			parsed = f.parse(date);

		} catch (ParseException pe) {

			pe.printStackTrace();
		}
		return new java.sql.Date(parsed.getTime());
	}
	
	/*
	public Date getDateFromFileName(String stringContainingDate) {
		stringContainingDate = stringContainingDate.substring(stringContainingDate.lastIndexOf('_') + 1,
				stringContainingDate.lastIndexOf('.'));

		String day = stringContainingDate.substring(06);
		day = day.replaceFirst("^0+(?!$)", "");

		String month = stringContainingDate.substring(4, 6);

		String year = stringContainingDate.substring(02, 04);
		switch (month) {
		case "01":
			month = "jan";
			break;
		case "02":
			month = "feb";
			break;
		case "03":
			month = "mar";
			break;
		case "04":
			month = "apr";
			break;
		case "05":
			month = "may";
			break;
		case "06":
			month = "jun";
			break;
		case "07":
			month = "jul";
			break;
		case "08":
			month = "aug";
			break;
		case "09":
			month = "sep";
			break;
		case "10":
			month = "oct";
			break;
		case "11":
			month = "nov";
			break;
		case "12":
			month = "dec";
			break;

		default:

			System.out.println("date failed");
			break;
		}
		stringContainingDate = day + "-" + month + "-" + year;
		return parseDate(stringContainingDate,"");
	}*/
}
