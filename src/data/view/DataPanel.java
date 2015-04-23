package data.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import data.controller.DataAppController;
import data.controller.DataController;

public class DataPanel extends JPanel
{
	private DataAppController baseController;
	private JButton appButton;
	private JTextArea displayArea;
	private JScrollPane displayPane;
	private SpringLayout baseLayout;
	
	/**
	 * sets the basecontroller
	 * setups up panel, layout and listners
	 */
	public DataPanel(DataAppController baseController)
	{
		this.baseController = baseController;
		appButton = new JButton("Test the query");
		displayArea = new JTextArea(10, 30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		
		
		setupDisplayPane();
		setupPanel();
		setupLayout();
		setupListeners();
		
	}
	
	private void setupDisplayPane()
	{
		displayArea.setWrapStyleWord(true);
		displayArea.setLineWrap(true);
		displayArea.setEditable(false);
	}
	
	/**
	 * setsup the panel of the gui
	 */
	private void setupPanel()
	{
		this.setBackground(Color.MAGENTA);
		this.setLayout(baseLayout);
		this.add(appButton);
		this.add(displayPane);
	}
	
	private void setupLayout()
	{
		
	}
	
	/**
	 * Listners for buttons
	 */
	private void setupListeners()
	{
		appButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String dataAnswer = baseController.getData().displayTables();
				displayArea.setText(dataAnswer);
			}

		
		});
	}
}