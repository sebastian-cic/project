package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import stock.Stock;

public class DatabaseCalls
{
	public ObservableList<Stock> getTable(String date)
	{
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		ObservableList<Stock> list = FXCollections.observableArrayList();
		{
		}
		;
		String query = "select * from AMEX where date ='" + date + "'";

		try
		{
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				Stock stock = new Stock();
				stock.setSymbol(resultSet.getString(1));
				stock.setDate(resultSet.getDate(2).toString());
				stock.setOpen(String.valueOf(resultSet.getDouble(3)));
				stock.setHigh(String.valueOf(resultSet.getDouble(4)));
				stock.setLow(String.valueOf(resultSet.getDouble(5)));
				stock.setClose(String.valueOf(resultSet.getDouble(6)));
				stock.setVolume(String.valueOf(resultSet.getInt(7)));
				list.add(stock);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return list;

	}

	public String getNewestDate()
	{
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		String query = "SELECT * FROM AMEX ORDER  BY date DESC LIMIT  1";
		java.sql.Statement statement;
		String result = "";
		try
		{
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				result = resultSet.getDate(2).toString();
			}
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public ObservableList<String> getAllDates()
	{
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		ObservableList<String> list = FXCollections.observableArrayList();

		String query = "SELECT DISTINCT date FROM AMEX ORDER  BY date DESC";
		java.sql.Statement statement;
		try
		{
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				list.add(resultSet.getDate(1).toString());
			}
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i));
		}
		return list;
	}

	public ObservableList<Stock> getStocksByDateAndExchange(String exchange, String date)
	{
		JDBCConnection jdbcConnection = new JDBCConnection();
		Connection connection = jdbcConnection.connectToDataBase();
		ObservableList<Stock> list = FXCollections.observableArrayList();
		String query = "select * from " + exchange + " where date ='" + date + "'";

		try
		{
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				Stock stock = new Stock();
				stock.setSymbol(resultSet.getString(1));
				stock.setDate(resultSet.getDate(2).toString());
				stock.setOpen(String.valueOf(resultSet.getDouble(3)));
				stock.setHigh(String.valueOf(resultSet.getDouble(4)));
				stock.setLow(String.valueOf(resultSet.getDouble(5)));
				stock.setClose(String.valueOf(resultSet.getDouble(6)));
				stock.setVolume(String.valueOf(resultSet.getInt(7)));
				list.add(stock);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}
}