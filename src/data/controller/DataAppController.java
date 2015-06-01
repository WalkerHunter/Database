package data.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import data.controller.DataController;
import data.model.QueryInfo;
import data.view.DataFrame;


	/*
	 * app controller
	 */
	public class DataAppController
	{
		private DataFrame appFrame;
		private DataController data;
		private ArrayList<QueryInfo> queryList;
		/*
		 * states the data and appframe
		 */
		public DataAppController()
		{
			queryList = new ArrayList<QueryInfo>();
			data = new DataController(this);
			appFrame = new DataFrame(this);
		}

		/**
		 * gets the data controller
		 * @return
		 */
		public DataController getData()
		{
			return data;
			
		}
		
		
		
		/*
		 * gets appframe, GUI
		 */
		public DataFrame getAppFrame()
		{
			return appFrame;
		}

		public void saveTimingInformation() //gets the timing info
		{
			try
			{
				File saveFile = new File("asdasda.save"); //saves file in asdasda
				PrintWriter writer = new PrintWriter(saveFile);
				if(saveFile.exists()) //checks for files existance
				{
					for (QueryInfo current : queryList)  //checks query list
					{
						writer.println(current.getQuery()); //writes the current query
						writer.println(current.getQueryTime());// writes the time
					}
					writer.close(); //closes file
					JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + " QueryInfo objects were saved"); //tells how many were saved
				}
				else
				{
					JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryINfo objects saved"); //if not files are saved, presents this message
				}
			}
			catch(IOException currentError) //checks for errors
			{
				data.displayErrors(currentError); //displays errors
			}
			
		}
		
		private void loadTimingInformation() //loads any previous timings
		{
			try
			{
				File loadFile = new File("asdasda.save"); //tries asdasda file
				if(loadFile.exists()) //checks for the files existence
				{
					queryList.clear(); //clears list if exists
					Scanner textScanner = new Scanner(loadFile); //scans for text
					while(textScanner.hasNext()) //Continues through text
					{
						String query = textScanner.nextLine(); //scans next line
						long queryTime = Long.parseLong(textScanner.nextLine()); //gets time
						queryList.add(new QueryInfo(query, queryTime)); //adds new query time
					}
					textScanner.close(); //closes scanner
					JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + "QueryInfo obecjts were loaded"); //displays message
					
				}
				else
				{
					JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects loaded"); //displays message
					
				}
				
			}
			catch(IOException currentError) //checks for errors
			{
				data.displayErrors(currentError);// displays errors
			}
		}
		
		public void saveText(String conversation, boolean appendToEnd) //saves text to a file
		{
			String fileName = "/User/"; //finds users
			
			PrintWriter outputWriter; //prints the text
			
			if(appendToEnd) //checks what to append
			{
				try 
				{
					outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, appendToEnd))); //writer
					outputWriter.append(conversation); //appends
					outputWriter.close(); //closes
				}
				catch(FileNotFoundException noExistingFile) //checks for exceptions
				{
					JOptionPane.showMessageDialog(appFrame, "There is no file here :("); //displays message for no file found
					JOptionPane.showMessageDialog(appFrame, noExistingFile.getMessage()); //gets message
					
				}
				catch (IOException inputOutputError) //checks for errors
				{
					JOptionPane.showMessageDialog(appFrame, "There is no file here :("); //displays message for errors
					JOptionPane.showMessageDialog(appFrame, inputOutputError.getMessage()); //gets message
	
					
				}
			}
		}
		
		public DataController getDatabase() //gets data
		{
			return data;//returns data
		}
		
		public ArrayList<QueryInfo> getQueryList()//gets query list
		{
			return queryList;//returns query list
		}

		public void start()//start
		{
			
			
		}
	}
