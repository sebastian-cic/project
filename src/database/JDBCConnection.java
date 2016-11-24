package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection
{

	private String url = "jdbc:mysql://localhost:3306/stocks?useSSL=false";
	private String userName = "root";
	private String password = "root";

	public JDBCConnection()
	{

	}

	public JDBCConnection(String url, String userName, String password)
	{
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	public Connection connectToDataBase()
	{

		Connection connection = null;

		try
		{

			connection = DriverManager.getConnection(url, userName, password);

		} catch (SQLException e)
		{

			throw new IllegalStateException("Cannot connect the database!", e);
		}

		return connection;
	}

}
