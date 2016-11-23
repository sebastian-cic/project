package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TechnicalSignals
{
	/**
	 * 
	 * @param days
	 * @param exchange
	 * @param date
	 * @param symbol
	 */
	public void simpleMovingAverage(Integer days, String exchange, String date, String symbol)
	{
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		String query = "select round(avg(close),4) from " + exchange + " where date <='" + date + "' AND symbol ='"
				+ symbol + "' ORDER BY date DESC LIMIT " + days;
		String query2 = "select count(*) from " + exchange + " where date <='" + date + "' AND symbol ='" + symbol
				+ "' ORDER BY date DESC";
		try
		{
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			java.sql.Statement statement2 = connection.createStatement();
			ResultSet resultSet2 = statement2.executeQuery(query2);

			while (resultSet2.next())
			{
				if(Integer.parseInt(resultSet2.getString(1)) < days){
					System.out.println("Not enough dates to get this Moving Average");
					break;
				}
				while (resultSet.next())
				{
					System.out.println(resultSet.getString(1));
				}
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void getMACrossover(String symbol, String exchange)
	{

		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		String query = "select * from " + exchange + " where symbol = '" + symbol + "' ORDER BY date DESC";
		try
		{
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				simpleMovingAverage(10, "amex", resultSet.getString(2), "aau");
				System.out.println(resultSet.getString(2));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
