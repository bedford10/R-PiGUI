import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class RPiGUIDriver extends JFrame implements WindowListener
{
	private JPanel rpiGrid = new JPanel();
	private JLabel[] rpis = new JLabel[16];
	private Layer[] layers = new Layer[2];
	private Icon green = new ImageIcon("images/green.png");
	private Icon yellow = new ImageIcon("images/yellow.png");
	private Icon red = new ImageIcon("images/red.png");
	private static final String[] layerNames = {"Layer 1", "Layer 2"};
	private JComboBox layerChoice;
	private JTextArea layerInfo;
	
	public static void main(String[] args) 
	{
		RPiGUIDriver myWindow = new RPiGUIDriver("R-Pi Manager");
		myWindow.setSize(800, 600);
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myWindow.setVisible(true);
	}

	public RPiGUIDriver(String title)
	{
		super(title);
		for(int i = 0; i < 2; i++)
		{
			layers[i] = new Layer(i);
		}		
		initLayerChoice();
		initLayerInfo();
		
		rpiGrid.setLayout(new GridLayout(4, 4, 10, 10));
		
		
		for(int i = 0; i < 16; i++)
		{
			rpis[i] = new JLabel(green);
			rpis[i].setToolTipText("Temperature in Celcius: " + Integer.toString(layers[0].getNode(i).getNodeTemp()));
			rpiGrid.add(rpis[i]);
		}
		setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(rpiGrid);
		this.add(layerChoice);
		this.add(layerInfo);
		addWindowListener(this);
	}
	
	private void initLayerInfo() 
	{
		String layerNumber = "You are viewing layer number: " + (layers[0].getLayerNumber()+1) + "\n\r";
		String averageTemp = "Average temp: " + Double.toString(layers[0].getAverageTemp()) + "\n\r";
		String redCount = "There are " + layers[0].getOfflineNodeCount() + " nodes offline.\n\r";
		layerInfo = new JTextArea(layerNumber + averageTemp + redCount);
		layerInfo.setBounds(0,0,500,600);
		layerInfo.setEditable(false);
	}

	private void initLayerChoice()
	{
		layerChoice = new JComboBox(layerNames);
		layerChoice.setMaximumRowCount(2);
		layerChoice.addItemListener(
				new ItemListener()
				{
					public void itemStateChanged(ItemEvent event) {
						if(event.getStateChange() == ItemEvent.SELECTED)
						{
							int[] temps = new int[16];
							for(int i = 0; i < 16; i++)
							{
								temps[i] = layers[layerChoice.getSelectedIndex()].getNode(i).getNodeTemp();
								if(temps[i] == 999)
								{
									rpis[i].setIcon(red);
								}
								else if(temps[i] > 61)
								{
									rpis[i].setIcon(yellow);
								}
								else
								{
									rpis[i].setIcon(green);
								}
							}
							updateLayerInfo(layerChoice.getSelectedIndex());
						}
					}
				}
		);
		return;
	}
	
	private void updateLayerInfo(int choice)
	{
		double newAvg = layers[choice].getAverageTemp();
		String layerNumber = "You are viewing layer number: " + (layers[choice].getLayerNumber()+1) + "\n\r";
		String averageTemp = "Average temp: " + layers[choice].getAverageTemp() + "\n\r";
		String redCount = "There are " + layers[choice].getOfflineNodeCount() + " nodes offline.\n\r";
		layerInfo.setText(layerNumber + averageTemp + redCount);
	}
	
	public void windowActivated(WindowEvent arg0) {	}
	public void windowClosed(WindowEvent arg0) {}
	
	public void windowClosing(WindowEvent arg0) {
		dispose();
		System.exit(0);
	}
	
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

}
