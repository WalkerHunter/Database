package data.controller;

/*
 * @Author Walker Hunter 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.model.QueryInfo;
import data.view.DataFrame;

public class DataController
{
	private String connectionString;
	private Connection dataConnection;
	private DataAppController baseController;
	private String currentQuery;
	long startTime, endTime;

	/**
	 * Declares objects and loads object
	 * @param baseController 
	 * @param baseController 
	 */
	public DataController(DataAppController baseController)
	{
		this.connectionString = "jdbc:mysql://127.0.0.1/games?user=root";//gets
																			//the
																			//database
																			//address		
		this.baseController = baseController;
		checkDriver();
		setupConnection();
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
	}
	
	/**
	 * Change connection string so we are able to talk to other databases
	 * 
	 * @param pathToDBServer
	 *            path to the database server
	 * @param databaseName
	 *            name of the database
	 * @param userName
	 *            user name for the database server
	 * @param password
	 *            password for the database server
	 */
	public void connectionStringBuilder(String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";// wipes out connection string,
											// starts new
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" + userName;// "?" = end of path, sends it
												// to program at end of path
		connectionString += "&password=" + password;
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
	}
	
	public void submitQuery(String tableName)
	{

	}
	
	/**
	 * Looks for the jdbc
	 * Looks for errors and exits
	 * if not installed, returns error, shuts down
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException); //driver not loaded
			System.exit(1); //shut down
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
	}
	
	/**
	 * closes connection
	 * displays any errors that occur
	 */
	
	public void closeConnection() //closes connection
	{
		try
		{
			dataConnection.close(); //tries closing connection
		}
		catch (SQLException currentException)//checks for any errors
		{
			displayErrors(currentException);//displays errors
		}
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
	}
	
	/**
	 * setups the connection
	 * displays any errors that occur
	 */
	private void setupConnection() //sets up connection
	{
		try
		{
			dataConnection = DriverManager.getConnection(connectionString);//goes to driver
		}
		catch(SQLException currentException)//checks for errors
		{
			displayErrors(currentException);//displays errors
		}
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
	}

	
	/**
	 * displays errors
	 * @param currentException
	 */
	public void displayErrors(Exception currentException)//checks and displays errors
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());//message display
		if(currentException instanceof SQLException)//checks Exceptions
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException).getErrorCode());
		}
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
	}

	/**
	 * gets the appframe for the data base display
	 * gets errors if any
	 * @return results
	 */
	
	public String displayTables()//displays data tables
	{
		String results = "";
		String query = "SHOW TABLES";
		
		try
		{
				Statement firstStatement = dataConnection.createStatement(); //gets connections
				ResultSet answer = firstStatement.executeQuery(query); //executes the query
				while(answer.next())
				{
					results += answer.getString(1) + "\n"; //answer shrinks
				}
				answer.close();
				firstStatement.close();
				
		}
		catch (SQLException currentSQLError)//checks for errors
		{
			displayErrors(currentSQLError);//displays errors
		}
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
		return results;//returns the results
	}
	
	/*
	 * Describes the database tables and reports errors if any
	 * @return results
	 */
	
	public int insertSample()//sample insert
	{
		int rowsAffected = 0; //no rows affected
		String insertQuery = "INSERT INTO ''.'' () VALUES();";//what's being inserted and its value
		
		try
		{
			Statement insertStatement = dataConnection.createStatement(); //gets connection and creates statement
			rowsAffected = insertStatement.executeUpdate(insertQuery); //checks for updates and rows affected
			insertStatement.close();//closes insert statement
			
		}
		catch(SQLException currentSQLError)// checks for errors
		{
			displayErrors(currentSQLError);//displays errors
			
		}
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
		return rowsAffected; //returns rows affected
	}
	
	/**
	 * Checks if there is a structure violation
	 * 
	 * @return if there is a structure violation
	 */
	private boolean checkForStructureViolation() //checks for violations
	{
		if (currentQuery.toUpperCase().contains(" DATABASE ")) //searches anything with database in it
		{
			return true; //returns if true
		}
		else
		{
			return false;//returns if false
		}
		
	}
	
	/**
	 * Method for dropping tables/indices from a database. Throws exception if
	 * attempted
	 * 
	 * @throws SQLException
	 *             when the query contains a DROP DATABASE command.
	 */
	public void dropStatement()//execute drops statements
	{
		String results = "";
		try
		{
			if (checkForStructureViolation())//checks for structure violations
			{
				throw new SQLException("You dropped the database.", "Try again.", Integer.MIN_VALUE); //throws the exceptions
			}

			if (currentQuery.toUpperCase().contains("INDEX"))//searches for index text
			{
				results = "The index was: ";//return results of the search
			}
			else 
			{
				results = "The table was: ";//returns if index and database aren't searched
			}

			Statement dropStatement = dataConnection.createStatement();//drops from table
			int affected = dropStatement.executeUpdate(currentQuery);//updates table

			dropStatement.close();//clo0ses drop table

			if (affected == 0)//executes if nothing is affected
			{
				results += "dropped"; //Returns the dropped
			}

			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch (SQLException dropError)// checks for errors
		{
			displayErrors(dropError);//displays errors
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing

	}
	
	public String[] getDatabaseColumnNames(String tableName) //gets colomuns
	{
		String[] columns; //columns are strings
		currentQuery = "SELECT * FROM `" + tableName + "`";// gets from entered
		long startTime, endTime; // timing info
		startTime = System.currentTimeMillis(); //starts

		try
		{
			Statement firstStatement = dataConnection.createStatement();//starts connection
			ResultSet answers = firstStatement.executeQuery(currentQuery);//gets results and executes 
			ResultSetMetaData answerData = answers.getMetaData();//returns any answers

			columns = new String[answerData.getColumnCount()]; 

			for (int column = 0; column < answerData.getColumnCount(); column++)
			{
				columns[column] = answerData.getColumnName(column + 1);
			}

			answers.close();//closes answers
			firstStatement.close();//closes statement
			endTime = System.currentTimeMillis();//ends timing

		}
		catch (SQLException currentException)//checks for errors
		{
			endTime = System.currentTimeMillis();//ends timing
			columns = new String[] { "empty" };//displays if empty
			displayErrors(currentException);//displays errors
		}

		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
		return columns;//returns columns
	}
	public ArrayList<QueryInfo> getQueryList()//gets array list in queryList
	{
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
		return getQueryList();//returns queryList
	}
	

	public String[] getMetaData() //gets data
	{
		String[] columnInformation;//column info

		try
		{
			Statement firstStatement = dataConnection.createStatement();//gets connections
			ResultSet answer = firstStatement.executeQuery(currentQuery);//gets results
			ResultSetMetaData myMeta = answer.getMetaData();//gets data

			columnInformation = new String[myMeta.getColumnCount()];//gets column count

			for (int index = 0; index < myMeta.getColumnCount(); index++)//checks index
			{
				columnInformation[index] = myMeta.getColumnName(index + 1);//index amount
			}

			answer.close();//closes answers
			firstStatement.close();//closes statement

		}
		catch (SQLException currentException)//checks for errors
		{
			columnInformation = new String[] { "nada exists" };//doesn't exist
			displayErrors(currentException);//displays errors
		}
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
		return columnInformation;//returns column information
	}

	public String[][] realInfo()//gets info in strings
	{
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
		return null;//returns nothing
	}

	public String[][] tableInfo()//gets table info in strings
	{
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
		return null;//returns nothing
	}
	
	/**
	 * Describes the database tables and reports any errors
	 * @return results
	 */
	public String describeTable()//describes table
	{
		String results = "";//starts blank
		String query = "DESCRIBE kwikee_marts";//gets string
		
		try
		{
				Statement firstStatement = dataConnection.createStatement(); //gets connections
				ResultSet answer = firstStatement.executeQuery(query); //executes the query
				while(answer.next())
				{
					results += answer.getString(1) + "\t" + answer.getString(2) + "\t" + answer.getString(3) + "\t" + answer.getString(4) + "\n";
				}
				answer.close();
				firstStatement.close();
				
		}
		catch (SQLException currentSQLError)//checks for errors
		{
			displayErrors(currentSQLError);//displays errors
		}
		long queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
		
		return results;//returns results
	}
	
	public DataFrame getAppFrame()//gets appFrame
	{
		long queryTime = endTime - startTime;//timing
		baseController.getQueryList().add(new QueryInfo(currentQuery, queryTime));//Timing
		return getAppFrame();//returns appFrame
	}
	
	
	public void choosePivot()//idk
	{
		
	}
	
}
