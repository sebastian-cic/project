package database;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadWriteData {

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ReadWriteData() {
	}

	private String exchange = "";
	private String date = "";

	/**
	 * Method to open CSV files and parse to array of strings containing the
	 * individual data.
	 */
	public void readInCSVFiles() {

		String output = "";
		String line = "";
		String[] split = new String[1000];
		String[] splitCSVarray = new String[100000];
		FileInputStream fstream = null;
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		String[] arrayOfIndividualStocks = new String[7];
		// Get list of all CSV files from downloads directory.
		File[] listOfFiles = getAllFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			// Get specific stock exchange name for each CSV file, used to enter
			// into correct database table
			exchange = getExchange(listOfFiles[i].toString());
			try {

				// Read in file and split into array of strings
				fstream = new FileInputStream(listOfFiles[i]);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));

				line = br.readLine();
				while (line != null) {
					output += line;
					output += ",";
					line = br.readLine();
				}

				splitCSVarray = output.split(",");
				//copy array and cut out header info ie "symbol, date,high etc"
				splitCSVarray = Arrays.copyOfRange(splitCSVarray, 7, splitCSVarray.length);
				output = "";
				br.close();

			} catch (FileNotFoundException ex) {

				System.out.println("File not found");

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					fstream.close();

				} catch (IOException ex) {

					System.out.println("I/O problem");
				}

			}
			int dataColumnNumber = 0;
			int countTotal = 0;
			for (int j = 0; j < splitCSVarray.length; j++) {

				// make array for each individual stock to pass to populate(),
				// change to stock Object?
				arrayOfIndividualStocks[dataColumnNumber] = splitCSVarray[j];
				dataColumnNumber++;

				// every 7 columns is 1 complete stock row, write to DB
				if (dataColumnNumber == 7) {
					populate(arrayOfIndividualStocks, connection);
					dataColumnNumber = 0;
					countTotal++;
				}

			}
			System.out.println(countTotal);
						// Pass each CSV file to be written to database
			//writeToDB(splitCSVarray);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Takes array of strings representing a row of stock data and writes to
	 * appropriate database table. Database table determined using global
	 * exchange variable obtained in readInCSVFiles(). Calls parseDate() to
	 * convert string to dateTime.
	 * 
	 * @param stockSplitIntoArray
	 *            7 element array of strings representing one row of stock data.
	 * @param connection
	 *            JDBC connection.
	 */
	public void populate(String[] stockSplitIntoArray, Connection connection) {

		PreparedStatement preparedStatement = null;
		//System.out.println(exchange);

		// Checks Global exchange variable thats obtained in readInCSVFiles()
		// for the correct database table to write to
		/*
		 * if (exchange.equalsIgnoreCase("nasdaq")) { for (int i = 0; i <
		 * stockSplitIntoArray.length; i++) {
		 * System.out.println(stockSplitIntoArray[i]); } }
		 */
		try {

			preparedStatement = connection.prepareStatement("insert into " + exchange + " values(?,?,?,?,?,?,?)");
			preparedStatement.setString(1, stockSplitIntoArray[0]);
			preparedStatement.setDate(2, parseDate(stockSplitIntoArray[1],"dd-MMM-yy"));
			preparedStatement.setDouble(3, Double.parseDouble(stockSplitIntoArray[2]));
			preparedStatement.setDouble(4, Double.parseDouble(stockSplitIntoArray[3]));
			preparedStatement.setDouble(5, Double.parseDouble(stockSplitIntoArray[4]));
			preparedStatement.setDouble(6, Double.parseDouble(stockSplitIntoArray[5]));
			preparedStatement.setInt(7, Integer.parseInt(stockSplitIntoArray[6]));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}

	/**
	 * Opens and closes connection to database. Passes String array of
	 * individual stock data to populate method to write data to DataBase.
	 * 
	 * @param arrayOfallStocks
	 *            String array of stock data.
	 */
	/*public void writeToDB(String[] arrayOfallStocks) {

		// dataColumnNumber used to keep track of how many rows of individual
		// data have been read
		int dataColumnNumber = 0;
		int countTotal = 0;

		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		String[] arrayOfIndividualStocks = new String[7];

		
		for (int i = 0; i < arrayOfallStocks.length; i++) {

			// make array for each individual stock to pass to populate(),
			// change to stock Object?
			arrayOfIndividualStocks[dataColumnNumber] = arrayOfallStocks[i];
			dataColumnNumber++;

			// every 7 columns is 1 complete stock row, write to DB
			if (dataColumnNumber == 7) {
				populate(arrayOfIndividualStocks, connection);
				dataColumnNumber = 0;
				countTotal++;
			}

		}
		System.out.println(countTotal);
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

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
		//System.out.println(parsed.getClass());
		return new java.sql.Date(parsed.getTime());
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

	public Date getDate(String stringContainingDate) {
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
	}

}
