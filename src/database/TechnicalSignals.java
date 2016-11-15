package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import stock.Stock;

public class TechnicalSignals
{
	public void simpleMovingAverage(Integer days,String exchange, String date)
	{
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		//ObservableList<Stock> list = FXCollections.observableArrayList();
		String query = "select date from " + exchange +  " where date <='" + date + "' ORDER BY date DESC LIMIT 5 ";		

		try
		{
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				System.out.println(resultSet.getString(1));
				/*Stock stock = new Stock();
				stock.setSymbol(resultSet.getString(1));
				stock.setDate(resultSet.getDate(2).toString());
				stock.setOpen(String.valueOf(resultSet.getDouble(3)));
				stock.setHigh(String.valueOf(resultSet.getDouble(4)));
				stock.setLow(String.valueOf(resultSet.getDouble(5)));
				stock.setClose(String.valueOf(resultSet.getDouble(6)));
				stock.setVolume(String.valueOf(resultSet.getInt(7)));
				list.add(stock);*/
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
