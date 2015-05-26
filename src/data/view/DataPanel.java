package data.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
	private JButton dataBtn;
	private JPasswordField samplePassword;
	
	/**
	 * sets the baseController
	 * setups up panel, layout and listeners
	 */
	public DataPanel(DataAppController baseController)
	{
		this.baseController = baseController;
		appButton = new JButton("Test the query");
		displayArea = new JTextArea(10, 30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		dataBtn = new JButton("Test the query");
		samplePassword = new JPasswordField(null, 20);
		
		
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
	 * sets up the panel of the GUI
	 */
	private void setupPanel()
	{
		this.setBackground(Color.MAGENTA);
		this.setLayout(baseLayout);
		this.add(appButton);
		this.setSize(200, 200);
		this.add(displayPane);
		this.add(dataBtn);
		this.add(displayArea);
		this.add(samplePassword);
		
		samplePassword.setEchoChar('*');
		samplePassword.setFont(new Font("Serif", Font.BOLD, 32));
		samplePassword.setForeground(Color.BLACK);
	}
	
	private void setupLayout() {
		baseLayout.putConstraint(SpringLayout.NORTH, displayArea, 16, SpringLayout.SOUTH, dataBtn);
		baseLayout.putConstraint(SpringLayout.WEST, displayArea, 40, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, dataBtn, 50, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, dataBtn, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, appButton, 50, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, appButton, 120, SpringLayout.WEST, this);
	}
	
	/**
	 * Listeners for buttons
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