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
		 * gets appframe
		 */
		public DataFrame getAppFrame()
		{
			return appFrame;
		}

		public void saveTimingInformation()
		{
			try
			{
				File saveFile = new File("asdasda.save");
				PrintWriter writer = new PrintWriter(saveFile);
				if(saveFile.exists())
				{
					for (QueryInfo current : queryList)
					{
						writer.println(current.getQuery());
						writer.println(current.getQueryTime());
					}
					writer.close();
					JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + " QueryInfo objects were saved");
				}
				else
				{
					JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryINfo objects saved");
				}
			}
			catch(IOException currentError)
			{
				data.displayErrors(currentError);
			}
			
		}
		
		private void loadTimingInformation()
		{
			try
			{
				File loadFile = new File("asdasda.save");
				if(loadFile.exists())
				{
					queryList.clear();
					Scanner textScanner = new Scanner(loadFile);
					while(textScanner.hasNext())
					{
						String query = textScanner.nextLine();
						long queryTime = Long.parseLong(textScanner.nextLine());
						queryList.add(new QueryInfo(query, queryTime));
					}
					textScanner.close();
					JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + "QueryInfo obecjts were loaded");
					
				}
				else
				{
					JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects loaded");
					
				}
				
			}
			catch(IOException currentError)
			{
				data.displayErrors(currentError);
			}
		}
		
		public void saveText(String conversation, boolean appendToEnd)
		{
			String fileName = "/User/";
			
			PrintWriter outputWriter;
			
			if(appendToEnd)
			{
				try
				{
					outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, appendToEnd)));
					outputWriter.append(conversation);
					outputWriter.close();
				}
				catch(FileNotFoundException noExistingFile)
				{
					JOptionPane.showMessageDialog(appFrame, "There is no file here :(");
					JOptionPane.showMessageDialog(appFrame, noExistingFile.getMessage());
					
				}
				catch (IOException inputOutputError)
				{
					JOptionPane.showMessageDialog(appFrame, "There is no file here :(");
					JOptionPane.showMessageDialog(appFrame, inputOutputError.getMessage());
	
					
				}
			}
		}
		
		public DataController getDatabase()
		{
			return data;
		}
		
		public ArrayList<QueryInfo> getQueryList()
		{
			return queryList;
		}

		public void start()
		{
			
			
		}
	}
