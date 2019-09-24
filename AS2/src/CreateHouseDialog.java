import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class CreateHouseDialog extends JFrame {

	/* Code goes here */
	private ArrayList<House> houseList;
	public JPanel toppanel=new JPanel();
	public JPanel buttonpanel=new JPanel();
	
	public JLabel houseImageName=new JLabel("Photograph File Name: ");
	private JTextField houseImageNameField=new JTextField(20);
	public JLabel addressLabel1=new JLabel("Address Line 1: ");
	private JTextField addressField1=new JTextField(20);
	public JLabel addressLabel2=new JLabel("Address Line 2: ");
	private JTextField addressField2=new JTextField(20);
	public JLabel bedroomsLabel=new JLabel("Number of bedrooms: ");
	private JTextField bedroomsField=new JTextField(20);
	public JLabel bathroomsLabel=new JLabel("Number of bathrooms: ");
	private JTextField bathroomsField=new JTextField(20);
	public JLabel priceLabel=new JLabel("Price: ");
	private JTextField priceField=new JTextField(20);
	public JLabel contactNumberLabel=new JLabel("Contact Number: ");
	private JTextField contactNumberField=new JTextField(20);
	public JButton addButton = new JButton("Add");
	private JButton cancelButton = new JButton("Cancel");
	
	public CreateHouseDialog(ArrayList<House> houseList)
	{
		
		this.houseList=houseList;
		toppanel.setLayout(new MigLayout());
		toppanel.add(houseImageName,"pushx,growx,span 10");
		toppanel.add(houseImageNameField,"pushx,growx,wrap");
		toppanel.add(addressLabel1,"pushx,growx,span 10");
		toppanel.add(addressField1,"pushx,growx,wrap");
		toppanel.add(addressLabel2,"pushx,growx,span 10");
		toppanel.add(addressField2,"pushx,growx,wrap");
		toppanel.add(bedroomsLabel,"pushx,growx,span 10");
		toppanel.add(bedroomsField,"pushx,growx,wrap");
		toppanel.add(bathroomsLabel,"pushx,growx,span 10");
		toppanel.add(bathroomsField,"pushx,growx,wrap");
		toppanel.add(priceLabel,"pushx,growx,span 10");
		toppanel.add(priceField,"pushx,growx,wrap");
		toppanel.add(contactNumberLabel,"pushx,growx,span 10");
		toppanel.add(contactNumberField,"pushx,growx,wrap");
	
		buttonpanel.add(addButton);
		addButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//check all fields are not null
			//check bedroom and bathroom are integers and price is a double
			if(houseImageNameField.getText()==null||addressField1.getText()==null||addressField2.getText()==null||
					isStringInt(bedroomsField.getText())==false||isStringInt(bathroomsField.getText())==false||
					isStringDouble(priceField.getText())==false||contactNumberField.getText()==null)
			{
				JOptionPane.showMessageDialog(toppanel, "You have entered invalid details for the new house");
			}
			else
			{
				// House(String street, String city, int bedrooms, int bathrooms, double price, String imageLocation, String contactNo)
				String address1=addressField1.getText();
				String address2=addressField2.getText();
				int bedrooms=Integer.parseInt(bedroomsField.getText());
				int bathrooms=Integer.parseInt(bathroomsField.getText());
				double price=Double.parseDouble(priceField.getText());
				String imageLocation=houseImageNameField.getText();
				String contactNumber=contactNumberField.getText();
				House newHouse=new House(address1,address2,bedrooms,bathrooms,price,imageLocation,contactNumber);
				houseList.add(newHouse);
				setVisible(false);
			}
		}
		});
	
		buttonpanel.add(cancelButton);

	
	
		setLayout(new BorderLayout());
	
		add(toppanel,BorderLayout.NORTH);
		add(buttonpanel,BorderLayout.SOUTH);
	
		setTitle("Add House Details");
		setSize(400, 260);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	
	}
	
	public boolean isStringInt(String s)
	{
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
	
	public boolean isStringDouble(String s)
	{
	    try
	    {
	        Double.parseDouble(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
	
}
