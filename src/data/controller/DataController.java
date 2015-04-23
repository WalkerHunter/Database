package data.controller;

/*
 * @Author Walker Hunter 
 */

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import data.model.Data;
import data.view.DataFrame;

public class DataController
{
	private String connectionString;
	private Connection dataConnection;
	private DataController baseController;

	public DataController(DataAppController basecontroller)
	{
		connectionString = "jdbc:mysql://127.0.0.1/?user=root";
		this.baseController = baseController;
		checkDriver();
		setupConnection();
		
	}
	
	public void connectionStringBuilder(String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" + userName;
		connectionString += "&password=" + password;
	}
	
	
	/**
	 * Looks for the jbc
	 * Looks for errors and exits
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}
	
	/*
	 * closes connection
	 * displays any errors that occur
	 */
	public void closeConnection()
	{
		try
		{
			dataConnection.close();
		}
		catch (SQLException error)
		{
			displayErrors(error);
		}
	}
	
	/**
	 * setups the connection
	 * displays any errors that occur
	 */
	private void setupConnection()
	{
		try
		{
			dataConnection = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	
	/**
	 * displays errors
	 * @param currentException
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());
		
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException).getErrorCode());
		}
	}

	/*
	 * gets the appframe for the data base display
	 */
	
	public String displayTables()
	{
		String results = "";
		String query = "SHOW TABLES";
		
		try
		{
				Statement firstStatement = dataConnection.createStatement();
				ResultSet answer = firstStatement.executeQuery(query);
				while(answer.next())
				{
					results += answer.getString(1) + "\n";
				}
				answer.close();
				firstStatement.close();
				
		}
		catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return results;
	}
	
	/*
	 * A sample of the insert
	 * @return
	 */
	
	public int insertSample()
	{
		int rowsAffected = 0;
		String insertQuery = "INSERT INTO ''.'' () VALUES();";
		
		try
		{
			Statement insertStatement = dataConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(insertQuery);
			insertStatement.close();
			
		}
		catch(SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
			
		}
		
		return rowsAffected;
	}
	
	/*
	 * Describes tables
	 */
	public String describeTable()
	{
		String results = "";
		String query = "DESCRIBE kwikee_marts";
		
		try
		{
				Statement firstStatement = dataConnection.createStatement();
				ResultSet answer = firstStatement.executeQuery(query);
				while(answer.next())
				{
					results += answer.getString(1) + "\t" + answer.getString(2) + "\t" + answer.getString(3) + "\t" + answer.getString(4) + "\n";
				}
				answer.close();
				firstStatement.close();
				
		}
		catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return results;
	}
	
	public DataFrame getAppFrame()
	{
		return getAppFrame();
	}
	
	public int partition(int arr[], int left, int right)
	{
	      int i = left, j = right;
	      int tmp;
	      int pivot = arr[(left + right) / 2];
	     
	      while (i <= j) {
	            while (arr[i] < pivot)
	                  i++;
	            while (arr[j] > pivot)
	                  j--;
	            if (i <= j) {
	                  tmp = arr[i];
	                  arr[i] = arr[j];
	                  arr[j] = tmp;
	                  i++;
	                  j--;
	            }
	      };
	     
	      return i;
	}
	 
	public void quickSort(int arr[], int left, int right) {
	      int index = partition(arr, left, right);
	      if (left < index - 1)
	            quickSort(arr, left, index - 1);
	      if (index < right)
	            quickSort(arr, index, right);
	}
	
	public void choosePivot()
	{
		
	}
	

}
