package data.controller;

import java.util.ArrayList;
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
			data = new DataController(this, data);
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

		public void saveQueryTimingInfo()
		{
			// TODO Auto-generated method stub
			
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
			// TODO Auto-generated method stub
			
		}
	}
