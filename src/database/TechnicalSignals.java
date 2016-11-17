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
	public void simpleMovingAverage(Integer days,String exchange, String date, String symbol)
	{
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		String query2 = "select round(avg(close),4) from " + exchange +  " where date <='" + date +"' AND symbol ='"+ symbol+"' ORDER BY date DESC LIMIT "+ days ;		
		try
		{
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query2);
			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1));
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
