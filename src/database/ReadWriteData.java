package database;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReadWriteData {

	public ReadWriteData() {
	}

	private String exchange = "";
	
	public void readInCSVFile() {

		String output = "";
		String line = "";

		String[] splitCSVarray = new String[100000];
		FileInputStream fstream = null;
		
		File[] listOfFiles = getAllFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			exchange = getExchange(listOfFiles[i].toString());
			System.out.println();
			try {

				// File inFile = new File(path);
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
			//System.out.println(exchange);
			writeToDB(splitCSVarray);
		}

	}

	public void populate(String[] stockSplitIntoArray, Connection connection) {

		PreparedStatement preparedStatement = null;
		System.out.println(exchange);
		if(exchange.equalsIgnoreCase("nasdaq")){
			for (int i = 0; i < stockSplitIntoArray.length; i++) {
				System.out.println(stockSplitIntoArray[i]);
			}
		}
		try {

			preparedStatement = connection.prepareStatement("insert into " + exchange + " values(?,?,?,?,?,?,?)");
			preparedStatement.setString(1, stockSplitIntoArray[0]);
			preparedStatement.setDate(2, parseDate(stockSplitIntoArray[1]));
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

	public void writeToDB(String[] arrayOfallStocks) {

		int dataColumnNumber = 0;
		int countTotal = 0;
	
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		String[] arrayOfIndividualStocks = new String[7];

		for (int i = 7; i < arrayOfallStocks.length; i++) {

			arrayOfIndividualStocks[dataColumnNumber] = arrayOfallStocks[i];
			dataColumnNumber++;

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
	}

	public Date parseDate(String date) {

		java.util.Date parsed = null;

		try {

			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
			parsed = format.parse(date);

		} catch (ParseException pe) {

			pe.printStackTrace();
		}

		return new java.sql.Date(parsed.getTime());
	}

	public File[] getAllFiles() {
		
		File file = new File("C:/Users/seb/Downloads/stocks");
		return file.listFiles();
	}
	
	public String getExchange(String nameOfExchange){
		
		return nameOfExchange.substring(nameOfExchange.lastIndexOf('\\') + 1, nameOfExchange.lastIndexOf('_'));
		
	}

	/*public static void main(String[] args) {
		ReadWriteData readWriteData = new ReadWriteData();
		readWriteData.getAllFiles();
	}*/

}
