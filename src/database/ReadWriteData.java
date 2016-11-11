package database;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import util.DateAndFilesParsing;

public class ReadWriteData
{

	private String exchange = "";

	/**
	 * Method to open CSV files and parse to array of strings containing the
	 * individual data.
	 */
	public void readInCSVFiles()
	{

		String output = "";
		String line = "";

		String[] splitCSVarray = new String[100000];
		String[] arrayOfIndividualStocks = new String[7];

		FileInputStream fstream = null;
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		DateAndFilesParsing parsing = new DateAndFilesParsing();

		// Get list of all CSV files from downloads directory.
		File[] listOfFiles = parsing.getAllFiles();

		for (int i = 0; i < listOfFiles.length; i++)
		{

			// Get specific stock exchange name for each CSV file, used to enter
			// into correct database table
			exchange = parsing.getExchange(listOfFiles[i].toString());

			try
			{

				// Read in file and split into array of strings
				fstream = new FileInputStream(listOfFiles[i]);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));

				line = br.readLine();
				while (line != null)
				{
					output += line;
					output += ",";
					line = br.readLine();
				}

				splitCSVarray = output.split(",");
				// copy array and cut out header info ie "symbol, date,high etc"
				splitCSVarray = Arrays.copyOfRange(splitCSVarray, 7, splitCSVarray.length);
				output = "";
				br.close();
				parsing.moveFile(listOfFiles[i]);
			} catch (FileNotFoundException ex)
			{

				System.out.println("File not found");

			} catch (IOException e)
			{

				e.printStackTrace();

			} finally
			{

				try
				{

					fstream.close();

				} catch (IOException ex)
				{

					System.out.println("I/O problem");
				}

			}

			int dataColumnNumber = 0;
			//int countTotal = 0;

			for (int j = 0; j < splitCSVarray.length; j++)
			{
				
				// make array for each individual stock to pass to populate(),
				// change to stock Object?
				arrayOfIndividualStocks[dataColumnNumber] = splitCSVarray[j];
				dataColumnNumber++;

				// every 7 columns is 1 complete stock row, write to DB
				if (dataColumnNumber == 7)
				{
				
					
					writeToDatabase(arrayOfIndividualStocks, connection);
					dataColumnNumber = 0;
					//countTotal++;
				}

			}
			
			//System.out.println(countTotal);
		}
		try
		{
			connection.close();
		} catch (SQLException e)
		{
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
	private void writeToDatabase(String[] stockSplitIntoArray, Connection connection)
	{

		DateAndFilesParsing parsing = new DateAndFilesParsing();
		PreparedStatement preparedStatement = null;

		// Checks Global exchange variable thats obtained in readInCSVFiles()
		// for the correct database table to write to
		try
		{

			preparedStatement = connection.prepareStatement("insert into " + exchange + " values(?,?,?,?,?,?,?)");
			preparedStatement.setString(1, stockSplitIntoArray[0]);
			preparedStatement.setDate(2, parsing.parseDate(stockSplitIntoArray[1], "dd-MMM-yy"));
			preparedStatement.setDouble(3, Double.parseDouble(stockSplitIntoArray[2]));
			preparedStatement.setDouble(4, Double.parseDouble(stockSplitIntoArray[3]));
			preparedStatement.setDouble(5, Double.parseDouble(stockSplitIntoArray[4]));
			preparedStatement.setDouble(6, Double.parseDouble(stockSplitIntoArray[5]));
			preparedStatement.setInt(7, Integer.parseInt(stockSplitIntoArray[6]));
			preparedStatement.executeUpdate();

		} catch (SQLException e)
		{

			e.printStackTrace();

		}
	}

}
