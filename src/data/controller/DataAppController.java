package data.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import data.controller.DataController;
import data.model.Data;
import data.view.DataFrame;


	/*
	 * app controller
	 */
	public class DataAppController
	{
		private DataFrame appFrame;
		private DataController data;
		private ArrayList<Data> timingInfoList;
		/*
		 * states the data and appframe
		 */
		public DataAppController()
		{
			timingInfoList = new ArrayList<Data>();
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
		
		public void start()
		{
			loadTimingInfo();
		}
		
		public ArrayList<Data>getTimingInfoLIst()
		{
			return timingInfoList;
		}
		
		public void loadTimingInfo()
		{
			File saveFile = new File("save.txt");
			try
			{
				Scanner readFileScanner;
				if(saveFile.exists())
				{
					timingInfoList.clear();
					readFileScanner = new Scanner(saveFile);
					while(readFileScanner.hasNext())
					{
						String tempQuery = readFileScanner.next();
						long tempTime = readFileScanner.nextLong();
						readFileScanner.next();
						timingInfoList.add(new Data(tempQuery, tempTime));
					}
					
					readFileScanner.close();
				}
				
			}
			catch(IOException current)
			{
				this.getData().displayErrors(current);
			}
		}
		
		public void saveQueryTimeingInfo()
		{
			
		}
		
		
		/*
		 * gets appframe
		 */
		public DataFrame getAppFrame()
		{
			return appFrame;
		}

		public void saveQueryTimingInfo()
		{
			// TODO Auto-generated method stub
			
		}
	}
