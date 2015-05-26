package data.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import data.controller.DataAppController;
import data.controller.DataController;
import data.view.DataPanel;


	/*
	 * the frame of the gui
	 */
	public class DataFrame extends JFrame
	{
		private DataPanel basePane;
		private DataAppController baseController;
		
		/*
		 * sates the base pane and the panel
		 */
		public DataFrame(DataAppController baseController)
		{
			this.baseController = baseController;
			basePane = new DataPanel(baseController);
			setupFrame();
			setupListeners();
			
		}
		
		/*
		 * setups the frame of the gui
		 */
		private void setupFrame()
		{
			this.setContentPane(basePane);
			this.setSize(1000,1000);
			this.setVisible(true);
		}
		
		private void setupListeners()
		{
			this.addWindowListener(new WindowListener()
			{

				public void windowActivated(WindowEvent arg0)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowClosed(WindowEvent arg0)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowClosing(WindowEvent arg0)
				{
					baseController.saveTimingInformation();
					
				}

				public void windowDeactivated(WindowEvent arg0)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowDeiconified(WindowEvent arg0)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowIconified(WindowEvent arg0)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowOpened(WindowEvent arg0)
				{
					// TODO Auto-generated method stub
					
				}
				
			});
		}
	}
