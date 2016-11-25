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

	public String getCross(String exchange, String date, String symbol){

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
				double yesteMA = Double.parseDouble(this.simpleMovingAverage(10, exchange, resultSet.getString(2), symbol));
				double todayClose = resultSet2.getDouble(6);
				double yestClose = resultSet.getDouble(6);
				
				if(todayClose > todaysMa && yestClose < yesteMA){
					return "cross crossup";
				}else if (todayClose < todaysMa && yestClose > yesteMA) {
					return "cross down";
				}else {
					return "";
				}
				/*System.out.println(todaysMa + "current ma");
				System.out.println(resultSet2.getString(2) +"current day");
				System.out.println(resultSet2.getString(6) + "current close" + todayClose);
				System.out.println();
				System.out.println(resultSet.getString(6) + "yesterdays close" + yestClose);
				System.out.println(resultSet.getString(2) + "yesterdays date");
				System.out.println(this.simpleMovingAverage(10, exchange, resultSet.getString(2), symbol) + "yest ma" + yesteMA);
				System.out.println("**********");*/
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} catch (NumberFormatException e)
		{
			//e.printStackTrace();
		}finally
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
/*
	public void getMACrossover(Integer days, String exchange, String date, String symbol)
	{
		int count = 0;
		double movingAVG = 0;
		double previousPrice;
		boolean above = false;
		boolean below = false;

		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();

		String query = "select * from " + exchange + " where symbol = '" + symbol + "' ORDER BY date ASC";

		try
		{
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				count++;
				if (count >= days)
				{
					try
					{
						movingAVG = Double.parseDouble(simpleMovingAverage(10, "amex", resultSet.getString(2), "aau"));
					} catch (NumberFormatException e)
					{
						System.out.println("not enough days");
					}
					if (count == days && movingAVG >= Double.parseDouble(resultSet.getString(6)))
					{
						above = true;
					} else if (count == days && movingAVG < Double.parseDouble(resultSet.getString(6)))
					{
						below = true;
					}
					if (movingAVG < Double.parseDouble(resultSet.getString(6)) && above)
					{
						System.out.println("above cross over");
						above = false;
						below = true;
					} else if (movingAVG > Double.parseDouble(resultSet.getString(6)) && below)
					{
						System.out.println("below cross over");
						above = true;
						below = false;
					} else
					{
						System.out.println("equal/ no cross over");
					}
					System.out.println("MA:" + simpleMovingAverage(10, "amex", resultSet.getString(2), "aau"));
					System.out.println(resultSet.getString(6) + "******");
					System.out.println(resultSet.getString(2));
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
	}
}
*/