package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TechnicalSignals
{
	/**
	 * Method to get simple moving average, total closing prices for number of
	 * days divided by number of days.
	 * 
	 * @param days
	 *            to be averaged
	 * @param exchange
	 * @param date
	 *            to average to
	 * @param symbol
	 */
	public String simpleMovingAverage(Integer days, String exchange, String date, String symbol)
	{
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();

		String query = "select round(avg(close),2) from " + exchange + " where date <='" + date + "' AND symbol ='"
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
				if (Integer.parseInt(resultSet2.getString(1)) < days)
				{
					return "Not enough dates to get this Moving Average";
				}
				while (resultSet.next())
				{
					return resultSet.getString(1);

				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				connection.close();
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * Method that returns a string indicating if the closing price for the
	 * specified date crossed over or under the specified simple moving average.
	 * 
	 * @param exchange
	 * @param date
	 * @param symbol
	 * @return
	 */
	public String getCross(String exchange, String date, String symbol)
	{

		String queryPreviousDay = "select * from " + exchange + " where date <'" + date + "' AND symbol ='" + symbol
				+ "' ORDER BY date DESC LIMIT 1";

		String queryCurrentDay = "select * from " + exchange + " where date <='" + date + "' AND symbol ='" + symbol
				+ "' ORDER BY date DESC LIMIT 1";

		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		try
		{
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryPreviousDay);
			java.sql.Statement statement2 = connection.createStatement();
			ResultSet resultSet2 = statement2.executeQuery(queryCurrentDay);

			while (resultSet.next() && resultSet2.next())
			{
				double todaysMa = Double.parseDouble(this.simpleMovingAverage(10, exchange, date, symbol));
				double yesteMA = Double
						.parseDouble(this.simpleMovingAverage(10, exchange, resultSet.getString(2), symbol));
				double todayClose = resultSet2.getDouble(6);
				double yestClose = resultSet.getDouble(6);

				if (todayClose > todaysMa && yestClose <= yesteMA)
				{
					return "Crossed over";
				} else if (todayClose < todaysMa && yestClose >= yesteMA)
				{
					return "Crossed under";
				} else
				{
					return "";
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (NumberFormatException e)
		{
			// e.printStackTrace();
		} finally
		{
			try
			{
				connection.close();
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		}
		return "";
	}
}
